package UserInterfaceElements;

import Data.Table;
import Data.dataController;
import EventHandlers.mouseEventHandler;
import paintModule.paintModule;

import java.awt.*;
import java.util.List;

public class UIRowModule {
    private paintModule paintModule;
    private EventHandlers.mouseEventHandler mouseEventHandler;
    private int xCoStart = 50 ;
    private int yCoStart = 50;


    //Constructor that init/creates paintModule and an empty list with tablenames
    //Each UImodule has own paintmodule to save settings (e.g. size, bg, ...)
    public UIRowModule(){
        paintModule = new paintModule();
        mouseEventHandler = new mouseEventHandler();
    }

    //Handles mousevent and returns if UImode need to change
    public String handleMouseEvent(int xCo, int yCo,int count, int ID,  dataController tableController){

        String nextUImode = "row";
        return nextUImode;
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
        paintModule.paintTitle(g, "Row Mode");

        //print tables in tabular view
        paintModule.paintTable(g,table, xCoStart,yCoStart);
    }
}
