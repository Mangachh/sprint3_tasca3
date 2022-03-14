package items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Floristeria {
	private String name;
	private HashMap<String, List<ItemBase>> items;

	// vale, para no complicarnos en demasía, el ticket será un diccionario con un String y el valor total (mierda! tendría que ponerle un id!)
	// nada, haremos la clase ticket :'(
	// esto es demasiado curro para uno
	private List<Ticket> tickets;
	public Floristeria(){}

	public Floristeria(final String name) {
		this.name = name;		
	}
	{
		this.items = new HashMap<String, List<ItemBase>>();
		this.tickets = new ArrayList<Ticket>();
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

	/**
	 * Devuelve una COPIA de la lista de items. Las operaciones de insertar y eliminar han de hacerse con los 
	 * propios métodos {@link #addItem(ItemBase, String)} y {@link #removeItem(String, ItemBase)} 
	 * @param id
	 * @return
	 */
	public List<ItemBase> getItemsFromID(final String id) {
		if (this.items.containsKey(id)) {
			return new ArrayList<ItemBase>(this.items.get(id));
		}
		return null;
	}

	public void removeItem(final String id, final ItemBase item){
		if(this.items.containsKey(id)){
			this.items.get(id).remove(item);
		}
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

	public int getKeySize(){
		return this.items.size();
	}


	public void addTicket(final Ticket ticket){
		this.tickets.add(ticket);
	}

	public Ticket getTicketByIndex(int index){
		return this.tickets.get(index);
	}

	public ItemBase getItemByName(final String name){
		for(String key: this.getKeys()){
			for(ItemBase itt: this.items.get(key)){
				if(itt.getName().equalsIgnoreCase(name)){
					return itt;
				}
			}
		}

		return null;
	}

	public ItemBase getItemByName(final String id, final String name){
		if(this.items.containsKey(id)){
			for(ItemBase itt: this.items.get(id)){
				if(itt.getName().equalsIgnoreCase(name)){
					return itt;
				}
			}
		}

		return null;
	}

}
