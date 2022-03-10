package io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import items.Floristeria;
import app.App;
import io.loaders.ILoadFloristeria;

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
