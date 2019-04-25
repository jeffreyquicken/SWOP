
import Data.*;
import UserInterfaceElements.UIDesignModule;
import UserInterfaceElements.UISuperClass;
import UserInterfaceElements.UITopLevelWindow;
import org.junit.jupiter.api.Test;
import UserInterfaceElements.Controller;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Scenario5 {
    //initialise class variables
    private Controller bestuurder;
    private dataController dc;
    private UITopLevelWindow topWindow;
    private UISuperClass window;

    //get values for class variables
    public Scenario5() {
        bestuurder = new Controller(1);
        dc = bestuurder.getTableDataController();
        bestuurder.setCurrentMode("table");
        bestuurder.relayMouseEvent(500,115,60,2); // now in rowmode
        bestuurder.relayKeyEvent(400,17,'o'); // CTRL
        bestuurder.relayKeyEvent(400,10,'o'); //ENTER
        topWindow = bestuurder.getTopLevelWindow();

        //initialise tables
        Table table1 = new Table("Table 1");
        Table table2 = new Table("Table 2");
        Table table3 = new Table("Table 3");
        dc.addTable(table1);
        dc.addTable(table2);
        dc.addTable(table3);
        //initialise rows + collumns

        bestuurder.relayMouseEvent(502,130,60,2); // now in design mode
        window = topWindow.getActiveSubWindow();
        for (int i = 0;i<3;i++) {

            Column col1 = new Column("Column1",new CellBoolean(true), "Boolean", true);
            Column col2 = new Column("Column2",new CellBoolean(true), "Boolean", true);
            Column col3 = new Column("Column3", new CellText(""), "String", true);
            Column col4 = new Column("Column4",new CellText(""), "String", true);
            dc.getTableList().get(i).addColumn(col1);
            dc.getTableList().get(i).addColumn(col2);
            dc.getTableList().get(i).addColumn(col3);
            dc.getTableList().get(i).addColumn(col4);
        }
    }

    //Precondition
    @Test
    public void IsInRightMode() {
        assertEquals(UIDesignModule.class,window.getClass());
    }













    //Old tests
    @Test
    public void UserAddsNewColumn() {
        MoveWindowToUpperLeftCorner();
        int originalLen = dc.getTableList().get(0).getColumnNames().size();
        bestuurder.relayMouseEvent(501,80,130,2); //doubleclick under table
        int newLen = dc.getTableList().get(0).getColumnNames().size();
        assertEquals((originalLen+1),newLen);
    }

    @Test
    public void UserAddsNewColumnWithAlreadyTakenName() {
        MoveWindowToUpperLeftCorner();
        bestuurder.relayMouseEvent(500,110,100,1); //click on name column4
        bestuurder.relayKeyEvent(400,8,'o'); //Backspace
        bestuurder.relayKeyEvent(400,53,'5');//press '5'
        bestuurder.relayKeyEvent(400,10,'o');//Enter
        //new column should be namen column6 instead of column5 because that is now taken
        bestuurder.relayMouseEvent(501,80,130,2); //doubleclick under table
        String colName = dc.getSelectedTable().getColumnNames().get(dc.getSelectedTable().getColumnNames().size()-1).getName();
        assertEquals("Column6", colName);
    }

    @Test
    public void UserAddsAColumnDeletesItAndAddsAnotherOne() {
        MoveWindowToUpperLeftCorner();
        int originalLen = dc.getTableList().get(0).getColumnNames().size();
        bestuurder.relayMouseEvent(501,80,130,2); //doubleclick under table
        bestuurder.relayMouseEvent(500,35,40,1); //Select left margin
        bestuurder.relayKeyEvent(400,127,'o'); //Delete
        bestuurder.relayMouseEvent(501,80,130,2); //doubleclick under table
        int newLen = dc.getTableList().get(0).getColumnNames().size();
        assertEquals((originalLen+1),newLen);

    }

    //moving the window so that old tests do work
    public void MoveWindowToUpperLeftCorner() {
        List<Integer> info = topWindow.getSubwindowInfo().get(window);
        info.set(0,0);
        info.set(1,0);
    }
}
