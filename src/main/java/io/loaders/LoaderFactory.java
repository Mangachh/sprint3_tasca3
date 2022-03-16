package io.loaders;

import io.SaveLoad;
import io.loaders.interfaces.ILoadFloristeria;
import io.loaders.interfaces.ILoadTickets;

/**
 * Factoria que pillará los diferentes tipos de carga.
 * La idea es tener tener una carga tanto por texto, sql y mondodb (a ver si da tiempo) y 
 * tales cargas utilizarán la misma interfaz. Así, a la hora de hacer los scripts y demás
 * no voy a tener que tocar nada más del código que ya funciona.
 * Tenemos tanto la factoria de tickets como la de floristeria en la misma clase. Obviamente
 * se podría separar, pero complicaría demasiado la cosa y ya la tengo bastante complicada...
 */
public class LoaderFactory {
    
    /**
     * Devuelve una instancia de {@link ILoadFloristeria} según el tipo. Si no existe
     * devuelve null
     * @param type
     * @return
     */
    public ILoadFloristeria getFloristeria(final String type){
        if(type.equalsIgnoreCase(SaveLoad.LOADER_TXT)){
            return new LoadTxt();
        }
        return null;
    }

    /**
     * Devuelve una instancia de {@link ILoadTickets} según el tipo. Si no existe
     * devuelve null
     * @param type
     * @return
     */
    public ILoadTickets getTickets(final String type){
        if(type.equalsIgnoreCase(SaveLoad.LOADER_TXT)){
            return new LoadTxt();
        }
        return null;
    }
}
