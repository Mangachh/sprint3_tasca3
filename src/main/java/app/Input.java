package app;

import java.util.Scanner;

/**
 * Clase para los inputs de consola
 */
public class Input {
	
	private final static String HEAD = ">> ";
	private final static Scanner sc = new Scanner(System.in);
	
	/**
	 * Pilla una string de la consola siempre y cuando esta no esté vacía
	 * @return
	 */
	public static String getString() {		
		String input = "";
		while(true) {
			printHead();
			input = sc.nextLine();
			if(input.isEmpty() == false) {
				return input;
			}
		}
	}	
	
	/**
	 * Pilla un int de la consola
	 * @return
	 */
	public static int getInt() {
		int input = -1;
		while(true) {
			printHead();
			try {
				input = Integer.parseInt(sc.nextLine());
				return input;
			}catch(Exception e) {
				System.out.println("Entrada no vàlida. Només están permessos números enters");
			}				
		}
	}
	
	/**
	 * Pilla un int de la consola SIEMPRE que sea positivo
	 * @return
	 */
	public static int getPositiveInt() {
		int input = -1;
		while(true) {
			input = getInt();
			if(input > -1) {
				return input;
			}else {
				System.out.println("Només s'accepten enters positius ó número 0");
			}
		}
	}
	
	/**
	 * Pilla un int de la consola ENTRE los valores min y max
	 * @param min -> valor mínimo aceptado como input
	 * @param max -> valor máximo aceptado como input
	 * @return    -> valor entre min y max, ambos incluidos
	 */
	public static int getIntInBetween(int min, int max) {
		while(true) {
			int input = getInt();
			if(input >= min && input <= max) {
				return input;
			}
			System.out.println(String.format("Només son vàlids els números entre %d i %d", min, max));
		}
	}
	
	/**
	 * Devuelve true si el input es yesValue, false si es noValue. En caso 
	 * de no ser ninguno de estos dos sigue preguntando
	 * @param yesValue -> valor aceptado como yes
	 * @param noValue  -> valor aceptado como no
	 * @return         -> es valor yes?
	 */
	public static boolean isInputYesValue(int yesValue, int noValue) {
		while(true) {
			int input = getInt();
			if(input == yesValue || input == noValue) {
				return input == yesValue;
			}else {
				System.out.println(String.format("Introdueix %d per sí, %d per no", yesValue, noValue));
			}
		}
	}
	
	/**
	 * Printea la cabeza del input
	 */
	private static void printHead() {
		System.out.print(HEAD);
	}

	/**
	 * Pilla si se ha presionado la tecla enter
	 * Tal como funciona el scanner, obviamente si se escribe se mostrará en la pantalla
	 * de la consola y demás, sin embargo, no hacemos nada con esa string.
	 */
	public static void getEnter() {
		//printHead();
		sc.nextLine();		
	}

	/**
	 * Pilla un double de la consola
	 * @return -> double de input
	 */
	public static double getDouble() {
		double input = -1;
		while(true) {
			printHead();
			try {
				input = Double.parseDouble(sc.nextLine());
				return input;
			}catch(Exception e) {
				System.out.println("Entrada no vàlida. Només están permessos números decimals");
			}				
		}
	}
	
	
}
