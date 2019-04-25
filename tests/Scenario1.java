
import Data.*;
import UserInterfaceElements.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Use case: Create table
public class Scenario1 {

    //initialise class variables
    private Controller bestuurder;
    private dataController dc;
    private UITopLevelWindow topWindow;
    private UISuperClass window;
    private MyCanvasWindow relay;


    //get values for class variables
    public Scenario1() {
        relay = new MyCanvasWindow("testing", 1);
        bestuurder = relay.getController();
        dc = bestuurder.getTableDataController();
        bestuurder.setCurrentMode("table");
        topWindow = bestuurder.getTopLevelWindow();
        window = topWindow.getActiveSubWindow();
        //initialise tables

        dc.addTable();
        dc.addTable();
        dc.addTable();
        //initialise rows + collumns
        for (int i = 0;i<3;i++) {

            Column col1 = new Column("Column1",new CellBoolean(true), "Boolean", true);
            Column col2 = new Column("Column2",new CellBoolean(true), "Boolean", true);
            Column col3 = new Column("Column3", new CellText(""), "String", true);
            Column col4 = new Column("Column4",new CellText(""), "String", true);
            dc.getTableList().get(i).addColumn(col1);
            dc.getTableList().get(i).addColumn(col2);
            dc.getTableList().get(i).addColumn(col3);
            dc.getTableList().get(i).addColumn(col4);

            Row row1 = new Row(dc.getTableList().get(i).getColumnNames());
            Row row2 = new Row(dc.getTableList().get(i).getColumnNames());
            Row row3 = new Row(dc.getTableList().get(i).getColumnNames());

            dc.getTableList().get(i).addRow(row1);
            dc.getTableList().get(i).addRow(row2);
            dc.getTableList().get(i).addRow(row3);
        }
    }

    //Precondition
    @Test
    public void IsInRightMode() {
        assertEquals(UITablesModule.class,window.getClass());
    }





    //Old tests
    @Test
    public void doubleClickUnderTableAddsNewTable() {
       MoveWindowToUpperLeftCorner();
       int origineleLengte = dc.getTableList().size();
       relay.handleMouseEvent(501,110,100,2);
       int eL = dc.getTableList().size() -1;
       assertEquals(origineleLengte, eL);
    }

    @Test
    public void doubleClickUnderTableAddsNewTableWithCorrectName() {
        MoveWindowToUpperLeftCorner();
        int origineleLengte = dc.getTableList().size();
        relay.handleMouseEvent(501,110,100,2);
        List<Table> tble = dc.getTableList();
        origineleLengte++;
        assertEquals("Table" +origineleLengte,tble.get(origineleLengte-1).getTableName());
    }

    //moving the window so that old tests do work
    public void MoveWindowToUpperLeftCorner() {
        List<Integer> info = topWindow.getSubwindowInfo().get(window);
        info.set(0,0);
        info.set(1,0);
    }

}
