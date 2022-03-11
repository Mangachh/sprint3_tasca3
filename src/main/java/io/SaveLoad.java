package io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import items.Floristeria;
import app.App;
import io.loaders.ILoadFloristeria;
import io.loaders.LoaderFactory;
import io.savers.ISaveFloristeria;
import io.savers.SaverFactory;

public class SaveLoad implements IReadProperty {

	private static final String PROPERTIES_FILE_NAME = "saveLoad_data.properties";
	private static final String LOADER_HEAD = "load_type";
	public static final String LOADER_TXT = "txt";

	public Floristeria loadFloristeria() {
		String loaderType = readProperty(LOADER_HEAD);
		Floristeria floris = null;

		if (loaderType.equalsIgnoreCase(LOADER_TXT)) {
			ILoadFloristeria loader = new LoaderFactory().getFloristeria(loaderType);
			floris = loader.loadFloristeria(this);
		}

		return floris;
	}

	public void saveFloristeria(final Floristeria floristeria){
		String saverType = readProperty(LOADER_HEAD);
		
		try{
			ISaveFloristeria saver = new SaverFactory().getSaver(saverType);
			saver.saveFloristeria(floristeria, this);
		}catch(NullPointerException e){
			System.out.println(e.getMessage());
		}		
	}

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
