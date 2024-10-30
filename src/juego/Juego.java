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
	    tortugasVivas = 4;
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
         // Verifica si Pep pierde vidas por varias condiciones: colisión con Rex, bombas, caer por debajo de cierta altura, etc.
            if (pep != null && (pep.colisionaTortuga(tortugas) || pep.getY() > 600 )
                    && pep.getVidas() >= 1 && !cooldownVidas) {
                
                pep.setVidas(pep.getVidas() - 1);  // Reduce las vidas de Pep
                pep.setY(entorno.alto() - 70);     // Reposiciona a Pep en Y
                pep.setX(entorno.ancho() / 2 + 50); // Reposiciona a Pep en X
                cooldownVidas = true;               // Activa el cooldown para evitar perder vidas continuamente
            }

            // Cooldown para evitar perder todas las vidas instantáneamente
            if (cooldownVidas) {
                timerVidas += 1;  // Incrementa el temporizador de las vidas
                if (timerVidas == 100) { // Espera 100 ticks antes de permitir la pérdida de otra vida
                    timerVidas = 0;  // Reinicia el temporizador
                    cooldownVidas = false;  // Finaliza el cooldown
                }
            }

            // Si Pep pierde todas las vidas, se elimina
            if (pep != null && pep.getVidas() == 0) {
                pep = null;  // Elimina a Pep del juego si no le quedan vidas
            }
    		// Si Mario está presente, muestra sus vidas, puntaje y lo dibuja en el entorno
    		if (pep != null) {
    			pep.vidas(entorno); // Muestra las vidas de Mario
    			pep.mostrarPuntaje(entorno); // Muestra el puntaje de Mario
    			pep.dibujarPep(entorno); // Dibuja a Mario en el entorno
    		} else {
    			// Si Mario no está presente, muestra la imagen de Mario y una calavera en su lugar
    			pepDer = Herramientas.cargarImagen("recursos/golemDer.png");
    			entorno.dibujarImagen(pepDer, entorno.ancho() - 60, 75, 0, 0.045); // Dibuja la imagen de Mario
    			calavera = Herramientas.cargarImagen("recursos/calavera.png");
    			entorno.dibujarImagen(calavera, entorno.ancho() - 20, 75, 0, 0.075); // Dibuja la calavera
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

     // LOGICA TORTUGAS
        timerTortugas++;
        if (timerTortugas >= tiempoSpawnTortugas) {
            // Lista de posiciones X válidas donde las islas están ubicadas
            double[] posicionesIslasX = new double[islas.length];
            int contador = 0;

            // Recopila las posiciones X de las islas
            for (Islas isla : islas) {
                if (isla != null) {
                    posicionesIslasX[contador++] = isla.getX();
                }
            }

            if (contador > 0) { // Verifica que hay islas disponibles
                int indiceAleatorio = (int) (Math.random() * contador); // Selecciona un índice aleatorio
                double xAleatorio = posicionesIslasX[indiceAleatorio]; // Obtiene la posición X de la isla seleccionada
                double yInicial = entorno.alto() - 400; // Altura fija para la tortuga

                // Verifica que no aparezca cerca de la casa
                if (Math.abs(xAleatorio - casa.getX()) > 50) {
                    for (int i = 0; i < tortugas.length; i++) {
                        if (tortugas[i] == null) { // si hay espacio disponible
                            tortugas[i] = new Tortuga(xAleatorio, yInicial, 30, 30, 1); 
                            System.out.println("Tortuga creada en posición X: " + xAleatorio + ", Y: " + yInicial);
                            return; // Sale del bucle de generación
                        }
                    }
                }
            }
            
            timerTortugas = 0; // Reinicia el temporizador después de intentar crear una tortuga
        }
        for (int j = 0; j < tortugas.length; j++) {
            if (tortugas[j] != null) {
                tortugas[j].dibujarTortugas(entorno); // Dibuja la tortuga

                // Si colisiona con una isla
                if (tortugas[j].colisionaAbajoTortuga(islas)) {
                    tortugas[j].mover(islas); // Mueve la tortuga lateralmente si está en una isla
                } else {
                    tortugas[j].caer(); // Si no colisiona con una isla, sigue cayendo
                }
            }
            // Comprobar colisión con el disparo
            
                if (disparo != null && tortugas[j].colisionaDisparoPep(disparo)) {
                    tortugasVivas--;           // Reduce el contador de tortugas vivas
                    tortugas[j] = null;         // Elimina la tortuga que fue impactada por el disparo
                    disparo = null;             // Elimina el disparo para que no continúe

                    if (pep != null) {          // Si el personaje existe, incrementa su contador de kills
                        pep.incrementarKills();
                    }
                    return; // Sale del ciclo para que no se verifiquen más colisiones en este ciclo
                }
            }
        }

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}

