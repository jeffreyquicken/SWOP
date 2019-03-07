package UserInterfaceElements;




import Data.Table;
import Data.dataController;
import paintModule.paintModule;
import EventHandlers.mouseEventHandler;
import java.util.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class UITablesModule {
    private paintModule paintModule;
    private mouseEventHandler mouseEventHandler;
    private String currMode;
    private int[] activeCell;
    private String tempText;



    //Constructor that init/creates paintModule and an empty list with tablenames
    //Each UImodule has own paintmodule to save settings (e.g. size, bg, ...)
    public UITablesModule(){
        paintModule = new paintModule();
        mouseEventHandler = new mouseEventHandler();
    }

    //Handles mousevent and returns if UImode need to change
    public String handleMouseEvent(int xCo, int yCo,int count, int ID,  dataController data){
        //EVENT DOUBLE CLICKS UNDER TABLE
        String nextUImode = "table";
        if(mouseEventHandler.doubleClickUnderTable(yCo,count,ID,data.getLowestY())){
           int numberOfTable =  data.getTableList().size() + 1;
            Table newTable = new Table("Table" + numberOfTable);
            data.addTable(newTable);
        }
        //EVENT CLICK CELL
        //TODO: check if margin clicked
        activeCell = mouseEventHandler.getCellID(xCo, yCo, paintModule.getxCoStart(), paintModule.getyCoStart(), paintModule.getCellHeight(), paintModule.getCellWidth(), data.getTableList().size(), 1);
        if (activeCell[1]!= -1){
            currMode = "edit";
            tempText = data.getTableList().get(activeCell[0]).getTableName();
        }
         nextUImode = "table";
        return nextUImode;
    }

    //Handles mousevent and returns if UImode need to change
    public String handleKeyEvent(int id, int keyCode, char keyChar,   dataController tableController){
        //EVENT: r pressed
        String nextUImode = "table";
        if(keyChar == 'r'){
            nextUImode = "row";
        }
        //Todo: move to new keyEventHandler class
        else if(keyCode == 8 && currMode == "edit"){
            tempText = tempText.substring(0, tempText.length()-1);
        }
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
d .
            paintModule.paintCursor(g, paintModule.getCellCoords(activeCell[0], activeCell[1])[0], paintModule.getCellCoords(activeCell[0], activeCell[1])[1], paintModule.getCellWidth(), paintModule.getCellHeight(), tempText);
        }
        //paintModule.paintBorder(g,paintModule.getxCoStart(), paintModule.getyCoStart(), 80, 20, "red");
    }



}
