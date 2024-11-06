package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Casa {
	private double x;
	private double y;
	private Image imagenCasa;

	public Casa(double x, double y, int ancho, int alto) {
		this.x = x;
		this.y = y;
		this.imagenCasa = Herramientas.cargarImagen("casa.png");
	}

	public void dibujarCasa(Entorno entorno) {
		entorno.dibujarImagen(this.imagenCasa, this.x, this.y, 0, 0.1); // Dibuja la imagen de la casa
	}

	public double getX() {
		// TODO Auto-generated method stub
		return 0;
	}

}
