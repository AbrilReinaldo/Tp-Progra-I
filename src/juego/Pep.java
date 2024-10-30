package juego;


import java.awt.Color;
import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

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
	private Image img1; // Imagen para representar una vida
	private Image img2; // Imagen para representar una vida
	private Image img3; // Imagen para representar una vida
	private int puntaje = 0; // Puntaje acumulado
	private int kills = 0; // Número de enemigos eliminados
	private int vidas = 3;


    public Pep(double x, double y, int ancho, int alto, int desplazamiento) {
        this.x = x;
        this.y = y; 
        this.ancho = ancho; 
        this.alto = alto; 
        this.desplazamiento = desplazamiento; 
        this.direccion = 1;
        this.imagenParado = Herramientas.cargarImagen("golemParado.png");
        this.imagenDerecha = Herramientas.cargarImagen("golemDer.png");
        this.imagenIzquierda = Herramientas.cargarImagen("golemIzq.png");
        this.img1 = Herramientas.cargarImagen("uno.png");
        this.img2 = Herramientas.cargarImagen("dos.png");
        this.img3 = Herramientas.cargarImagen("tres.png");
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
	               // System.out.println("Colisión de Pep detectada con isla en: " + isla.getX() + ", " + isla.getY());
	                return true; // Hay colisión
	            }
	        }
	    }
	    return false; // No hay colisión
	}
	
	public void incrementarKills() {
		this.kills++; // Incrementa en 1 el contador de muertes
		this.puntaje += 2; // Incrementa en 2 el puntaje del jugador
	}
	
	public boolean colisionaTortuga(Tortuga[] tortuga) {
		for(Tortuga t : tortuga) { // Itera sobre cada objeto Rex en el arreglo
			if (t != null && // Verifica si el objeto Rex no es nulo
					this.y + this.alto / 2 - 25 <= t.getY() + t.getAlto() / 2 && // Verifica si la parte inferior del Mario está a una distancia máxima de 25 unidades de la parte superior del Rex
					this.y - this.alto / 2 + 30 >= t.getY() - t.getAlto() / 2 && // Verifica si la parte superior del Mario está a una distancia máxima de 30 unidades de la parte inferior del Rex
					this.x - this.ancho / 2 + 10 <= t.getX() + t.getAncho() / 2 && // Verifica si el lado izquierdo del Mario está a una distancia máxima de 10 unidades del lado derecho del Rex
					this.x + this.ancho / 2 - 10 >= t.getX() - t.getAncho() / 2 // Verifica si el lado derecho del Mario está a una distancia máxima de 10 unidades del lado izquierdo del Rex
					) {
				return true; // Si se cumple la condición para alguna instancia de Rex, devuelve verdadero indicando que hay una colisión
			}
		}
		return false; // Si no se encontró ninguna colisión, devuelve falso
	}
	public void vidas(Entorno entorno) {		
		// Dibuja la imagen de Mario en la esquina superior derecha de la pantalla
		entorno.dibujarImagen(imagenDerecha, entorno.ancho() - 60, 75, 0, 0.045);
		// Dibuja la cantidad de vidas restantes según el valor de la variable "vidas"
		if (vidas == 3) {
			entorno.dibujarImagen(this.img1, entorno.ancho(),77, 0, 0.045);
		}
		if (vidas == 2) {
			entorno.dibujarImagen(this.img2, entorno.ancho() - 20, 77, 0, 0.045);
		}
		if (vidas == 1) {
			entorno.dibujarImagen(this.img1, entorno.ancho() - 20, 77, 0, 0.045);
		}
	}
	
	public void mostrarPuntaje(Entorno entorno) {
		entorno.cambiarFont("Arial", 25, Color.white); // Cambiar la fuente y el color del texto
		entorno.escribirTexto("Pep's", entorno.ancho() - 120, entorno.alto() - 70); // Muestra el nombre del jugador
		entorno.escribirTexto("Points: " + puntaje, entorno.ancho() - 120, entorno.alto() - 40); // Muestra el puntaje del jugador
		entorno.escribirTexto("Kills: " + kills, entorno.ancho() - 120, entorno.alto() - 10); // Muestra el número de muertes del jugador
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

	public int getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}

	public int getKills() {
		return kills;
	}

	public void setKills(int kills) {
		this.kills = kills;
	}

	public int getVidas() {
		return vidas;
	}

	public void setVidas(int vidas) {
		this.vidas = vidas;
	}
	

}
