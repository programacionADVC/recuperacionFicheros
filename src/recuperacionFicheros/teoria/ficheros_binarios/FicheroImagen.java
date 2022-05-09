package recuperacionFicheros.teoria.ficheros_binarios;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class FicheroImagen {
	//ruta del fichero origen (imagen.jpeg)
	private static Path path = Paths.get("FILES/imagen.jpeg");
	private static Path backup = Paths.get("FILES/imagen_copia.jpeg");
	private static Path backup_parcial = Paths.get("FILES/imagen_copia_parcia.jpeg");

	//ruta de fichero copia (imagen_copia.jpeg)
	//ruta de fichero copia parcial (imagen_copia_parcial.jpeg)
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//método que nos haga una copia de la imagen
		copiarImagen();
		copiarParcialImagen(10,100);
		//método que nos copie los bytes indicados, ej (12,200) copia desde el byte 12 al 200
		//clase Files usamos los métodos read y write relacionados con bytes
		
	}

	private static void copiarParcialImagen(int byteInicial, int byteFinal) {
		if (byteInicial < 0) {
			System.out.println("no se pude hacer la copia parcial");
			return;
		}
		if (byteInicial >= byteFinal) {
			System.out.println("no se pude hacer la copia parcial");
			return;
		}
		try {
			byte[] bytesFichero = Files.readAllBytes(path);
			if (byteFinal >= bytesFichero.length) {
				System.out.println("no se pude hacer la copia parcial");
				return;
			}
			byte[] bytesAcopiarArray = new byte[byteFinal - byteInicial];
			int contador = 0;
			for (int i = byteInicial; i < byteFinal; i++) {
				bytesAcopiarArray[contador] = bytesFichero[i];
				contador++;
			}
			Files.write(backup_parcial, bytesAcopiarArray, StandardOpenOption.CREATE);
			System.out.printf("copiados %d bytes a fichero%n", bytesAcopiarArray.length);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static void copiarImagen() {
		// TODO Auto-generated method stub
		File ficheroOriginal = new File("FILES/imagen_copia.jpeg");
		if (ficheroOriginal.length() == 0) {
			System.out.println("nada que copiar");
			return;
		}
		try {
			Files.copy(path, backup, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File ficheroCopiado = new File("FILES/imagen_copia.jpeg");
		if (ficheroCopiado.length() == ficheroOriginal.length())
			System.out.println("Copia con éxito");
		else
			System.out.println("No se pudo copiar");
	}

}
