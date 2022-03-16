package io;

/**
 * Interfaz para el lector de archivos de properties.
 * Gracias a esta interfaz podemos pasar el lector a diferentes
 * clases sin necesidad ni de instanciar ni de injerctar la clase.
 * De esta nmanera el lector está en una clase y la aplicación
 * puede estar en múltiples
 */
public interface IReadProperty {
    /**
     * Lee una propiedad a partir del nombre
     * @param propertyName -> npmbre de la propiedad
     * @return             -> valor de la propiedad
     */
    String readProperty(final String propertyName);
}
