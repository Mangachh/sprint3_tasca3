package io.loaders.interfaces;

import io.IReadProperty;
import items.Floristeria;

/**
 * Interfaz para cargar una {@link Floristeria} nueva
 */
public interface ILoadFloristeria {
    
    /**
     * Devuelve una nueva instancia de floristeria a partir de un archivo.
     * @param reader -> necesario para las rutas y demás
     * @return       -> instancia de floristeria
     */
    Floristeria loadFloristeria(final IReadProperty reader);
}
