package juego;


import java.awt.Color;

import entorno.Entorno;
import entorno.InterfaceJuego;
import java.util.Random;

public class Juego extends InterfaceJuego {
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
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
    
 	
	Juego() {
		this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);
		inicializarJuego();
		this.entorno.iniciar();
	}
	
	private void inicializarJuego() {
		this.casa = new Casa(415, 85, 25, 30);
		this.pep = new Pep(entorno.ancho() / 2, entorno.alto() - 160, 25, 40, 5);
		tortugas = new Tortuga[2];
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
        }
        if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
        	pep.setDireccion(-1); 
        	derecha = false;
            pep.moverIzquierda();
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

 //LOGICA TORTUGAS
                       
        timerTortugas ++;
        if(timerTortugas >= tiempoSpawnTortugas) {
            for (int i = 0; i < tortugas.length; i++) {
                if (tortugas[i] == null) { // si hay espacio disponible
                    double xAleatorio = Math.random() * (entorno.ancho() - 30); 
                    double yInicial = entorno.alto() - 550; 
                    tortugas[i] = new Tortuga(xAleatorio, yInicial, 30, 30, 1); 
                    System.out.println("Tortuga creada en posición X: " + xAleatorio);
                    return; 
                }
            }
            timerTortugas = 0; // Resetea el temporizador
        }      
       
        for (int j = 0; j < tortugas.length; j++) {
            if (tortugas[j] != null) {
                tortugas[j].dibujarTortugas(entorno);
                tortugas[j].mover(entorno);
                if (!tortugas[j].colisionaAbajoTortuga(islas)) {
                	tortugas[j].caer();
                }
                if (tortugas[j].colisionaAbajoTortuga(islas)) {
                	tortugas[j].cambioDireccion();
                }
            }
        }
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}

