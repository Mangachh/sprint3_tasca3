package items;

public abstract class ItemBase implements Cloneable {
	public static final String ITEM_ID = "Base";
	protected String name;
	protected double price;
	protected int quantity;
	
	public ItemBase() {};
	
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

	public Object clone() throws CloneNotSupportedException{
		return super.clone();
	}
	
	
}
