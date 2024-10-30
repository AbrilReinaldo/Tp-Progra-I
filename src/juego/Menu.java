package juego;

import java.awt.Color;
import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Menu {
	private double x;
	private double y;
	private int ancho;
	private int alto;
	private Image imagenMenu;

	public Menu(double x, double y, int ancho, int alto) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.imagenMenu = Herramientas.cargarImagen("menu.png");
	}

	public void dibujarCasa(Entorno entorno) {
		entorno.dibujarImagen(this.imagenMenu, this.x, this.y, 0, 0.1); // Dibuja la imagen de la casa
	}

	public double getX() {
		// TODO Auto-generated method stub
		return 0;
	}

}