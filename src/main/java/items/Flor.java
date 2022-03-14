package items;

import java.util.Locale;

public class Flor extends ItemBase {
	public static final String ITEM_ID = "Flor";
	private String color;
	
	public Flor() {};
	
	public Flor(String name, double price, int quantity, final String color) {
		super(name, price, quantity);
		this.color = color;		
	}
	
	public String getColor() {
		return this.color;
	}
	
	public void setColor(final String color) {
		this.color = color;
	}

	@Override
	public String getId(){
		return Flor.ITEM_ID;
	}
	
	@Override
	public String toString(){
		return String.format(Locale.ROOT, "%s, %f, %d, %s", this.name, this.price, this.quantity, this.color);
	}

}
