package app;

public class Print {
	public final int MENU_OFFSET = 1;
	
	public void printHeader(final String header) {
		String value = String.valueOf(header.length() + 2);		
		
		String lines = String.format("%-" + value + "s", "").replace(' ', '-');
		System.out.println(lines);
		System.out.println(" " + header.toUpperCase());
		System.out.println(lines);
	}
	
	public void printOptions(final String[] options) {
		String format = "%2d.- %s\n";
		for(int i = 0; i < options.length; i++ ) {
			System.out.printf(format, i+1, options[i]);
		}
	}
	
	public void printLine(final String toPrint) {
		System.out.println(toPrint);
	}
	
	public void printEmpty() {
		System.out.println();
	}
	
	public void printListLine(final String head, final String line) {
		String formatted = String.format("%s%s", head, line);
		System.out.println(formatted);
	}
	public void printEmptyLines(int amount) {
		for(int i = 0; i < amount; i++) {
			System.out.println();
		}
	}
}
