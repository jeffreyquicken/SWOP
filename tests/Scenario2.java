
import Data.*;
import UserInterfaceElements.UISuperClass;
import UserInterfaceElements.UITablesModule;
import UserInterfaceElements.UITopLevelWindow;
import org.junit.jupiter.api.Test;
import UserInterfaceElements.Controller;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Use case: Edit Table Name
public class Scenario2 {
    //initialise class variables
    private Controller bestuurder;
    private dataController dc;
    private UITopLevelWindow topWindow;
    private UISuperClass window;

    //get values for class variables
    public Scenario2() {
        bestuurder = new Controller(1);
        dc = bestuurder.getTableDataController();
        bestuurder.setCurrentMode("table");
        topWindow = bestuurder.getTopLevelWindow();
        window = topWindow.getActiveSubWindow();
        //initialise tables
        Table table1 = new Table("Table 1");
        Table table2 = new Table("Table 2");
        Table table3 = new Table("Table 3");
        dc.addTable(table1);
        dc.addTable(table2);
        dc.addTable(table3);
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
    public void StandardEditTableNameByClickingNextToTable() {
        MoveWindowToUpperLeftCorner();
        bestuurder.relayMouseEvent(500,75,40,1);
        //we should be in table name editing mode right now
        assertEquals("edit",window.getCurrMode());
        bestuurder.relayKeyEvent(400,101,'a');
        bestuurder.relayMouseEvent(500,110,100,1);
        assertEquals("normal",window.getCurrMode());
        List<Table> table = dc.getTableList();
        String tbleName = table.get(0).getTableName();
        assertEquals("Table 1a",tbleName);

    }

    @Test
    public void StandardEditTableNameByPressingEnter() {
        MoveWindowToUpperLeftCorner();
        bestuurder.relayMouseEvent(500,75,40,1);
        //we should be in table name editing mode right now
        assertEquals("edit",window.getCurrMode());
        bestuurder.relayKeyEvent(400,101,'a');
        bestuurder.relayKeyEvent(400,10,'o');
        assertEquals("normal",window.getCurrMode());
        List<Table> table = dc.getTableList();
        String tbleName = table.get(0).getTableName();
        assertEquals("Table 1a",tbleName);
    }

    @Test
    public void StandardEditTableNameUsingBackspaceByClickingNextToTable() {
        MoveWindowToUpperLeftCorner();
        bestuurder.relayMouseEvent(500,75,40,1);
        //we should be in table name editing mode right now
        for(int i=0; i<7;i++) {
            bestuurder.relayKeyEvent(400,8,'o');
        }


        bestuurder.relayKeyEvent(400,101,'a');
        bestuurder.relayKeyEvent(400,101,'b');
        bestuurder.relayKeyEvent(400,101,'c');
        bestuurder.relayMouseEvent(500,110,100,1);
        List<Table> table = dc.getTableList();
        String tbleName = table.get(0).getTableName();
        assertEquals("abc",tbleName);

    }

    //moving the window so that old tests do work
    public void MoveWindowToUpperLeftCorner() {
        List<Integer> info = topWindow.getSubwindowInfo().get(window);
        info.set(0,0);
        info.set(1,0);
    }

}
