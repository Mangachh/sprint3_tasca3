package io.loaders;

import io.SaveLoad;

public class LoaderFactory {
    
    public ILoadFloristeria getFloristeria(final String type){
        if(type.equalsIgnoreCase(SaveLoad.LOADER_TXT)){
            return new LoadTxt();
        }
        return null;
    }
}
