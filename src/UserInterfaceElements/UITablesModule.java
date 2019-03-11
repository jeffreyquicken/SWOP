package UserInterfaceElements;

import Data.Table;
import Data.dataController;
import paintModule.paintModule;
import EventHandlers.*;
import sun.font.TrueTypeFont;

import java.util.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class UITablesModule {

    private paintModule paintModule;
    private mouseEventHandler mouseEventHandler;
    private String currMode = "normal";
    private int[] activeCell;
    private String tempText;
    private Boolean invalidInput;
    private int draggedColumn;
    private int draggedX;

    //Constructor that init/creates paintModule and an empty list with tablenames
    //Each UImodule has own paintmodule to save settings (e.g. size, bg, ...)
    public UITablesModule() {
        paintModule = new paintModule(1);
        mouseEventHandler = new mouseEventHandler();
        invalidInput = false;
        tempText = "Default_Text";
        draggedColumn = 1;
        draggedX = 1;
    }

    //Handles mousevent and returns if UImode need to change
    public String handleMouseEvent(int xCo, int yCo, int count, int ID, dataController data) {
        String nextUImode = "table";
        //EVENT DOUBLE CLICKS UNDER TABLE
        if (currMode == "normal" && mouseEventHandler.doubleClickUnderTable(yCo, count, ID, data.getLowestY()) ) {
            int numberOfTable = data.getTableList().size() + 1;
            Table newTable = new Table("Table" + numberOfTable);
            data.addTable(newTable);
        }

        //EVENT CLICK CELL
        //TODO: check if margin clicked
        int[] clickedCell = mouseEventHandler.getCellID(xCo, yCo, paintModule.getxCoStart(), paintModule.getyCoStart(), paintModule.getCellHeight(), paintModule.getCellWidth(), data.getTableList().size(), 1,paintModule.getWidthList());
       //Checks if user is dragging border
            if(currMode == "drag"){
                if(ID == 506){
                    int delta = xCo - draggedX;
                    int previousWidth = paintModule.getWidthList().get(draggedColumn);
                    int newWidth = previousWidth +delta;
                    int sum = paintModule.getWidthList().stream().mapToInt(Integer::intValue).sum();
                    if(newWidth >= paintModule.getMinCellWidth() && sum + delta < 590 - paintModule.getxCoStart() ){
                        paintModule.getWidthList().set(draggedColumn, newWidth);
                        draggedX = xCo;
                    }
                }else{
                    currMode ="normal";
                }
        }
        //check if leftmargin is clicked
        else if(currMode != "edit" && mouseEventHandler.marginLeftClicked(xCo,yCo,paintModule.getxCoStart(), paintModule.getyCoStart(), paintModule.getCellHeight(), paintModule.getCellWidth(), data.getTableList().size(), 1, paintModule.getCellLeftMargin(), paintModule.getWidthList()) != null) {
            currMode = "delete";
            activeCell = clickedCell;
        }
        //Check if a cell is clicked
        else if (!invalidInput && currMode!= "delete" && clickedCell[1] != -1 && clickedCell[0] != -1) {
            if (count != 2){
            activeCell = clickedCell;
            currMode = "edit";
            tempText = data.getTableList().get(activeCell[0]).getTableName();}
            else{
                data.setSelectedTable(data.getTableList().get(clickedCell[0]));
                nextUImode = "row";
                return nextUImode;
            }
            }
        //Check if header is clicked
        else if(mouseEventHandler.rightBorderClicked(xCo,yCo,paintModule.getxCoStart(), paintModule.getyCoStart(), paintModule.getWidthList().size(), paintModule.getCellHeight(), paintModule.getWidthList()) != -1){
            System.out.println("RIGHT BORDER CLICKED");
            currMode ="drag";
            draggedColumn= mouseEventHandler.rightBorderClicked(xCo,yCo,paintModule.getxCoStart(), paintModule.getyCoStart(), paintModule.getWidthList().size(), paintModule.getCellHeight(), paintModule.getWidthList());
            draggedX = xCo;
        }
            //EVENT edit mode and clicked outside table
        else if (currMode == "edit"  && !invalidInput) {
            currMode = "normal";
            data.getTableList().get(activeCell[0]).setTableName(tempText);
        }
        else if (currMode == "delete"){
            currMode = "normal";
        }
        invalidInput = !textIsValid(tempText, data);

        return nextUImode;
    }

    //Handles keyevent and returns if UImode need to change
    public String handleKeyEvent(int id, int keyCode, char keyChar, dataController data) {
        keyEventHandler eventHandler = new keyEventHandler();
        if (currMode == "edit") {
            //EVENT: ASCSII char pressed
            if (eventHandler.isChar(keyCode)) {
                tempText = tempText + keyChar;
                invalidInput = !textIsValid(tempText, data);
            }

            //EVENT BS pressed and in edit mode
            else if (eventHandler.isBackspace(keyCode)) {

                //Check if string is not empty
                if (tempText.length() != 0) {
                    tempText = tempText.substring(0, tempText.length() - 1);
                }
                //empty string, display red border
            }
            //EVENT ENTER pressed
            else if (eventHandler.isEnter(keyCode)) {
                currMode = "normal";
                data.getTableList().get(activeCell[0]).setTableName(tempText);

            }

        }
        //DEL key only has functionality in delete mode
        //EVENT: DEL pressed
        else if (currMode == "delete" ){
            if(eventHandler.isDelete(keyCode)){
            List<Table> list = data.getTableList();
            Table selectedTable = list.get(activeCell[0]);
            data.deleteTable(selectedTable);
            currMode = "normal";
            }
        }
        //out of if checks: functionality doesn't depend on mode and works as  a way to reset the previous state
        //EVENT: ESC char pressed
        if (eventHandler.isEscape(keyCode)) {
            currMode = "normal";
            tempText = "default_text";
        }
        invalidInput = !textIsValid(tempText, data);
        return "table";
    }

    //Method that takes care of painting the canvas
    //It calls method from paintModule
    public void paint(Graphics g, dataController data) {
        //Creates title
        paintModule.paintTitle(g, "Table Mode");

        //print tables in tabular view
        paintModule.paintTableView(g, data.getTableList(), paintModule.getxCoStart(), paintModule.getyCoStart());

        //Check mode
        if (currMode == "edit" ) {
            paintModule.paintCursor(g, paintModule.getCellCoords(activeCell[0], activeCell[1])[0], paintModule.getCellCoords(activeCell[0], activeCell[1])[1], paintModule.getWidthList().get(activeCell[1]), paintModule.getCellHeight(), tempText);
        }

        //check if there are warnings
        if (invalidInput || currMode == "delete") {
            paintModule.paintBorder(g, paintModule.getCellCoords(activeCell[0], activeCell[1])[0], paintModule.getCellCoords(activeCell[0], activeCell[1])[1],  paintModule.getWidthList().get(activeCell[1]), paintModule.getCellHeight(), Color.RED);
        } else {
            paintModule.setColor(g, Color.BLACK);
        }
        //paintModule.paintBorder(g,paintModule.getxCoStart(), paintModule.getyCoStart(), 80, 20, "red");
    }

    //method that checks if a string is valid as a table name
    public boolean textIsValid(String text, dataController data) {
        for (Table table : data.getTableList()) {
            if (table.getTableName().equals(text)) {
                if (!table.getTableName().equals(data.getTableList().get(activeCell[0]).getTableName())) {
                    return false;
                }
            }
        }
        if (text.length() == 0) {
            return false;
        }
        return true;
    }


}
