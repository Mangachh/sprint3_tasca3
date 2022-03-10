package menus;

import app.AppController;
import app.Input;
import app.Print;
import items.ItemBase;

public class ShowItemsMenu extends MenuBase{
	
	private final String TXT_CONTINUE = "Pressioni Enter per continuar";
	private final String ORDER_HEAD = "-."; 

	@Override
	public void showMenu(Print p, AppController controller) {
		p.printLine("Yeeeeelooooow");
		for(String key: controller.getFloristeria().getKeys()) {
			p.printHeader(key);
			for(ItemBase item: controller.getFloristeria().getItemsFromID(key)) {
				if(item.getQuantity() > 0) {
					p.printListLine(this.ORDER_HEAD, item.getName());
				}
				
			}
			p.printEmpty();
		}
		p.printLine(TXT_CONTINUE);
		Input.getEnter();
		controller.removeFromStack(this);		
	}

}
