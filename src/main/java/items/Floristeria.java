package items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Floristeria {
	private String name;
	private HashMap<String, List<ItemBase>> items;

	public Floristeria(){}

	public Floristeria(final String name) {
		this.name = name;		
	}
	{
		this.items = new HashMap<String, List<ItemBase>>();
	}

	public void addItem(final ItemBase item, final String itemID) {
		if (this.items.containsKey(itemID) == false) {
			this.items.put(itemID, new ArrayList<ItemBase>());
		}

		items.get(itemID).add(item);

	}

	public ItemBase getIttem(final String itemId, int index) {
		if (this.items.containsKey(itemId) && this.items.get(itemId).size() > index) {
			return this.items.get(itemId).get(index);
		}

		return null;
	}

	public String[] getKeys() {
		String[] keys = new String[this.items.size()];
		keys = this.items.keySet().stream().toArray(String[]::new);
		return keys;
	}

	public List<ItemBase> getItemsFromID(final String id) {
		if (this.items.containsKey(id)) {
			return this.items.get(id);
		}

		return null;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name){
		this.name = name;
	}

    public int getItemCount(String key) {
        if(this.items.containsKey(key)){
			return this.items.get(key).size();
		}

		return 0;
    }
}
