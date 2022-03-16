package app;

/**
 * Clase para printear en consola.
 */
public class Print {
	/**
	 * Offset a partir del cual empieza la numeración de los menús en {@link #printOptions(String[])} y {@link #printOptions(String[], int)}
	 * Lo tenemos como public porque así cualquier clase puede saber qué offset tenemos para sumar ó restar
	 * 
	 */
	public final int MENU_OFFSET = 1;
	
	/**
	 * Printea una string como un header. Convierte en mayúsculas toda la string y la subraya
	 * @param header
	 */
	public void printHeader(final String header) {
		String value = String.valueOf(header.length() + 2);		
		
		String lines = String.format("%-" + value + "s", "").replace(' ', '-');
		System.out.println(lines);
		System.out.println(" " + header.toUpperCase());
		System.out.println(lines);
	}
	
	/**
	 * Printea una opción por línea numerada empezando con {@link #MENU_OFFSET}
	 * TODO: hacer una versión con lista
	 * @param options -> opciones a printear
	 */
	public void printOptions(final String[] options) {
		String format = "%2d.- %s\n";
		for(int i = 0; i < options.length; i++ ) {
			System.out.printf(format, i+1, options[i]);
		}
	}

	/**
	 * Printera "columns" opciones por línea, numeradas empezando con {@link #MENU_OFFSET}
	 * @param options -> opciones a printear
	 * @param columns -> columnas de printeo
	 */
	public void printOptions(final String[] options, int columns){
		String format = "%2d.- %s%5s";
		for(int i = 0; i < options.length; i++){
			if(i > 0 && i % columns == 0){
				System.out.println();
			}

			System.out.print(String.format(format, i +1, options[i], ""));
			if(i == options.length - 1){
				System.out.println();
			}
		}
	}
	
	/**
	 * Printea una línea
	 * @param toPrint -> linea a printear
	 */
	public void printLine(final String toPrint) {
		System.out.println(toPrint);
	}
	
	/**
	 * Printea una línea vacía
	 */
	public void printEmpty() {
		System.out.println();
	}
	
	/**
	 * Printea una lista no numerada con un cabezal a escoger
	 * @param head -> cabezal del item
	 * @param line -> linea a rintear
	 */
	public void printListLine(final String head, final String line) {
		String formatted = String.format("%s%s", head, line);
		System.out.println(formatted);
	}

	/**
	 * Printea "amount" líneas vacías
	 * @param amount -> número de líneas vacías a printear
	 */
	public void printEmptyLines(int amount) {
		for(int i = 0; i < amount; i++) {
			System.out.println();
		}
	}
}
