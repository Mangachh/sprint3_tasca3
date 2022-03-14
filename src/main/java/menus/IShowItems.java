package menus;

import app.AppController;
import app.Print;

@FunctionalInterface
public interface IShowItems {    
    void printItems(final AppController controller, final Print print);
}
