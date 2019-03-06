package UserInterfaceElements;

import paintModule.Square;
import Data.Table;
import paintModule.paintModule;
import EventHandlers.mouseEventHandler;
import java.util.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class UITablesModule {
    private String tableHeight;
    private Square square;
    private paintModule paintModule;
    private mouseEventHandler mouseEventHandler;
    private List<String> tableList;

    public int[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(int[] coordinates) {
        this.coordinates = coordinates;
    }

    private int[] coordinates;

    //Constructor that init/creates paintModule and an empty list with tablenames
    //Each UImodule has own paintmodule to save settings (e.g. size, bg, ...)
    //TODO: table list with actual tables as elements, (now its only strings)
    public UITablesModule(){
        paintModule = new paintModule();
        mouseEventHandler = new mouseEventHandler();
        tableList = new ArrayList<String>();
    }

    public void handleMouseEvent(int xCo, int yCo,int startX, int startY, int height, int width,int numberOfRows,int numberOfColumns, int margin){

        this.setCoordinates(mouseEventHandler.marginLeftClicked(xCo, yCo,100,100, height, width, numberOfRows, numberOfColumns, margin));



    }
    //Method that takes care of painting the canvas
    //It calls method from paintModule
    public void paint(Graphics g, List<Table> list){
        if(this.getCoordinates() != null ){
            paintModule.paintTitle(g, "MarginClicked");
        }
        //Creates title
        paintModule.paintTitle(g, "Table Mode");

        //Dummy list to test functionality paintmodule
        tableList.clear();
        tableList.add("Table 1");
        tableList.add("Table 2");
        tableList.add("Table 3");
        tableList.add("Table 4");
        tableList.add("Table 5");

        //Test of tableview paint
        paintModule.paintText(g,100,80,"Example of printed collum");
        paintModule.paintTableView(g, tableList, 100, 100);

        //Test of row paint
        paintModule.paintText(g,100,230,"Example of printed row");
        paintModule.paintRow(g, tableList, 100, 250);

    }

}
