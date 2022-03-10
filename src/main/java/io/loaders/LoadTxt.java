package io.loaders;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Scanner;

import io.IReadProperty;
import items.Arbre;
import items.Decoracio;
import items.Flor;
import items.Floristeria;
import items.ItemBase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private final String PATH_NAME = "txt_path";
    private final String NO_READ_CHAR = "#";
    private final String NAME_PREFIX = "name";
    private final String SEPARATION_CHAR = "=";
    private final String SEPARATION_GROUP_CHAR = "}";
    private final byte ITEM_NAME = 0;
    private final byte ITEM_VALLUE = 1;
    private final byte ITEM_AMOUNT = 2;

    // estos tres los podemos agrupar de otra manera
    private final byte ITEM_HEIGH = 3;
    private final byte ITEM_COLOR = 3;
    private final byte ITEM_MATERIAL = 3;

    @Override
    public Floristeria loadFloristeria(IReadProperty propReader) {
        propReader.readProperty(this.PATH_NAME);
        String line = "";

        // vale, tenemos la ruta y demás.
        String path = propReader.readProperty(PATH_NAME);
        File f = new File(System.getProperty("user.dir") + path);
        Floristeria floristeria = new Floristeria();

        if (f.exists() == false) {
            return null;
        }

        try (Scanner reader = new Scanner(f)) {
            while (reader.hasNext()) {
                line = reader.nextLine().trim();
                
                // si la linea está blanca o empieza con el comment continiuamos
                if (line.isBlank() || line.startsWith(NO_READ_CHAR)) {
                    continue;
                } else if (line.startsWith(NAME_PREFIX)) {
                    floristeria.setName(this.getNameFromFile(line));
                } else if (line.startsWith(Arbre.ITEM_ID)) {
                    this.setItems(floristeria, reader, Arbre.class);
                } else if (line.startsWith(Decoracio.ITEM_ID)) {
                    this.setItems(floristeria, reader, Decoracio.class);
                } else if (line.startsWith(Flor.ITEM_ID)) {
                    this.setItems(floristeria, reader, Flor.class);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return floristeria;

    }

    private void setItems(final Floristeria floristeria, Scanner sc, final Class<? extends ItemBase> clazz){
        String line = "";
        while(sc.hasNextLine()){
            //primero miramos si la línea es cierre
            line = sc.nextLine();

            if(line.trim().equals(SEPARATION_GROUP_CHAR)){
                return;
            }

            String[] values = line.split(",");

            ItemBase item = null;
            try {
                item = clazz.getConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                e.printStackTrace();
                break;
            }
            item.setName(values[ITEM_NAME].trim());
            item.setPrice(Double.parseDouble(values[ITEM_VALLUE].trim()));
            item.setQuantity(Integer.parseInt(values[ITEM_AMOUNT].trim()));

            if(item.getId().equals(Arbre.ITEM_ID)){
                this.setArbreHeight(values[ITEM_HEIGH], (Arbre)item);
            }else if(item.getId().equals(Decoracio.ITEM_ID)){
                this.setDecoracioTipu(values[ITEM_MATERIAL], (Decoracio)item);
            }else if(item.getId().equals(Flor.ITEM_ID)){
                this.setFlorColor(values[ITEM_COLOR], (Flor)item);
            }

            floristeria.addItem(item, item.getId());

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

    private ArrayList<String> separateValues(final String line) {
        Pattern patter = Pattern.compile("\\(([^\\)]+)\\)");
        Matcher m = patter.matcher(line);
        ArrayList<String> matches = new ArrayList<String>();
        m.results().forEach(s -> matches.add(s.group().replaceAll("[()]", "")));
        return matches;
    }

    private String getNameFromFile(final String line) {
        String name = line.split(SEPARATION_CHAR)[1].trim();
        return name;
    }

}
