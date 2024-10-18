package juego;

import java.awt.Color;
import java.util.Random;

import entorno.Entorno;
//import entorno.Herramientas;


public class Tortuga {
		private double x; 
		private double y;
		private int ancho; 
		private int alto; 
		private int direccion;
		private double bordeInferior;
		private double bordeSuperior;
		private double bordeIzq;
		private double bordeDer;
	    private Boolean seCayo;
	    
	    
		public Tortuga (double x, double y, int ancho, int alto, int direccion) {
		this.x = x;
		this.y = y; 
		this.ancho = ancho; 
		this.alto = alto; 
		this.direccion = direccion; 
		this.bordeInferior= this.y + this.alto/2;
		this.bordeSuperior= this.y - this.alto/2;
		this.bordeIzq = this.x - this.ancho / 2;
		this.bordeDer = this.x + this.ancho/2;
		this.seCayo = false;
	}
		public void dibujarTortugas(Entorno entorno) {
			entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0 , Color.green);

		}

		public void mover(Entorno entorno) { 
		    // Movimiento horizontal
		    if (direccion == -1) { 
		        this.x -= 0.5;
		    } else if (direccion == 1) { 
		        this.x += 0.5;
		    }
		    
		    // Actualizar los bordes después de mover
		    actualizarBordes(); 
		}

		public void actualizarBordes() {
		    this.bordeInferior = this.y + this.alto / 2;
		    this.bordeSuperior = this.y - this.alto / 2;
		    this.bordeIzq = this.x - this.ancho / 2;
		    this.bordeDer = this.x + this.ancho / 2;
		}
	
		public boolean colisionaAbajoTortuga(Islas[] islas) {
		    for (Islas isla : islas) {
		        if (isla != null && 
		            this.bordeDer >= isla.getBordeIzq() && 
		            this.bordeIzq <= isla.getBordeDer() && 
		            Math.abs(this.bordeInferior - isla.getBordeSuperior()) < 1) {
		            return true; // Hay colisión
		        }
		    }
		    return false; // No hay colisión
		}
				
		public void caer() {
			this.y += 1; //la velocidad en la que baja en el eje y 
			seCayo=true;
		}
		
		public void cambioDireccion() {
		    if (seCayo) {
		        // Solo cambia la dirección si está cayendo
		        if (direccion == -1) { 
		            direccion = 1; // Cambia a la derecha
		        } else if (direccion == 1) { 
		            direccion = -1; // Cambia a la izquierda
		        }
		        seCayo = false; // Restablece el estado
		    }
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

		
		


