package UserInterfaceElements;

import Data.Table;
import Data.dataController;
import paintModule.paintModule;
import EventHandlers.*;
import settings.settings;
import sun.font.TrueTypeFont;

import java.util.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class UITablesModule extends UISuperClass{

    //Constructor that init/creates paintModule and an empty list with tablenames
    //Each UImodule has own paintmodule to save settings.settings (e.g. size, bg, ...)
	//constructor inherited from SuperClass
    public UITablesModule() {
        super();
    }

    //Handles mousevent and returns if UImode need to change
    public String handleMouseEvent(int xCo, int yCo, int count, int ID, dataController data) {
        String nextUImode = "table";
        //EVENT DOUBLE CLICKS UNDER TABLE
        if (currMode == "normal" && mouseEventHandler.doubleClickUnderTable(yCo, count, ID, data.getLowestY()) ) {
            int numberOfTable = data.getTableList().size() + 1;
            String newName = "Table" + numberOfTable;
            int i = numberOfTable;
            while (!textIsValid(newName, data, null)){
                i++;
                newName = "Table" + i;
            }
            Table newTable = new Table(newName);
            data.addTable(newTable);
        }

        //EVENT CLICK CELL
        //TODO: check if margin clicked
        settings setting;
        if (data.getSelectedTable() == null){
            setting = data.getSetting();
        }
        else{
            setting = data.getSelectedTable().getSetting();
        }
        List<Integer> widthList = setting.getWidthList();
        int[] clickedCell = mouseEventHandler.getCellID(xCo, yCo, paintModule.getxCoStart(), paintModule.getyCoStart(),
                paintModule.getCellHeight(), paintModule.getCellWidth(), data.getTableList().size(), 1,widthList);
       //Checks if user is dragging border
            if(currMode == "drag"){
                if(ID == 506){
                    int delta = xCo - draggedX;
                    int previousWidth = widthList.get(draggedColumn);
                    int newWidth = previousWidth +delta;
                    int sum = widthList.stream().mapToInt(Integer::intValue).sum();
                    if(newWidth >= paintModule.getMinCellWidth() && sum + delta < 590 - paintModule.getxCoStart() ){
                        widthList.set(draggedColumn, newWidth);
                        draggedX = xCo;
                    }
                }else{
                    currMode ="normal";
                }
        }
        //check if leftmargin is clicked
        else if(currMode != "edit" && mouseEventHandler.marginLeftClicked(xCo,yCo,paintModule.getxCoStart(),
                    paintModule.getyCoStart(), paintModule.getCellHeight(), paintModule.getCellWidth(),
                    data.getTableList().size(), 1, paintModule.getCellLeftMargin(), widthList) != null) {
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
        else if(mouseEventHandler.rightBorderClicked(xCo,yCo,paintModule.getxCoStart(), paintModule.getyCoStart(),
                    widthList.size(), paintModule.getCellHeight(), widthList) != -1){
            System.out.println("RIGHT BORDER CLICKED");
            currMode ="drag";
            draggedColumn= mouseEventHandler.rightBorderClicked(xCo,yCo,paintModule.getxCoStart(),
                    paintModule.getyCoStart(), widthList.size(), paintModule.getCellHeight(), widthList);
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


        return nextUImode;
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

    //method that checks if a string is valid as a table name
    public boolean textIsValid(String text, dataController data, String currName) {
        for (Table table : data.getTableList()) {
            if (table.getTableName().equals(text)) {
                if (!table.getTableName().equals(currName)) {
                    return false;
                }
            }
        }
        if (text.length() == 0) {
            return false;
        }
        return true;
    }
   
    /*
     * (non-Javadoc)
     * @see UserInterfaceElements.UISuperClass#handleKeyEditMode(int, int, char, Data.dataController)
     * 
     * the next methods will be called using the superclass method handleKeyEvent
     * the method in the superclass works as a flow controller but doesn't have the functionality needed for the specific UI modules
     * this is implemented in the next few methods
     */
    @Override
    protected void handleKeyEditMode(int id, int keyCode, char keyChar, dataController data){
    	keyEventHandler eventHandler = new keyEventHandler();
        //EVENT: ASCSII char pressed
        if (eventHandler.isChar(keyCode)) {
            tempText = tempText + keyChar;
            String currName = data.getTableList().get(activeCell[0]).getTableName();
            invalidInput = !textIsValid(tempText, data, currName);
        }

        //EVENT BS pressed and in edit mode
        else if (eventHandler.isBackspace(keyCode)) {

            //Check if string is not empty
            if (tempText.length() != 0) {
                tempText = tempText.substring(0, tempText.length() - 1);
                String currName = data.getTableList().get(activeCell[0]).getTableName();
                invalidInput = !textIsValid(tempText, data, currName);

            }
            //empty string, display red border
        }
        //EVENT ENTER pressed
        else if (eventHandler.isEnter(keyCode)) {
            currMode = "normal";
            data.getTableList().get(activeCell[0]).setTableName(tempText);

        }

    }
    
    @Override
    protected void handleKeyNormalMode(int id, int keyCode, char keyChar, dataController data){}
    
    @Override
    protected void handleKeyDeleteMode(int id, int keyCode, char keyChar, dataController data){
    	keyEventHandler eventHandler = new keyEventHandler();
    	//DEL key pressed
        if(eventHandler.isDelete(keyCode) || keyChar == 'd'){
        List<Table> list = data.getTableList();
        Table selectedTable = list.get(activeCell[0]);
        data.deleteTable(selectedTable);
        currMode = "normal";
        }
    }
    
    @Override
    protected void handleNonModeDependantKeys (int id, int keyCode, char keyChar, dataController data){
    	keyEventHandler eventHandler = new keyEventHandler();
    	if (eventHandler.isEscape(keyCode)) {
            currMode = "normal";
            tempText = "default_text";
        }
    }

}
