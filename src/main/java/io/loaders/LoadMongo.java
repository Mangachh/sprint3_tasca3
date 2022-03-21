package io.loaders;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.MongoSocketException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import io.IReadProperty;
import io.loaders.interfaces.ILoadFloristeria;
import io.loaders.interfaces.ILoadTickets;
import io.utils.MongoDbUtils;
import items.Arbre;
import items.Decoracio;
import items.Flor;
import items.Floristeria;
import items.ItemBase;
import items.Ticket;

/**
 * Carga los datos de una floristeria de una base de datos mongobd
 */
public class LoadMongo implements ILoadFloristeria, ILoadTickets {

    MongoDbUtils utils;

    public LoadMongo() {
        this.utils = new MongoDbUtils();
    }

    @Override
    public List<Ticket> loadTickets(IReadProperty reader) {
        List<Ticket> tickets = new ArrayList<Ticket>();

        try {
            MongoClient client = utils.getClient(reader);
            FindIterable<Document> documents = this.getDocuments(client, this.utils.DB_NAME, this.utils.COLL_TICKETS);
            Ticket ticket = null;
            
            List<ItemBase> items;

            for (Document doc : documents) {
                ticket = new Ticket();
                ticket.setId((long) doc.get(this.utils.DOC_TICKET_ID_NAME));

                // está comprobado que funciona
                ArrayList<Document> itemDocs = (ArrayList<Document>) doc.get(this.utils.DOC_TICKET_ITEMS_NAME);

                items = this.getItemsFromDocuments(itemDocs);

                for (ItemBase item : items) {
                    ticket.addItem(item);
                }

                tickets.add(ticket);
            }

            client.close();

        } catch (MongoSocketException e) {
            System.err.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
        return tickets;
    }

    @Override
    public Floristeria loadFloristeria(IReadProperty reader) {
        Floristeria floristeria = null;

        try {
            MongoClient client = utils.getClient(reader);
            // pillamos el nombre de la floristeria
            FindIterable<Document> documents = this.getDocuments(client, utils.DB_NAME, utils.COLL_FLORISTERIA);
            floristeria = new Floristeria(this.florNameFromDocument(documents));

            // usamos lo mismo para pillar la colección de stocks
            documents = this.getDocuments(client, utils.DB_NAME, utils.COLL_STOCKS);
            List<ItemBase> items = this.getItemsFromDocuments(documents);

            for (ItemBase item : items) {
                floristeria.addItem(item);
            }

            client.close();
        } catch (MongoSocketException e) {
            System.err.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }

        return floristeria;
    }

    /**
     * Devuelve una {@link FindIterable}
     * @param client         -> cliente para operatr
     * @param dbName         -> base de datos de la que pillar docs
     * @param collectionName -> nombre de la colección
     * @return               -> iterable con los documentos
     * @throws IllegalArgumentException
     */
    private FindIterable<Document> getDocuments(final MongoClient client, final String dbName,
            final String collectionName) throws IllegalArgumentException {
        MongoDatabase db = client.getDatabase(dbName);
        FindIterable<Document> collection = db.getCollection(collectionName).find();
        return collection;
    }

    /**
     * Pilla el nombre de la floristeria de un documento
     * @param documents -> documento donde esté e nombre de la floristeria
     * @return          -> nombre floristeria
     */
    private String florNameFromDocument(final FindIterable<Document> documents) {
        for (Document d : documents) {
            try {
                return (String) d.get(this.utils.DOC_FLOR_NAME);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

        return "";
    }

    /**
     * Devuelve una lista de {@link ItemBase} de una serie de documentos
     * @param documents -> documentos donde están los objetos
     * @return          -> lista de items
     */
    private List<ItemBase> getItemsFromDocuments(final FindIterable<Document> documents) {
        List<ItemBase> itemList = new ArrayList<ItemBase>();

        for (Document doc : documents) {
            itemList.add(this.itemFormDocument(doc));
        }

        return itemList;
    }

    /**
     * Devuelve una lista de {@link ItemBase} de un array de documentos
     * @param documents -> array de documentos
     * @return          -> lista de items
     */
    private List<ItemBase> getItemsFromDocuments(final ArrayList<Document> documents) {
        List<ItemBase> itemList = new ArrayList<ItemBase>();

        for (Document doc : documents) {
            itemList.add(this.itemFormDocument(doc));
        }

        return itemList;
    }

    /**
     * Devuelve un {@link ItemBase} de un documento
     * @param doc -> documento con los datos
     * @return    -> item
     */
    private ItemBase itemFormDocument(final Document doc) {
        String tipus = (String) doc.get(this.utils.DOC_TIPUS_NAME);
        ItemBase item = null;

        if (tipus.equalsIgnoreCase(Arbre.ITEM_ID)) {
            item = this.getArbre(doc);
        } else if (tipus.equalsIgnoreCase(Decoracio.ITEM_ID)) {
            item = this.getDecoracio(doc);
        } else if (tipus.equalsIgnoreCase(Flor.ITEM_ID)) {
            item = this.getFlor(doc);
        }

        if (item != null) {
            item.setName((String) doc.get(this.utils.DOC_ITEM_NAME));
            item.setPrice((double) doc.get(this.utils.DOC_PRICE_NAME));
            item.setQuantity((int) doc.get(this.utils.DOC_AMOUNT_NAME));
        }

        return item;

    }

    /**
     * Devuelve una instancia de {@link Arbre} con la altura seteada
     * @param doc -> documento con los datos
     * @return    -> instancia de arbol
     */
    private ItemBase getArbre(final Document doc) {
        Arbre arbre = new Arbre();
        arbre.setHeight((double) doc.get(this.utils.DOC_HEIGHT_NAME));
        return arbre;
    }

    /**
     * Devuelve una instancia de {@link Decoracio} con el material seteado
     * @param doc -> documento con los datos
     * @return    -> instancia de decoracio
     */
    private ItemBase getDecoracio(final Document doc) {
        Decoracio dec = new Decoracio();
        dec.setMaterial((String) doc.get(utils.DOC_MATERIAL_NAME));
        return dec;
    }

    /**
     * Devuelve una isntancia de {@link Flor} con el color seteado
     * @param doc -> documento con los datos
     * @return    -> flor
     */
    private ItemBase getFlor(final Document doc) {
        Flor flor = new Flor();
        flor.setColor((String) doc.get(this.utils.DOC_COLOR_NAME));
        return flor;
    }

}
