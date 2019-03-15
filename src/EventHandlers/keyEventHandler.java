package EventHandlers;

import Data.Table;
import Data.dataController;

import java.util.List;

public class keyEventHandler {

    /**
     * Check if keycode is a normal char
     * @param keyCode
     * @return
     */
    public Boolean isChar(int keyCode){
        return (keyCode > 31 && keyCode < 123);
    }

    /**
     * Check if keycode is delete
     * @param keyCode
     * @return
     */
    public Boolean isDelete(int keyCode){
        return (keyCode == 127);
    }

    /**
     * Checks of keycode is a Backspace
     * @param keyCode
     * @return
     */
    public boolean isBackspace (int keyCode){
        return (keyCode == 8);
    }

    /**
     * Cheks if keycode is enter
     * @param keyCode
     * @return
     */
    public boolean isEnter(int keyCode){
        return(keyCode == 10);
    }

    /**
     * Checks is keycode is escape
     * @param keyCode
     * @return
     */
    public boolean isEscape(int keyCode){
        return(keyCode == 27);
    }

}
