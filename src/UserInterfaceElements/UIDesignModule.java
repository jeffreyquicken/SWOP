package UserInterfaceElements;

import Data.Row;
import Data.Table;
import Data.dataController;
import EventHandlers.mouseEventHandler;
import paintModule.paintModule;

import java.awt.*;

public class UIDesignModule {
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
        paintModule = new paintModule(4);
        mouseEventHandler = new mouseEventHandler();
    }

    //Handles mousevent and returns if UImode need to change
    public String handleMouseEvent(int xCo, int yCo,int count, int ID,  dataController data){
        return "design";
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
