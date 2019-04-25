
import UserInterfaceElements.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Data.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Use case: AddRow
public class Scenario8 {
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
    public Scenario8() {
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





    //Step 1 (double click under table)
    @Test
    public void DoubleClickUnderTable() {
        MoveWindowToUpperLeftCorner();
        ResizeWindow();
        assertEquals("normal",window.getCurrMode());
        bestuurder.relayMouseEvent(501,110,100,2);//Double click under table
        assertEquals("normal",window.getCurrMode()); //Adding row should not change mode
    }

    //Step 2 (new row should be added)
    @Test
    public void DoubleClickUnderTableShouldAddRow() {
        int l = dc.getTableList().get(0).getTableRows().size();
        assertEquals(3, l); //Nb of rows should be +1
    }





    //Old tests
    @Test
    public void UserDoubleClicksUnderTableAndAddsNewRow() {
        MoveWindowToUpperLeftCorner();
        ResizeWindow();
        int l1 = dc.getTableList().get(0).getTableRows().size();
        bestuurder.relayMouseEvent(501,110,100,2);
        int l2 = dc.getTableList().get(0).getTableRows().size();
        assertEquals((l1+1),l2);
    }


    @Test
    public void UserAddsNewRowWithCorrectDefaultValues() {
        MoveWindowToUpperLeftCorner();
        ResizeWindow();
        bestuurder.relayMouseEvent(501,120,120,2);
        int size = dc.getTableList().get(0).getTableRows().size();
        List<Cell> newRow = dc.getTableList().get(0).getTableRows().get(size-1).getColumnList();
        List<Column> kols = dc.getTableList().get(0).getColumnNames();

        for(int i=0;i<(newRow.size()-1);i++) {
            assertEquals(kols.get(i).getDefaultV().getString(),newRow.get(i).getString());
        }



    }

    //Test resizingheader
    @Test
    public void ResizeHeader() {
        MoveWindowToUpperLeftCorner();
        ResizeWindow();
        int originalWidth = dc.getTableList().get(0).getRowSetting().getWidthList().get(0);
        for (int i = 130;i <= 150;i++) {
            relay.handleMouseEvent(506,i,25,1);
        }
        int newWidth = dc.getTableList().get(0).getRowSetting().getWidthList().get(0);
        assertEquals(originalWidth+20,newWidth);
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



    @Test
    public void HorizontalScrollbar() {
        MoveWindowToUpperLeftCorner();
        for (int i =0;i<10;i++) {
            relay.handleMouseEvent(501,135,155,1);
        }

    }
/*
    //ScrollbarTests
    @Test
    public void VerticalScrollbar() {
        MoveWindowToUpperLeftCorner();
        for (int i = 0; i < 10; i++) {
            Row row1 = new Row(dc.getTableList().get(0).getColumnNames());
            dc.getTableList().get(i).addRow(row1);
        }
        for (int i = 0; i < 10; i++) {
            relay.handleMouseEvent(501, 155, 140, 1);
        }
    }
    */
}
