package io.savers.interfaces;

import io.IReadProperty;
import items.Floristeria;

/**
 * Interfaz para guardar una floristeria en un archivo. Al igual que para cargar, 
 * la intención es usar diferentes archivos y todos usarán esta interfaz
 */
public interface ISaveFloristeria {
    /**
     * Gaurda una floristeria en un archivo
     * @param floristeria -> floristeria a guardar
     * @param reader      -> lector para las opciones
     */
    void saveFloristeria(final Floristeria floristeria, final IReadProperty reader);
}
