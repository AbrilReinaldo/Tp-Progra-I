package juego;

import java.awt.Color;

import entorno.Entorno;
//import entorno.Herramientas;


public class Tortuga {
		private double x; 
		private double y;
		private int ancho; 
		private int alto; 
		private int direccion;

		public Tortuga (double x, double y, int ancho, int alto, int direccion) {
		this.x = x;
		this.y = y; 
		this.ancho = ancho; 
		this.alto = alto; 
		this.direccion = direccion; 
	}
		public void dibujarTortugas(Entorno entorno) {
			entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0 , Color.green);

		}
		public void mover(Entorno entorno) { 
		    // Movimiento horizontal
			if (direccion == -1) { 
		        this.x -= 0.05;
		    } else if (direccion == 1) { 
		        this.x += 0.05;
		    }
		}
		
		public void cambioDireccion() {  //para que las tortugas ser mantengan en la misma isla
			if (direccion == -1) { 
				direccion = 1;
		    } else if (direccion == 1) { 
		    	direccion = -1;
		    }
	
		}
		
		
		public boolean colisionaAbajoTortuga(Islas[] islas) {
		    for (Islas isla : islas) {
		        //colision que puso la profe el otro dia
		    	if (isla != null && 
		            this.y + this.alto / 2 >= isla.getY() - isla.getAlto() / 2 &&
		            this.x + this.ancho / 2 >= isla.getX() - isla.getAncho() / 2 &&
		            this.x - this.ancho / 2 <= isla.getX() + isla.getAncho() / 2) {
		            System.out.println("Colisión detectada con isla en: " + isla.getX() + ", " + isla.getY()); //Lo puse para verificar que habia colision
		            return true; // Hay colisión
		        }

		    }
		    return false; // No hay colisión
		}

		public double getX() {
			return x;
		}
		public void setX(double x) {
			this.x = x;
		}
		public double getY() {
			return y;
		}
		public void setY(double y) {
			this.y = y;
		}
		public double getAncho() {
			return ancho;
		}
		public void setAncho(int ancho) {
			this.ancho = ancho;
		}
		public double getAlto() {
			return alto;
		}
		public void setAlto(int alto) {
			this.alto = alto;
		}
		public int getDireccion() {
			return direccion;
		}
		public void setDireccion(int direccion) {
			this.direccion = direccion;
		}
	}

		
		


