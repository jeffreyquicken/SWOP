package UserInterfaceElements;




import Data.Table;
import Data.dataController;
import paintModule.paintModule;
import EventHandlers.mouseEventHandler;
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

    //Constructor that init/creates paintModule and an empty list with tablenames
    //Each UImodule has own paintmodule to save settings (e.g. size, bg, ...)
    public UITablesModule(){
        paintModule = new paintModule();
        mouseEventHandler = new mouseEventHandler();
        invalidInput = false;
    }

    //Handles mousevent and returns if UImode need to change
    public String handleMouseEvent(int xCo, int yCo,int count, int ID,  dataController data){
        //EVENT DOUBLE CLICKS UNDER TABLE

        String nextUImode = "table";
        if(mouseEventHandler.doubleClickUnderTable(yCo,count,ID,data.getLowestY()) && currMode != "edit"){
           int numberOfTable =  data.getTableList().size() + 1;
            Table newTable = new Table("Table" + numberOfTable);
            data.addTable(newTable);
        }
        //EVENT CLICK CELL
        //TODO: check if margin clicked
        int[] clickedCell = mouseEventHandler.getCellID(xCo, yCo, paintModule.getxCoStart(), paintModule.getyCoStart(), paintModule.getCellHeight(), paintModule.getCellWidth(), data.getTableList().size(), 1);
        if (clickedCell[1]!= -1 && clickedCell[0] != -1 && !invalidInput){
            activeCell = clickedCell;
            currMode = "edit";
            tempText = data.getTableList().get(activeCell[0]).getTableName();
            //EVENT edit mode and clicked outside table
        }else if(currMode == "edit" && !invalidInput){
            currMode = "normal";
            data.getTableList().get(activeCell[0]).setTableName(tempText);
        }
        invalidInput = !textIsValid(tempText, data);
        nextUImode = "table";
        return nextUImode;
    }

    //Handles mousevent and returns if UImode need to change
    public String handleKeyEvent(int id, int keyCode, char keyChar, dataController data){

        String nextUImode = "table";

        //EVENT: ASCSII char pressed
        if(keyCode > 31 && keyCode < 123){
            tempText = tempText + keyChar;
            invalidInput = !textIsValid(tempText, data);
        }
        //EVENT: TAB pressed
        else if(keyCode == 9){
            nextUImode = "row";
        }
        //Todo: move to new keyEventHandler class

        //EVENT BS pressed and in edit mode
        else if(keyCode == 8 && currMode == "edit"){

            //Check if string is not empty
            if(tempText.length() != 0){
            tempText = tempText.substring(0, tempText.length()-1);}
            //empty string, display red border
        }
        //EVENT ENTER pressed
        else if(keyCode == 10 && currMode == "edit" && !invalidInput){
            currMode = "normal";
            data.getTableList().get(activeCell[0]).setTableName(tempText);

        }
        //EVENT: ESC char pressed
        else if(keyCode == 27){
            currMode = "normal";
            tempText = "default_text";
        }
        invalidInput = !textIsValid(tempText, data);
        return nextUImode;
    }
    //Method that takes care of painting the canvas
    //It calls method from paintModule
    public void paint(Graphics g, dataController data){
        //Creates title
        paintModule.paintTitle(g, "Table Mode");

        //print tables in tabular view
        paintModule.paintTableView(g,data.getTableList(), paintModule.getxCoStart(),paintModule.getyCoStart());

        //Check mode
        if (currMode == "edit"){
            paintModule.paintCursor(g, paintModule.getCellCoords(activeCell[0], activeCell[1])[0], paintModule.getCellCoords(activeCell[0], activeCell[1])[1], paintModule.getCellWidth(), paintModule.getCellHeight(), tempText);
        }

        //check if there are warnings
        if(invalidInput){
            paintModule.paintBorder(g,paintModule.getCellCoords(activeCell[0], activeCell[1])[0], paintModule.getCellCoords(activeCell[0], activeCell[1])[1], paintModule.getCellWidth(), paintModule.getCellHeight(), Color.RED );
        }else{
            paintModule.setColor(g, Color.BLACK);
        }
        //paintModule.paintBorder(g,paintModule.getxCoStart(), paintModule.getyCoStart(), 80, 20, "red");
    }
    public boolean textIsValid(String text,dataController data ){
        for(Table table: data.getTableList()){
            if(table.getTableName().equals(text)){
                if(!table.getTableName().equals(data.getTableList().get(activeCell[0]).getTableName())){
                    return false;
                }
            }
        }
        if (text.length() == 0){
            return false;
        }
        return true;
    }



}
