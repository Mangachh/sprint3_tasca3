package io.loaders;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import io.IReadProperty;
import io.loaders.interfaces.ILoadFloristeria;
import io.loaders.interfaces.ILoadTickets;
import io.utils.TxtUtils;
import items.Arbre;
import items.Decoracio;
import items.Flor;
import items.Floristeria;
import items.ItemBase;
import items.Ticket;

/**
 * Clase para cargar tanto la floristeria con su inventario y los tickets.
 * Para ello tengo dos archivos diferentes. Las rutas estan en el archivo "saveLoad_data.properties"
 */
public class LoadTxt implements ILoadFloristeria, ILoadTickets {
    private TxtUtils txtUtils;

    /**
     * constructor
     */
    public LoadTxt(){
        this.txtUtils = new TxtUtils();
    }

    
    @Override
    public Floristeria loadFloristeria(IReadProperty propReader) {

        // vale, tenemos la ruta y demás.
        String path = propReader.readProperty(txtUtils.FLOR_PATH_NAME);
        File f = new File(System.getProperty("user.dir") + path);
        Floristeria floristeria = new Floristeria();  
        ItemBase item;      
        String line;

        if (f.exists() == false) {
            return null;
        }
        // la primeria línea es el nombre, igualmente miraremso a ver
        try (Scanner reader = new Scanner(f, txtUtils.LOCALE)) {
            line = reader.nextLine();
            floristeria.setName(this.getNameFromLine(line));

            // ahora miraremos el tipo que es y demás
            while(reader.hasNextLine()){
                line = reader.nextLine();
                item = this.getItemFromLine(line);
                if(item != null){
                    floristeria.addItem(item);
                }
                
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }

        return floristeria;
    }

    /**
     * Carga un ticket a partir de un archivo de texto.
     * La lectura del ticket va en dos partes:
     *  - Leemos el id del ticket
     *  - Llamos a {@link #getTicketFromParagraph(Scanner)} para leer el párrafo donde están los items
     * Obviamente podríamos haberlo hecho todo en la misma función, pero así esta todo como más separado
     */
    @Override
    public List<Ticket> loadTickets(IReadProperty propReader) {             
        List<Ticket> tickets = new ArrayList<Ticket>();
        String path = propReader.readProperty(txtUtils.TICKET_PATH_NAME);
        File f = new File(System.getProperty("user.dir") + path);
        Ticket tempTicket;
        int id;  
        String line;

        if (f.exists() == false) {
            return null;
        }
        
        try (Scanner reader = new Scanner(f, txtUtils.LOCALE)) {
            while(reader.hasNextLine()){

                // primero leemos el id
                line = reader.nextLine();
                id = this.getIdFromTicketLine(line);

                // luego los items
                tempTicket = this.getTicketFromParagraph(reader);
                tempTicket.setId(id);

                //añadimos a la lista
                tickets.add(tempTicket);                
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }

        return tickets;
    }

    /**
     * Devuelve la id del ticket desde un archivo
     * @param line -> línea a leer
     * @return     -> id, -1 si no la ha encontrado
     */
    public int getIdFromTicketLine(final String line){
        String[] data = line.split(txtUtils.IDENTIF_CHAR);
        int id = -1;
        try{
            id = Integer.parseInt(data[0].trim());
        }catch(NumberFormatException e){
            System.err.println(e.getMessage());
        }

        return id;        
    }

    /**
     * Devuelve un nuevo {@link Ticket} con sus {@link ItemBase} 
     * OJU! esto no lee la id del ticket!
     * @param reader -> lector para leer el archivo
     * @return       -> nuevo ticket
     */
    public Ticket getTicketFromParagraph(final Scanner reader){
        ItemBase item;
        boolean inside = true;
        String line;
        Ticket ticket = new Ticket();

        while(inside && reader.hasNextLine()){
            line = reader.nextLine();
            if(line.contains(this.txtUtils.END_GROUP_CHAR)){
                inside = false;
            }else{
                item = this.getItemFromLine(line);
                ticket.addItem(item);    
            }                    
        }

        return ticket;
    }
   
    /**
     * Devuelve un nuevo {@link ItemBase} de una línea. Todas las líneas de item están formateadas
     * igual (tanto las del inventario de la floristeria como las del ticket). De esta manera podemos
     * utilizar el mismo parseador para los dos.
     * @param line
     * @return
     */
    public ItemBase getItemFromLine(final String line) {
        String[] data = line.split(txtUtils.VALUES_SEPARATOR);
        ItemBase item = null;
        String type = data[txtUtils.ITEM_TYPE].trim();

        if (data.length != txtUtils.DATA_PER_LINE) {
            return null;
        }

        // como ItemBase es una clase base, tenemos que cargar la clase particular y setear
        // sus particularidades
        if (type.equalsIgnoreCase(Arbre.ITEM_ID)) {
            item = this.getNewParticularItem(Arbre.class);
            this.setArbreHeight(data[txtUtils.ITEM_HEIGH], (Arbre) item);
        } else if (type.equalsIgnoreCase(Decoracio.ITEM_ID)) {
            item = this.getNewParticularItem(Decoracio.class);
            this.setDecoracioTipu(data[txtUtils.ITEM_MATERIAL], (Decoracio) item);
        } else if (type.equalsIgnoreCase(Flor.ITEM_ID)) {
            item = this.getNewParticularItem(Flor.class);
            this.setFlorColor(data[txtUtils.ITEM_COLOR], (Flor) item);
        }

        // seteamos lo común a todas las clases
        this.setSharedProperties(data, item);

        return item;
    }

    /**
     * Como para cargar operamos con la clase base {@link ItemBase} y esta es abstracta, necesitamos una instancia
     * de una clase hija para trabajar. Este método provee dicha clase siempre y cuando le metamos como parametro 
     * la clase que queremos. 
     * Creo que hay maneras más limpias para hacerlo, pero de momento lo dejo así. 
     * @param clazz -> clase que queremos instanciar
     * @return      -> instancia de la clase
     */
    private ItemBase getNewParticularItem(final Class<? extends ItemBase> clazz) {
        ItemBase item;

        try {
            item = clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
            return null;
        }

        return item;
    }

    /**
     * Setea las propiedades comunes a toda clase que deriva de {@link ItemBase} 
     * @param data -> datos a setear
     * @param item -> item de seteo
     */
    private void setSharedProperties(final String[] data, final ItemBase item) {
        double price;
        int amount;

        try {
            price = Double.parseDouble(data[txtUtils.ITEM_VALLUE].trim());
            amount = Integer.parseInt(data[txtUtils.ITEM_AMOUNT].trim());
            item.setName(data[txtUtils.ITEM_NAME].trim());
            item.setPrice(price);
            item.setQuantity(amount);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Setea la altura del {@link Arbre}a partir de un string
     * @param height -> altura del arbol
     * @param arbre  -> arbol al que setear la altura
     */
    private void setArbreHeight(final String height, Arbre arbre) {
        arbre.setHeight(Double.parseDouble(height.trim()));
    }

    /**
     * Setea el tipo de decoracion de {@link Decoracio} 
     * @param tipus -> tipo de decoración
     * @param dec   -> decoracion al que setear
     */
    private void setDecoracioTipu(final String tipus, Decoracio dec) {
        dec.setTipus(tipus.trim());
    }

    /**
     * Setea el clor de {@link Flor}
     * @param color -> clor a setear
     * @param flor  -> flor a la que setear
     */
    private void setFlorColor(final String color, Flor flor) {
        flor.setColor(color.trim());
    }

    /**
     * Pilla el nombre de una floristeria a partir de una línea
     * @param line -> línea donde está el nombre
     * @return     -> nombre
     */
    private String getNameFromLine(final String line) {
        try {
            return line.split(txtUtils.IDENTIF_CHAR)[1].trim();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return "no name found";
        }

    }

}
