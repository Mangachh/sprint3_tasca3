package io.savers;

import java.io.File;
import java.io.PrintStream;

import io.IReadProperty;
import io.TxtUtils;
import items.Floristeria;
import items.ItemBase;

public class SaveTxt implements ISaveFloristeria {
    // como usaremos lo mismo, quizás deberíamos crear un archivo desde el que leer para las dos clases...
    private TxtUtils txtUtils;
    @Override
    public void saveFloristeria(final Floristeria floristeria, final IReadProperty reader) {
        this.txtUtils = new TxtUtils();
        String path = reader.readProperty(this.txtUtils.FLOR_PATH_NAME);
        File f = new File(System.getProperty("user.dir") + path);
        String data;
        
        try{
            f.getParentFile().mkdirs();
            f.createNewFile();
            PrintStream stream = new PrintStream(f, this.txtUtils.LOCALE);

            // y aqui lo guardamos todo...
            // primero el nombre
            data = String.format("%s %s %s", this.txtUtils.NAME_PREFIX, this.txtUtils.IDENTIF_CHAR, floristeria.getName());
            stream.println(data);

            // ahora lo que hay en el diccionario
            String[] keys = floristeria.getKeys();
            for(String key: keys){
                for(ItemBase item: floristeria.getItemsFromID(key)){
                    data = String.format("%s, %s", key, item.toString());
                    stream.println(data);
                }
            }

            // y aquí los tickets cuando los tengamos
            // cerramosstream
            stream.close();   


        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        
    }

   
    
}
