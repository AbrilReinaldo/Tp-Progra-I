package juego;

import java.awt.Color;
import java.util.Random;

import entorno.Entorno;

public class Gnomos {
		private double x; 
		private double y;
		private int ancho; 
		private int alto; 
		private int direccion;
		private Random random = new Random();

		public Gnomos(double x, double y, int ancho, int alto, int direccion) {
		this.x = x;
		this.y = y; 
		this.ancho = ancho; 
		this.alto = alto; 
		this.direccion = direccion; 
	}
		public void dibujarGnomos(Entorno entorno) {
			entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0 , Color.red);

		}
		
		public int azar(Entorno entorno) {
			return -1 + (2 * random.nextInt());
				
		}
		//public void azar(Entorno entorno) {
		//	if (Math.random() < 0.5) { 
		 //       this.x -= 0.5* direccion;
		//    } else {                                              //UNA FORMA DEL AZAR, HAY QUE MODIFICARLA PARA QUE ANDE BIEN
		 //       this.x += 0.5* direccion;
		  //  }
		//}
		public void mover(Entorno entorno) {
		    // Movimiento horizontal
		    if (direccion == -1) { 
		        this.x -= 0.1;
		    } else if (direccion == 1) { 
		        this.x += 0.1;
		    }
		}
		public void caer() {
			this.y += 0.25; //la velocidad en la que baja en el eje y 
		}
		public void ubicacionGnomo() {
			x += direccion;
		}
		
		public boolean colisionaAbajoGnomo(Islas[] islas) {
		    for (Islas isla : islas) {
		        //colision que puso la profe el otro dia
		    	if (isla != null && 
		            this.y + this.alto / 2 >= isla.getY() - isla.getAlto() / 2 &&
		            this.x + this.ancho / 2 >= isla.getX() - isla.getAncho() / 2 &&
		            this.x - this.ancho / 2 <= isla.getX() + isla.getAncho() / 2) {
	             System.out.println("Colisión de gnomo detectada con isla en: " + isla.getX() + ", " + isla.getY()); //Lo puse para verificar que habia colision
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
		public void cambioDireccion() {  //para que las tortugas ser mantengan en la misma isla
			if (direccion == -1) { 
				direccion = 1;
		    } else if (direccion == 1) { 
		    	direccion = -1;
		    }
	
		}
	}

		
		

