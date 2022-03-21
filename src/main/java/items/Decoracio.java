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
	public static final String[] MATERIAL_DEC = {"Fusta", "Plastic"};
	
	private String material;
	
	/**
	 * Constructor vacio
	 */
	public Decoracio() {};
	
	/**
	 * Constructor
	 * @param name		-> nombre decoración
	 * @param price		-> precio
	 * @param quantity	-> cantidad
	 * @param material		-> tipo de decoracion
	 */
	public Decoracio(String name, double price, int quantity, final String material) {
		super(name, price, quantity);
		this.material = material;
	}
	
	public String getMaterial() {
		return this.material;
	}
	
	public void setMaterial(final String material) {
		this.material = material;
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
		return String.format(Locale.ROOT, "%s, %.2f, %d, %s", this.name, this.price, this.quantity, this.material);
	}



	
	
	

}
