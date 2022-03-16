package io.savers.interfaces;

import java.util.List;

import io.IReadProperty;
import items.Ticket;

/**
 * Interfaz para guardas los tickets en un archivo
 */
public interface ISaveTickets {

    /**
     * Guarda una lista de tikets en un archivo
     * @param tickets -> tickets a guardat
     * @param reader  -> reader para el archivo de propiedades
     */
    void saveTickets(final List<Ticket> tickets, final IReadProperty reader);

}
