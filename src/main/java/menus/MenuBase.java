package menus;

import app.AppController;
import app.Print;

public abstract class MenuBase {
	
	protected String menuHeader;
	protected final static int VALUE_YES = 1;
	protected final static int VALUE_NO = 0;
	
	public abstract void showMenu(final Print p, final AppController controller);
	
}
