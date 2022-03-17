package items;


/**
 * Clase base para todos los items
 */
public abstract class ItemBase implements Cloneable {
	public static final String ITEM_ID = "Base";
	protected String name;
	protected double price;
	protected int quantity;
	
	/**
	 * Constructor vacío
	 */
	public ItemBase() {};
	
	/**
	 * Constructor
	 * @param name		-> nombre del item
	 * @param price		-> precio del item
	 * @param quantity	-> cantidad del item
	 */
	public ItemBase(final String name, double price, int quantity) {
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public String getId() {
		return ITEM_ID;
	}

	/**
	 * Método que clona al objeto
	 * @returns -> objeto clonado
	 */
	public Object clone() throws CloneNotSupportedException{
		return super.clone();
	}
	
	
}
