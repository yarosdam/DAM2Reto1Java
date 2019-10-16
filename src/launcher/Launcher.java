package launcher;

import modelo.Modelo;
import vista.VentanaPpal;

/**
 * Launcher de la aplicacion
 *
 */
public class Launcher {

	/**
	 * Main del programa
	 * @param args parametros de la aplicacion
	 */
	public static void main(String[] args) {
		VentanaPpal vista = new VentanaPpal();
		Modelo modelo = new Modelo();
		
		@SuppressWarnings("unused")
		Controlador controlador = new Controlador(vista, modelo);
	}
}
