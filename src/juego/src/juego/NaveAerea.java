package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class NaveAerea {
    private double x;
    private double y;
    private int ancho;
    private int alto;
    private Image nave;
    private int desplazamiento;

    public NaveAerea(double x, double y, int ancho, int alto, int desplazamiento) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.nave = Herramientas.cargarImagen("nave.png"); // Imagen de la nave
        this.desplazamiento = desplazamiento;
    }

    // Método que maneja el movimiento de la nave
    public void mover(Entorno entorno) {
        // Movimiento basado en las teclas
        if (entorno.estaPresionada(entorno.TECLA_DERECHA)) {
            if (this.x + this.ancho / 2 < entorno.ancho()) {
                this.x += this.desplazamiento;
            }
        }
        if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
            if (this.x - this.ancho / 2 > 0) {
                this.x -= this.desplazamiento;
            }
        }

        // Movimiento basado en el ratón
        if (entorno.mouseX() < entorno.ancho()) {
            this.x = entorno.mouseX();
        }
    }

    // Método para dibujar la nave
    public void dibujarNave(Entorno entorno) {
        entorno.dibujarImagen(nave, this.x, this.y, 0, 1); // Dibujamos la nave con la imagen cargada
    }

	public Gnomos colisionaGnomo(Gnomos[] gnomo) {
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
	
	
	public boolean colisionaPep(Pep p) {
		return (this.y + this.alto / 2 >= p.getY() && this.y - this.alto / 2 <= p.getY()
				&& this.x - this.ancho / 2 <= p.getX() + p.getAncho() / 2
				&& this.x + this.ancho / 2 >= p.getX() - p.getAncho() / 2);	
	}
	
	
	
	
    // Getters y setters
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }
}
