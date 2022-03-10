package menus;

import app.AppController;
import app.Input;
import app.Print;

public class MainMenu extends MenuBase {

	private String[] options = { "Crear floristeria", "Afegir producte", "Impressió de productes", "Eliminar producte",
			"Impressió d'stock", "Impressió de valor", "Ticket de compra", "Historic de compres", "Total de compres",
			"Sortir de l'aplicació" };

	public MainMenu() {
		this.menuHeader = "Menú principal";
	}

	@Override
	public void showMenu(final Print p, final AppController controller) {		
		int option = -1;

		p.printHeader(this.menuHeader);

		p.printOptions(options);
		option = Input.getIntInBetween(p.MENU_OFFSET, options.length);
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
		case 1:
			return new CreateFlorMenu();
		case 2:
			return new AddItemMenu();
		case 3:
			return new ShowItemsMenu();
		case 4:
			break;
		case 5:
			break;
		case 6:
			break;
		case 7:
			break;
		case 8:
			break;
		case 9:
			break;
		case 10:
			break;
		}
		return null;
	}

}
