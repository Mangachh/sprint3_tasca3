package items;

/**
 * Factoria para spawnear items
 */
public class ItemFactory {
	
	/**
	 * Genera un item determinado a partir de la id que se pasa
	 * @param itemID -> id del item que queremos
	 * @return		 -> item que corresponde a esa id
	 */
	public ItemBase generateItem(final String itemID) {
		if(itemID.equalsIgnoreCase(Arbre.ITEM_ID)) {
			return new Arbre();
		}else if(itemID.equalsIgnoreCase(Decoracio.ITEM_ID)) {
			return new Decoracio();
		}else if(itemID.equalsIgnoreCase(Flor.ITEM_ID)) {
			return new Flor();
		}
		
		return null;
	}

}
