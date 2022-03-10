package app;

import java.util.Stack;

import io.SaveLoad;
import items.Floristeria;
import menus.MainMenu;
import menus.MenuBase;

public class AppController {
	
	private Floristeria floristeria;
	private Stack<MenuBase> menus;
	private final int CLEAR_LINES = 15;
	
	public AppController() {
		this.menus = new Stack<MenuBase>();
	}
	
	public void initApp() {
		MainMenu main = new MainMenu();
		Print print = new Print();
		this.menus.push(main);

		SaveLoad s = new SaveLoad();
		this.floristeria = s.loadFloristeria();

		// aqui ponemos si es null o no
		// TODO: miraremos si es null o si no lo és, tonces podemos hacer un menú para crear la floristeria
		
		while(this.menus.isEmpty() == false) {
			this.menus.peek().showMenu(print, this);
			print.printEmptyLines(this.CLEAR_LINES);
		}	
	}
	
	public Floristeria getFloristeria() {
		return this.floristeria;
	}
	
	public void setFloristeria(final Floristeria floristeria) {
		this.floristeria = floristeria;
	}
	
	public void addToStack(final MenuBase menu) {
		this.menus.push(menu);
	}
	
	public void removeFromStack(final MenuBase menu) {
		if(this.menus.peek() == menu) {
			this.menus.pop();
		}
	}
	
}
