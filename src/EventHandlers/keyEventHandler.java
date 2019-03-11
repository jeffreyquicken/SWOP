package EventHandlers;

import Data.Table;
import Data.dataController;

import java.util.List;

public class keyEventHandler {

    public Boolean isChar(int keyCode){
        return (keyCode > 31 && keyCode < 123);
    }

    public Boolean isDelete(int keyCode){
        return (keyCode == 127);
    }

    public boolean isBackspace (int keyCode){
        return (keyCode == 8);
    }

public boolean isEnter(int keyCode){
        return(keyCode == 10);
    }

    public boolean isEscape(int keyCode){
        return(keyCode == 27);
    }

}
