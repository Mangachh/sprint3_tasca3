package items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Clase para la floristeria.
 * Esta clase tiene un HashMap para guardar los items donde la clave es la {@link ItemBase.ITEM_ID} de cada 
 * tipo de item y el valor es una lista de los item de ese tipo.
 * También tiene una lista que guarda todos los {@link Ticket}
 */
public class Floristeria {
	private String name;
	private HashMap<String, List<ItemBase>> items;	
	private List<Ticket> tickets;

	/**
	 * Constructor vacío
	 */
	public Floristeria(){}

	/**
	 * Constructor
	 * @param name	-> nombre de la floristeria
	 */
	public Floristeria(final String name) {
		this.name = name;		
	}
	// inicialización de la lista y diccionario
	{
		this.items = new HashMap<String, List<ItemBase>>();
		this.tickets = new ArrayList<Ticket>();
	}	

	/**
	 * Añade un item a la lista {@link #items}
	 * @param item		-> item a añadir
	 */
	public void addItem(final ItemBase item){
		if(this.items.containsKey(item.getId()) == false){
			this.items.put(item.getId(), new ArrayList<ItemBase>());
		}

		items.get(item.getId()).add(item);
	}

	/**
	 * Clase que pilla un item del diccionario {@link #items} a partir de un id de item y un index 
	 * @param itemId -> id del item
	 * @param index  -> index en la lista
	 * @return		 -> itembase, null si no lo encuentra
	 */
	public ItemBase getIttem(final String itemId, int index) {
		if (this.items.containsKey(itemId) && this.items.get(itemId).size() > index) {
			return this.items.get(itemId).get(index);
		}

		return null;
	}	

	/**
	 * Devuelve un array-copia de las keys del diccionario {@link #items}
	 * @return -> arrayc on las keys
	 */
	public String[] getKeys() {
		String[] keys = new String[this.items.size()];
		keys = this.items.keySet().stream().toArray(String[]::new);
		return keys;
	}

	/**
	 * Devuelve una copia de la lista en {@link #items} key.	
	 * Así las operaciones de insertar y eliminar han de hacerse con los 
	 * propios métodos {@link #addItem(ItemBase, String)} y {@link #removeItem(String, ItemBase)} y no
	 * usar la lista que devuelve este método ya que es una nueva instancia
	 * @param id
	 * @return
	 */
	public List<ItemBase> getItemsFromID(final String id) {
		if (this.items.containsKey(id)) {
			return new ArrayList<ItemBase>(this.items.get(id));
		}
		return null;
	}

	/**
	 * Elimina un item de {@link #items} si existe
	 * @param item
	 */
	public void removeItem(final ItemBase item){
		if(this.items.containsKey(item.getId())){
			this.items.get(item.getId()).remove(item);
		}
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name){
		this.name = name;
	}

	/**
	 * Devuelve la cantidad de items de una id determinada
	 * @param key
	 * @return
	 */
    public int getItemCount(String key) {
        if(this.items.containsKey(key)){
			return this.items.get(key).size();
		}

		return 0;
    }

	/**
	 * Devuelve la cantidad de keys en {@link #items}
	 * @return
	 */
	public int getKeySize(){
		return this.items.size();
	}

	/**
	 * Añade un ticket a {@link #tickets}
	 * @param ticket -> ticket a añadir
	 */
	public void addTicket(final Ticket ticket){
		this.tickets.add(ticket);
	}

	/**
	 * Devuelve la cantidad de tickets 
	 * @return -> cantidad de tickets
	 */
	public int getTicketCount(){
		return this.tickets.size();
	}	

	/**
	 * Devuevle un ticket de {@link #tickets} por su índice
	 * @param index -> index que nos interesa
	 * @return		-> ticket
	 */
	public Ticket getTicketByIndex(int index){
		return this.tickets.get(index);
	}

	/**
	 * Setea la lista de tickets
	 * @param tickets -> tickets a setear
	 */
	public void setTicketList(final List<Ticket> tickets){
		this.tickets = tickets;

		if(this.tickets == null){
			this.tickets = new ArrayList<Ticket>();
		}
	}

	public List<Ticket> getTickets(){
		return this.tickets;
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
