package menus;

import app.AppController;
import app.Input;
import app.Print;

/**
 * Menu principal de la aplicación si existe una floristeria
 */
public class MainMenu extends MenuBase {
	/**
	 * Opciones que se muestran SI la floristeria existe
	 */
	private String[] optionsFull = { "Crear floristeria", "Afegir producte", "Eliminar producte",
			"Impressió de productes",
			"Ticket de compra", "Historic de compres", "Total de compres",
			"Sortir de l'aplicació" };

	/**
	 * Opciones que se muestran SI la floristeria NO existe
	 */
	private String[] optionsNo = { "Crear Floristeria", "Sortir de l'aplicació" };

	private final String NO_FLOR = "Benvingud@! No existeix una floristeria. Si us plau, seleccioni la opció adient per tal de crear-ne una";

	private final String SI_FLOR = "Benvigud@ a la floristeria %s! Seleccioni una opció:";
	// se puede hacer con un enum, pero no me acaban de gustar los enums de java...
	private final int OPTION_CREATE = 0;
	private final int OPTION_ADD_ITEM = 1;
	private final int OPTION_REMOVE_ITEM = 2;
	private final int OPTION_PRINT = 3;
	private final int OPTION_TICKET = 4;
	private final int OPTION_HISTORIC = 5;
	private final int OPTION_TOTAL = 6;
	private final int OPTION_EXIT = 7;

	public MainMenu() {
		this.menuHeader = "Menú principal";
	}
	
	@Override
	public void showMenu(final Print p, final AppController controller) {
		MenuBase next = null;

		p.printHeader(this.menuHeader);

		if (controller.getFloristeria().getName() == null) {
			p.printLine(this.NO_FLOR);
			next = this.getFromNoFlorMenu(p, controller);
		} else {
			p.printLine(String.format(this.SI_FLOR, controller.getFloristeria().getName()));
			next = this.getFromFullMenu(p, controller);
		}

		if (next != null) {
			controller.addToStack(next);
		} else {
			controller.removeFromStack(this);
		}
	}

	/**
	 * 
	 * Menú que se muestra SI existe una floristeria.
	 * Este menú muestra todas las opciones que tenemos disponibles.
	 * De nuevo, dichos submenus se podrían hacer por separado, pero era
	 * más sencillo dejarlo así.
	 * 
	 * @param p		-> printeo
	 * @param cont	-> controlador de la app
	 * @return		-> siguiente menú
	 */
	private MenuBase getFromFullMenu(final Print p, final AppController cont) {
		int option = -1;
		p.printEmpty();
		p.printOptions(optionsFull);
		option = Input.getIntInBetween(p.MENU_OFFSET, optionsFull.length) - p.MENU_OFFSET;
		return this.fullMenuByNum(option);
	}

	/**
	 * Menú que se muestra SI NO existe una floristeria. Sólo tiene dos opciones y
	 * lo hacemos más que nada para evitar que se introduzcan datos
	 * @param p		-> clase de printeo
	 * @param cont	-> controllador de la app	
	 * @return		-> siguiente menú
	 */
	private MenuBase getFromNoFlorMenu(final Print p, final AppController cont) {
		int option = -1;
		p.printEmpty();
		p.printOptions(optionsNo);
		option = Input.getIntInBetween(p.MENU_OFFSET, optionsFull.length) - p.MENU_OFFSET;

		if (option == OPTION_CREATE) {
			return new CreateFloristeriaMenu();
		}

		return null;
	}

	/**
	 * Devuelve un menú a partir de un número. el número está introducido por el usuario
	 * y el orden depende tanto del {@link #optionsFull} como de las constantes {@link #OPTION_CREATE} y siguientes.
	 * No es la solución más elegante, pero sirve y es fácil de mantener. 
	 * La otra opción era crear un array o una lista con los menús.
	 * @param option -> opción de menú
	 * @return		 -> menú o null si no existe la opción
	 */
	public MenuBase fullMenuByNum(int option) {
		switch (option) {
			case OPTION_CREATE:
				return new CreateFloristeriaMenu();
			case OPTION_ADD_ITEM:
				return new AddItemMenu();
			case OPTION_REMOVE_ITEM:
				return new RemoveItemMenu();
			case OPTION_PRINT:
				return new ShowItemsMenu();
			case OPTION_TICKET:
				return new CreateTicket();
			case OPTION_HISTORIC:
				return new ShowTickets();
			case OPTION_TOTAL:
				return new TotalTickets();
			case OPTION_EXIT:
				break;
		}
		return null;
	}

}
