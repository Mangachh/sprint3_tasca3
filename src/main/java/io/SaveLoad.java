package io;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import items.Floristeria;
import items.Ticket;
import app.App;
import io.loaders.LoaderFactory;
import io.loaders.interfaces.ILoadFloristeria;
import io.loaders.interfaces.ILoadTickets;
import io.savers.SaverFactory;
import io.savers.interfaces.ISaveFloristeria;
import io.savers.interfaces.ISaveTickets;

/**
 * Clase pincipal para Guardar y cargar archivos
 */
public class SaveLoad implements IReadProperty {

	/**
	 * Archivo de propiedas con todos los seteos
	 */
	private static final String PROPERTIES_FILE_NAME = "saveLoad_data.properties";

	/**
	 * Nombre de la propiedad que determina el tipo de archivo en el que guardar-cargar
	 */
	private static final String LOADER_HEAD = "load_type";

	/**
	 * valor de carga txt
	 */
	public static final String LOADER_TXT = "txt";

	/**
	 * Factoria de carga
	 */
	private LoaderFactory loadFactory;

	public SaveLoad(){
		this.loadFactory = new LoaderFactory();
	}

	/**
	 * Método que carga una loristeria a partir de un archivo
	 * @return -> nueva floristeria
	 */
	public Floristeria loadFloristeria() {
		String loaderType = readProperty(LOADER_HEAD);
		Floristeria floris = null;
		ILoadFloristeria loader = null;

		loader = this.loadFactory.getFloristeria(loaderType);
		floris = loader.loadFloristeria(this);
		return floris;
	}

	/**
	 * Método que carga una lista de tickets a partir de u archivo
	 * @return -> lista de tickets
	 */
	public List<Ticket> loadTickets(){
		String loaderType = readProperty(LOADER_HEAD);
		List<Ticket> tickets = null;
		ILoadTickets loader = null;

		loader = this.loadFactory.getTickets(loaderType);
		tickets = loader.loadTickets(this);
		return tickets;
	}

	/**
	 * Método para guardar una floristeria con su stock
	 * @param floristeria
	 */
	public void saveFloristeria(final Floristeria floristeria){
		String saverType = readProperty(LOADER_HEAD);
		
		try{
			ISaveFloristeria saver = new SaverFactory().getFloristeriaSaver(saverType);
			saver.saveFloristeria(floristeria, this);
		}catch(NullPointerException e){
			System.out.println(e.getMessage());
		}		
	}

	/**
	 * Método para guardar los tickets
	 * @param floristeria -> floristeria qeu contiene los tickets
	 */
	public void saveTickets(final Floristeria floristeria){
		String saverType = readProperty(LOADER_HEAD);
		try{
			ISaveTickets saver = new SaverFactory().getTicketsSaver(saverType);
			saver.saveTickets(floristeria.getTickets(), this);
		}catch(NullPointerException e){
			System.err.println(e.getMessage());
		}
	}

	
	@Override
	public String readProperty(final String property) {
		String propText = "";
		try (InputStream input = App.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME)) {
			Properties prop = new Properties();

			if (input == null) {
				System.out.println("No se ha encontrado el archivo de propiedades: " + PROPERTIES_FILE_NAME);
			}
			prop.load(input);
			propText = prop.getProperty(property);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		return propText;

	}

}
