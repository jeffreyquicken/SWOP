package UserInterfaceElements;

import Data.Column;
import Data.Table;
import Data.dataController;
import EventHandlers.mouseEventHandler;
import paintModule.paintModule;
import settings.settings;

import java.awt.*;
import java.util.List;

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
        invalidInput = false;
    }

    //Handles mousevent and returns if UImode need to change
    public String handleMouseEvent(int xCo, int yCo,int count, int ID,  dataController data){

        settings setting;
        if (data.getSelectedTable() == null){
            setting = data.getSelectedTable().getDesignSetting();
        }
        else{
            setting = data.getSelectedTable().getDesignSetting();
        }
        List<Integer> widthList = setting.getWidthList();
        int[] clickedCell = mouseEventHandler.getCellID(xCo, yCo, paintModule.getxCoStart(), paintModule.getyCoStart(),
                paintModule.getCellHeight(), paintModule.getCellWidth(), data.getSelectedTable().getColumnNames().size(), 1,widthList);

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

        //Check if a cell is clicked

        else if (!invalidInput && currMode!= "delete" && clickedCell[1] != -1 && clickedCell[0] != -1) {
            if (count != 2){
                activeCell = clickedCell;
                currMode = "edit";
                tempText = data.getSelectedTable().getColumnNames().get(activeCell[0]).getName();}
            else{
                data.setSelectedTable(data.getTableList().get(clickedCell[0]));
            }
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
    public void paint(Graphics g, Table table, dataController data){
        settings setting;
        if (data.getSelectedTable() == null){
            setting = data.getSelectedTable().getDesignSetting();
        }
        else{
            setting = data.getSelectedTable().getDesignSetting();
        }
        List<Integer> widthList = setting.getWidthList();

        //Creates title
        paintModule.paintTitle(g, "Design Mode");

        //print tables in tabular view
        paintModule.paintDesignView(g, table);

        //Check mode
        if (currMode == "edit" ) {
            paintModule.paintCursor(g, paintModule.getCellCoords(activeCell[0], activeCell[1])[0],
                    paintModule.getCellCoords(activeCell[0], activeCell[1])[1], widthList.get(activeCell[1]),
                    paintModule.getCellHeight(), tempText);
        }





    }




}
