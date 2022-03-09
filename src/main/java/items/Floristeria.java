package items;

import java.util.HashMap;

public class Floristeria {
	private String name;
	private HashMap<String, ItemBase> items;
	
	public Floristeria(final String name) {
		this.name = name;
		this.items = new HashMap<String, ItemBase>();
	}
	
	public void addItem(final ItemBase item) {
		
	}
	
	public String getName() {
		return this.name;
	}
}
