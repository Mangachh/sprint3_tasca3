package io.savers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;

import javax.swing.plaf.nimbus.State;

import com.mysql.cj.xdevapi.Result;

import io.IReadProperty;
import io.savers.interfaces.ISaveFloristeria;
import io.savers.interfaces.ISaveTickets;
import io.utils.SqlUtils;
import items.Arbre;
import items.Decoracio;
import items.Flor;
import items.Floristeria;
import items.ItemBase;
import items.Ticket;

public class SaveSql implements ISaveFloristeria, ISaveTickets {
    private SqlUtils utils;

    String GET_ID_QUERY = "SELECT id FROM items WHERE items.nom LIKE ? ";
    String UPDATE_QUERY = "INSERT INTO stock VALUES(?, ?, ?)";

    // para ir más rápido y no tener que mirar qué hay, qué se repite, etc,
    // borramos las tablas de stock, ticket y item_ticket y las volvemos a guardar
    String DELETE_STOCK_QUERY = "DELETE FROM stock";
    String DELETE_TICKETS_QUERY = "DELETE FROM tickets";
    String DELETE_ITEM_TICKET_QUERY = "DELETE FROM ticket_items";
    String INSERT_ITEM_QUERY = "INSERT INTO items (nom, tipus_id, alcada, material, color) VALUES(?, ?, ?, ?, ?)";
    String INSERT_TICKET_QUERY = "INSERT INTO tickets VALUES (?)";
    String INSERT_ITEM_TICKET_QUERY = "INSERT INTO ticket_items VALUES(?, ?, ?, ?)";
    String GET_TIPUS_QUERY = "SELECT id FROM item_tipus WHERE nom LIKE ?";

    public SaveSql() {
        this.utils = new SqlUtils();
    }

    @Override
    public void saveTickets(List<Ticket> tickets, IReadProperty reader) {
        try (Connection con = this.utils.getConnection(reader)) {
            // Statement selectId = con.createStatement();
            Statement generalSt = con.createStatement();
            PreparedStatement selectItemId = con.prepareStatement(GET_ID_QUERY);
            PreparedStatement insertItem = con.prepareStatement(INSERT_ITEM_QUERY, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement getTipusId = con.prepareStatement(GET_TIPUS_QUERY);
            PreparedStatement insertTicket = con.prepareStatement(INSERT_TICKET_QUERY);
            PreparedStatement insertItemTicket = con.prepareStatement(INSERT_ITEM_TICKET_QUERY);
            ResultSet result = null;
            // selectId.

            generalSt.execute(DELETE_ITEM_TICKET_QUERY);
            generalSt.execute(DELETE_TICKETS_QUERY);
            
            int id = -1;

            for (Ticket ticket : tickets) {
                // primero grabamos ticket
                insertTicket.setString(1, String.valueOf(ticket.getId()));
                insertTicket.executeUpdate();

                // miramos los items
                for (ItemBase item : ticket.getItemsCopy()) {
                    selectItemId.setString(1, item.getName());
                    result = selectItemId.executeQuery();

                    // tecnicamente hems hecho un copia-pega, es más sencillo
                    // así que intentar hacer un método (creo)
                    if (result.next()) {
                        id = result.getInt(1);
                    } else {
                        // metemos cosa
                        // primero pillamos id
                        id = this.getIdFromTipus(getTipusId, item.getId());

                        // ahora preparamos el statemnt segun el item
                        this.setInsertItemQuery(insertItem, item, id);

                        id = this.insertItemAndGetKey(insertItem);
                    }

                    insertItemTicket.setString(1, String.valueOf(ticket.getId()));
                    insertItemTicket.setString(2, String.valueOf(id));
                    insertItemTicket.setDouble(3, item.getPrice());
                    insertItemTicket.setInt(4, item.getQuantity());

                    insertItemTicket.executeUpdate();
                }

            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

    @Override
    public void saveFloristeria(Floristeria floristeria, IReadProperty reader) {
        ResultSet idSet = null;

        try (Connection con = this.utils.getConnection(reader)) {
            // Statement selectId = con.createStatement();
            PreparedStatement selectId = con.prepareStatement(GET_ID_QUERY);
            PreparedStatement updateStock = con.prepareStatement(UPDATE_QUERY);
            PreparedStatement insertItem = con.prepareStatement(INSERT_ITEM_QUERY, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement getTipusId = con.prepareStatement(GET_TIPUS_QUERY);
            // selectId.
            selectId.execute(DELETE_STOCK_QUERY);
            int id = -1;

            for (String key : floristeria.getKeys()) {
                for (ItemBase item : floristeria.getItemsFromID(key)) {
                    selectId.setString(1, item.getName());
                    idSet = selectId.executeQuery();

                    // pillamos id del set a ver qué tal
                    if (idSet.next()) {
                        id = idSet.getInt(1);
                    } else {
                        // metemos cosa
                        // primero pillamos id
                        id = this.getIdFromTipus(getTipusId, item.getId());

                        // ahora preparamos el statemnt segun el item
                        this.setInsertItemQuery(insertItem, item, id);

                        id = this.insertItemAndGetKey(insertItem);
                    }

                    // metemos la mierda en stock
                    updateStock.setInt(1, id);
                    updateStock.setDouble(2, item.getPrice());
                    updateStock.setInt(3, item.getQuantity());

                    // y debería funcar...
                    updateStock.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private int insertItemAndGetKey(final PreparedStatement insertStatement) throws SQLException {
        insertStatement.executeUpdate();
        ResultSet result = insertStatement.getGeneratedKeys();
        result.next();
        return result.getInt(1);
    }

    private int getIdFromTipus(final PreparedStatement st, final String itemId) throws SQLException {
        st.setString(1, itemId);

        ResultSet result = st.executeQuery();
        result.next();
        return result.getInt(1);
    }

    // nom, tipus_id, alcada, material, color)
    // mmmm, creo que se podría hacer alguna cosilla para que la propia base
    // devolviese a la hija
    // por su tipo...
    private void setInsertItemQuery(final PreparedStatement insertStament, final ItemBase item, int id) throws SQLException {
        if (item.getId().equals(Arbre.ITEM_ID)) {
            this.setArbreQuery(insertStament, (Arbre) item);
        } else if (item.getId().equals(Decoracio.ITEM_ID)) {
            this.setDecoraciQuery(insertStament, (Decoracio) item);
        } else if (item.getId().equals(Flor.ITEM_ID)) {
            this.setFlorQuery(insertStament, (Flor) item);
        }

        // lo metemos como string para que lo convierta el driver
        insertStament.setString(2, String.valueOf(id));
        insertStament.setString(1, item.getName());
    }

    private void setArbreQuery(final PreparedStatement insertStament, final Arbre arbre) throws SQLException {
        insertStament.setDouble(3, arbre.getHeight());
        insertStament.setString(4, null);
        insertStament.setString(5, null);
    }

    private void setDecoraciQuery(final PreparedStatement insertStament, final Decoracio dec) throws SQLException {
        insertStament.setNull(3, Types.DOUBLE);
        insertStament.setString(4, dec.getMaterial());
        insertStament.setString(5, null);
    }

    private void setFlorQuery(final PreparedStatement insertStament, final Flor f) throws SQLException {
        insertStament.setNull(3, Types.DOUBLE);
        insertStament.setString(4, null);
        insertStament.setString(5, f.getColor());
    }

}
