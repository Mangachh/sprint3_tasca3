package menus;

import java.util.List;

import app.AppController;
import app.Input;
import app.Print;
import items.Ticket;

/**
 * Menú que imprime por pantalla el total de vendas
 */
public class TotalTickets extends MenuBase {

    public TotalTickets(){
        this.menuHeader = "Total guanyat en vendes.";
    }
    @Override
    public void showMenu(Print p, AppController controller) {
        p.printHeader(this.menuHeader);
        List<Ticket> tickets = controller.getFloristeria().getTickets();
        double total = 0;

        for(Ticket t: tickets){
            total += t.getTotalPrice();
        }

        p.printLine(String.format("La floristeria ha guanyat: %.2f Euros", total));
        p.printEmpty();
        p.printLine("Presioni Enter per continuar");
        Input.getEnter();
        controller.removeFromStack(this);  
        
    }
    
}
