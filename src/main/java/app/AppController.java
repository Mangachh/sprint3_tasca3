package app;

import items.Floristeria;
import menus.MainMenu;
import menus.Print;

public class AppController {
	
	private Floristeria floristeria;
	
	public void initApp() {
		MainMenu main = new MainMenu();
		Print print = new Print();
		main.showMenu(print, this);
	}
	
	public Floristeria getFloristeria() {
		return this.floristeria;
	}
	
	public void setFloristeria(final Floristeria floristeria) {
		this.floristeria = floristeria;
	}
}
