package juego;

import java.awt.Color;

import entorno.Entorno;

public class DisparoPep {
	private double x; 
	private double y; 
	private double ancho; 
	private double alto;
	private boolean derecha; // (true para derecha, false para izquierda)
	
	public DisparoPep(double x, double y, double ancho, double alto, boolean derecha) {
		this.x = x; 
		this.y = y; 
		this.ancho = ancho; 
		this.alto = alto; 
		this.derecha = derecha; 
	}

	public void dibujarDisparo(Entorno entorno) {
		entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.cyan);
	}
	
	public void mover() {
		if (derecha) {
			this.x += 11; // Mueve el disparo hacia la derecha
		} else {
			this.x -= 11; // Mueve el disparo hacia la izquierda
		}
	}
	public boolean seFue(Entorno entorno) {
		return (this.x - this.ancho / 2 > entorno.ancho() || this.x + this.ancho / 2 < 0); // Verifica si el disparo de Mario se fue del entorno
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



