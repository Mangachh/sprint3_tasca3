package menus;

import app.AppController;
import app.Print;

/**
 * Clase básica de los menú. Tiene los valores de sí y no así como un header.
 * Realmente lo podríamos haber hecho con una interfaz o así, pero ya lo tenía
 * todo aplicado y preferí dejarlo de esta manera. 
 */
public abstract class MenuBase {
	
	protected String menuHeader;
	protected final static int VALUE_YES = 1;
	protected final static int VALUE_NO = 0;
	
	public abstract void showMenu(final Print p, final AppController controller);
	
}
