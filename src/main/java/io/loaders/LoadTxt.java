package io.loaders;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import io.IReadProperty;
import items.Arbre;
import items.Decoracio;
import items.Flor;
import items.Floristeria;
import items.ItemBase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoadTxt implements ILoadFloristeria {
    private final String PATH_NAME = "txt_path";
    private final String NO_READ_CHAR = "#";
    private final String NAME_PREFIX = "name";
    private final String ARBRE_PREFIX = "arbre";
    private final String DEC_PREFIX = "decoracio";
    private final String FLOR_PREFIX = "flor";
    private final String SEPARATION_CHAR = "=";
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

                // esto se puede mejorar de alguna manera...
                if (line.isBlank() || line.startsWith(NO_READ_CHAR)) {
                    continue;
                } else if (line.startsWith(NAME_PREFIX)) {
                    floristeria.setName(this.getNameFromFile(line));
                } else if (line.startsWith(ARBRE_PREFIX)) {
                    this.setItems(floristeria, line, Arbre.class);
                } else if (line.startsWith(DEC_PREFIX)) {
                    this.setItems(floristeria, line, Decoracio.class);
                } else if (line.startsWith(FLOR_PREFIX)) {
                    this.setItems(floristeria, line, Flor.class);
                }
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return floristeria;

    }

    private void setItems(final Floristeria floristeria, final String line, final Class<? extends ItemBase> clazz) {
        ArrayList<String> itemRaw = this.separateValues(line);

        for (String s : itemRaw) {
            String[] values = s.split(",");

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
