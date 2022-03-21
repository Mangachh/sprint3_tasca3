package io.utils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import io.IReadProperty;

public class MongoDbUtils {
    // url de mongo
    private final String FILE_URL_NAME = "mongo_url";

    // nombres de las coleccions
    public final String COLL_FLORISTERIA = "tenda";
    public final String COLL_STOCKS = "stocks";
    public final String COLL_TICKETS = "tickets";

    // nombre base de datos
    public final String DB_NAME = "floristeria";

    // nombres de los datos en el documento
    public final String DOC_FLOR_NAME = "nom";
    public final String DOC_TIPUS_NAME = "tipus";
    public final String DOC_HEIGHT_NAME = "alcada";
    public final String DOC_MATERIAL_NAME = "material";
    public final String DOC_COLOR_NAME = "color";
    public final String DOC_ITEM_NAME = "nom";
    public final String DOC_PRICE_NAME = "price";
    public final String DOC_AMOUNT_NAME = "amount";
    public final String DOC_TICKET_ID_NAME = "id";
    public final String DOC_TICKET_ITEMS_NAME = "items";
    
    /**
     * Devuelve un nuevo cliente mongodb
     * @param reader
     * @return
     */
    public MongoClient getClient(final IReadProperty reader){
        String url = reader.readProperty(this.FILE_URL_NAME).trim();
        MongoClient client = MongoClients.create(url);

        return client;
    }



    
}
