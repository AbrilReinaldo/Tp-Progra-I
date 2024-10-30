
package juego;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.Image;
import java.io.File;

public class Herramientas {
	private Clip clip;

	// Método para cargar imágenes
	public static Image cargarImagen(String archivo) {
		Image imagen = null;
		try {
			imagen = ImageIO.read(new File(archivo)); // lee la imagen desde el archivo
		} catch (Exception e) {
			System.err.println("Error al cargar la imagen: " + archivo);
			e.printStackTrace();
		}
		return imagen;
	}

	public Herramientas(String filePath) {
		try {
			File audioFile = new File(filePath);
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
			clip = AudioSystem.getClip(); // obtiene un clip de audio que se puede reproducir
			clip.open(audioInputStream);
			System.out.println("Sonido cargado correctamente"); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Método para reproducir sonido en loop
	public void loop() {
		if (clip != null) {
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} else {
			System.out.println("Clip is null, cannot play sound.");
		}
	}

	public void stop() {
		if (clip != null && clip.isRunning()) {
			clip.stop();
		}
	}
}
