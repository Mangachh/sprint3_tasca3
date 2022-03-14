package menus;

import app.AppController;
import app.Input;
import app.Print;
import items.ItemBase;

public class RemoveItemMenu extends MenuBase{

    public RemoveItemMenu(){
        this.menuHeader = "Eliminar Item";
    }

    @Override
    public void showMenu(Print p, AppController controller) {
        p.printHeader(this.menuHeader);
        SelectItemSubMenu itemMenu = new SelectItemSubMenu();
        ItemBase toRemove = itemMenu.getItemFromOptions(p, controller, true);

        if(toRemove != null){
            controller.getFloristeria().removeItem(toRemove.getId(), toRemove);            
        }else{
            controller.removeFromStack(this);
        }

        p.printLine("Presione Enter para continuar...");
        Input.getEnter();
        
    }
    
}
