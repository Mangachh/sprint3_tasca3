package io.loaders;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import io.IReadProperty;
import io.TxtUtils;
import items.Arbre;
import items.Decoracio;
import items.Flor;
import items.Floristeria;
import items.ItemBase;

/* He tenido ciertos problemas para guardar esto, más que nada por el diseño del txt.
 * Al principio había optado por ponerl cada conjunto de items en una misma línea, aún así
 * no me parecía limpio y, en caso de tener muchos items quedaba un poco feo.
 * ej:
   
   name = nombre_floristeria
   Arbre = (arbre_1, 25, 2, 577), (arbre_2, 25, 7, 2)
   Decoracio = (decor_1, 25, 2, fusta), (decor_2, 14, 21, plastic)
   ...

 * Una vez había acabado de parsearlo todo y demás, pfff he pensao en hacerlo de una mejor manera
 * 
    name = nombre_floristeria
    arbre = {
        cerezo, 25.5(precio), 2(cantidad), 7.5 (altura)
        naranjo, 27.87(precio), 7(cantidad), 7.8(altura)
        }
    decoracio = {
        anilla, 25.6, 2, fusta
        bola de discoteca, 25.65, 2, plastic
        }
    ...
 * De esta manera no sólo queda más limpio, si no que me parece más fácil de parsear y demás.
 * Obviamente, está sujeto a cambio, jajaja.
/*

*/
public class LoadTxt implements ILoadFloristeria {
    private TxtUtils txtUtils;

    @Override
    public Floristeria loadFloristeria(IReadProperty propReader) {
        txtUtils = new TxtUtils();

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
                    floristeria.addItem(item, item.getId());
                }
                
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }

        return floristeria;
    }

    public ItemBase getItemFromLine(final String line) {
        String[] data = line.split(txtUtils.VALUES_SEPARATOR);        
        ItemBase item = null;
        String type = data[txtUtils.ITEM_TYPE].trim();

        if(data.length != txtUtils.DATA_PER_LINE){
            return null;
        }

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

        this.setSharedProperties(data, item);

        return item;
    }

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

    private void setSharedProperties(final String[] data, final ItemBase item){
        double price;
        int amount;
        
        try{
            price = Double.parseDouble(data[txtUtils.ITEM_VALLUE].trim());
            amount = Integer.parseInt(data[txtUtils.ITEM_AMOUNT].trim());
            item.setName(data[txtUtils.ITEM_NAME]);
            item.setPrice(price);
            item.setQuantity(amount);
        }catch(Exception e){
            System.err.println(e.getMessage());
        }        
    }

    private void setArbreHeight(final String height, Arbre arbre) {
        arbre.setHeight(Double.parseDouble(height));
    }

    private void setDecoracioTipu(final String tipus, Decoracio dec) {
        dec.setTipus(tipus);
    }

    private void setFlorColor(final String color, Flor flor) {
        flor.setColor(color);
    }

    private String getNameFromLine(final String line){
        try{
            return line.split(txtUtils.IDENTIF_CHAR)[1];
        }catch(Exception e){
            System.err.println(e.getMessage());
            return "no name found";
        }
        
    }

}
