package items;

import java.util.ArrayList;
import java.util.List;

public class Ticket {

    private List<ItemBase> items;
    private final String TICKET_HEADER = "Ticket número: %d\n";

    // usamos esta ñapa para no pensar mucho...
    private static long ticketId = 0;
    private long id;

    public Ticket() {
        this.id = ticketId;
        ticketId++;
        this.items = new ArrayList<ItemBase>();
    }

    public void addItem(final ItemBase item) {
        this.items.add(item);

    }

    public long getId() {
        return this.id;
    }

    public double getTotalPrice() {
        double total = 0;
        for (ItemBase it : items) {
            total += it.getPrice() * it.getQuantity();
        }

        return total;
    }

    public String formattedTicket() {
        double total = 0;
        double price = 0;
        String format_first = "%s -> %s (%.2f€)\n";
        String format_second = "\t quantity X %d\t\t%.2f€\n";
        //String format = "%s-> %-35s X%d\t\t%.2f\n";
        String text = String.format(TICKET_HEADER, this.id);
        for (ItemBase b : items) {
            text += String.format(format_first, b.getId().toUpperCase(), b.getName(), b.getPrice());
            // esto lo podría tener el item base
            price = b.getPrice() * b.getQuantity();

            text += String.format(format_second, b.getQuantity(), price);
            // podríanos llamar a la función que tenemos más arriba, pero no tiene sentido recorrer el array 2 veces
            total += price;
        }

        text += String.format("\n\tTOTAL: %.2€f", total);

        return text;
    }


    public int getItemsSize(){
        return this.items.size();
    }

    public ItemBase getItemByIndex(int index){
        return this.items.get(index);
    }


}
