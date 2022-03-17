package menus;

import app.AppController;
import app.Input;
import app.Print;
import items.ItemBase;

/**
 * Menú que imprime los productos que hay en stock
 */
public class ShowItemsMenu extends MenuBase{
	private final String[] OPTIONS = {"Imprimir productes disponibles (en stock)", "Imprimir tots el productes amb l'stock", "Imprimir valor total dels productes", "Sortir del menú"};
	private final String TXT_CONTINUE = "Pressioni Enter per continuar";
	private final String ORDER_HEAD = "-."; 

	// basamos estos valores en el array OPTIONS
	private final byte IMPRIMIR_DISPONIBLES = 0;
	private final byte IMPRIMIR_TOT = 1;
	private final byte IMPRIMIR_TOTAL_VALOR = 2;
	private final byte IMPRIMIR_SORTIDA = 3;

	
	public ShowItemsMenu(){
		this.menuHeader = "Impressió d'stocks";
	}

	@Override
	public void showMenu(Print p, AppController controller) {
		int option = -1;
		p.printHeader(this.menuHeader);
		p.printOptions(this.OPTIONS);

		// quitamos el offset para que la opción sea la correcta
		option = Input.getIntInBetween(p.MENU_OFFSET, this.OPTIONS.length) - p.MENU_OFFSET;
		IShowItems shower = getShowItem(option);
		shower.printItems(controller, p);
		p.printEmpty();
		p.printLine(TXT_CONTINUE);
		Input.getEnter();

		// esto lo quitaremos de aquí.. 
	}

	/**
	 * Devuelve una instancia de IShowItems(que no es más que un delegate) a partir de la opción introducida por el usuario
	 * @param option -> option
	 * @return		 -> método a ejecutar
	 */
	private IShowItems getShowItem(int option){
		IShowItems show = null;
		switch(option){
			case IMPRIMIR_DISPONIBLES:
				show = (controller, p) -> this.printAvaliableProducts(controller, p);
				break;
			case IMPRIMIR_TOT:
				show = (controller, p) -> this.printProductsWithStocks(controller, p);
				break;
			case IMPRIMIR_TOTAL_VALOR:
				show = (controller, p) -> this.printShopValue(controller, p);
				break;
			case IMPRIMIR_SORTIDA: 
				show = (controller, p) -> this.exitMenu(controller, p);
		}

		return show;
	}

	/**
	 * Printea en pantalla los articulos que están en stock
	 * @param controller -> controller
	 * @param p			 -> printeo
	 */
	private void printAvaliableProducts(final AppController controller, final Print p){
		
		for(String key: controller.getFloristeria().getKeys()) {
			p.printHeader(key);
			for(ItemBase item: controller.getFloristeria().getItemsFromID(key)) {
				if(item.getQuantity() > 0) {
					p.printListLine(this.ORDER_HEAD, item.getName());
				}				
			}		
		}		
	}

	/**
	 * Printea los productos que están en stock con su stock
	 * @param controller
	 * @param p
	 */
	private void printProductsWithStocks(final AppController controller, final Print p){
		final String formatter = "Producte: %s, Stock: %d";

		for(String key: controller.getFloristeria().getKeys()){
			p.printHeader(key);
			for(ItemBase item: controller.getFloristeria().getItemsFromID(key)){
				p.printListLine(ORDER_HEAD, String.format(formatter, item.getName(), item.getQuantity()));
			}
		}
	}

	/**
	 * Printea el valor total de la tienda
	 * @param controller
	 * @param p
	 */
	private void printShopValue(final AppController controller, final Print p){
		double value = -1;
		for(String key: controller.getFloristeria().getKeys()){
			for(ItemBase item: controller.getFloristeria().getItemsFromID(key)){
				value += item.getQuantity() * item.getPrice();
			}
		}

		p.printLine(String.format("El valor total dels productes de la tenda és: %.2f", value));
	}

	/**
	 * Método al que llamar cuando se sale del menú
	 * @param controller
	 * @param p
	 */
	private void exitMenu(final AppController controller, final Print p){
		p.printLine("Sortint del menú actual...");
		controller.removeFromStack(this);
	}

}
