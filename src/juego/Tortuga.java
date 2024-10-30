package juego;

import java.awt.Image;
import entorno.Entorno;
import java.util.Random;

import entorno.Entorno;
//import entorno.Herramientas;

public class Tortuga {
	private double x;
	private double y;
	private int ancho;
	private int alto;
	private double direccion;
	private double bordeInferior;
	private double bordeSuperior;
	private double bordeIzq;
	private double bordeDer;
	private Boolean seCayo;
	private Image tortugaDerecha;
	private Image tortugaIzquierda;
	private Boolean sobreIsla;
	private double velocidad;
	private boolean haCambiadoDireccion;

	public Tortuga(double x, double y, int ancho, int alto, double direccion) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.direccion = direccion;
		this.bordeInferior = this.y + this.alto / 2;
		this.bordeSuperior = this.y - this.alto / 2;
		this.bordeIzq = this.x - this.ancho / 2;
		this.bordeDer = this.x + this.ancho / 2;
		this.seCayo = false;
		this.tortugaDerecha = Herramientas.cargarImagen("recursos/tortugaDer.png");
		this.tortugaIzquierda = Herramientas.cargarImagen("recursos/tortugaIzq.png");
		this.velocidad = 0.5;
		this.haCambiadoDireccion = false;
	}

	public void dibujarTortugas(Entorno entorno) {
		if (this.direccion == 1) { // Derecha
			entorno.dibujarImagen(this.tortugaDerecha, this.x, this.y, 0, 0.12); // Dibuja la imagen hacia la derecha
		} else { // Izquierda
			entorno.dibujarImagen(this.tortugaIzquierda, this.x, this.y, 0, 0.12); // Dibuja la imagen hacia la
																					// izquierda
		}
	}

	public void mover(Islas[] islas) {
		// Mueve la tortuga hacia la izquierda o derecha según su dirección
		this.x += direccion * 0.5;

		// Recorremos las islas para verificar si la tortuga está en una de ellas
		for (Islas isla : islas) {
			if (isla != null) {
				// Borde izquierdo y derecho de la isla
				double bordeIzquierdoIsla = isla.getX() - isla.getAncho() / 2;
				double bordeDerechoIsla = isla.getX() + isla.getAncho() / 2;

				// Verificamos si la tortuga está sobre la isla
				if (this.y + this.alto / 2 >= isla.getY() - isla.getAlto() / 2
						&& this.y + this.alto / 2 <= isla.getY() + isla.getAlto() / 2 && this.x >= bordeIzquierdoIsla
						&& this.x <= bordeDerechoIsla) {

					// Cambiamos de dirección si toca el borde izquierdo o derecho de la isla
					if ((this.x - this.ancho / 2 <= bordeIzquierdoIsla && direccion == -0.5)
							|| (this.x + this.ancho / 2 >= bordeDerechoIsla && direccion == 0.5)) {
						cambiarDireccion(); // Cambia la dirección
					}

					return; // Salimos del bucle ya que estamos sobre una isla
				}
			}
		}
	}

	public void cambiarDireccion() {
		direccion *= -1; // Invierte la dirección de movimiento
	}

	public boolean colisionaAbajoTortuga(Islas[] islas) {
		for (Islas isla : islas) {
			if (isla != null) {
				// Borde inferior de Pep
				double tortugaBordeInferior = this.y + this.alto / 2;
				// Borde superior de la isla
				double islaBordeSuperior = isla.getY() - isla.getAlto() / 2;

				// Alineación horizontal: comprobar si Pep está sobre la isla
				boolean colisionX = (this.x + this.ancho / 2 >= isla.getX() - isla.getAncho() / 2)
						&& (this.x - this.ancho / 2 <= isla.getX() + isla.getAncho() / 2);

				// Verificar si Pep está justo sobre la isla
				if (tortugaBordeInferior >= islaBordeSuperior && tortugaBordeInferior <= islaBordeSuperior + 10
						&& colisionX) {
					System.out
							.println("Colisión de Tortuga detectada con isla en: " + isla.getX() + ", " + isla.getY());
					return true; // Hay colisión
				}
			}
		}
		return false; // No hay colisión
	}
	
	public Gnomos colisionaConGnomo(Gnomos[] gnomo) {
	    for (Gnomos g : gnomo) {
	        if (g != null && this.y + this.alto / 2 - 25 <= g.getY() + g.getAlto() / 2
	                && this.y - this.alto / 2 + 30 >= g.getY() - g.getAlto() / 2
	                && this.x - this.ancho / 2 + 10 <= g.getX() + g.getAncho() / 2
	                && this.x + this.ancho / 2 - 10 >= g.getX() - g.getAncho() / 2) {
	            return g; // Retorna el gnomo con el que colisiona
	        }
	    }
	    return null; // Retorna null si no hay colisión
	}
	

	public void caer() {
		this.y += 5; // la velocidad en la que baja en el eje y
		seCayo = true;
	}

	public boolean colisionaDisparoPep(DisparoPep d) {
		return (this.y + this.alto / 2 >= d.getY() && this.y - this.alto / 2 <= d.getY()
				&& this.x - this.ancho / 2 <= d.getX() + d.getAncho() / 2
				&& this.x + this.ancho / 2 >= d.getX() - d.getAncho() / 2);
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

	public double getDireccion() {
		return direccion;
	}

	public void setDireccion(int direccion) {
		this.direccion = direccion;
	}
}
