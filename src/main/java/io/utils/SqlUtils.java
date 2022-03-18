package io.utils;

public class SqlUtils {
    
    public final String SQL_URL_PREFIX = "jdbc:";

    // títulos del archivo para pillar las rutas
    public final String SQL_FILE_PATH = "sql_url";
    public final String SQL_FILE_USERNAME = "sql_username";
    public final String SQL_FILE_PASS = "sql_password";
    public final String SQL_FILE_VIEW_STOCK = "sql_view_stock";
    public final String SQL_FILE_VIEW_TICKET = "sql_view_ticket";
    public final String SQL_FILE_NAME_SELECT = "sql_name_select";

    // posiciones de los atributos en la vista vw_tock
    public final int VW_POS_TYPE = 1;
    public final int VW_POS_NAME = 2;
    public final int VW_POS_HEIGHT = 3;
    public final int VW_POS_MATERIAL = 4;
    public final int VW_POS_COLOR = 5;
    public final int VW_POS_PRICE = 6;
    public final int VW_POS_AMOUNT = 7;
    public final int VW_POS_TICKET = 8;

    

}
