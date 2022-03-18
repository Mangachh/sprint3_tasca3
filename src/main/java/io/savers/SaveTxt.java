package io.savers;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import io.IReadProperty;
import io.savers.interfaces.ISaveFloristeria;
import io.savers.interfaces.ISaveTickets;
import io.utils.TxtUtils;
import items.Floristeria;
import items.ItemBase;
import items.Ticket;

public class SaveTxt implements ISaveFloristeria, ISaveTickets {    
    private TxtUtils txtUtils;

    /**
     * Constructor
     */
    public SaveTxt(){
        this.txtUtils = new TxtUtils();
    }

    @Override
    public void saveFloristeria(final Floristeria floristeria, final IReadProperty reader) {
        File f = this.fileFromPath(this.txtUtils.FLOR_PATH_NAME, reader);
        String data;
        byte[] bytes;
        
        try{
            // miramos que todo exista
            this.createDirectories(f);
            PrintStream stream = new PrintStream(f, this.txtUtils.LOCALE);

            // y aqui lo guardamos todo...
            // primero el nombre
            data = String.format("%s %s %s", this.txtUtils.NAME_PREFIX, this.txtUtils.IDENTIF_CHAR, floristeria.getName());
            stream.println(data);

            // ahora lo que hay en el diccionario
            String[] keys = floristeria.getKeys();
            for(String key: keys){
                for(ItemBase item: floristeria.getItemsFromID(key)){
                    data = String.format("%s, %s", key, item.toString().trim());
                    bytes = data.getBytes(StandardCharsets.UTF_8);
                    stream.println(new String(bytes, StandardCharsets.UTF_8));
                }
            }

            // y aquí los tickets cuando los tengamos
            // cerramosstream
            stream.close();   


        }catch(Exception e){
            System.out.println(e.getMessage());
        }   
        
    }
    @Override
    public void saveTickets(final List<Ticket> tickets, final IReadProperty reader) {
        File f = this.fileFromPath(this.txtUtils.TICKET_PATH_NAME, reader);
        String data;
        ItemBase item;
        
        try{
            // miramos que todo exista
            this.createDirectories(f);
            PrintStream stream = new PrintStream(f, this.txtUtils.LOCALE);

            for(Ticket t: tickets){
                data = String.format("%d %s %s", t.getId(), this.txtUtils.IDENTIF_CHAR, txtUtils.START_GROUP_CHAR);
                stream.println(data);

                for(int i = 0; i < t.getItemsSize(); i++){
                    item = t.getItemByIndex(i);
                    data = String.format("\t%s, %s", item.getId(), item.toString());
                    stream.println(data);
                }

                stream.println(this.txtUtils.END_GROUP_CHAR);
            }
            stream.close();   


        }catch(Exception e){
            System.out.println(e.getMessage());
        }   
        
    }

    /**
     * Crea los directoris y archivos en caso de ser necesario
     * @param f -> file del directorio
     * @throws IOException
     */
    private void createDirectories(final File f) throws IOException{
        f.getParentFile().mkdirs();
        f.createNewFile();
    }

    /**
     * Crea un nuevo {@link File} dependiendo del tipo. Para ello leemos la propiedad
     * y demás.
     * Usamos las static de {@link TxtUtils}
     * @param type  ->  tipo de propiedad
     * @param reader -> lector de propiedades
     * @return -> new file con el path correcto
     */
    private File fileFromPath(final String type, final IReadProperty reader){
        String path = reader.readProperty(type);
        return new File(System.getProperty("user.dir") + path);
    }
    

   
    
}
