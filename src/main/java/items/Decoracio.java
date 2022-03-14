package items;

import java.util.Locale;

public class Decoracio extends ItemBase {
	public static final String ITEM_ID = "Decoracio";
	public static final String[] TIPUS_DEC = {"Fusta", "Plàstic"};
	
	private String tipus;
	
	public Decoracio() {};
	
	public Decoracio(String name, double price, int quantity, final String tipus) {
		super(name, price, quantity);
		this.tipus = tipus;
	}
	
	public String getTipus() {
		return this.tipus;
	}
	
	public void setTipus(final String tipus) {
		this.tipus = tipus;
	}

	@Override
	public String getId(){
		return Decoracio.ITEM_ID;
	}

	@Override
	public String toString(){
		// utilizamos Locale.ROOT para que los decimales estén con un '.' (punto), así no fastidia para guardar
		// TODO: quizás hacer un toString sólo para guardar?
		return String.format(Locale.ROOT, "%s, %f, %d, %s", this.name, this.price, this.quantity, this.tipus);
	}



	
	
	

}
