package io.savers;

import io.SaveLoad;
import io.savers.interfaces.ISaveFloristeria;
import io.savers.interfaces.ISaveTickets;

/** 
 * Factoria de guardado.
 * En algún momento pensé en hacer una abstract factory pero, de nuevo,
 * para lo que hago era complicar el código.
 */
public class SaverFactory {

    /**
     * Devulete una instancia de {@link ISaveFloristeria} si existe.
     * @param type -> typo de instancia
     * @return     -> nueva iflor
     */
    public ISaveFloristeria getFloristeriaSaver(final String type){
        if(type.equalsIgnoreCase(SaveLoad.LOADER_TXT)){
            return new SaveTxt();
        }
        return null;
    }

    /**
     * Devulete una instancia de {@link ISaveTickets} si existe.
     * @param type -> typo de instancia
     * @return     -> nueva iflor
     */
    public ISaveTickets getTicketsSaver(final String type){
        if(type.equalsIgnoreCase(SaveLoad.LOADER_TXT)){
            return new SaveTxt();
        }
        return null;
    }
}
