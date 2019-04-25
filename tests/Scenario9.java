
import Data.*;
import UserInterfaceElements.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//Use case = Edit Row Value
public class Scenario9 {
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
    public Scenario9() {
        relay = new MyCanvasWindow("testing", 1);
        bestuurder = relay.getController();;
        dc = bestuurder.getTableDataController();
        bestuurder.setCurrentMode("table");
        topWindow = bestuurder.getTopLevelWindow();


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

    //Step 1
    @Test
    public void ClickValueFirstRowFirstColumnShouldEnterEditMode() {
        MoveWindowToUpperLeftCorner();
        ResizeWindow();
        assertEquals("normal",window.getCurrMode());
        String originalValue = dc.getTableList().get(0).getTableRows().get(0).getColumnList().get(2).getString();
        bestuurder.relayMouseEvent(500,275,40,1); //Click cell(1,1)
        assertEquals("edit",window.getCurrMode()); //Should be in edit mode
    }

    //Step 2
    @Test
    public void EditRowValue() {
        MoveWindowToUpperLeftCorner();
        ResizeWindow();
        String originalValue = dc.getTableList().get(0).getTableRows().get(0).getColumnList().get(2).getString();
        assertEquals("normal", window.getCurrMode());
        bestuurder.relayMouseEvent(500,275,40,1);
        bestuurder.relayKeyEvent(400,101,'a');
        bestuurder.relayKeyEvent(400,10,'o'); //ENTER
        String newValue = dc.getTableList().get(0).getTableRows().get(0).getColumnList().get(2).getString();
        assertEquals((originalValue + "a"), newValue);
    }



    //Old tests
    @Test
    public void EditRowValueFirstRowFirstColumn() {
        MoveWindowToUpperLeftCorner();
        ResizeWindow();
        String originalValue = dc.getTableList().get(0).getTableRows().get(0).getColumnList().get(2).getString();
        bestuurder.relayMouseEvent(500,275,40,1);
        bestuurder.relayKeyEvent(400,101,'a');
        bestuurder.relayKeyEvent(400,10,'o'); //ENTER
        String newValue = dc.getTableList().get(0).getTableRows().get(0).getColumnList().get(2).getString();
        assertEquals((originalValue + "a"), newValue);
    }
    /**
    @Test
    public void EditRowBooleanValue() {
        String originalValue = dc.getTableList().get(0).getTableRows().get(0).getColumnList().get(1);
        bestuurder.relayMouseEvent(500,199,58,1);
        String newValue = dc.getTableList().get(0).getTableRows().get(0).getColumnList().get(1);
        assertFalse(originalValue == newValue);
    } */

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
