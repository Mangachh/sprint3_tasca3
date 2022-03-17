package items;

import java.util.Locale;

/**
 * Clase arbol que implementa {@link ItemBase}
 */
public class Arbre extends ItemBase {
	/**
	 * Id de la clase
	 */
	public static final String ITEM_ID = "Arbre";
	private double height;
	
	/**
	 * Constructor vacio
	 */
	public Arbre() {};
	
	/**
	 * Constructor
	 * @param name		-> nombre del item
	 * @param price		-> precio del item
	 * @param quantity	-> cantidad de stock
	 * @param height	-> altura del arbol
	 */
	public Arbre(String name, double price, int quantity, double height) {
		super(name, price, quantity);
		this.height = height;
	}
	
	public double getHeight() {
		return this.height;
	}
	
	public void setHeight(double height) {
		this.height = height;
	}

	@Override
	public String getId(){
		return Arbre.ITEM_ID;
	}

	/**
	 * Utilizamos un orden básico que es:
	 * nombre, precio, cantitad, altura
	 * Los precios y alturas se guardan y muestran con '.' para que sea compatible con el guardado
	 */
	@Override
	public String toString(){
		return String.format(Locale.ROOT, "%s, %.2f, %d, %.3f", this.name, this.price, this.quantity, this.height);
	}
	
}
