package io.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

import io.IReadProperty;

public class SqlUtils {
    
    public final String SQL_URL_PREFIX = "jdbc:";

    // títulos del archivo para pillar las rutas
    public final String SQL_FILE_PATH = "sql_url";
    public final String SQL_FILE_USERNAME = "sql_username";
    public final String SQL_FILE_PASS = "sql_password";
    

    // posiciones de los atributos en la vista vw_tock
    public final int VW_POS_TYPE = 1;
    public final int VW_POS_NAME = 2;
    public final int VW_POS_HEIGHT = 3;
    public final int VW_POS_MATERIAL = 4;
    public final int VW_POS_COLOR = 5;
    public final int VW_POS_PRICE = 6;
    public final int VW_POS_AMOUNT = 7;
    public final int VW_POS_TICKET = 8;

    public Connection getConnection(final IReadProperty reader) throws SQLException{
        String url = this.SQL_URL_PREFIX + reader.readProperty(this.SQL_FILE_PATH);
        String name = reader.readProperty(this.SQL_FILE_USERNAME);
        String pass = reader.readProperty(this.SQL_FILE_PASS);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, name, pass);
            return con;
        }catch(ClassNotFoundException e){
            System.err.println(e.getMessage());
        }
        return null;
    }
    

}
