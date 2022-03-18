package items;

import java.util.Locale;

/**
 * Clase decoracio que implementa {@link ItemBase}
 */
public class Decoracio extends ItemBase {
	public static final String ITEM_ID = "Decoracio";
	/**
	 * Tipos de material para la decoración
	 */
	public static final String[] TIPUS_DEC = {"Fusta", "Plastic"};
	
	private String tipus;
	
	/**
	 * Constructor vacio
	 */
	public Decoracio() {};
	
	/**
	 * Constructor
	 * @param name		-> nombre decoración
	 * @param price		-> precio
	 * @param quantity	-> cantidad
	 * @param tipus		-> tipo de decoracion
	 */
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

	/**
	 * Utilizamos un orden básico que es:
	 * nombre, precio, cantitad, tipo
	 * Los precios se guardan y muestran con '.' para que sea compatible con el guardado
	 */
	@Override
	public String toString(){
		// utilizamos Locale.ROOT para que los decimales estén con un '.' (punto), así no fastidia para guardar
		return String.format(Locale.ROOT, "%s, %.2f, %d, %s", this.name, this.price, this.quantity, this.tipus);
	}



	
	
	

}
