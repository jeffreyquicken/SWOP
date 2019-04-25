
import Data.*;
import UserInterfaceElements.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
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

    //Test resizingheader
    @Test
    public void ResizeHeader() {
        MoveWindowToUpperLeftCorner();
        int originalWidth = dc.getSetting().getWidthList().get(0);
        for (int i = 130;i <= 140;i++) {
            relay.handleMouseEvent(506,i,25,1);
        }
        int newWidth = dc.getSetting().getWidthList().get(0);
        assertEquals(originalWidth+10,newWidth);
    }

    //moving the window so that old tests do work
    public void MoveWindowToUpperLeftCorner() {
        List<Integer> info = topWindow.getSubwindowInfo().get(window);
        info.set(0,0);
        info.set(1,0);
    }

    //ScrollbarTests
    @Test
    public void VerticalScrollbar() {
        MoveWindowToUpperLeftCorner();
        //we will first add a lot of tables so that there will be vertical scrollbar
        for (int i = 0; i < 10; i++) {
            dc.addTable();
        }
        for (int i = 0; i < 10; i++) {
            relay.handleMouseEvent(501, 155, 140, 1);
        }
    }



}
