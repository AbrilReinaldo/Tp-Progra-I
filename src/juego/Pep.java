package juego;


import java.awt.Image;
import entorno.Entorno;

public class Pep {
    private double x; 
    private double y;
    private int ancho; 
    private int alto; 
    private int desplazamiento;
    private int direccion;
    private Image imagenParado; //  imagen de Pep parado 
    private Image imagenDerecha; // Imagen de Pep moviéndose a la derecha
    private Image imagenIzquierda; // Imagen de Pep moviéndose a la izquierda


    public Pep(double x, double y, int ancho, int alto, int desplazamiento) {
        this.x = x;
        this.y = y; 
        this.ancho = ancho; 
        this.alto = alto; 
        this.desplazamiento = desplazamiento; 
        this.direccion = 1;
        this.imagenParado = Herramientas.cargarImagen("recursos/golemParado.png"); // Carga la imagen de Pep
        this.imagenDerecha = Herramientas.cargarImagen("recursos/golemDer.png");
        this.imagenIzquierda = Herramientas.cargarImagen("recursos/golemizq.png");
    }

    public void dibujarPep(Entorno entorno) {
    	// Si Pep se está moviendo a la derecha
    	if (this.direccion == 1) {
            entorno.dibujarImagen(this.imagenDerecha, this.x, this.y, 0, 0.04); // Imagen para la derecha
        } else if (this.direccion == -1) {
            entorno.dibujarImagen(this.imagenIzquierda, this.x, this.y, 0, 0.04); // Imagen para la izquierda
        } else {
            entorno.dibujarImagen(this.imagenParado, this.x, this.y, 0, 0.12); // Imagen para parado
        }
    }
    

    public void moverDerecha(Entorno e) {
        if (this.x + this.ancho / 2 < e.ancho()) {
            this.x += this.desplazamiento; 
        }
    }

    public void moverIzquierda() {
        if (this.x - this.ancho / 2 > 0) {
            this.x -= this.desplazamiento; 
        }
    }

    // Nuevo método para el salto
	public void saltar() {
		y-= 5;
	}


	public void caer() {
		this.y += 5; //la velocidad en la que baja en el eje y 
	}
    

    // Método para verificar si está en el suelo (sobre una isla)
	public boolean colisionaAbajoPep(Islas[] islas) {
	    for (Islas isla : islas) {
	        if (isla != null) {
	            // Borde inferior de Pep
	            double pepBordeInferior = this.y + this.alto / 2; 
	            // Borde superior de la isla
	            double islaBordeSuperior = isla.getY() - isla.getAlto() / 2; 

	            // Alineación horizontal: comprobar si Pep está sobre la isla
	            boolean colisionX = (this.x + this.ancho / 2 >= isla.getX() - isla.getAncho() / 2) &&
	                                 (this.x - this.ancho / 2 <= isla.getX() + isla.getAncho() / 2);

	            // Verificar si Pep está justo sobre la isla
	            if (pepBordeInferior >= islaBordeSuperior && pepBordeInferior <= islaBordeSuperior + 10 && colisionX) {
	                System.out.println("Colisión de Pep detectada con isla en: " + isla.getX() + ", " + isla.getY());
	                return true; // Hay colisión
	            }
	        }
	    }
	    return false; // No hay colisión
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
	public int getDesplazamiento() {
		return desplazamiento;
	}
	public void setDesplazamiento(int desplazamiento) {
		this.desplazamiento = desplazamiento;
	}

	public int getDireccion() {
		return direccion;
	}

	public void setDireccion(int direccion) {
		this.direccion = direccion;
	}
	

}
