
import Data.*;
import UserInterfaceElements.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Scenario7 {
    //initialise class variables
    private Controller bestuurder;
    private dataController dc;
    private UITopLevelWindow topWindow;
    private UISuperClass window;
    private MyCanvasWindow relay;

    //get values for class variables
    public Scenario7() {
        relay = new MyCanvasWindow("testing", 1);
bestuurder = relay.getController();;
        dc = bestuurder.getTableDataController();
        bestuurder.setCurrentMode("table");
        bestuurder.relayMouseEvent(500,115,60,2); // now in rowmode
        bestuurder.relayKeyEvent(400,17,'o'); // CTRL
        bestuurder.relayKeyEvent(400,10,'o'); //ENTER
        topWindow = bestuurder.getTopLevelWindow();


        dc.addTable();
        dc.addTable();
        dc.addTable();
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

    public void switchToDesignMode(){
        bestuurder.relayMouseEvent(502,122,64,2);
        bestuurder.relayMouseEvent(502,212,105,1);
        bestuurder.relayKeyEvent(401, 17,'\uFFFF');
        bestuurder.relayKeyEvent(401, 10,'\n');
        bestuurder.relayMouseEvent(502,213,131,1);
    }
    //Precondition
    @Test
    public void IsInRightMode() {
        assertEquals(UIDesignModule.class,window.getClass());
    }


    //Test to see if margin clicked + delete add removes row(=collumn)
    @Test
    public void TestDeleteColumn() {
        switchToDesignMode();

        UIDesignModule UIDesignModule = (UIDesignModule) topWindow.getActiveSubWindow();

        int previousColl = UIDesignModule.getTable().getColumnNames().size();

        bestuurder.relayMouseEvent(501,236,70,1);
        bestuurder.relayKeyEvent(401, 127,'\u007F');
        int newColl = UIDesignModule.getTable().getColumnNames().size();

        assertEquals((previousColl -1), newColl);

        //  assertEquals(window.getTable().getTableRows(), 4);

    }

    //Step 1 & 2
    @Test
    public void LeftMarginClickedAndSelected() {
        assertEquals("normal",window.getCurrMode());
        bestuurder.relayMouseEvent(502,235,70,1); // Left margin clicked
        assertEquals("delete",window.getCurrMode());
    }


    //Step 3
    @Test
    public void LeftMarginClickedAndDeletePressed() {
        bestuurder.relayMouseEvent(502,235,70,1); // Left margin clicked
        bestuurder.relayKeyEvent(500,127,'d'); //delete pressed
        assertEquals("normal",window.getCurrMode());
    }





    //Step 4
    @Test
    public void LeftMarginClickedAndDeletePressedAndColumnDeleted() {
        int originalSize = dc.getTableList().get(0).getColumnNames().size();
        bestuurder.relayMouseEvent(502,235,70,1); // Left margin clicked
        bestuurder.relayKeyEvent(500,127,'d'); //delete pressed
        int newSize = dc.getTableList().get(0).getColumnNames().size();
        assertEquals(originalSize-1,newSize);
    }



    //Old tests
    @Test
    public void UserDeletesColumn() {
        MoveWindowToUpperLeftCorner();
        int originalLen = dc.getTableList().get(0).getColumnNames().size();
        bestuurder.relayMouseEvent(500,35,40,1); //Select left margin
        bestuurder.relayKeyEvent(400,127,'o'); //Delete
        int newLen = dc.getTableList().get(0).getColumnNames().size();
        assertEquals((originalLen-1),newLen);
    }

    @Test
    public void UserClicksLeftMarginAndPressesEscapeBeforeDelete() {
        MoveWindowToUpperLeftCorner();
        int originalLen = dc.getTableList().get(0).getColumnNames().size();
        bestuurder.relayMouseEvent(500,35,40,1); //Select left margin
        assertEquals("delete",window.getCurrMode());
        bestuurder.relayKeyEvent(400,27,'o');//Escape
        bestuurder.relayKeyEvent(400,127,'o'); //Delete
        int newLen = dc.getTableList().get(0).getColumnNames().size();
        assertEquals(originalLen,newLen);
    }

    //moving the window so that old tests do work
    public void MoveWindowToUpperLeftCorner() {
        List<Integer> info = topWindow.getSubwindowInfo().get(window);
        info.set(0,0);
        info.set(1,0);
    }
}
