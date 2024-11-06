package juego;

import java.awt.Color;

import entorno.Entorno;

public class Tiempo {
	private Entorno entorno;
	private long tiempoInicio;
	private boolean activo; // para controlar el estado del tiempo

	public void pausar() {
		activo = false; // Pausa el temporizador
	}

	public void reanudar() {
		if (!activo) {
			tiempoInicio = System.currentTimeMillis() - (System.currentTimeMillis() - tiempoInicio); // Ajusta el tiempo
																										// de inicio
			activo = true; // Reactiva el temporizador
		}
	}

	public Tiempo(Entorno entorno) {
		this.entorno = entorno;
		this.tiempoInicio = System.currentTimeMillis(); // currentTimeMillis devuelve el tiempo actual en miliseg
		this.activo = true; // Inicia como activo
	}

	public String obtenerTiempoTranscurrido() {
		long tiempoTranscurrido = System.currentTimeMillis() - tiempoInicio;
		long segundos = (tiempoTranscurrido / 1000) % 60;
		long minutos = (tiempoTranscurrido / (1000 * 60)) % 60;
		return "Tiempo: " + minutos + " : " + String.format("%02d", segundos);
		// "%02d" convierte el número de segundos para que siempre tenga dos dígitos
	}

	public void dibujarTiempo() {
		String tiempoTexto = obtenerTiempoTranscurrido();
		entorno.cambiarFont("Verdana", 24, Color.WHITE, entorno.NEGRITA);
		entorno.escribirTexto(tiempoTexto, 580, 60); // dibuja el tiempo
	}
}