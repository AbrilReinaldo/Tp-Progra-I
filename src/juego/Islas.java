package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Islas {
	private double x;
	private double y;
	private double ancho;
	private double alto;
	private double bordeInferior;
	private double bordeSuperior;
	private double bordeIzq;
	private double bordeDer;
	private Image imgIslas;

	public Islas(double x, double y, int ancho, int alto) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.bordeInferior = this.y + this.alto / 2;
		this.bordeSuperior = this.y - this.alto / 2;
		this.bordeIzq = this.x - this.ancho / 2;
		this.bordeDer = this.x + this.ancho / 2;
		this.imgIslas = Herramientas.cargarImagen("isla.png");
	}

	public void dibujarIslas(Entorno entorno) {
		// entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0,
		// Color.white);
		entorno.dibujarImagen(imgIslas, this.x, this.y, 0, 2);
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

	public double getBordeInferior() {
		return bordeInferior;
	}

	public void setBordeInferior(double bordeInferior) {
		this.bordeInferior = bordeInferior;
	}

	public double getBordeSuperior() {
		return bordeSuperior;
	}

	public void setBordeSuperior(double bordeSuperior) {
		this.bordeSuperior = bordeSuperior;
	}

	public double getBordeIzq() {
		return bordeIzq;
	}

	public void setBordeIzq(double bordeIzq) {
		this.bordeIzq = bordeIzq;
	}

	public double getBordeDer() {
		return bordeDer;
	}

	public void setBordeDer(double bordeDer) {
		this.bordeDer = bordeDer;
	}

}
