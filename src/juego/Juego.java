package juego;


import java.awt.Color;

import entorno.Entorno;
import entorno.InterfaceJuego;
import java.util.Random;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	private Islas [] islas;
	private Gnomos [] gnomo;
	private Tortuga [] tortugas;
	//private Pep[] pep;
	
	// Variables y métodos propios de cada grupo
	// ...
	
	Juego()
	{
		//pep = new Pep[1];
		this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);
		inicializarJuego();
		this.entorno.iniciar();
	}
	
	private void inicializarJuego() {
		tortugas = new Tortuga[1];
	    gnomo = new Gnomos[1];     // Inicializa el array de gnomos
	    double x = entorno.ancho() - 380;  // Definimos la posición inicial en el eje X para todos los gnomos
	    double y = entorno.alto() - 560 ;    // Posición inicial en el eje Y
	    // Generación de los gnomos (todos spawnean en la misma posición x)
	  
	    for (int i = 0; i < 1; i++) {
	    
	        gnomo[i] = new Gnomos(x, y, 10, 10, 1);  // Crea el gnomo en la posición (x, y)
	    }
	    for (int j = 0; j < 1; j++) {
		    tortugas[j] = new Tortuga(x, y, 30, 30, -1); 

	    }
		
	    
	}
	public void tick(){
	    islas = new Islas[15]; //cantidad de islas   
	    double xIslas = entorno.ancho() - 60;  
	    double yIslas = entorno.alto() - 100;
	    
	    for (int i = 0; i <= 4; i++) { //crea las islas de abajo
	        islas[i] = new Islas(xIslas, yIslas, 120, 40);
	        xIslas -= 170;
	    }
	    xIslas = entorno.ancho() - 100;
	    for (int i = 5; i <= 8; i++) { //segunda fila
	        islas[i] = new Islas(xIslas, yIslas-120 , 120, 40);
	        xIslas -= 200;
	    } 
	    xIslas = entorno.ancho() - 180;
	    for (int i = 9; i <= 11; i++) { //tercera
	        islas[i] = new Islas(xIslas, yIslas-240 , 120, 40);
	        xIslas -= 230;
	    } 
	    xIslas = entorno.ancho() - 320;
	    for (int i = 12; i <= 13; i++) { //cuarta
	        islas[i] = new Islas(xIslas, yIslas-340 , 120, 40);
	        xIslas -= 170;
	    } 
	    xIslas = entorno.ancho() / 2;
	    for (int i = 14; i <= 14; i++) { //isla de arriba del todo
	        islas[i] = new Islas(xIslas, yIslas-430 , 120, 40);
	        xIslas -= 170;
	    } 
	
    for (int i = 0; i < islas.length; i++) {
        if (islas[i] != null) { 
            islas[i].dibujarIslas(entorno);  
        }

            // Dibuja y mueve los gnomos
       
        for (int j = 0; j < gnomo.length; j++) {
            if (gnomo[j] != null) {
                gnomo[j].dibujarGnomos(entorno);
                gnomo[j].mover(entorno); 
                

                // Verifica si el gnomo colisiona con alguna isla 
                if (!gnomo[j].colisionaAbajoGnomo(islas)) { //si no colisiona, cae
                	gnomo[j].caer(); 
                	 if (gnomo[j].colisionaAbajoGnomo(islas)) {
                	    gnomo[j].cambioDireccion();
                	 }
                	}
    }
        }
     // Dibuja y mueve las tortus
        for (int j = 0; j < tortugas.length; j++) {
            if (tortugas[j] != null) {
                tortugas[j].dibujarTortugas(entorno);
                tortugas[j].mover(entorno); 
                
                if (!tortugas[j].colisionaAbajoTortuga(islas)) { //se queda en la isla
                	tortugas[j].cambioDireccion();

            }

		    }
        }
    }
	}
	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
