package io.savers;

import io.SaveLoad;

public class SaverFactory {
    public ISaveFloristeria getSaver(final String type){
        if(type.equalsIgnoreCase(SaveLoad.LOADER_TXT)){
            return new SaveTxt();
        }
        return null;
    }
}
