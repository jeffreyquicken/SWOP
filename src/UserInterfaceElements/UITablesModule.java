package UserInterfaceElements;

import paintModule.Square;
import Data.Table;
import paintModule.paintModule;
import java.util.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class UITablesModule {
    private String tableHeight;
    private Square square;
    private paintModule paintModule;
    private List<String> tableList;

    //Constructor that init/creates paintModule and an empty list with tablenames
    //Each UImodule has own paintmodule to save settings (e.g. size, bg, ...)
    //TODO: table list with actual tables as elements, (now its only strings)
    public UITablesModule(){
        paintModule = new paintModule();
        tableList = new ArrayList<String>();
    }

    //Method that takes care of painting the canvas
    //It calls method from paintModule
    public void paint(Graphics g, List<Table> list){
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
