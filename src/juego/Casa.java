package juego;
import java.awt.Color;

import entorno.Entorno;
public class Casa {
	 private double x; 
	 private double y;
	 private int ancho; 
	 private int alto; 
	 
public Casa(double x, double y, int ancho, int alto) {
	        this.x = x;
	        this.y = y; 
	        this.ancho = ancho; 
	        this.alto = alto;  
}
	 
public void dibujarCasa(Entorno entorno) {
	entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0 , Color.orange);
}

}
