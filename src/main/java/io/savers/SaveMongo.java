package io.savers;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import io.IReadProperty;
import io.savers.interfaces.ISaveFloristeria;
import io.savers.interfaces.ISaveTickets;
import io.utils.MongoDbUtils;
import items.Arbre;
import items.Decoracio;
import items.Flor;
import items.Floristeria;
import items.ItemBase;
import items.Ticket;

/**
 * Clase que guardas los datos de la floristeria en una base de datos Mongodb
 * Al igual que con el SQL y el TXT, he ido a lo fácil y cada vez que queremos guardar
 * nuevos datos, los viejos se borran.
 * Si bien es cierto que podríamos hacer updates de los datos y demás, como el ejercicio 
 * es bastante largo y se me echaba el tiempo encima he decidido usar este tipo de aproximación.
 * 
 */
public class SaveMongo implements ISaveFloristeria, ISaveTickets {

    MongoDbUtils utils;

    public SaveMongo() {
        this.utils = new MongoDbUtils();
    }

    @Override
    public void saveTickets(List<Ticket> tickets, IReadProperty reader) {
        try{
            MongoClient client = this.utils.getClient(reader);
            List<Document> documents = new ArrayList<Document>();
            List<Document> tempDocu = new ArrayList<Document>();
            Document doc = new Document();

            for(Ticket ticket: tickets){
                doc = new Document();
                doc.append(this.utils.DOC_TICKET_ID_NAME, ticket.getId());

                for(ItemBase item: ticket.getItemsCopy()){
                    tempDocu.add(this.itemDocument(item));
                }

                doc.append(this.utils.DOC_TICKET_ITEMS_NAME, tempDocu);
                documents.add(doc);
            }

            this.insertDocumentsToCollection(client, this.utils.DB_NAME, this.utils.COLL_TICKETS, documents);
            client.close();

        }catch(Exception e){

        }

    }

    @Override
    public void saveFloristeria(Floristeria floristeria, IReadProperty reader) {
        try {
            List<Document> documents;
            MongoClient client = this.utils.getClient(reader);
            documents = new ArrayList<Document>();
            documents.add(new Document().append(this.utils.DOC_FLOR_NAME, floristeria.getName()));
            this.insertDocumentsToCollection(client, this.utils.DB_NAME, this.utils.COLL_FLORISTERIA, documents);

            // ahora pillamos los documentos de la floristeria
            documents.clear();
            for(String key: floristeria.getKeys()){
                for(ItemBase item : floristeria.getItemsFromID(key)){
                    documents.add(this.itemDocument(item));
                }                                
            }

            this.insertDocumentsToCollection(client, this.utils.DB_NAME, this.utils.COLL_STOCKS, documents);
            
            documents.clear();
            client.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
    
    /**
     * Inserta una lista de documentos en la collection que se nombra
     * @param client    -> cliente
     * @param dbName    -> nombre de la base de datos
     * @param collName  -> nombre de la colección
     * @param documents -> documentos a introducir
     */ 
    private void insertDocumentsToCollection(final MongoClient client, final String dbName, final String collName, final List<Document> documents){
        MongoDatabase db = client.getDatabase(dbName);
        MongoCollection<Document> collection = db.getCollection(collName);

        // borramos todo
        collection.drop();

        //y metemos
        collection.insertMany(documents);
    }

    /**
     * Transforma un {@link ItemBase} a un {@link Document} para poder guardarlo
     * en la base de datos
     * @param item  -> item a transformar
     * @return      -> documento con los datos del item
     */
    private Document itemDocument(final ItemBase item) {
        Document doc = new Document();
        doc.append(this.utils.DOC_ITEM_NAME, item.getName());
        doc.append(this.utils.DOC_TIPUS_NAME, item.getId());
        doc.append(this.utils.DOC_PRICE_NAME, item.getPrice());
        doc.append(this.utils.DOC_AMOUNT_NAME, item.getQuantity());

        if (item.getId().equalsIgnoreCase(Arbre.ITEM_ID)) {
            Arbre a = (Arbre) item;
            doc.append(this.utils.DOC_HEIGHT_NAME, a.getHeight());
        } else if (item.getId().equalsIgnoreCase(Decoracio.ITEM_ID)) {
            Decoracio d = (Decoracio) item;
            doc.append(this.utils.DOC_MATERIAL_NAME, d.getMaterial());
        } else if (item.getId().equalsIgnoreCase(Flor.ITEM_ID)) {
            Flor f = (Flor) item;
            doc.append(this.utils.DOC_COLOR_NAME, f.getColor());
        }

        return doc;
    }

}
