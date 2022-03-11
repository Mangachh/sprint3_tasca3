package io.savers;

import java.io.File;
import java.io.PrintStream;

import io.IReadProperty;
import io.SaveLoad;
import items.Floristeria;

public class SaveTxt implements ISaveFloristeria {
    // como usaremos lo mismo, quizás deberíamos crear un archivo desde el que leer para las dos clases...
    private final String PATH_PROP = "txt_path";
    private final String FLOR_NAME = "name";
    private final String EQUAL = "=";
    private final String BEGIN_GROUP = "{";
    private final String END_GROUP = "}";
    private final String LOCALE = "UTF-8";

    @Override
    public void saveFloristeria(final Floristeria floristeria, final IReadProperty reader) {
        String path = reader.readProperty(PATH_PROP);
        File f = new File(System.getProperty("user.dir") + path);
        
        try{
            f.getParentFile().mkdirs();
            f.createNewFile();
            PrintStream stream = new PrintStream(f, LOCALE);

            // y aqui lo guardamos todo...
            // primero el nombre
            stream.println(String.format("%s %s %s", FLOR_NAME, EQUAL, floristeria.getName()));

            // ahora lo que hay en el diccionario
            String[] keys = floristeria.getKeys();
            for(String key: keys){
                // primero grabamos la key 
                stream.println(String.format("%s %s %s", key, this.EQUAL, this.BEGIN_GROUP));

                //y ahora los valores
                for(int i = 0; i < floristeria.getItemCount(key); i++){
                    stream.println(String.format("\t%s", floristeria.getIttem(key, i)).toString());
                }

                // y ahora cerramos
                stream.println(this.END_GROUP);
            }

            // y aquí los tickets cuando los tengamos
            // cerramosstream
            stream.close();   


        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        
    }

   
    
}
