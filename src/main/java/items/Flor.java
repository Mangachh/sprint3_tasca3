package items;

public class Flor extends ItemBase {
	public static final String ITEM_ID = "Flor";
	private String color;
	
	public Flor() {};
	
	public Flor(String name, double price, final String color) {
		super(name, price);
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

}
