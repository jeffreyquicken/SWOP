package UserInterfaceElements;


import Data.dataController;
import EventHandlers.keyEventHandler;
import EventHandlers.mouseEventHandler;
import paintModule.paintModule;
import settings.settings;

import java.awt.*;
import java.util.List;

public class UISuperClass {
    protected paintModule paintModule;
    protected mouseEventHandler mouseEventHandler;
    protected String currMode = "normal";
    protected int[] activeCell;
    protected String tempText;
    protected Boolean invalidInput;
    protected int draggedColumn;
    protected int draggedX;

    //TODO rewrite other UI modules to use superclass methods
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
        if (currMode == "edit") {
            this.handleKeyEditMode(id, keyCode, keyChar, data);
        }
        else if (currMode == "delete" ){
            this.handleKeyDeleteMode(id, keyCode, keyChar, data);
        }
        else if (currMode == "normal"){
            this.handleKeyNormalMode(id, keyCode, keyChar, data);
        }
        this.handleNonModeDependantKeys(id, keyCode, keyChar, data);
        return "table";
    }

    //Method that takes care of painting the canvas
    //It calls method from paintModule
    public void paint(Graphics g, dataController data) {
        settings setting;
        if (data.getSelectedTable() == null){
            setting = data.getSetting();
        }
        else{
            setting = data.getSelectedTable().getSetting();
        }

        List<Integer> widthList = setting.getWidthList();
        //Creates title
        paintModule.paintTitle(g, "Table Mode");

        //print tables in tabular view
        paintModule.paintTableView(g, data.getTableList(), paintModule.getxCoStart(), paintModule.getyCoStart(), setting);

        //Check mode
        if (currMode == "edit" ) {
            paintModule.paintCursor(g, paintModule.getCellCoords(activeCell[0], activeCell[1])[0],
                    paintModule.getCellCoords(activeCell[0], activeCell[1])[1], widthList.get(activeCell[1]),
                    paintModule.getCellHeight(), tempText);
        }

        //check if there are warnings
        if (invalidInput || currMode == "delete") {
            paintModule.paintBorder(g, paintModule.getCellCoords(activeCell[0], activeCell[1])[0],
                    paintModule.getCellCoords(activeCell[0], activeCell[1])[1],  widthList.get(activeCell[1]),
                    paintModule.getCellHeight(), Color.RED);
        } else {
            paintModule.setColor(g, Color.BLACK);
        }
        //paintModule.paintBorder(g,paintModule.getxCoStart(), paintModule.getyCoStart(), 80, 20, "red");
    }

    protected void handleKeyEditMode(int id, int keyCode, char keyChar, dataController data){}
    protected void handleKeyNormalMode(int id, int keyCode, char keyChar, dataController data){}
    protected void handleKeyDeleteMode(int id, int keyCode, char keyChar, dataController data){}
    protected void handleNonModeDependantKeys (int id, int keyCode, char keyChar, dataController data){}
}
