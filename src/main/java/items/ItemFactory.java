package items;

public class ItemFactory {
	
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
