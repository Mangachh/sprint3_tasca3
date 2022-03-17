package menus;

import app.AppController;
import app.Input;
import app.Print;
import items.Floristeria;

/**
 * Menu para crear una floristeria nueva.
 * Si ya existe una, la borra y la sustituye por esta
 */
public class CreateFloristeriaMenu extends MenuBase {
	
	private String menuHeader = "Creació floristeria";
	private final String TXT_NAME = "Introdueixi el nóm de la nova floristeria:";
	private final String TXT_ALREADY_EXIST = "Ja existeix una floristeria. Si continúa s'esborrarà aquesta floristeria i totes les seves dades";
	private final String TXT_CONTINUE = "Vol continuar? 1(sí), 0(no)";
	private final String TXT_NAME_SELECTED = "El nóm de la floristería será: %s";
	private final String TXT_NO_CREATION = "La floristeria no s'ha creat.";
	private final String TXT_CREATION = "La floristeria %s s'ha creat amb exit.";

	@Override
	public void showMenu(final Print p, final AppController controller) {
		p.printHeader(this.menuHeader);
		Floristeria f = null;
		
		if(controller.getFloristeria().getName() == null) {
			f = this.createFloristeria(p);
		}else {
			p.printLine(TXT_ALREADY_EXIST);
			p.printLine(TXT_CONTINUE);
			p.printEmpty();

			if(Input.isInputYesValue(VALUE_YES, VALUE_NO)) {
				f = this.createFloristeria(p);
			}
		}
		
		if(f == null) {
			p.printLine(TXT_NO_CREATION);
		}else {
			p.printLine(String.format(TXT_CREATION, f.getName()));
			controller.setFloristeria(f);
		}
		
		p.printLine("Presiona Enter per continuar");
		Input.getEnter();
		controller.removeFromStack(this);		
	}
	
	/**
	 * Menu para crear la floristeria
	 * @param p -> printeo
	 * @return  -> nueva floristeria
	 */
	private Floristeria createFloristeria(final Print p) {
		p.printLine(this.TXT_NAME);
		String name = Input.getString();
		p.printEmpty();

		p.printLine(String.format(this.TXT_NAME_SELECTED, name));
		p.printEmpty();
		p.printLine(this.TXT_CONTINUE);
		
		if(Input.isInputYesValue(VALUE_YES, VALUE_NO)) {
			return new Floristeria(name);
		}
		return null;
	}

}
