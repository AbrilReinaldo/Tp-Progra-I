package juego;

import java.awt.Color;
import java.awt.Image;
import entorno.Entorno;
import java.awt.Color;
import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class DisparoPep {
	private double x;
	private double y;
	private double ancho;
	private double alto;
	private boolean derecha; // (true para derecha, false para izquierda)
	private Image disparoDerecha; // Imagen para el disparo hacia la derecha
	private Image disparoIzquierda;

	public DisparoPep(double x, double y, double ancho, double alto, boolean derecha) {
		this.x = x;
		this.y = y;
		this.ancho = 5;
		this.alto = 5;
		this.derecha = derecha;
		this.disparoDerecha = Herramientas.cargarImagen("disparoDer.png");
		this.disparoIzquierda = Herramientas.cargarImagen("disparoIzq.png");
	}

	public void dibujarDisparo(Entorno entorno) {
		if (derecha && this.disparoDerecha != null) {
			entorno.dibujarImagen(this.disparoDerecha, this.x, this.y, 0, this.ancho / 100.0); // Dibuja la imagen del
																								// disparo a la derecha
		} else if (!derecha && this.disparoIzquierda != null) {
			entorno.dibujarImagen(this.disparoIzquierda, this.x, this.y, 0, this.ancho / 100.0); // Dibuja la imagen del
																									// disparo a la
																									// izquierda
		}
	}

	public void mover() {
		if (derecha) {
			this.x += 5; // Mueve el disparo hacia la derecha
		} else {
			this.x -= 5; // Mueve el disparo hacia la izquierda
		}
	}

	public boolean seFue(Entorno entorno) {
		return (this.x - this.ancho / 2 > entorno.ancho() || this.x + this.ancho / 2 < 0); // Verifica si el disparo de
																							// Mario se fue del entorno
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

	public void setAncho(double ancho) {
		this.ancho = ancho;
	}

	public double getAlto() {
		return alto;
	}

	public void setAlto(double alto) {
		this.alto = alto;
	}

	public boolean isDerecha() {
		return derecha;
	}

	public void setDerecha(boolean derecha) {
		this.derecha = derecha;
	}

}
