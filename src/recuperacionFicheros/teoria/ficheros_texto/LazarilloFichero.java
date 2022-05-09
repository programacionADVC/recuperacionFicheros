package recuperacionFicheros.teoria.ficheros_texto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;

public class LazarilloFichero {
	
	private static Path path = Paths.get("FILES/lazarillo.txt");
	private static Path backup = Paths.get("FILES/lazarillo_back.txt");
	private static Path backup_parcial = Paths.get("FILES/lazarillo_back_parcial.txt");

	private static Scanner sc = null;

	public static void main(String[] args) {
		
		List<String> lineasLeidas = leerFicheroTexto(path);
		//metodo que nos busque una palabra determinada, ejemplo lazarillo
		//lineasLeidas.forEach(System.out::println);
		String palabraABuscar = "Toledo";
		int numeroPalabrasBuscadas = buscarPalabras(lineasLeidas, palabraABuscar);
		System.out.printf("La palabra %s aparece %d veces%n", palabraABuscar, numeroPalabrasBuscadas);
		//un método que cuente las palabras
		int numeroPalabras = contarPalabras(lineasLeidas);
		System.out.printf("%d palabras tiene el texto%n", numeroPalabras);

		//usando el método copy que cree una copia del fichero
		copiarFichero();
		//usando un método que copie solo a partir de una palabra hasta otra, 
		//ejemplo se le pasa 25 y 2000 copia desde la palabra 25 a la 2000
		crearFicheroParcial(25, 100);
	}

	//métod que crea un fichero desde una línea, hasta otra especificada
	private static void crearFicheroParcial(int palabraInicial, int palabraFinal) {
		// comprobar que palabraFinal > palabraInicial
		if (palabraFinal <= palabraInicial) {
			System.out.println("No se puede hacer");
			return;
		}
		// comprobar que palabraInicial > 0
		if (palabraInicial < 0){
			System.out.println("No se puede hacer");
			return;
		}
		// comprobar que palabraFinal < última palabra
		List<String> lineas = leerFicheroTexto(path);
		int numeroPalabras = contarPalabras(lineas);
		if (palabraFinal >= numeroPalabras)  {
			System.out.println("No se puede hacer");
			return;
		}
		int contador = 0, contadorNuevasLineas = 0;
		StringBuilder stringBuilder = new StringBuilder();
		for (String linea : lineas) {
			sc = new Scanner(linea);
			while (sc.hasNext()) {
				String palabra = sc.next();
				
				if (contador >= palabraInicial && contador <= palabraFinal) {
				//	System.err.println("contador: " + contador + ", palabra: " + palabra);
					stringBuilder.append(palabra);
					contadorNuevasLineas++;
					if (contadorNuevasLineas % 10 == 0)
						stringBuilder.append("\n");
					stringBuilder.append(" ");
				}
				contador++;
				if (contador == palabraFinal)
					break;
			}
		

		}
		sc.close();
		String texto = stringBuilder.toString();
		System.out.println("texto: " + texto);
		char ultimoCaracter = texto.charAt(texto.length() - 1);
		if (ultimoCaracter  == ' ' || ultimoCaracter == '\n' )
			texto = texto.substring(0, texto.length() - 1);
		// escribimos desde la línea especificada a la indica con write del api Files
		try {
			Files.writeString(backup_parcial, texto, StandardOpenOption.CREATE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	private static void copiarFichero() {
		// TODO Auto-generated method stub
		try {
			Files.copy(path, backup, StandardCopyOption.REPLACE_EXISTING);
			System.out.println("Copiado fichero");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static int contarPalabras(List<String> lineasLeidas) {
		// TODO Auto-generated method stub
		int contador = 0;
		//Scanner sc = null;
		for (String linea : lineasLeidas) {
			sc = new Scanner(linea);
			while (sc.hasNext()) {
				sc.next();
				contador++;
			}
		}
		sc.close();
		return contador;
	}

	private static int buscarPalabras(List<String> lineasLeidas, 
			String palabraABuscar) {
		// TODO Auto-generated method stub
		palabraABuscar = palabraABuscar.toLowerCase();
		//Scanner sc = null;
		String palabra = null;
		int contador = 0;
		for (String linea : lineasLeidas) {
			sc = new Scanner(linea);
			while (sc.hasNext()) {
				 palabra = sc.next().toLowerCase();
				 if (palabra.contains(palabraABuscar))
					 contador++;
				 
			}
		}
		sc.close();
		return contador;
	}

	private static List<String> leerFicheroTexto(Path path) {
		// TODO Auto-generated method stub
		try {
			return Files.readAllLines(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}

}
