package UserInterfaceElements;


import Data.dataController;
import EventHandlers.keyEventHandler;
import EventHandlers.mouseEventHandler;
import paintModule.paintModule;
import settings.settings;

import java.awt.*;
import java.util.List;

public abstract class UISuperClass {
    protected paintModule paintModule;
    protected mouseEventHandler mouseEventHandler;
    protected String currMode = "normal";
    protected int[] activeCell;
    protected String tempText;
    protected Boolean invalidInput;
    protected int draggedColumn;
    protected int draggedX;
    protected boolean ctrlPressed;

    /**
     * constructor for UISuperclass
     */
    public UISuperClass() {
        paintModule = new paintModule();
        mouseEventHandler = new mouseEventHandler();
        invalidInput = false;
        tempText = "Default_Text";
        draggedColumn = 1;
        draggedX = 1;
    }


    //Handles keyevent and returns if UImode need to change
    public String handleKeyEvent(int id, int keyCode, char keyChar, dataController data) {
        keyEventHandler eventHandler = new keyEventHandler();
        String nextUIMode = "";



        if (currMode == "edit") {
            List<String> result = this.handleKeyEditMode(id, keyCode, keyChar, data);
            currMode = result.get(0);
            nextUIMode = result.get(1);
            ctrlPressed = false;

        }
        else if (currMode == "delete" ){
            List<String> result = this.handleKeyDeleteMode(id, keyCode, keyChar, data);
            currMode = result.get(0);
            nextUIMode = result.get(1);
            ctrlPressed = false;
        }
        else if (currMode == "normal"){
            List<String> result = this.handleKeyNormalMode(id, keyCode, keyChar, data);
            currMode = result.get(0);
            nextUIMode = result.get(1);

        }
        else if (currMode == "drag"){
            List<String> result = this.handleKeyNormalMode(id, keyCode, keyChar, data);
            currMode = result.get(0);
            nextUIMode = result.get(1);
        }
        //this.handleNonModeDependantKeys(id, keyCode, keyChar, data);
        return nextUIMode;
    }

    protected void paint(Graphics g, dataController data, Integer[] coords, Integer[] dimensions){ }



    protected List<String> handleKeyEditMode(int id, int keyCode, char keyChar, dataController data){return null;}
    protected List<String> handleKeyNormalMode(int id, int keyCode, char keyChar, dataController data){return null;}
    protected List<String> handleKeyDeleteMode(int id, int keyCode, char keyChar, dataController data){return null;}
    protected String handleMouseEvent(int xCo, int yCo, int count, int ID, dataController data){

        List<String> result = this.handleMouseEvent2(xCo,  yCo,  count, ID,  data);
        currMode = result.get(0);
        return result.get(1);

    }

    protected List<String> handleMouseEvent2(int xCo, int yCo, int count, int ID, dataController data){

        return null;}
    protected void handleNonModeDependantKeys (int id, int keyCode, char keyChar, dataController data){}
}
