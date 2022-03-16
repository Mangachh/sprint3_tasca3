package menus;

import app.AppController;
import app.Input;
import app.Print;
import items.Floristeria;

public class ShowTickets extends MenuBase {

    public ShowTickets(){
        this.menuHeader = "Historic Tickets";
    }

    @Override
    public void showMenu(Print p, AppController controller) {
        p.printHeader(this.menuHeader);
        Floristeria floristeria = controller.getFloristeria();
        for(int i = 0; i < floristeria.getTicketCount(); i++){
            p.printLine(floristeria.getTicketByIndex(i).formattedTicket());
            p.printEmptyLines(3);
        }

        p.printLine("Presioni Enter per continuar...");
        Input.getEnter();
        controller.removeFromStack(this);
        
    }
    
}
