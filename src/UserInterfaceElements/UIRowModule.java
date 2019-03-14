package UserInterfaceElements;

import Data.Row;
import Data.Table;
import Data.dataController;
import EventHandlers.keyEventHandler;
import EventHandlers.mouseEventHandler;
import paintModule.paintModule;
import settings.settings;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UIRowModule extends UISuperClass{
    private paintModule paintModule;
    private EventHandlers.mouseEventHandler mouseEventHandler;
    private int xCoStart = 50 ;
    private int yCoStart = 50;
    private String currMode = "normal";
    private int[] activeCell;
    private String tempText;
    private Boolean invalidInput= false;
    private int draggedColumn;
    private int draggedX;


    //Constructor that init/creates paintModule and an empty list with tablenames
    //Each UImodule has own paintmodule to save settings.settings (e.g. size, bg, ...)
    public UIRowModule(){
        paintModule = new paintModule();
        mouseEventHandler = new mouseEventHandler();
    }

    //Handles mousevent and returns if UImode need to change
    public List<String> handleMouseEvent2(int xCo, int yCo,int count, int ID,  dataController data){
        List<Integer> widthList = data.getSelectedTable().getRowSetting().getWidthList();


        int[] clickedCell = mouseEventHandler.getCellID(xCo, yCo, paintModule.getxCoStart(), paintModule.getyCoStart(),
                paintModule.getCellHeight(), paintModule.getCellWidth(), data.getSelectedTable().getTableRows().size(), data.getSelectedTable().getColumnNames().size(), widthList);
        int lowestY = (data.getSelectedTable().getColumnNames().size() * paintModule.getCellHeight()) + paintModule.getyCoStart();


        //EVENT DOUBLE CLICKS UNDER TABLE
        if (currMode == "normal" && mouseEventHandler.doubleClickUnderTable(yCo, count, ID, data.getSelectedTable().getLengthTable() + paintModule.getyCoStart()) ) {
           Row row = new Row(data.getSelectedTable().getColumnNames());
           data.getSelectedTable().addRow(row);
        }

        //EVENT CELL CLICKED (VALID INPUT)
        else if (!invalidInput && ID == 500 && currMode != "delete" && clickedCell[1] != -1 && clickedCell[0] != -1) {
                activeCell = clickedCell;
                currMode = "edit";
                if (data.getSelectedTable().getColumnNames().get(activeCell[1]).getType().equals("Boolean")){

                    tempText = data.getSelectedTable().getTableRows().get(activeCell[0]).getColumnList().get(activeCell[1]);

                    if (tempText.equals("true")){
                        tempText = "false";
                    }
                    else if (tempText.equals("false")){
                        if (data.getSelectedTable().getColumnNames().get(activeCell[0]).getBlanksAllowed()){
                            tempText = "empty";
                        }
                        else{
                            tempText = "true";
                        }

                    }
                    else{
                        tempText = "true";
                    }
                    saveText(data);

                }
                else {
                    tempText = data.getSelectedTable().getTableRows().get(activeCell[0]).getColumnList().get(activeCell[1]);
                }
            }




        //Check if header is clicked
        if(mouseEventHandler.rightBorderClicked(xCo,yCo,paintModule.getxCoStart(), paintModule.getyCoStart(), widthList.size(), paintModule.getCellHeight(), widthList) != -1){
            System.out.println("RIGHT BORDER CLICKED");
            currMode ="drag";
            draggedColumn= mouseEventHandler.rightBorderClicked(xCo,yCo,paintModule.getxCoStart(), paintModule.getyCoStart(), widthList.size(), paintModule.getCellHeight(), widthList);
            draggedX = xCo;
            //Checks if user is dragging border
        }else if(currMode == "drag"){
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

        String nextUImode = "row";
        List<String> result = new ArrayList<>();
        result.add(currMode);
        result.add("row");
        return result;
    }


    protected List<String> handleKeyEditMode(int id, int keyCode, char keyChar, dataController data){

        List<String> result = new ArrayList<>();
        result.add("edit");
        result.add("row");
        return result;

    }
    protected List<String> handleKeyNormalMode(int id, int keyCode, char keyChar, dataController data){
        //EVENT: t pressed
        String nextUIMode = "row";
        keyEventHandler eventHandler = new keyEventHandler();
        if(keyCode == 27){ nextUIMode = "table";}

        else if (keyCode == 17){
            ctrlPressed = true;
        }
        else if (ctrlPressed) {
            if (eventHandler.isEnter(keyCode)) {
                nextUIMode = "design";
                ctrlPressed = false;
            }
        }
        else{
            ctrlPressed = false;
        }
        List<String> result = new ArrayList<>();
        result.add("normal");
        result.add(nextUIMode);
        return result;

    }
    protected List<String> handleKeyDeleteMode(int id, int keyCode, char keyChar, dataController data){

        List<String> result = new ArrayList<>();
        result.add(currMode);
        result.add("table");
        return result;
    }

    //Method that takes care of painting the canvas
    //It calls method from paintModule
    public void paint(Graphics g, Table table){
        //Creates title
        paintModule.paintTitle(g, "Row Mode");

        //print tables in tabular view
        paintModule.paintTable(g,table, xCoStart,yCoStart);

    }
    /**
     * Saves the edited text to the datacontroller
     * @param data
     * datacontroller
     */
    private void saveText(dataController data){
        currMode = "normal";
        data.getSelectedTable().getTableRows().get(activeCell[0]).setColumnCell(activeCell[1], tempText);
    }

}
