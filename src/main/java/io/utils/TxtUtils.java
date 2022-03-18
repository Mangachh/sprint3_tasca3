package io.utils;

/** 
 * Archivo con varias constantes para el guardado-carga de los datos en un txt.
 * como usamos las mismas constantes para guardar y cargar, es más útil tenerlo
 * todo en la misma clase
 */
public class TxtUtils {
    /**
     * Nombre de la propiedad en el archivo propierties donde está la ruta del archivo de carga de floristeria
     */
    public final String FLOR_PATH_NAME = "txt_floristeria_path";

    /**
     * Nombre de la propiedad en el archivo properties donde está la ruta del archivo de tickets
     */
    public final String TICKET_PATH_NAME = "txt_tickets_path";

    /**
     * Character pra saltar línea
     */
    public final String NO_READ_CHAR = "#";

    /**
     * Prefijo del nombre de la floristeria en el archivo de floristeria
     */
    public final String NAME_PREFIX = "name";

    /**
     * Character de identificación. Usado para separar, por ejemplo, "name = Nombre floristeria"
     * ó
     * 1 = {items} -> donde 1 es el id
     */
    public final String IDENTIF_CHAR = "=";

    /**
     * Character que separa diferentes valores
     */
    public final String VALUES_SEPARATOR = ",";

    /**
     * Char que empieza un grupo
     */
    public final String START_GROUP_CHAR = "{";

    /**
     * Char para acabar un grupo
     */
    public final String END_GROUP_CHAR = "}"; 

    /**
     * Encoding para el archivo
     */
    public final String LOCALE = "UTF-8";   

    /**
     * cada item está guardado de la misma manera:
     * <<tipo_item, nombre_item, precio_item, cantidad_item, cosa_especial>>(altura para arbol, tipo para decor, color para flor)
     * Esto marca la posición entro de la l´nea del tipo de item
     */
    public final byte ITEM_TYPE = 0;

    /**
     * cada item está guardado de la misma manera:
     * <<tipo_item, nombre_item, precio_item, cantidad_item, cosa_especial>>(altura para arbol, tipo para decor, color para flor)
     * Esto marca la posición entro de la l´nea del nombre
     */
    public final byte ITEM_NAME = 1;

    /**
     * cada item está guardado de la misma manera:
     * <<tipo_item, nombre_item, precio_item, cantidad_item, cosa_especial>>(altura para arbol, tipo para decor, color para flor)
     * Esto marca la posición entro de la l´nea del precio del item
     */
    public final byte ITEM_VALLUE = 2;

    /**
     * cada item está guardado de la misma manera:
     * <<tipo_item, nombre_item, precio_item, cantidad_item, cosa_especial>>(altura para arbol, tipo para decor, color para flor)
     * Esto marca la posición entro de la l´nea de la cantidad
     */
    public final byte ITEM_AMOUNT = 3;   

    /**
     * Posición en la línea del item de la altura del arbol
     */
    public final byte ITEM_HEIGH = 4;

    /**
     * Posición en la línea del item del color de la flor
     */
    public final byte ITEM_COLOR = 4;

    /**
     * Posición en la línea del item del material de la decoración
     */
    public final byte ITEM_MATERIAL = 4;

    /**
     * Elementos en la línea de item
     */
    public final byte DATA_PER_LINE = 5;
}
