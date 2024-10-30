package juego;

import java.awt.Color;

import entorno.Entorno;
import entorno.InterfaceJuego;
import java.util.Random;
import java.awt.Image;

public class Juego extends InterfaceJuego {
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	private Herramientas sonido; // Declaración de la clase Sonido
	private Islas[] islas;
	private Gnomos[] gnomo;
	private Tortuga[] tortugas;
	private Pep pep;
	private Casa casa;
	private Boolean puedeCaer = false;
	private Boolean saltoCooldown = false;
	private int timerSalto; // Temporizador para el cooldown del salto
	private Boolean estaSaltando = false;
	private double saltoMaxY;
	private Boolean puedeSaltar = true;
	private DisparoPep disparo;
	private Boolean derecha = false;
	private int timerGnomos = 0;
	private int gnomosVivos = 1;
	private int timerTortugas = 0;
	private int tiempoSpawnTortugas = 300;
	private Boolean estaCayendo = false;
	private DisparoPep disparoPep; // Disparo de Mario
	private int tortugasVivas = 4;
	double[] posicionesPermitidasX = new double[12];
	int indicePosiciones = 0;
	private boolean cooldownVidas;
	private int timerVidas = 0;
	private Image fondo;
	
	Juego() {
	    this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);
	    this.sonido = new Herramientas("recursos/musica_fondo.aiff"); 
	    this.sonido.loop(); // Inicia la música en loop
	    inicializarJuego();
	    this.entorno.iniciar();
	}


	private void inicializarJuego() {
		this.casa = new Casa(415, 85, 25, 30);
		this.pep = new Pep(entorno.ancho() / 2, entorno.alto() - 160, 25, 40, 3);
		tortugas = new Tortuga[4];
		gnomo = new Gnomos[12]; // Inicializa el array de gnomos

		double x = entorno.ancho() - 380; // Definimos la posición inicial en el eje X para todos los gnomos
		double y = entorno.alto() - 515; // Posición inicial en el eje Y

		// hice las islas aca asi no se crean en cada tick
		islas = new Islas[15]; // cantidad de islas
		double xIslas;
		double yIslas = entorno.alto() - 80;
		int[] cantidadIslasPorNivel = { 5, 4, 3, 2, 1 };
		double[] desplazamientoXPorNivel = { 160, 190, 220, 250, 0 };
		int cantIslas = 0;
		for (int nivel = 0; nivel < cantidadIslasPorNivel.length; nivel++) {
			xIslas = entorno.ancho() / 2 - ((cantidadIslasPorNivel[nivel] - 1) * desplazamientoXPorNivel[nivel]) / 2;
			for (int j = 0; j < cantidadIslasPorNivel[nivel]; j++) {
				islas[cantIslas] = new Islas(xIslas, yIslas - (nivel * 100), 120, 40); // aca crea la isla
				xIslas += desplazamientoXPorNivel[nivel]; // Desplaza la posición de la siguiente isla
				cantIslas++; // Incrementa el índice de las islas
			}

		}
		// otras variables
		gnomo[0] = new Gnomos(x, y, 10, 10, 1);
		gnomosVivos = 0;
		puedeCaer = false;
		saltoCooldown = false;
		estaSaltando = false;
		derecha = false;
		disparo = null;
		derecha = true;
		tortugasVivas = 0;
		cooldownVidas = false;
		timerVidas = 0;
		fondo = Herramientas.cargarImagen("recursos/fondo.jpg");
	}

	public void tick() {
		entorno.dibujarImagen(fondo, entorno.ancho()/2, entorno.alto()/2, 0, 1);
		
		if (pep.getVidas() <= 0) {
			pep.mostrarGameOver(entorno);
			return;
		}
		// Dibujar las islas
		for (int i = 0; i < islas.length; i++) {
			if (islas[i] != null) {
				islas[i].dibujarIslas(entorno);
			}
		}

		// Chequea si Pep está tocando una isla
		if (pep != null && pep.colisionaAbajoPep(islas)) {
			puedeCaer = false;
			puedeSaltar = true; // Puede saltar solo cuando está tocando una isla
		} else {
			puedeCaer = true;
		}

		// Gravedad de Pep
		if (pep != null && puedeCaer && !estaSaltando) {
			pep.setY(pep.getY() + 4); // Aplica gravedad
		}

		// Movimiento lateral
		if (entorno.estaPresionada(entorno.TECLA_DERECHA)) {
			pep.moverDerecha(entorno);
			derecha = true;
			pep.setDireccion(1); // Actualiza la dirección del personaje (para disparo)
		} else if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
			pep.setDireccion(-1);
			derecha = false;
			pep.moverIzquierda();
		} else {
			pep.setDireccion(0); // Establecer dirección a 0 cuando este parado
		}

		// Disparo
		if (pep != null && entorno.sePresiono(entorno.TECLA_ESPACIO)) {
			if (disparo == null) {
				disparo = new DisparoPep(pep.getX(), pep.getY(), 10, 10, this.derecha);
			}
		}
		if (disparo != null) {
			disparo.dibujarDisparo(entorno);
			disparo.mover();
			if (disparo.seFue(entorno)) {
				disparo = null;
			}
		}

		// Salto
		if (entorno.sePresiono(entorno.TECLA_ARRIBA) && puedeSaltar && !saltoCooldown) {
			estaSaltando = true;
			puedeSaltar = false;
			saltoMaxY = pep.getY() - 100; // Altura máxima del salto
		}

		if (estaSaltando && pep.getY() > saltoMaxY) {
			pep.saltar(); // Salta hacia arriba
			puedeCaer = false; // No cae mientras salta
		}
		if (estaSaltando && pep.getY() <= saltoMaxY) {
			estaSaltando = false;
			puedeCaer = true;
			saltoCooldown = true;
		}

		if (saltoCooldown) {
			timerSalto++;
			if (timerSalto >= 27) { // Tiempo de cooldown para el salto
				saltoCooldown = false;
				timerSalto = 0;
			}
		}
		pep.dibujarPep(entorno);
		casa.dibujarCasa(entorno);

		// Mostrar puntaje y vidas de Pep
		if (pep != null) {
			pep.mostrarKills(entorno);
			pep.vidas(entorno);
			pep.mostrarRescatados(entorno);
			// Colisión con las tortugas y reducción de vida
			if (pep.colisionaTortuga(tortugas)) {
				if (!cooldownVidas) {
					pep.setVidas(pep.getVidas() - 1); // Reduce una vida
					pep.setX(entorno.ancho() / 2);
					pep.setY(entorno.alto() - 160);
					cooldownVidas = true;
					timerVidas = 0;
				}
				if (pep.getVidas() <= 0) {
					pep = null; // Si pierde todas las vidas, se elimina a Pep
				}
			}
		}
		if (pep != null && pep.getY() > entorno.alto()) {
			pep.setVidas(pep.getVidas() - 1); // Reduce una vida
			if (pep.getVidas() > 0) {
				pep.setX(entorno.ancho() / 2);
				pep.setY(entorno.alto() - 160);
			}
		}

		if (cooldownVidas) {
			timerVidas++;
			if (timerVidas >= 100) { // Ajusta el tiempo de cooldown si es necesario
				cooldownVidas = false;
			}
		}

		if (pep.colisionaGnomo(gnomo) != null) { // Si hay un gnomo rescatado
		    pep.incrementarRescates(); // Incrementa los rescates
		    // Borra el gnomo rescatado de la lista
		    for (int i = 0; i < gnomo.length; i++) {
		        if (gnomo[i] == pep.colisionaGnomo(gnomo)) {
		            gnomo[i] = null; // Borra el gnomo rescatado
		            return; // Sale del bucle una vez que lo ha encontrado
		        }
		    }
		}


		// Spawneo de gnomos
		timerGnomos++;
		if (timerGnomos >= 400 && gnomosVivos < 12) {
			for (int i = 0; i < gnomo.length; i++) {
				if (gnomo[i] == null) {
					double x = entorno.ancho() - 380;
					double y = entorno.alto() - 505;
					gnomo[i] = new Gnomos(x, y, 10, 10,1);
					gnomosVivos++;
					timerGnomos = 0;
					System.out.println("Gnomo creado en posición " + i);
					return;
				}
			}
		}

		// Movimiento de gnomos
		for (int j = 0; j < gnomo.length; j++) {
			if (gnomo[j] != null) {
				gnomo[j].dibujarGnomos(entorno);
				gnomo[j].mover(entorno);
				if (!gnomo[j].colisionaAbajoGnomo(islas)) {
					gnomo[j].caer();
				}
				if (gnomo[j].colisionaAbajoGnomo(islas)) {
					gnomo[j].cambioDireccion();
				}
			}
		}

		// Spawneo de tortugas
		timerTortugas++;
		if (timerTortugas >= tiempoSpawnTortugas && tortugasVivas < tortugas.length) {
			double[] posicionesIslasX = new double[islas.length];
			int contador = 0;

			for (Islas isla : islas) {
				if (isla != null) {
					posicionesIslasX[contador++] = isla.getX();
				}
			}

			if (contador > 0) {
				int indiceAleatorio = (int) (Math.random() * contador);
				double xAleatorio = posicionesIslasX[indiceAleatorio];
				double yInicial = entorno.alto() - 400;

				if (Math.abs(xAleatorio - casa.getX()) > 50) {
					for (int i = 0; i < tortugas.length; i++) {
						if (tortugas[i] == null) {
							tortugas[i] = new Tortuga(xAleatorio, yInicial, 30, 30, 0.5);
							System.out.println("Tortuga creada en posición X: " + xAleatorio + ", Y: " + yInicial);
							tortugasVivas++;
							timerTortugas = 0;
							return;
						}
					}
				}
			}
		}

		for (int j = 0; j < tortugas.length; j++) {
		    if (tortugas[j] != null) {
		        tortugas[j].dibujarTortugas(entorno);

		        // Mueve la tortuga si colisiona con una isla, de lo contrario cae
		        if (tortugas[j].colisionaAbajoTortuga(islas)) {
		            tortugas[j].mover(islas);
		        } else {
		            tortugas[j].caer();
		        }

		        // Comprobar colisión con el disparo
		        if (disparo != null && tortugas[j].colisionaDisparoPep(disparo)) {
		            tortugasVivas--;
		            tortugas[j] = null; // Elimina la tortuga que fue impactada
		            disparo = null; // Elimina el disparo
		            if (pep != null) {
		                pep.incrementarKills();
		            }
		        }

		        // Comprobar colisión con gnomos
		        Gnomos gnomoColisionado = tortugas[j].colisionaConGnomo(gnomo);
		        if (gnomoColisionado != null) {
		            // Elimina el gnomo que fue impactado por la tortuga
		            for (int k = 0; k < gnomo.length; k++) {
		                if (gnomo[k] == gnomoColisionado) {
		                    gnomo[k] = null; // Elimina el gnomo que colisionó
		                    break; // Sale del bucle una vez que se elimina el gnomo
		                }
		            }
		        }
		    }
		}


		}


	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}
