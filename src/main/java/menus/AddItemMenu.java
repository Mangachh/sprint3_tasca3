package menus;

import app.AppController;
import app.Input;
import app.Print;
import items.Arbre;
import items.Decoracio;
import items.Flor;
import items.ItemBase;
import items.ItemFactory;

/**
 * Menú para añadir items en una floristeria.
 * Como todos los items vienen de la misma clase {@link ItemBase} metemos los datos en dos partes:
 * Primero metemos los datos comunes (los que vienen de la clase padre) y luego
 * miramos qué item queremos crear y metemos los datos de ese item.
 * Es cierto que podríamos haber hecho estos subsistemas en otra clase (por ejemplo meter la entrada de cada clase en diferentes archivos)
 * pero me parecía más sencillo dejarlo así.
 */
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
			controller.getFloristeria().addItem(item);
			
			p.printLine(TXT_ANOTHER);
			inMenu = Input.isInputYesValue(VALUE_YES, VALUE_NO);
			p.printEmptyLines(10);
		}
		
		controller.removeFromStack(this);
		
	}
	
	/** 
	 * Introducte los datos de las clases hijas {@link Arbre}, {@link Decoracio}, {@link Flor}
	 * 
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
	
	/**
	 * Introduce los datos de la clase {@link Flor}
	 * @param item -> item a los que meter datos
	 * @param p	   -> clase de printeo
	 */
	private void setDecoracioData(Decoracio item, Print p) {
		p.printLine(TXT_SEL_TIPUS);	
		int option = -1;
		p.printOptions(Decoracio.TIPUS_DEC);
		
		option = Input.getIntInBetween(p.MENU_OFFSET, Decoracio.TIPUS_DEC.length);
		
		// restamos el menu offset para cuadrar con el array
		option -= p.MENU_OFFSET;
		item.setTipus(Decoracio.TIPUS_DEC[option]);		
	}


	/**
	 * Introduce los datos de la clase padre {@link ItemBase}
	 * @param item -> item a que meter los datos
	 * @param p	   -> printeo
	 */
	private void setSharedData(ItemBase item, final Print p) {
		p.printLine(TXT_NAME);
		item.setName(Input.getString());
		p.printEmpty();

		p.printLine(TXT_PRICE);
		item.setPrice(Input.getDouble());	
		p.printEmpty();

		p.printLine(TXT_AMOUNT);
		item.setQuantity(Input.getPositiveInt());
		p.printEmpty();
	}
	
	/**
	 * Setea los datos de la clase {@link Arbre}
	 * @param arbre
	 * @param p
	 */
	private void setArbreData(Arbre arbre, final Print p) {
		p.printLine(TXT_ALCADA);
		arbre.setHeight(Input.getDouble());
	}
	
	/**
	 * Setea los datos de la clase {@link Flor}
	 * @param flor
	 * @param p
	 */
	private void setFlorData(Flor flor, final Print p) {
		p.printLine(TXT_COLOR);
		flor.setColor(Input.getString());
	}
	
	/**
	 * Devuelve la opción de {@link #options} metida por input.
	 * @param p
	 * @param options
	 * @return
	 */
	private String selectOption(final Print p, final String[] options) {
		p.printOptions(options);
		int selection = Input.getIntInBetween(p.MENU_OFFSET, options.length);
		selection -= p.MENU_OFFSET;
		return options[selection];
	}

}
