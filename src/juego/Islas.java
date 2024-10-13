package juego;

import java.awt.Color;

import entorno.Entorno;

public class Islas {
	private double x; 
	private double y; 
	private double ancho; 
	private double alto;
	
	public Islas(double x, double y, int ancho, int alto) {
		this.x = x;
		this.y = y; 
		this.ancho = ancho;
		this.alto = alto; 
	}

	public void dibujarIslas(Entorno entorno) {
		entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.white);
		
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
	}
	
