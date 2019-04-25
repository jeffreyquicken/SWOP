


import Data.*;
import UserInterfaceElements.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TABLR {
    //initialise class variables
    private Controller bestuurder;
    private dataController dc;
    private UITopLevelWindow topWindow;
    private UISuperClass window;
    private MyCanvasWindow relay;

    public TABLR() {
        relay = new MyCanvasWindow("Testing", 1);
        bestuurder =  relay.getController();
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

    //bottom right corner of table subwindow is 180,180
    @Test
    public void VerticalResize() {
        int originalHeight = topWindow.getSubwindowInfo().get(window).get(3);
        for (int i = 180; i <= 280; i++) {
            relay.handleMouseEvent(506,100,i,1);
        }
        int newHeight = topWindow.getSubwindowInfo().get(window).get(3);
        assertEquals(originalHeight+100,newHeight);

    }

    @Test
    public void HorizontalResize() {
        int originalWidth = topWindow.getSubwindowInfo().get(window).get(2);
        for (int i = 180; i <= 280; i++) {
            relay.handleMouseEvent(506,i,100,1);
        }
        int newWidth = topWindow.getSubwindowInfo().get(window).get(2);
        assertEquals(originalWidth+100,newWidth);

    }

    @Test
    public void HorizontalAndVerticalResizeOutward() {
        int originalWidth = topWindow.getSubwindowInfo().get(window).get(2);
        int originalHeight = topWindow.getSubwindowInfo().get(window).get(3);
        for (int i = 180; i <= 280; i++) {
            relay.handleMouseEvent(506,i,i,1);
        }
        int newWidth = topWindow.getSubwindowInfo().get(window).get(2);
        assertEquals(originalWidth+100,newWidth);
        int newHeight = topWindow.getSubwindowInfo().get(window).get(3);
        assertEquals(originalHeight+100,newHeight);
    }

    @Test
    public void HorizontalAndVerticalResizeInward() {
        for (int i = 180; i >= 80; i--) {
            relay.handleMouseEvent(506,i,i,1);
            Integer[] dim = topWindow.getDimensions(topWindow.getActiveSubWindow());
            System.out.println(dim[0]+", "+dim[1]);
        }
        int newWidth = topWindow.getSubwindowInfo().get(window).get(2);
        assertEquals(150,newWidth);
        int newHeight = topWindow.getSubwindowInfo().get(window).get(3);
        assertEquals(150,newHeight);
    }

    //Point on header is 100,30
    @Test
    public void DragSubWindow() {
        int originalX = topWindow.getSubwindowInfo().get(window).get(0);
        int originalY = topWindow.getSubwindowInfo().get(window).get(1);
        for (int i = 20; i <= 121; i++) {
            relay.handleMouseEvent(506,i,i,1);
        }
        int newX = topWindow.getSubwindowInfo().get(window).get(0);
        int newY = topWindow.getSubwindowInfo().get(window).get(1);
        assertEquals(originalX+100,newX);
        assertEquals(originalY+100,newY);
    }

    //Point of closing button is 35,30
    @Test
    public void CloseSubWindow() {
        assertEquals(1,topWindow.getSubWindows().size());
        CloseFirstTable();
        assertEquals(0,topWindow.getSubWindows().size());
    }

    @Test
    public void CheckIfSubWindowActive() {
        assertEquals(topWindow.getSubWindows().get(0), topWindow.getActiveSubWindow());
    }

    @Test
    public void CheckIfActiveSubWindowIsNullIfThereAreNoSubWindows() {
        CloseFirstTable();
        assertEquals(null, topWindow.getActiveSubWindow());
        assertEquals(0,topWindow.getActiveSubWindowList().size());
    }

    @Test
    public void CheckIfNewTablesWindowBecomesActive() {
        UISuperClass originalWindow = topWindow.getActiveSubWindow();
        AddNewTablesWindow();
        UISuperClass newWindow = topWindow.getActiveSubWindow();
        assertNotEquals(originalWindow,newWindow);
    }

    @Test
    public void  CheckIfWindowBecomesActiveAfterClickingOnIt() {
        UISuperClass originalWindow = topWindow.getActiveSubWindow();
        AddNewTablesWindow();
        //after adding a new window we will click on the inactive window (the original).
        relay.handleMouseEvent(501,100,100,1);
        UISuperClass newWindow = topWindow.getActiveSubWindow();
        assertEquals(originalWindow,newWindow);
    }

    //closing button of new window is on 215,40
    @Test
    public void AfterClosingActiveWindowThePreviousActiveWindowBecomesActiveAgain() {
        UISuperClass originalWindow = topWindow.getActiveSubWindow();
        AddNewTablesWindow();
        //after adding a new window we will click on the inactive window (the original).
        relay.handleMouseEvent(501,215,40,1);
        UISuperClass newWindow = topWindow.getActiveSubWindow();
        assertEquals(originalWindow,newWindow);
    }

    @Test
    public void AddNewTablesWindowWithCtrlT() {
        int originalSize = topWindow.getSubWindows().size();
        AddNewTablesWindow();
        int newSize = topWindow.getSubWindows().size();
        assertEquals(originalSize+1,newSize);
    }

    //Table1 Cell position is 130,60
    @Test
    public void AddNewDesignWindowByDoubleClickingOnTableName() {
        EmptyTable1();
        relay.handleMouseEvent(502,130,60,2);
        CloseFirstTable();
        UISuperClass window = topWindow.getSubWindows().get(0);
        assertEquals(UIDesignModule.class,window.getClass());
    }

    @Test
    public void AddNewRowWindowByDoubleClickingOnTableName() {
        relay.handleMouseEvent(502,130,60,2);
        CloseFirstTable();
        UISuperClass window = topWindow.getSubWindows().get(0);
        assertEquals(UIRowModule.class,window.getClass());
    }

    @Test
    public void AddNewRowsWindowByPressingCtrlEnterWhileDesignWindowIsActive() {
        EmptyTable1();
        relay.handleMouseEvent(502,130,60,2);
        CloseFirstTable();
        relay.handleKeyEvent(500,17,'c');//ctrl
        relay.handleKeyEvent(500,10,'c');//enter
        UISuperClass window = topWindow.getActiveSubWindow();
        assertEquals(UIRowModule.class,window.getClass());

    }

    @Test
    public void AddNewDesignWindowByPressingCtrlEnterWhileRowsWindowIsActive() {
        relay.handleMouseEvent(502,130,60,2); // opens rows window
        CloseFirstTable();//closes first window
        relay.handleKeyEvent(500,17,'c');//ctrl
        relay.handleKeyEvent(500,10,'c');//enter
        UISuperClass window = topWindow.getActiveSubWindow();
        assertEquals(UIDesignModule.class,window.getClass());
    }

    public void AddNewTablesWindow(){
        relay.handleKeyEvent(500,17,'c');
        relay.handleKeyEvent(500,84,'t');
    }

    public void CloseFirstTable() {
        relay.handleMouseEvent(501,35,30,1);
    }

    public void EmptyTable1() {
        for (int i= 0;i < 3; i++) {
            Row rij = dc.getTableList().get(0).getTableRows().get(0);
            dc.getTableList().get(0).deleteRow(rij);
        }
        for (int i= 0;i < 4; i++) {
            Column col = dc.getTableList().get(0).getColumnNames().get(0);
            dc.getTableList().get(0).deleteColumn(col);
        }
    }

    //Tests to make the coverage more complete
    @Test
    public void CheckIfCorrectBordersAreClicked() {
        //corners
        topWindow.whichBorderClicked(0,0,160,160);
        assertEquals(1,topWindow.getBorderClicked());
        topWindow.whichBorderClicked(160,0,160,160);
        assertEquals(2,topWindow.getBorderClicked());
        topWindow.whichBorderClicked(0,160,160,160);
        assertEquals(4,topWindow.getBorderClicked());
        //sides
        topWindow.whichBorderClicked(80,0,160,160);
        assertEquals(5,topWindow.getBorderClicked());
        topWindow.whichBorderClicked(0,80,160,160);
        assertEquals(8,topWindow.getBorderClicked());
    }

    @Test
    public void ClickEventWithNoSubwindows() {
        CloseFirstTable();
        relay.handleMouseEvent(501,100,100,1);
        assertEquals(null,topWindow.getActiveSubWindow());
    }
    @Test
    public void CheckStartCoords() {
        Integer[] result = topWindow.getStartCoords(topWindow.getActiveSubWindow());
        assertEquals(result[0].intValue(),20);
        assertEquals(result[1].intValue(),20);
    }

}
