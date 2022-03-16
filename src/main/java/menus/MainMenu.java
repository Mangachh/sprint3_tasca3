package menus;

import app.AppController;
import app.Input;
import app.Print;

public class MainMenu extends MenuBase {
	// TODO : todo lo que sea de impresión lo haremos en un submenú
	private String[] options = { "Crear floristeria", "Afegir producte", "Eliminar producte", "Impressió de productes",
			"Ticket de compra", "Historic de compres", "Total de compres",
			"Sortir de l'aplicació" };

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
		int option = -1;	

		p.printHeader(this.menuHeader);

		if(controller.getFloristeria().getName() == null){
			p.printLine(this.NO_FLOR);
		}else{
			p.printLine(String.format(this.SI_FLOR, controller.getFloristeria().getName()));
		}
		p.printEmpty();
		p.printOptions(options);
		option = Input.getIntInBetween(p.MENU_OFFSET, options.length) - p.MENU_OFFSET;
		MenuBase next = this.fullMenuByNum(option);
		if (next != null) {
			controller.addToStack(next);
		} else {
			controller.removeFromStack(this);
		}
	}
	
	// TODO: no me gusta depender de magic numbers..., ¿quizás un array?
	public MenuBase fullMenuByNum(int option) {
		switch (option) {
		case OPTION_CREATE:
			return new CreateFlorMenu();
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
