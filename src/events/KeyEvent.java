package events;

import Data.Table;
import Data.dataController;

import java.util.List;

/**
 * A class of keyevents with methods for keyevent checking
 */
public class KeyEvent {

    /**
     * Check if keycode is a normal character
     *
     * @param keyCode the keycode to be checked
     * @return whether the keycode is a normal character
     */
    public Boolean isChar(int keyCode) {
        return (keyCode > 31 && keyCode < 123);
    }

    /**
     * Check if keycode is delete
     *
     * @param keyCode the keycode to be checked
     * @return whether the keycode is delete
     */
    public Boolean isDelete(int keyCode) {
        return (keyCode == 127);
    }

    /**
     * Checks of keycode is a Backspace
     *
     * @param keyCode the keycode to be checked
     * @return whether the keycode is a backspace
     */
    public boolean isBackspace(int keyCode) {
        return (keyCode == 8);
    }

    /**
     * Cheks if keycode is enter
     *
     * @param keyCode the keycode to be checked
     * @return whether the keycode is enter
     */
    public boolean isEnter(int keyCode) {
        return (keyCode == 10);
    }

    /**
     * Checks is keycode is escape
     *
     * @param keyCode the keycode to be checked
     * @return whether the keycode is escape
     */
    public boolean isEscape(int keyCode) {
        return (keyCode == 27);
    }

}
