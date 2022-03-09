package app;

import java.util.Scanner;

public class Input {
	
	private final static String HEAD = ">>";
	private final static Scanner sc = new Scanner(System.in);
	
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
	
	public static int getIntInBetween(int min, int max) {
		while(true) {
			int input = getInt();
			if(input >= min && input <= max) {
				return input;
			}
			System.out.println(String.format("Només son vàlids els números entre %d i %d", min, max));
		}
	}
	
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
	
	private static void printHead() {
		System.out.print(HEAD);
	}

	public static void getEnter() {
		printHead();
		sc.nextLine();		
	}
	
	
}
