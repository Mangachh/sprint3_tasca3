package io.loaders.interfaces;

import java.util.List;

import io.IReadProperty;
import items.Ticket;

/**
 * Interfaz para cargar una lista de {@link Ticket}
 */
public interface ILoadTickets {
    /**
     * Devuelve una nueva lista de tickets a partir de un archivo
     * @param reader -> necesario para leer los detalles del archivo de properties
     * @return       -> nueva lista de tickets
     */
    List<Ticket> loadTickets(final IReadProperty reader);
}
