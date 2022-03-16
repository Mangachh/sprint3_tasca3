package app;

import java.util.Stack;

import io.SaveLoad;
import items.Floristeria;
import menus.MainMenu;
import menus.MenuBase;

/**
 * Clase que controla la aplicación. Tiene un stack para los menús y conserva la referencia
 * de la floristeria.
 */
public class AppController {
	
	private Floristeria floristeria;
	private Stack<MenuBase> menus;
	private final int CLEAR_LINES = 15;
	
	/**
	 * Constructor
	 */
	public AppController() {
		this.menus = new Stack<MenuBase>();
	}
	
	/**
	 * Inicia y opera la app. Es el método llamado en el main
	 */
	public void initApp() {
		// cargamos el main menu y lo metemos en el stack
		MainMenu main = new MainMenu();
		Print print = new Print();
		this.menus.push(main);

		this.load();

		// mientras haya menús en el stack, el bucle seguirá funcionando
		while(this.menus.isEmpty() == false) {
			this.menus.peek().showMenu(print, this);
			print.printEmptyLines(this.CLEAR_LINES);
		}	

		// de momento guardamos al salir, comprobamos si la floristeria tiene nombre, si o tiene, existe
		if(this.floristeria.getName() != null){
			this.save();
		}
		
	}

	/**
	 * Carga una floristeria a partir de un archivo si este existe
	 */
	private void load(){
		SaveLoad s = new SaveLoad();
		this.floristeria = s.loadFloristeria();

		if(floristeria == null){
			this.floristeria = new Floristeria();
		}
		this.floristeria.setTicketList(s.loadTickets());
	}

	/**
	 * Guarda la floristeria en un archivo si esta existe
	 */
	private void save(){
		SaveLoad s = new SaveLoad();
		s.saveFloristeria(this.floristeria);
		s.saveTickets(this.floristeria);
	}

	/**
	 * Devuelve la floristeria.
	 * OJU! para evitar ciertos bugs la floristeria SIEMPRE existe aún cuando no se haya cagado 
	 * ni creado. Sin embargo, no podemos meter ni productos ni nada en ella. Para comprobar que 
	 * existe se mira si el nombre es null
	 * @return -> floristeria acutal.
	 */
	public Floristeria getFloristeria() {
		return this.floristeria;
	}
	
	/**
	 * Setea una floristeria. Utilizamos este método cuando cargamos.
	 * @param floristeria
	 */
	public void setFloristeria(final Floristeria floristeria) {
		this.floristeria = floristeria;
	}
	
	/**
	 * añade un {@link MenuBase} al stack de menus
	 * @param menu
	 */
	public void addToStack(final MenuBase menu) {
		this.menus.push(menu);
	}
	
	/** 
	 * Quita un {@link MenuBase} del stack si este es el que está en ejecución.
	 */
	public void removeFromStack(final MenuBase menu) {
		if(this.menus.peek() == menu) {
			this.menus.pop();
		}
	}
	
}
