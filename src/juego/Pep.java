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
	private Image imagenParado; // imagen de Pep parado
	private Image imagenDerecha; // Imagen de Pep moviéndose a la derecha
	private Image imagenIzquierda; // Imagen de Pep moviéndose a la izquierda
	private Image imagenSaltando; // Imagen de Pep saltando
	private Image img1; // Imagen para representar una vida
	private Image img2; // Imagen para representar una vida
	private Image img3; // Imagen para representar una vida
	private Image img4; // Imagen para representar una vida
	private int puntaje = 0; // Puntaje acumulado
	private int kills = 0; // Número de enemigos eliminados
	private int vidas = 3;
	private int rescates = 0;
	private boolean saltar = false;

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
		this.imagenSaltando = Herramientas.cargarImagen("golemsaltando.png");
		this.img1 = Herramientas.cargarImagen("uno.png");
		this.img2 = Herramientas.cargarImagen("dos.png");
		this.img3 = Herramientas.cargarImagen("tres.png");
		this.img4 = Herramientas.cargarImagen("cuatro.png");
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
		if (saltar) {
			entorno.dibujarImagen(this.imagenSaltando, this.x, this.y, 0, 0.12); // Imagen para parado
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
		y -= 5;
		saltar= true;	

	}

	public void caer() {
		this.y += 5; // la velocidad en la que baja en el eje y
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
				boolean colisionX = (this.x + this.ancho / 2 >= isla.getX() - isla.getAncho() / 2)
						&& (this.x - this.ancho / 2 <= isla.getX() + isla.getAncho() / 2);

				// Verificar si Pep está justo sobre la isla
				if (pepBordeInferior >= islaBordeSuperior && pepBordeInferior <= islaBordeSuperior + 10 && colisionX) {
					// System.out.println("Colisión de Pep detectada con isla en: " + isla.getX() +
					// ", " + isla.getY());
					System.out
					.println("Colisión de Pep detectada con isla en: " + isla.getX() + ", " + isla.getY());
					saltar= false;
					return true; // Hay colisión
				}
			}
		}
		return false; // No hay colisión
	}
	
	public boolean pepPuedeSalvar() { //para que pep no pueda salvar gnomos en las primeras tres islas
		if (this.y >= 300) {
			return true;
		}
		return false;
	}
	
	public void incrementarKills() {
		this.kills++; // Incrementa en 1 el contador de muertes
	}

	public boolean colisionaTortuga(Tortuga[] tortuga) {
		for (Tortuga t : tortuga) {
			if (t != null && this.y + this.alto / 2 - 25 <= t.getY() + t.getAlto() / 2
					&& this.y - this.alto / 2 + 30 >= t.getY() - t.getAlto() / 2
					&& this.x - this.ancho / 2 + 10 <= t.getX() + t.getAncho() / 2
					&& this.x + this.ancho / 2 - 10 >= t.getX() - t.getAncho() / 2) {
				return true;
			}
		}
		return false;
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


	public void vidas(Entorno entorno) {
		// Dibuja la imagen de Mario en la esquina superior derecha de la pantalla
		entorno.dibujarImagen(imagenDerecha, 70, 75, 0, 0.045);
		// Dibuja la cantidad de vidas restantes según el valor de la variable "vidas"
		if (vidas == 4) {
			entorno.dibujarImagen(this.img4, 120, 77, 0, 0.045);
		}
		if (vidas == 3) {
			entorno.dibujarImagen(this.img3, 120, 77, 0, 0.045);
		}
		if (vidas == 2) {
			entorno.dibujarImagen(this.img2, 120, 77, 0, 0.045);
		}
		if (vidas == 1) {
			entorno.dibujarImagen(this.img1, 120, 77, 0, 0.045);
		}
	}

	public void mostrarKills(Entorno entorno) {
		entorno.cambiarFont("Arial", 25, Color.white); // Cambiar la fuente y el color del texto
		entorno.escribirTexto("Pep", entorno.ancho() - 120, entorno.alto() - 70); // Muestra el nombre del jugador
		entorno.escribirTexto("Kills: " + kills, entorno.ancho() - 120, entorno.alto() - 10); // Muestra el número de
																								// muertes del jugador
	}

	public void incrementarRescates() {
		this.rescates++;
	}

	public void mostrarRescatados(Entorno entorno) {
		entorno.escribirTexto("Rescatados: " + rescates, entorno.ancho() - 207, entorno.alto() - 40); // Muestra el
																										// puntaje del
																										// jugador
	}

	//public void mostrarGameOver(Entorno entorno) {
	//	entorno.cambiarFont("Arial", 25, Color.white);
	//	entorno.escribirTexto("GAME OVER", entorno.ancho() - 480, entorno.alto() / 2);
	//}

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

	public int getRescates() {
		return rescates;
	}

	public void setRescates(int rescates) {
		this.rescates = rescates;
	}

}
