package io.loaders;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.filechooser.FileNameExtensionFilter;

import io.IReadProperty;
import io.loaders.interfaces.ILoadFloristeria;
import io.loaders.interfaces.ILoadTickets;
import io.utils.SqlUtils;
import items.Arbre;
import items.Decoracio;
import items.Flor;
import items.Floristeria;
import items.ItemBase;
import items.Ticket;

public class LoadSql implements ILoadFloristeria, ILoadTickets {
    private SqlUtils utils;
    private final static String DEFAULT_STR = "def";

    // cosa que usamos para testear
    /*private void test() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cul_ampolla2", "root", "aquiles");
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery("select * from adreces");

            while (result.next()) {
                System.out.println(result.getInt(1) + ", " + result.getInt(2) + ", " +
                        result.getString(3) + ", " + result.getString(4) + " " +
                        result.getString(5) + ", " + result.getString(6));
            }
            con.close();

        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 

    }*/

    public LoadSql() {
        this.utils = new SqlUtils();
    }

    @Override
    public List<Ticket> loadTickets(IReadProperty reader) {
        String url = utils.SQL_URL_PREFIX + reader.readProperty(utils.SQL_FILE_PATH);
        String name = reader.readProperty(utils.SQL_FILE_USERNAME);
        String pass = reader.readProperty(utils.SQL_FILE_PASS);
        String ticketSelect = reader.readProperty(utils.SQL_FILE_VIEW_TICKET);
        List<Ticket> tickets = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, name, pass);
            Statement statement = con.createStatement();

            ResultSet result = statement.executeQuery(ticketSelect);            
            tickets = this.loadTickets(result);              
            
            con.close();
        }catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return tickets;
    }

    @Override
    public Floristeria loadFloristeria(IReadProperty reader) {
        Floristeria floristeria = null;
        String url = utils.SQL_URL_PREFIX + reader.readProperty(utils.SQL_FILE_PATH);
        String name = reader.readProperty(utils.SQL_FILE_USERNAME);
        String pass = reader.readProperty(utils.SQL_FILE_PASS);
        String itemSelect = reader.readProperty(utils.SQL_FILE_VIEW_STOCK);
        String florisSelect = reader.readProperty(utils.SQL_FILE_NAME_SELECT);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, name, pass);
            Statement statement = con.createStatement();

            // hacemos la búsqueda del nombre
            ResultSet result = statement.executeQuery(florisSelect);
            floristeria = this.createFloristeria(result);

            // ahora hacemos la búsqueda de los item y creamos items
            result = statement.executeQuery(itemSelect);
            this.addItems(result, floristeria);
            // cerramos al acabar
            con.close();
        }catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return floristeria;
    }

    private Floristeria createFloristeria(final ResultSet nameSet) throws SQLException{
        nameSet.next();
        String name = nameSet.getString(1);
        System.out.println("Loaded name: " + name);
        return new Floristeria(name);
    }

    private void addItems(final ResultSet toLoad, final Floristeria floristeria)throws SQLException{
        List<ItemBase> items = loadItems(toLoad);
        items.stream().forEach(i -> floristeria.addItem(i));
    }

    private List<ItemBase> loadItems(final ResultSet toLoad) throws SQLException{
        List<ItemBase> items = new ArrayList<ItemBase>();
        ItemBase item = null;
        String type = "";
        while(toLoad.next()){
            // primero pillamos el tipo y lo pasamos al loader
            type = toLoad.getString(utils.VW_POS_TYPE);
            item = this.loadItem(type, toLoad);
            items.add(item);
            //floristeria.addItem(item);
        }

        return items;
    }

    private List<Ticket> loadTickets(final ResultSet toLoad) throws SQLException{
        Ticket currentTicket = null;
        List<Ticket> tickets = new ArrayList<Ticket>();
        ItemBase item;

        //damos una puntada para pillar el primer resultado
        //toLoad.next();

        // como está ordenado en el sql, miramos la id y la colocamos como última
        int lastId = -1;
        int newId = -1;

        while(toLoad.next()){
            newId = toLoad.getInt(utils.VW_POS_TICKET);

            if(newId != lastId){
                currentTicket = new Ticket();
                currentTicket.setId(newId);
                lastId = newId;                
                tickets.add(currentTicket);
            }

            item = loadItem(toLoad.getString(utils.VW_POS_TYPE), toLoad);
            currentTicket.addItem(item);
        }        

        return tickets;
    }

    private void setBaseItem(final ResultSet item, final ItemBase base) throws SQLException{
        String name = item.getString(utils.VW_POS_NAME);
        double price = item.getDouble(utils.VW_POS_PRICE);
        int amount = item.getInt(utils.VW_POS_AMOUNT);

        //cargamos
        base.setName(name);
        base.setPrice(price);
        base.setQuantity(amount);
    }

    private ItemBase loadItem(final String type, final ResultSet set)throws SQLException{
        ItemBase item = null;
        if(type.equalsIgnoreCase(Arbre.ITEM_ID)){
            item = loadArbre(set.getDouble(utils.VW_POS_HEIGHT));
        }else if(type.equalsIgnoreCase(Decoracio.ITEM_ID)){
            item = loadDecoracio(set.getString(utils.VW_POS_TYPE));
        }else if(type.equalsIgnoreCase(Flor.ITEM_ID)){
            item = loadFlor(set.getString(utils.VW_POS_COLOR));
        }

        this.setBaseItem(set, item);
        return item;
    }

    private Arbre loadArbre(final double height){
        Arbre a = new Arbre();
        a.setHeight(height);
        return a;
    }

    private Decoracio loadDecoracio(final String tipus){
        Decoracio d = new Decoracio();
        d.setTipus(tipus);
        return d;
    }

    private Flor loadFlor(final String color){
        Flor f = new Flor();
        f.setColor(color);
        return f;
    }

}
