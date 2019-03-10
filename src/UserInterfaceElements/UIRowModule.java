package UserInterfaceElements;

import Data.Column;
import Data.Row;
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
    private String currMode = "normal";
    private int[] activeCell;
    private String tempText;
    private Boolean invalidInput;
    private int draggedColumn;
    private int draggedX;


    //Constructor that init/creates paintModule and an empty list with tablenames
    //Each UImodule has own paintmodule to save settings (e.g. size, bg, ...)
    public UIRowModule(){
        paintModule = new paintModule(4);
        mouseEventHandler = new mouseEventHandler();
    }

    //Handles mousevent and returns if UImode need to change
    public String handleMouseEvent(int xCo, int yCo,int count, int ID,  dataController data){


        //EVENT DOUBLE CLICKS UNDER TABLE
        if (currMode == "normal" && mouseEventHandler.doubleClickUnderTable(yCo, count, ID, data.getSelectedTable().getLengthTable() + paintModule.getyCoStart()) ) {
            data.getSelectedTable().addRow(new Row("Test"));
        }
        
        //Check if header is clicked
        if(mouseEventHandler.rightBorderClicked(xCo,yCo,paintModule.getxCoStart(), paintModule.getyCoStart(), paintModule.getWidthList().size(), paintModule.getCellHeight(), paintModule.getWidthList()) != -1){
            System.out.println("RIGHT BORDER CLICKED");
            currMode ="drag";
            draggedColumn= mouseEventHandler.rightBorderClicked(xCo,yCo,paintModule.getxCoStart(), paintModule.getyCoStart(), paintModule.getWidthList().size(), paintModule.getCellHeight(), paintModule.getWidthList());
            draggedX = xCo;
            //Checks if user is dragging border
        }else if(currMode == "drag"){
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
