package items;

public class Decoracio extends ItemBase {
	public static final String ITEM_ID = "Decoracio";
	public static final String[] TIPUS_DEC = {"Fusta", "Plàstic"};
	
	private String tipus;
	
	public Decoracio() {};
	
	public Decoracio(String name, double price, final String tipus) {
		super(name, price);
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



	
	
	

}
