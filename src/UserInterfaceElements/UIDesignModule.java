package UserInterfaceElements;

import Data.Column;
import Data.Table;
import Data.dataController;
import EventHandlers.keyEventHandler;
import EventHandlers.mouseEventHandler;
import paintModule.paintModule;
import settings.settings;

import java.awt.*;
import java.util.ArrayList;
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
    public List<String> handleMouseEvent2(int xCo, int yCo,int count, int ID,  dataController data){

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
            //check which collumn
            if (count != 2 && clickedCell[1] == 0 ){
                activeCell = clickedCell;
                currMode = "edit";
                tempText = data.getSelectedTable().getColumnNames().get(activeCell[0]).getName();}
            else if(clickedCell[1] == 3){
                Boolean prevBool = data.getSelectedTable().getColumnNames().get(clickedCell[0]).getBlanksAllowed();
                data.getSelectedTable().getColumnNames().get(clickedCell[0]).setBlanksAllowed(!prevBool);
        }}

        List<String> result = new ArrayList<>();
        result.add(currMode);
        result.add("design");
        return result;
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



    //Method that takes care of painting the canvas
    //It calls method from paintModules
    public void paint(Graphics g, dataController data){
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
        paintModule.paintDesignView(g, data.getSelectedTable());

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
    protected List<String> handleKeyEditMode(int id, int keyCode, char keyChar, dataController data){
        keyEventHandler eventHandler = new keyEventHandler();
        //EVENT: ASCSII char pressed
        if (eventHandler.isChar(keyCode)) {
            tempText = tempText + keyChar;
            String currName = data.getSelectedTable().getColumnNames().get(activeCell[0]).getName();
            invalidInput = !textIsValid(tempText, data, currName);
        }

        //EVENT BS pressed and in edit mode
        else if (eventHandler.isBackspace(keyCode)) {

            //Check if string is not empty
            if (tempText.length() != 0) {
                tempText = tempText.substring(0, tempText.length() - 1);
                String currName = data.getSelectedTable().getColumnNames().get(activeCell[0]).getName();
                invalidInput = !textIsValid(tempText, data, currName);

            }
            //empty string, display red border
        }
        //EVENT ENTER pressed
        else if (eventHandler.isEnter(keyCode)) {
            currMode = "normal";
            data.getSelectedTable().getColumnNames().get(activeCell[0]).getName();

        }
        List<String> result = new ArrayList<>();
        result.add(currMode);
        result.add("design");
        return result;

    }

    protected List<String> handleKeyNormalMode(int id, int keyCode, char keyChar, dataController data){ List<String> result = new ArrayList<>();
        result.add("normal");
        result.add("design");
        return result;}
    protected List<String> handleKeyDeleteMode(int id, int keyCode, char keyChar, dataController data){
        List<String> result = new ArrayList<>();

        result.add("normal");
        result.add("design");
        return result;}


}
