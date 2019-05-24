
import Data.*;
import UserInterfaceElements.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class EditTableQuery {

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
    public EditTableQuery() {
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


    @Test
    public void InvalidQueryNotExitingEdit() {
        MoveWindowToUpperLeftCorner();

        relay.handleMouseEvent(500,138,43,1);
        //we should be in table name editing mode right now
        assertEquals("edit",window.getCurrMode());

        relay.handleKeyEvent(400,101,'a');
        relay.handleMouseEvent(500,138,60,1);

        assertEquals("edit",window.getCurrMode());

    }

    @Test
    public void ValidQueryCanExitEdit() {
        MoveWindowToUpperLeftCorner();

        relay.handleMouseEvent(500,138,43,1);
        //we should be in table name editing mode right now
        assertEquals("edit",window.getCurrMode());

        ((UITablesModule) window).setTempTextString("SELECT table.Column1 AS col FROM Table1 AS table WHERE table.Column1 = ");
        relay.handleKeyEvent(400,101,'6');

        relay.handleMouseEvent(500,84,140,1);

        assertEquals("normal",window.getCurrMode());

    }
    @Test
    public void ValidQueryJoinCanExitEdit() {
        MoveWindowToUpperLeftCorner();

        relay.handleMouseEvent(500,138,43,1);
        //we should be in table name editing mode right now
        assertEquals("edit",window.getCurrMode());

        ((UITablesModule) window).setTempTextString("SELECT table.Column1 AS col FROM Table1 AS table INNER JOIN Table2 AS table2 ON table2.Column1 = tabele.Column1 WHERE table.Column1 = ");
        relay.handleKeyEvent(400,101,'6');

        relay.handleMouseEvent(500,84,140,1);

        assertEquals("normal",window.getCurrMode());

    }

    @Test
    public void ExecuteValidQuery() {
        MoveWindowToUpperLeftCorner();

        relay.handleMouseEvent(500,138,43,1);
        //we should be in table name editing mode right now
        assertEquals("edit",window.getCurrMode());

<<<<<<< HEAD
        ((UITablesModule) window).setTempTextString("SELECT table.Column1 AS col FROM Table1 AS table INNER JOIN Table2 AS table2 ON table2.Column1 = table1.Column1 WHERE table.Column1 = ");
=======
        ((UITablesModule) window).setTempTextString("SELECT table.Column1 AS col FROM Table1 AS table INNER JOIN Table2 AS table2 ON table2.Column1 = tabele.Column1 WHERE table.Column1 = 6 ");
>>>>>>> 282082731e1b187f99c6a78f517c9e18f8156bef
        relay.handleKeyEvent(400,101,'6');
        Table table =  dc.getSelectedTable();
        relay.handleMouseEvent(500,84,140,1);
        dc.getTableList().get(0).setQuery("SELECT table.Column1 AS col FROM Table1 AS table WHERE table.Column1 = 6");
        assertEquals("normal",window.getCurrMode());
        relay.handleMouseEvent(502,56,35,2);
        relay.handleMouseEvent(500,56,35,2);

        assertEquals(UIComputedModule.class,topWindow.getActiveSubWindow().getClass());
    }

    @Test
    public void ExecuteValidQuery2() {
        MoveWindowToUpperLeftCorner();

        relay.handleMouseEvent(500,138,43,1);
        //we should be in table name editing mode right now
        assertEquals("edit",window.getCurrMode());

        ((UITablesModule) window).setTempTextString("SELECT table.Column1 AS col FROM Table1 AS table INNER JOIN Table2 AS table2 ON table2.Column1 = table1.Column1 WHERE table.Column1 = ");
        relay.handleKeyEvent(400,101,'6');
        Table table =  dc.getSelectedTable();
        relay.handleMouseEvent(500,84,140,1);
        dc.getTableList().get(0).setQuery("SELECT table.Column1 AS col FROM Table1 AS table INNER JOIN Table2 AS table2 ON table2.Column1 = table1.Column1 WHERE table.Column1 = 6");
        assertEquals("normal",window.getCurrMode());
        relay.handleMouseEvent(502,56,35,2);
        relay.handleMouseEvent(500,56,35,2);

        assertEquals(UIComputedModule.class,topWindow.getActiveSubWindow().getClass());
    }

    @Test
    public void ExecuteValidQuery3() {
        MoveWindowToUpperLeftCorner();
        AddFifthNumericCol();
        relay.handleMouseEvent(500,138,43,1);
        //we should be in table name editing mode right now
        assertEquals("edit",window.getCurrMode());

        ((UITablesModule) window).setTempTextString("SELECT table.Column5 AS col FROM Table1 AS table INNER JOIN Table2 AS table2 ON table2.Column5 = table1.Column5 WHERE table.Column5 = ");
        relay.handleKeyEvent(400,101,'6');
        Table table =  dc.getSelectedTable();
        relay.handleMouseEvent(500,84,140,1);
        dc.getTableList().get(0).setQuery("SELECT table.Column5 AS col FROM Table1 AS table WHERE table.Column5 = 6");
        assertEquals("normal",window.getCurrMode());
        //relay.handleMouseEvent(502,56,35,2);
        relay.handleMouseEvent(500,56,35,2);

        assertEquals(UIComputedModule.class,topWindow.getActiveSubWindow().getClass());
    }


    @Test
    public void StandardEditQueryByClickingInQueryBox() {
        MoveWindowToUpperLeftCorner();

        relay.handleMouseEvent(500,138,43,1);
        //we should be in table name editing mode right now
        assertEquals("edit",window.getCurrMode());

        relay.handleKeyEvent(400,101,'a');
        List<Table> table = dc.getTableList();
        String QueryName = ((UITablesModule) window).getTemptext();
        assertEquals("a",QueryName);

    }

    //moving the window so that old tests do work
    public void MoveWindowToUpperLeftCorner() {
        List<Integer> info = topWindow.getSubwindowInfo().get(window);
        info.set(0,0);
        info.set(1,0);
    }

    void AddFifthNumericCol() {
        List<Row> rij = dc.getTableList().get(0).getTableRows();
            for (int i = 0; i<rij.size();i++) {
                dc.getTableList().get(0).deleteRow(rij.get(i));
            }

            for(int i = 0; i<3;i++) {
                Column col5 = new Column("Column5",new CellInteger(6), "Numerical", true);

                Column col1 = new Column("Column1",new CellBoolean(true), "Boolean", true);
                Column col2 = new Column("Column2",new CellBoolean(true), "Boolean", true);
                Column col3 = new Column("Column3", new CellText(""), "String", true);
                Column col4 = new Column("Column4",new CellText(""), "String", true);
                dc.getTableList().get(i).addColumn(col1);
                dc.getTableList().get(i).addColumn(col2);
                dc.getTableList().get(i).addColumn(col3);
                dc.getTableList().get(i).addColumn(col4);

                dc.getTableList().get(i).addColumn(col5);

                Row row1 = new Row(dc.getTableList().get(i).getColumnNames());
                Row row2 = new Row(dc.getTableList().get(i).getColumnNames());
                Row row3 = new Row(dc.getTableList().get(i).getColumnNames());
                dc.getTableList().get(i).addRow(row1);
                dc.getTableList().get(i).addRow(row2);
                dc.getTableList().get(i).addRow(row3);
            }
    }

}
