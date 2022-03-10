package items;

public class Arbre extends ItemBase {
	public static final String ITEM_ID = "Arbre";
	private double height;
	
	public Arbre() {};
	
	public Arbre(String name, double price, double height) {
		super(name, price);
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
	
}
