package UserInterfaceElements;



import paintModule.Square;
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
    private int xCoStart = 50 ;
    private int yCoStart = 50;


    //Constructor that init/creates paintModule and an empty list with tablenames
    //Each UImodule has own paintmodule to save settings (e.g. size, bg, ...)
    public UITablesModule(){
        paintModule = new paintModule();
        mouseEventHandler = new mouseEventHandler();
    }

    //Handles mousevent and returns if UImode need to change
    public String handleMouseEvent(int xCo, int yCo,int count, int ID,  dataController tableController){
        //EVENT DOUBLE CLICKS UNDER TABLE
        if(mouseEventHandler.doubleClickUnderTable(yCo,count,ID,tableController.getLowestY())){
           int numberOfTable =  tableController.getTableList().size() + 1;
            Table newTable = new Table("Table" + numberOfTable);
            tableController.addTable(newTable);
        }
        String nextUImode = "table";
        return nextUImode;
    }

    //Handles mousevent and returns if UImode need to change
    public String handleKeyEvent(int id, int keyCode, char keyChar,   dataController tableController){
        //EVENT: r pressed
        String nextUImode = "table";
        if(keyChar == 'r'){ nextUImode = "row";}else{
            nextUImode = "table";
        }
        return nextUImode;
    }
    //Method that takes care of painting the canvas
    //It calls method from paintModule
    public void paint(Graphics g, List<Table> tables){
        //Creates title
        paintModule.paintTitle(g, "Table Mode");

        //print tables in tabular view
        paintModule.paintTableView(g,tables, xCoStart,yCoStart);
    }



}
