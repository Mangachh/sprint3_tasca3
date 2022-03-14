package menus;

import java.util.ArrayList;
import java.util.List;

import app.AppController;
import app.Input;
import app.Print;
import items.Floristeria;
import items.ItemBase;

// usaremos esto para diferentes menús
public class SelectItemSubMenu {

    private final String submenuHeader = "Selecció d'ítem";
    private final int COLUMNS_NUM = 3;
    private final String TXT_EXIT = "Sortir del menú";
    private final String TXT_NO_VALUE = "no_value";
    private final int NO_VALUE = -1;

    public ItemBase getItemFromOptions(final Print p, final AppController controller, boolean showFull) {
        ItemBase item = null;
        int option = -1;
        String key = "";

        // primero pillamos las opciones, sumamos uno para la salida, y tiramos...
        String[] itemOptions = this.typeOptions(controller.getFloristeria());

        if (itemOptions.length > 0) {
            key = this.selectKeyFromMenu(itemOptions, p);
            if (key.equals(this.TXT_NO_VALUE) == false) {
                option = this.getItemIndex(controller.getFloristeria().getItemsFromID(key), p, showFull);
                if(option != NO_VALUE){
                    item = controller.getFloristeria().getIttem(key, option);
                }                
            }
        } else {
            p.printLine("No existeix cap element a la floristeria...");
        }
        
        return item;
    }

    private String selectKeyFromMenu(final String[] options, final Print p) {
        int input = -1;
        String key = TXT_NO_VALUE;
        p.printOptions(options);

        input = Input.getIntInBetween(p.MENU_OFFSET, options.length) - p.MENU_OFFSET;
        if (input != options.length - 1) {
            key = options[input];
        }
        return key;
    }

    private int getItemIndex(final List<ItemBase> base, final Print p, boolean showFull) {
        String[] itemsNames;
        int input = this.NO_VALUE;

        // pillamos los nombres
        ArrayList<String> names = null;
        if(showFull){
            names = this.fullItemsNames(base);
        }else{
            names = this.onlyStockItems(base);
        }

        //añadimos la salida y convertimos a array
        names.add(TXT_EXIT);
        itemsNames = names.toArray(new String[names.size()]);
        p.printOptions(itemsNames, this.COLUMNS_NUM);
        input = Input.getIntInBetween(p.MENU_OFFSET, itemsNames.length) - p.MENU_OFFSET;

        if(input == itemsNames.length - 1){
            input = -1;
        }
        return input;
    }

    private ArrayList<String> fullItemsNames(final List<ItemBase> base){
        return new ArrayList<String>(base.stream().map(i -> i.getName()).toList());
    }

    private ArrayList<String> onlyStockItems(final List<ItemBase> base){
        ArrayList<String> names = new ArrayList<>();
        for(ItemBase i: base){
            if(i.getQuantity() > 0){
                names.add(i.getName());
            }
        }

        return names;
    }

    private String[] typeOptions(final Floristeria flor) {
        ArrayList<String> options = new ArrayList<String>();

        for(String key: flor.getKeys()){
            options.add(key);
        }

        //al acabar metemos el exit
        options.add(this.TXT_EXIT);
        String[] stringOpt = options.toArray(new String[options.size()]);
        return stringOpt;
    }

   

}
