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
	private Boolean saltoCooldown= false;
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
	private Image pepDer;
	private Image calavera;
    
	Juego() {
		this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);
        this.sonido = new Herramientas("recursos/musica_fondo.aiff"); // Inicializa la clase Sonido con la ruta del archivo
        this.sonido.loop(); // Inicia la música en loop
        inicializarJuego();
        this.entorno.iniciar();
    }
	
	private void inicializarJuego() {
		this.casa = new Casa(415, 85, 25, 30);
		this.pep = new Pep(entorno.ancho() / 2, entorno.alto() - 160, 25, 40, 3);
		tortugas = new Tortuga[4];
	    gnomo = new Gnomos[6];     // Inicializa el array de gnomos
	   
   
	    double x = entorno.ancho() - 380;  // Definimos la posición inicial en el eje X para todos los gnomos
	    double y = entorno.alto() - 515 ;    // Posición inicial en el eje Y
	
	    
	    //hice las islas aca asi no se crean en cada tick
	    islas = new Islas[15]; // cantidad de islas
	    double xIslas;
	    double yIslas = entorno.alto() - 80;
	    int[] cantidadIslasPorNivel = {5, 4, 3, 2, 1}; 
	    double[] desplazamientoXPorNivel = {160, 190, 220, 250, 0}; 
	    int cantIslas = 0;
	    for (int nivel = 0; nivel < cantidadIslasPorNivel.length; nivel++) {
	        xIslas = entorno.ancho() / 2 - ((cantidadIslasPorNivel[nivel] - 1) * desplazamientoXPorNivel[nivel]) / 2;
	        for (int j = 0; j < cantidadIslasPorNivel[nivel]; j++) {
	            islas[cantIslas] = new Islas(xIslas, yIslas - (nivel * 100), 120, 40); // aca crea la isla
	            xIslas += desplazamientoXPorNivel[nivel]; // Desplaza la posición de la siguiente isla
	            cantIslas++; // Incrementa el índice de las islas
	        }
	        
	    }	   	    
	   //otras variables 
	    gnomo[0]=new Gnomos(x,y,10,10,1);
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
	}
	
    public void tick() {
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
        	pep.setDireccion(1);  // Actualiza la dirección del personaje (para disparo)
        }else if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
        	pep.setDireccion(-1); 
        	derecha = false;
            pep.moverIzquierda();
        } else {
            pep.setDireccion(0); // Establecer dirección a 0 cuando este parado
        }
		if(pep != null && entorno.sePresiono(entorno.TECLA_ESPACIO)) {
			if(disparo == null) {
				disparo = new DisparoPep(pep.getX(), pep.getY(), 10, 10, this.derecha);
			}
		}
		if(disparo != null) {
			disparo.dibujarDisparo(entorno); 
			disparo.mover();  
			if(disparo.seFue(entorno)) {
				disparo = null;
			}
		}

        //salto al presionar tecla arriba
        if (entorno.sePresiono(entorno.TECLA_ARRIBA) && puedeSaltar && !saltoCooldown) {
            estaSaltando = true;
            puedeSaltar = false; 
            saltoMaxY = pep.getY() - 100; // altura maxima (no se como lo ven)
        }

        // Realiza el salto
        if (estaSaltando && pep.getY() > saltoMaxY) {
            pep.saltar(); // va para arriba
            puedeCaer = false; //y no cae de golpe mientras salta
        }

        // Termina el salto al llegar a la altura máxima
        if (estaSaltando && pep.getY() <= saltoMaxY) {
            estaSaltando = false;
            puedeCaer = true; // cae después del salto
            saltoCooldown = true; // Activa el cooldown de salto
        }

        // Cooldown para evitar salto seguidos
        if (saltoCooldown) {
            timerSalto++;
            if (timerSalto >= 27) { // se puede cambiar
                saltoCooldown = false;
                timerSalto = 0; //reinicia el timer
            }             
        }
        pep.dibujarPep(entorno);
	    casa.dibujarCasa(entorno);

         
        
        
        
// LOGICA GNOMOS
	    
        timerGnomos++;
        if (timerGnomos >= 400 && gnomosVivos < 6) {
            for (int i = 0; i < gnomo.length; i++) {
                if (gnomo[i] == null) {
                    double x = entorno.ancho() - 380;  
                    double y = entorno.alto() - 505;   
                    gnomo[i] = new Gnomos(x, y, 10, 10, 1);  

                    gnomosVivos++; 
                    timerGnomos = 0;  
                    System.out.println("Gnomo creado en posición " + i);
                    return;
                }
            }
        }

        // Dibuja y mueve los gnomos existentes
        for (int j = 0; j < gnomo.length; j++) {
            if (gnomo[j] != null) {
                gnomo[j].dibujarGnomos(entorno);
                gnomo[j].mover(entorno); 
                if(!gnomo[j].colisionaAbajoGnomo(islas)) {
                	gnomo[j].caer();
                }
                if (gnomo[j].colisionaAbajoGnomo(islas)) {
                	gnomo[j].cambioDireccion();
                }
            }
        } 
        
            timerTortugas++;
            if (timerTortugas >= tiempoSpawnTortugas && tortugasVivas < tortugas.length) {
                double[] posicionesIslasX = new double[islas.length];
                int contador = 0;

                // Recopila las posiciones X de las islas
                for (Islas isla : islas) {
                    if (isla != null) {
                        posicionesIslasX[contador++] = isla.getX();
                    }
                }

                // Selecciona una posición aleatoria si hay islas disponibles
                if (contador > 0) {
                    int indiceAleatorio = (int) (Math.random() * contador);
                    double xAleatorio = posicionesIslasX[indiceAleatorio];
                    double yInicial = entorno.alto() - 400;

                    // Verifica que no esté cerca de la casa
                    if (Math.abs(xAleatorio - casa.getX()) > 50) {
                        for (int i = 0; i < tortugas.length; i++) {
                            if (tortugas[i] == null) { // Encuentra un espacio vacío
                                tortugas[i] = new Tortuga(xAleatorio, yInicial, 30, 30, 1);
                                System.out.println("Tortuga creada en posición X: " + xAleatorio + ", Y: " + yInicial);

                                tortugasVivas++; // Incrementa el contador de tortugas vivas
                                timerTortugas = 0; // Reinicia el temporizador solo si se crea una tortuga
                                return;
                            }
                        }
                    }
                }
            }

            // Lógica de movimiento y eliminación de tortugas
            for (int j = 0; j < tortugas.length; j++) {
                if (tortugas[j] != null) {
                    tortugas[j].dibujarTortugas(entorno);

                    // Mueve la tortuga lateralmente si está en una isla, de lo contrario, cae
                    if (tortugas[j].colisionaAbajoTortuga(islas)) {
                        tortugas[j].mover(islas);
                    } else {
                        tortugas[j].caer();
                    }

                    // Comprobar colisión con el disparo y reducir tortugas vivas si es eliminado
                    if (disparo != null && tortugas[j].colisionaDisparoPep(disparo)) {
                        tortugasVivas--; // Reduce el contador de tortugas vivas
                        tortugas[j] = null; // Elimina la tortuga que fue impactada
                        disparo = null; // Elimina el disparo
                        if (pep != null) {
                            pep.incrementarKills();
                        }
                        return;
                    }
                }
            }
        }


	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}

