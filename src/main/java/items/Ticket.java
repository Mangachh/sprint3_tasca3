package items;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que almacena una serie de items para hacer un ticket
 */
public class Ticket {

    private List<ItemBase> items;
    /**
     * Header para los tickets, lo pongo aquí para aprovehcar
     */
    private final String TICKET_HEADER = "Ticket número: %d\n";

    // usamos esta ñapa para no pensar mucho...
    private static long ticketId = 0;
    private long id;

    /**
     * Constructor
     */
    public Ticket() {
        this.id = ticketId;
        ticketId++;
        this.items = new ArrayList<ItemBase>();
    }

    /**
     * Añade un item al ticket
     * @param item  -> item que añadir al ticket
     */
    public void addItem(final ItemBase item) {
        if(item != null){
            this.items.add(item);
        }
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id){
        this.id = id;
        if(id >= Ticket.ticketId){
            Ticket.ticketId = id + 1;
        }
    }

    /**
     * Devuelve el precio total del ticket. 
     * Hemos decicido calcularlo en vez de guardarlo en una variable.
     * @return
     */
    public double getTotalPrice() {
        double total = 0;
        for (ItemBase it : items) {
            total += it.getPrice() * it.getQuantity();
        }

        return total;
    }

    /**
     * Formatea el ticket para la impresión.
     * @return -> ticket formateado
     */
    public String formattedTicket() {
        double total = 0;
        double price = 0;
        String format_first = "%s -> %s (%.2f Euros)\n";
        String format_second = "\t quantity X %d\t\t%.2f Euros\n";
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

        text += String.format("\n\tTOTAL: %.2f Euros", total);

        return text;
    }


    public int getItemsSize(){
        return this.items.size();
    }

    public ItemBase getItemByIndex(int index){
        return this.items.get(index);
    }

    public List<ItemBase> getItemsCopy(){
        return new ArrayList<ItemBase>(this.items);
    }




}
