
import Data.*;
import UserInterfaceElements.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Use case: Delete Row
public class Scenario10 {
    //initialise class variables
    private Controller bestuurder;
    private dataController dc;
    private UITopLevelWindow topWindow;
    private UISuperClass window;
    private MyCanvasWindow relay;

    //get values for class variables
    public Scenario10() {
        relay = new MyCanvasWindow("testing", 1);
        bestuurder = relay.getController();;
        dc = bestuurder.getTableDataController();
        bestuurder.setCurrentMode("table");
        topWindow = bestuurder.getTopLevelWindow();
        window = topWindow.getActiveSubWindow();

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
        bestuurder.relayMouseEvent(502,115,60,2);
        bestuurder.relayMouseEvent(501,220,100,1); //Now in row mode
        window = topWindow.getActiveSubWindow();
    }

    //Precondition
    @Test
    public void IsInRightMode() {
        assertEquals(UIRowModule.class,window.getClass());
    }


    //Step 1&2
    @Test
    public void UserClicksLeftMargin() {
        MoveWindowToUpperLeftCorner();
        ResizeWindow();
        assertEquals("normal", window.getCurrMode());
        bestuurder.relayMouseEvent(500,35,40,1); //Click left margin
        assertEquals("delete", window.getCurrMode());
    }

    //Step 3
    @Test
    public void UserClicksLeftMarginAndDeleteKey() {
        MoveWindowToUpperLeftCorner();
        ResizeWindow();
        bestuurder.relayMouseEvent(500,52,56,1); //Click left margin
        bestuurder.relayKeyEvent(500,127,'d'); //delete pressed
        assertEquals("normal",window.getCurrMode());
    }

    //Step 4
    @Test
    public void UserClicksLeftMarginAndDeleteKeyAndRowDeleted(){
        MoveWindowToUpperLeftCorner();
        ResizeWindow();
        int originalSize = dc.getTableList().get(0).getTableRows().size();
        bestuurder.relayMouseEvent(500,35,40,1); //Click left margin
        bestuurder.relayKeyEvent(500,127,'d'); //delete pressed
        int newSize = dc.getTableList().get(0).getTableRows().size();
        assertEquals((originalSize-1), newSize);
    }

    //Old tests
    @Test
    public void UserDeletesFirstRow() {
        MoveWindowToUpperLeftCorner();
        ResizeWindow();
        int originalSize = dc.getTableList().get(0).getTableRows().size();
        bestuurder.relayMouseEvent(500,35,40,1); //Click left margin
        bestuurder.relayKeyEvent(400,127,'o'); //DELETE
        int newSize = dc.getTableList().get(0).getTableRows().size();
        assertEquals((originalSize-1), newSize);
    }

    @Test
    public void UserGoesInDeleteModeAndPressesDeleteAfterExitingThisMode() {
        MoveWindowToUpperLeftCorner();
        ResizeWindow();
        int originalSize = dc.getTableList().get(0).getTableRows().size();
        bestuurder.relayMouseEvent(500,52,56,1); //Click left margin
        bestuurder.relayKeyEvent(400,27,'o'); //ESCAPE
        bestuurder.relayKeyEvent(400,127,'o'); //DELETE
        int newSize = dc.getTableList().get(0).getTableRows().size();
        assertEquals(originalSize,newSize);
    }

    //moving the window so that old tests do work
    public void MoveWindowToUpperLeftCorner() {
        List<Integer> info = topWindow.getSubwindowInfo().get(window);
        info.set(0,0);
        info.set(1,0);
    }

    //Resizing the window so that everything is visible
    public void ResizeWindow() {
        List<Integer> info = topWindow.getSubwindowInfo().get(window);
        info.set(2,500);
        info.set(3,150);
    }
}
