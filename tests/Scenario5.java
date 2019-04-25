
import Data.*;
import UserInterfaceElements.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Scenario5 {
    //initialise class variables
    private Controller bestuurder;
    private dataController dc;
    private UITopLevelWindow topWindow;
    private UISuperClass window;
    private MyCanvasWindow relay;
    private Graphics imageGraphics;

    @BeforeEach
    public void paint() {
        BufferedImage image = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_RGB);
        imageGraphics = image.getGraphics();
    }

    @AfterEach
    public void paint2() {
        bestuurder.paint(imageGraphics);
    }

    //get values for class variables
    public Scenario5() {
        relay = new MyCanvasWindow("testing", 1);
        bestuurder = relay.getController();
        dc = bestuurder.getTableDataController();
        bestuurder.setCurrentMode("table");
        relay.handleMouseEvent(500,115,60,2); // now in rowmode
        relay.handleKeyEvent(400,17,'o'); // CTRL
        relay.handleKeyEvent(400,10,'o'); //ENTER
        topWindow = bestuurder.getTopLevelWindow();


        dc.addTable();
        dc.addTable();
        dc.addTable();
        //initialise rows + collumns

        relay.handleMouseEvent(502,130,60,2); // now in design mode
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

    public void switchToDesignMode(){
        relay.handleMouseEvent(502,122,64,2);
        relay.handleMouseEvent(502,212,105,1);
        relay.handleKeyEvent(401, 17,'\uFFFF');
        relay.handleKeyEvent(401, 10,'\n');
        relay.handleMouseEvent(502,213,131,1);
    }
    @Test
    public void TestAddCollumn() {

        switchToDesignMode();

        UIDesignModule UIDesignModule = (UIDesignModule) topWindow.getActiveSubWindow();
        int previousColl = UIDesignModule.getTable().getColumnNames().size();

        relay.handleMouseEvent(501,217,148,2);

        int newColl = UIDesignModule.getTable().getColumnNames().size();

        assertEquals((previousColl +1), newColl);

        //  assertEquals(window.getTable().getTableRows(), 4);

    }












    //Old tests
    @Test
    public void UserAddsNewColumn() {
        MoveWindowToUpperLeftCorner();
        int originalLen = dc.getTableList().get(0).getColumnNames().size();
        relay.handleMouseEvent(501,80,130,2); //doubleclick under table
        int newLen = dc.getTableList().get(0).getColumnNames().size();
        assertEquals((originalLen+1),newLen);
    }

    @Test
    public void UserAddsNewColumnWithAlreadyTakenName() {
        MoveWindowToUpperLeftCorner();
        relay.handleMouseEvent(500,110,100,1); //click on name column4
        relay.handleKeyEvent(400,8,'o'); //Backspace
        relay.handleKeyEvent(400,53,'5');//press '5'
        relay.handleKeyEvent(400,10,'o');//Enter
        //new column should be namen column6 instead of column5 because that is now taken
        relay.handleMouseEvent(501,80,130,2); //doubleclick under table
        String colName = dc.getSelectedTable().getColumnNames().get(dc.getSelectedTable().getColumnNames().size()-1).getName();
        assertEquals("Column6", colName);
    }

    @Test
    public void UserAddsAColumnDeletesItAndAddsAnotherOne() {
        MoveWindowToUpperLeftCorner();
        int originalLen = dc.getTableList().get(0).getColumnNames().size();
        relay.handleMouseEvent(501,80,130,2); //doubleclick under table
        relay.handleMouseEvent(500,35,40,1); //Select left margin
        relay.handleKeyEvent(400,127,'o'); //Delete
        relay.handleMouseEvent(501,80,130,2); //doubleclick under table
        int newLen = dc.getTableList().get(0).getColumnNames().size();
        assertEquals((originalLen+1),newLen);

    }

    //moving the window so that old tests do work
    public void MoveWindowToUpperLeftCorner() {
        List<Integer> info = topWindow.getSubwindowInfo().get(window);
        info.set(0,0);
        info.set(1,0);
    }



    @Test
    public void HorizontalScrollbar() {
        MoveWindowToUpperLeftCorner();
        for (int i =0;i<10;i++) {
            relay.handleMouseEvent(501,135,155,1);
        }

    }

    //ScrollbarTests
    @Test
    public void VerticalScrollbar() {
        MoveWindowToUpperLeftCorner();
        for (int i = 0; i < 10; i++) {
            Column col1 = new Column("Col"+i,new CellBoolean(true), "Boolean", true);
            dc.getTableList().get(0).addColumn(col1);
        }
        for (int i = 0; i < 10; i++) {
            relay.handleMouseEvent(501, 155, 140, 1);
        }
    }
}
