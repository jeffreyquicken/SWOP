
import Data.*;
import UserInterfaceElements.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UIFormWindowTest {
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
    public UIFormWindowTest() {
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



    //Old tests
    @Test
    public void ControlFGoesToForm() {
        MoveWindowToUpperLeftCorner();

        relay.handleMouseEvent(500,75,40,1);
        //we should be in table name editing mode right now
        assertEquals("edit",window.getCurrMode());

        relay.handleMouseEvent(500,84,140,1);

        assertEquals("normal",window.getCurrMode());

        relay.handleKeyEvent(400,17,'o'); //CTRL
        relay.handleKeyEvent(400,70,'f'); //F

        assertEquals(UIFormModule.class,topWindow.getActiveSubWindow().getClass());
    }


    //moving the window so that old tests do work
    public void MoveWindowToUpperLeftCorner() {
        List<Integer> info = topWindow.getSubwindowInfo().get(window);
        info.set(0,0);
        info.set(1,0);
    }

}
