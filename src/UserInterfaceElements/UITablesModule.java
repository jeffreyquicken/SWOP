package UserInterfaceElements;

import Data.Table;
import Data.dataController;
import EventHandlers.*;
import settings.settings;

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

    /**
     * Eventhandler that takes care of a mouseclick, and returns the new state of the program
     *
     * @param xCo x-coordinate of click
     * @param yCo y-coordinate of click
     * @param count number of  clicked
     * @param ID Id of mouseclick
     * @param data datacontroller to make changes to the data
     * @return returns a list with the nextUIMode and the state of the UI
     */
    public List<String> handleMouseEvent2(int xCo, int yCo, int count, int ID, dataController data) {
        String nextUImode = "";

        //EVENT DOUBLE CLICKS UNDER TABLE
        if (currMode == "normal" && mouseEventHandler.doubleClickUnderTable(yCo, count, ID, data.getLowestY(30)) ) {
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
        settings setting = data.getSetting();

        List<Integer> widthList = setting.getWidthList();
        int[] clickedCell = mouseEventHandler.getCellID(xCo, yCo, paintModule.getxCoStart(), paintModule.getyCoStart(),
                paintModule.getCellHeight(), paintModule.getCellWidth(), data.getTableList().size(), 1,widthList);

       //Checks if user is dragging border
            if(currMode == "drag"){
                if(ID == 506 || ID == 502){
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
        else if (!invalidInput  && currMode!= "delete" && clickedCell[1] != -1 && clickedCell[0] != -1) {
            if (count != 2){
            activeCell = clickedCell;
            currMode = "edit";
            tempText = data.getTableList().get(activeCell[0]).getTableName();}
            else{
                data.setSelectedTable(data.getTableList().get(clickedCell[0]));
                if((data.getSelectedTable().getColumnNames().size() == 0) && (ID == 502)){
                    nextUImode = "design";
                }else if (ID == 502) {
                nextUImode = "row";}
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


        List<String> result = new ArrayList<>();
        result.add(currMode);
        result.add(nextUImode);
        return result;
    }

    //Method that takes care of painting the canvas
    //It calls method from paintModule

    /**
     * Method that paints the correct view on the canvas
     *
     * @param g graphics object
     * @param data datacontroller
     */
    @Override
    public void paint(Graphics g, dataController data, Integer[] coords, Integer[] dimensions) {
        settings setting;
        setting = data.getSetting();
        paintModule.setBackground(g,coords[0], coords[1], dimensions[0], dimensions[1], Color.WHITE);
        paintModule.paintBorderSubwindow( g, coords, dimensions, "Table Mode",this.getActive());

        List<Integer> widthList = setting.getWidthList();

        int sum = widthList.stream().mapToInt(Integer::intValue).sum();
        double percentageHorizontal = 0;
        double percentageVertical =0;
        if(sum > dimensions[0] - 31 ){
            percentageHorizontal =  (Double.valueOf(dimensions[0]-30)/ Double.valueOf(sum));
            System.out.println(percentageHorizontal);
        }
        if(data.getTableList().size() * 20 > dimensions[1] - 46){
            percentageVertical = ( Double.valueOf(dimensions[1] - 46)/ Double.valueOf((data.getTableList().size() * 20)));
            System.out.println(percentageVertical);
        }

        paintModule.paintHScrollBar(g,coords[0],coords[1] + dimensions[1]-10, dimensions[0], percentageHorizontal);
        paintModule.paintVScrollBar(g, coords[0] + dimensions[0] -10, coords[1] + 15, dimensions[1] - 15, percentageVertical);




        //print tables in tabular view
        paintModule.paintTableView(g, data.getTableList(), coords[0] + paintModule.getMargin(), coords[1] +paintModule.getMargin(), setting, dimensions[0] - 45, dimensions[1] - 58);

        //Check mode
        if (currMode == "edit" ) {
            int[] coords1 = paintModule.getCellCoords(activeCell[0], activeCell[1], widthList);
            paintModule.paintCursor(g, coords1[0] + coords[0],
                    coords1[1] + coords[1], widthList.get(activeCell[1]),
                    paintModule.getCellHeight(), tempText);
        }

        //check if there are warnings
        if (invalidInput || currMode == "delete") {
            int[] coords1 = paintModule.getCellCoords(activeCell[0], activeCell[1], widthList);
            paintModule.paintBorder(g, coords[0] + coords1[0],
                    coords[1] + coords1[1],  widthList.get(activeCell[1]),
                    paintModule.getCellHeight(), Color.RED);
        } else {
            paintModule.setColor(g, Color.BLACK);
        }
        //paintModule.paintBorder(g,paintModule.getxCoStart(), paintModule.getyCoStart(), 80, 20, "red");
    }


    /**
     * Checks if updated text is valid
     *
     * @param text text to be validated
     * @param data datacontroller
     * @param currName old name
     * @return Wheter the name of the table is valid (hence unique and non-empty)
     */
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
    /**
     * Method that handles key event when the UI is in edit-mode and returns state of program
     *
     * @param id id of key pressed
     * @param keyCode keycde of key pressed
     * @param keyChar keychar of key pressed
     * @param data datacontroller
     * @return returns a list with the nextUImode and the current mode of the UI
     */
    @Override
    protected List<String> handleKeyEditMode(int id, int keyCode, char keyChar, dataController data){
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
        else if (eventHandler.isEnter(keyCode) && !invalidInput) {
            currMode = "normal";
            data.getTableList().get(activeCell[0]).setTableName(tempText);

        }
        //ESCAPE
        if(keyCode == 27){
        	if (eventHandler.isEscape(keyCode)) {
                currMode = "normal";
                tempText = "default_text";
        		}        
        	}

        List<String> result = new ArrayList<>();
        result.add(currMode);
        result.add("");
        return result;


    }

    /**
     * Method that handles key event when the UI is in normal-mode and returns state of program
     *
     * @param id id of key pressed
     * @param keyCode keycde of key pressed
     * @param keyChar keychar of key pressed
     * @param data datacontroller
     * @return returns a list with the nextUImode and the current mode of the UI
     */
    @Override
    protected List<String> handleKeyNormalMode(int id, int keyCode, char keyChar, dataController data){
        List<String> result = new ArrayList<>();
        result.add("normal");
        result.add("");
        return result;


    }

    /**
     * Method that handles key event when the UI is in delete-mode and returns state of program
     *
     * @param id id of key pressed
     * @param keyCode keycde of key pressed
     * @param keyChar keychar of key pressed
     * @param data datacontroller
     * @return returns a list with the nextUImode and the current mode of the UI
     */
    @Override
    protected List<String> handleKeyDeleteMode(int id, int keyCode, char keyChar, dataController data){
    	keyEventHandler eventHandler = new keyEventHandler();
    	//DEL key pressed
        if(eventHandler.isDelete(keyCode) || keyChar == 'd'){
        List<Table> list = data.getTableList();
        Table selectedTable = list.get(activeCell[0]);
        data.deleteTable(selectedTable);
        currMode = "normal";
        }
        List<String> result = new ArrayList<>();
        result.add(currMode);
        result.add("");
        return result;
    }

    @Override
    protected void handleNonModeDependantKeys (int id, int keyCode, char keyChar, dataController data){
    }
}
