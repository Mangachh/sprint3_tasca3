package menus;

import app.AppController;
import app.Input;
import app.Print;
import items.ItemBase;
import items.Ticket;

/**
 * Menú para la creación de tickets
 */
public class CreateTicket extends MenuBase {

    private final String TXT_QUANTITAT = "Introdueixi la quantitat d'ítems a afegir al ticket.";
    private final String TXT_CLOSE_TICKET = "Vol introduïr un altre producte? 1(sí), 0(no)";
    private final String TXT_ACCEPT_TICKET = "Vol guardar el ticket actual? 1(sí), 0(no)";

    private final int VALUE_YES = 1;
    private final int VALUE_NO = 0;

    //TODO: habrá que refractorizar...
    
    @Override
    public void showMenu(Print p, AppController controller) {
        Ticket ticket = new Ticket();
        SelectItemSubMenu itemMenu = new SelectItemSubMenu();
        ItemBase item = null;
        ItemBase clonedItem = null;
        boolean inMenu = true;
        int amount = -1;

        while (inMenu) {
            item = itemMenu.getItemFromOptions(p, controller, false);

            if (item != null) {
                p.printLine(TXT_QUANTITAT);
                amount = Input.getIntInBetween(1, item.getQuantity());
                // cambiamos la cantidad en el stock
                item.setQuantity(item.getQuantity() - amount);
                
                // clonamos y ponemos la cantidad al nuevo objeto
                try {
                    clonedItem = (ItemBase) item.clone();
                    clonedItem.setQuantity(amount);
                } catch (CloneNotSupportedException e) {
                    System.err.println(e.getMessage());
                    System.err.println("Pasando referencia...");
                    clonedItem = item;
                }

                ticket.addItem(clonedItem);
            }

            p.printLine(this.TXT_CLOSE_TICKET);
            inMenu = Input.isInputYesValue(this.VALUE_YES, this.VALUE_NO);
        }

        // preguntamos si queremos aceptar el ticket e imprimos en ese caso
        p.printEmptyLines(10);

        if(ticket.getItemsSize() > 0){
            p.printLine(TXT_ACCEPT_TICKET);
            if(Input.isInputYesValue(this.VALUE_YES, this.VALUE_NO)){
                controller.getFloristeria().addTicket(ticket);
                p.printLine(ticket.formattedTicket());
            }else{
                for(int i = 0; i < ticket.getItemsSize(); i++){
                    clonedItem = ticket.getItemByIndex(i);
                    item = controller.getFloristeria().getItemByName(clonedItem.getId(), clonedItem.getName());
                    item.setQuantity(item.getQuantity() + clonedItem.getQuantity());
                }
            }
        }
        p.printLine("Presioni Enter per continuar...");
        Input.getEnter();
        controller.removeFromStack(this);
    }   

}
