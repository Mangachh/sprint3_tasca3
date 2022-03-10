package menus;

import app.AppController;
import app.Input;
import app.Print;
import items.Arbre;
import items.Decoracio;
import items.Flor;
import items.ItemBase;
import items.ItemFactory;

public class AddItemMenu extends MenuBase{
	private String TXT_NAME = "Introdueix el nom del producte:";
	private String TXT_PRICE = "Introdueix el preu del producte:";
	private String TXT_TYPE = "Introdueix el tipus de producte:";
	private String TXT_ALCADA = "Introdueix l'alçada de l'arbre:";
	private String TXT_COLOR = "Introdueix el color de la flor:";
	private String TXT_SEL_TIPUS = "Seleccioni el tipus de decoració:";
	private String TXT_ANOTHER = "Vol introduir un nou producte? 1(sí) 0(no)";
	private String TXT_AMOUNT= "Introdueix la quantitat d'stock";
	
	private String[] options = {Arbre.ITEM_ID, Decoracio.ITEM_ID, Flor.ITEM_ID};

	@Override
	public void showMenu(final Print p, final AppController controller) {
		boolean inMenu = true;
		
		while(inMenu) {
			p.printLine(TXT_TYPE);		
			String optionId = this.selectOption(p, this.options);
			ItemBase item = new ItemFactory().generateItem(optionId);
			this.setSharedData(item, p);		
			this.setParticularData(item, p, optionId);
			controller.getFloristeria().addItem(item, optionId);
			
			p.printLine(TXT_ANOTHER);
			inMenu = Input.isInputYesValue(VALUE_YES, VALUE_NO);
			p.printEmptyLines(10);
		}
		
		controller.removeFromStack(this);
		
	}
	
	/* TODO: Todo esto lo podríamos meter en diferentes clases y llamarlas cuando sea necesario.
	 * Así podríamos aprovechar el mismo código para modificar los items. Sin embargo como
	 * el ejercicio no pide modificar los items es complicar un poco el código (por ahora)
	 */
	private void setParticularData(ItemBase item, final Print p, final String optionID) {
		if(optionID.equals(Arbre.ITEM_ID)) {
			this.setArbreData((Arbre)item, p);
		}else if(optionID.equals(Flor.ITEM_ID)) {
			this.setFlorData((Flor)item, p);
		}else if(optionID.equals(Decoracio.ITEM_ID)) {
			this.setDecoracioData((Decoracio)item, p);
		}
	}
	
	private void setDecoracioData(Decoracio item, Print p) {
		p.printLine(TXT_SEL_TIPUS);	
		int option = -1;
		p.printOptions(Decoracio.TIPUS_DEC);
		
		option = Input.getIntInBetween(p.MENU_OFFSET, Decoracio.TIPUS_DEC.length);
		
		// restamos el menu offset para cuadrar con el array
		option -= p.MENU_OFFSET;
		item.setTipus(Decoracio.TIPUS_DEC[option]);		
	}

	private void setSharedData(ItemBase item, final Print p) {
		p.printLine(TXT_NAME);
		item.setName(Input.getString());
		p.printLine(TXT_PRICE);
		item.setPrice(Input.getDouble());		
		p.printLine(TXT_AMOUNT);
		item.setQuantity(Input.getPositiveInt());
	}
	
	private void setArbreData(Arbre arbre, final Print p) {
		p.printLine(TXT_ALCADA);
		arbre.setHeight(Input.getDouble());
	}
	
	private void setFlorData(Flor flor, final Print p) {
		p.printLine(TXT_COLOR);
		flor.setColor(Input.getString());
	}
	
	private String selectOption(final Print p, final String[] options) {
		p.printOptions(options);
		int selection = Input.getIntInBetween(p.MENU_OFFSET, options.length);
		selection -= p.MENU_OFFSET;
		return options[selection];
	}

}
