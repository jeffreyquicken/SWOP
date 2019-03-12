package UserInterfaceElements;

import Data.Column;
import Data.Table;
import Data.dataController;
import EventHandlers.mouseEventHandler;
import paintModule.paintModule;

import java.awt.*;

public class UIDesignModule extends UISuperClass {
    private paintModule paintModule;
    private EventHandlers.mouseEventHandler mouseEventHandler;
    private int xCoStart = 50 ;
    private int yCoStart = 50;
    private String currMode = "normal";
    private int[] activeCell;
    private String tempText;
    private Boolean invalidInput;
    private int draggedColumn;
    private int draggedX;


    //Constructor that init/creates paintModule and an empty list with tablenames
    //Each UImodule has own paintmodule to save settings.settings (e.g. size, bg, ...)
    public UIDesignModule(){
        paintModule = new paintModule();
        mouseEventHandler = new mouseEventHandler();
    }

    //Handles mousevent and returns if UImode need to change
    public String handleMouseEvent(int xCo, int yCo,int count, int ID,  dataController data){
        //EVENT DOUBLE CLICKS UNDER TABLE
        int lowestY = (data.getSelectedTable().getColumnNames().size()*paintModule.getCellHeight())+paintModule.getyCoStart();
        if (currMode == "normal" && mouseEventHandler.doubleClickUnderTable(yCo, count, ID, lowestY) ) {
            int numberOfCols = data.getSelectedTable().getColumnNames().size() + 1;
            String newName = "Column" + numberOfCols;
            int i = numberOfCols;
            while (!textIsValid(newName, data, null)){
                i++;
                newName = "Column" + i;
            }
            Column newCol = new Column(newName, "", "String", true);
            data.getSelectedTable().addColumn(newCol);
        }
        return "design";
    }

    private boolean textIsValid(String text, dataController data, String currName) {
        for (Column col : data.getSelectedTable().getColumnNames()) {
            if (col.getName().equals(text)) {
                if (!col.getName().equals(currName)) {
                    return false;
                }
            }
        }
        if (text.length() == 0) {
            return false;
        }
        return true;
    }

    //Handles mousevent and returns if UImode need to change
    public String handleKeyEvent(int id, int keyCode, char keyChar,   dataController tableController){
        //EVENT: t pressed
        String nextUImode = "row";
        if(keyChar == 't'){ nextUImode = "table";}else{
            nextUImode = "row";
        }

        return nextUImode;
    }

    //Method that takes care of painting the canvas
    //It calls method from paintModule
    public void paint(Graphics g, Table table){
        //Creates title
        paintModule.paintTitle(g, "Design Mode");

        //print tables in tabular view
        paintModule.paintDesignView(g, table);
    }




}
