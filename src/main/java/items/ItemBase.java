package items;

public abstract class ItemBase {
	public static final String ITEM_ID = "Base";
	private String name;
	private double price;
	private int quantity;
	
	public ItemBase(final String name, double price) {
		this.name = name;
		this.price = price;
		this.quantity = 0;
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
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
	
}