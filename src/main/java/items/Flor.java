package items;

import java.util.Locale;

/**
 * Clase decoracio que implementa {@link ItemBase}
 */
public class Flor extends ItemBase {
	public static final String ITEM_ID = "Flor";
	private String color;
	
	/**
	 * Constructor vacío
	 */
	public Flor() {};
	
	/**
	 * Constructor
	 * @param name		-> nombre
	 * @param price		-> precio
	 * @param quantity	-> cantidad
	 * @param color		-> color
	 */
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
	
	/**
	 * Utilizamos un orden básico que es:
	 * nombre, precio, cantitad, color
	 * Los precios se guardan y muestran con '.' para que sea compatible con el guardado
	 */
	@Override
	public String toString(){
		return String.format(Locale.ROOT, "%s, %.2f, %d, %s", this.name, this.price, this.quantity, this.color);
	}

}
