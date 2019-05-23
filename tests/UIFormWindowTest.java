
import Data.*;
import UserInterfaceElements.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
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
       // List<Integer> list = new ArrayList<>();
        //list.add(50);
        //list.add(50);
        //dc.getTableList().get(0).getFormSetting().setWidthList(list);
    }




    @Test
    public void ControlFGoesToForm() {
        MoveWindowToUpperLeftCorner();

        relay.handleMouseEvent(500,96,43,2);
        //we should be in table name editing mode right now
        //assertEquals("edit",window.getCurrMode());
        relay.handleMouseEvent(500,260,120,2);

        relay.handleKeyEvent(400,17,'o'); //CTRL
        relay.handleKeyEvent(400,13,'e'); //enter

        relay.handleMouseEvent(500,260,120,2);


        relay.handleMouseEvent(500,56,124,1);
        //assertEquals("normal",window.getCurrMode());

        relay.handleKeyEvent(400,17,'o'); //CTRL
        relay.handleKeyEvent(400,70,'f'); //F

        assertEquals(UIFormModule.class,topWindow.getActiveSubWindow().getClass());
    }
    @Test
    public void editColNotPossibleInFormWindow() {
        OpenFormWindow();
        relay.handleMouseEvent(500,319,72,1);
        assertEquals(topWindow.getActiveSubWindow().getCurrMode(), "normal");
    }
    @Test
    public void clickOUtsideTableInFormWindow() {
        OpenFormWindow();
        relay.handleMouseEvent(500,216,173,1);
       assertEquals(topWindow.getActiveSubWindow().getCurrMode(), "normal");
    }
    @Test
    public void clickOnCellInFormWindow() {
        OpenFormWindow();
        relay.handleMouseEvent(500,336,72,1);
        assertEquals(topWindow.getActiveSubWindow().getCurrMode(), "edit");
    }

    @Test
    public void clickOnCellAndEditInFormWindow() {
        OpenFormWindow();
        relay.handleMouseEvent(500,336,72,1);
        assertEquals(topWindow.getActiveSubWindow().getCurrMode(), "edit");
        relay.handleKeyEvent(400,65,'a'); //PageUp
        String temp = ((UIFormModule) topWindow.getActiveSubWindow()).getTempText().getString();
        assertEquals(temp, "Truea");
    }
    @Test
    public void EditAndExitInFormWindow() {
        OpenFormWindow();
        relay.handleMouseEvent(500,336,69,1);
        assertEquals(topWindow.getActiveSubWindow().getCurrMode(), "edit");
        relay.handleKeyEvent(400,65,'a'); //PageUp
        String temp = ((UIFormModule) topWindow.getActiveSubWindow()).getTempText().getString();
        relay.handleMouseEvent(501,216,173,1);
    }
    @Test
    public void EditEmailInFormWindow() {
        OpenFormWindow();
        relay.handleMouseEvent(500,336,69,1);
        assertEquals(topWindow.getActiveSubWindow().getCurrMode(), "edit");
        ((UIFormModule) topWindow.getActiveSubWindow()).getTable().getColumnNames().get(0).setType("Email");
        relay.handleKeyEvent(400,65,'a'); //PageUp
    }
    @Test
    public void EditStrinfInFormWindow() {
        OpenFormWindow();
        relay.handleMouseEvent(500,336,69,1);
        assertEquals(topWindow.getActiveSubWindow().getCurrMode(), "edit");
        ((UIFormModule) topWindow.getActiveSubWindow()).getTable().getColumnNames().get(0).setType("String");
        relay.handleKeyEvent(400,65,'a'); //PageUp
    }
    @Test
    public void EditIntegerInFormWindow() {
        OpenFormWindow();
        relay.handleMouseEvent(500, 336, 69, 1);
        assertEquals(topWindow.getActiveSubWindow().getCurrMode(), "edit");
        ((UIFormModule) topWindow.getActiveSubWindow()).getTable().getColumnNames().get(0).setType("Integer");
        relay.handleKeyEvent(400, 65, 'a'); //PageUp
    }
    @Test
    public void exitEditInvalidInputInFormWindow() {
        OpenFormWindow();
        relay.handleMouseEvent(500,336,69,1);
        assertEquals(topWindow.getActiveSubWindow().getCurrMode(), "edit");
        relay.handleKeyEvent(400,65,'a'); //PageUp
        String temp = ((UIFormModule) topWindow.getActiveSubWindow()).getTempText().getString();
        ((UIFormModule)topWindow.getActiveSubWindow()).getTable().getColumnNames().get(0).setBlanksAllowed(false);
        relay.handleKeyEvent(400,8,'a'); //delete

        assertEquals(topWindow.getActiveSubWindow().getCurrMode(), "edit");
        relay.handleMouseEvent(500,216,173,1);
    }
    @Test
    public void EditInvalidInputInFormWindow() {
        OpenFormWindow();
        relay.handleMouseEvent(500,336,69,1);
        assertEquals(topWindow.getActiveSubWindow().getCurrMode(), "edit");
        relay.handleKeyEvent(400,65,'a'); //PageUp
        String temp = ((UIFormModule) topWindow.getActiveSubWindow()).getTempText().getString();
        ((UIFormModule)topWindow.getActiveSubWindow()).getTable().getColumnNames().get(0).setBlanksAllowed(false);
        relay.handleKeyEvent(400,8,'a'); //delete

        assertEquals(topWindow.getActiveSubWindow().getCurrMode(), "edit");
    }

    @Test
    public void PageUpInFormWindow() {
       OpenFormWindow();
       relay.handleKeyEvent(400,33,'p'); //PageUp
        int index =((UIFormModule) topWindow.getActiveSubWindow()).getRowIndex();
        assertEquals(1, index);
    }
    @Test
    public void TwoTimesPageUpInFormWindow() {
        OpenFormWindow();
        relay.handleKeyEvent(400,33,'p'); //PageUp
        relay.handleKeyEvent(400,33,'p'); //PageUp
        int index =((UIFormModule) topWindow.getActiveSubWindow()).getRowIndex();
        assertEquals(2, index);
    }
    @Test
    public void MultipleTimesPageUpInFormWindow() {
        OpenFormWindow();
        relay.handleKeyEvent(400,33,'p'); //PageUp
        relay.handleKeyEvent(400,33,'p'); //PageUp
        relay.handleKeyEvent(400,33,'p'); //PageUp
        relay.handleKeyEvent(400,33,'p'); //PageUp
        relay.handleKeyEvent(400,33,'p'); //PageUp
        relay.handleKeyEvent(400,33,'p'); //PageUp
        relay.handleKeyEvent(400,33,'p'); //PageUp
        relay.handleKeyEvent(400,33,'p'); //PageUp
        int index =((UIFormModule) topWindow.getActiveSubWindow()).getRowIndex();
        assertEquals(3, index);
    }
    @Test
    public void PageDownInFormWindow() {
        OpenFormWindow();
        relay.handleKeyEvent(400,34,'p'); //PageDown
        int index =((UIFormModule) topWindow.getActiveSubWindow()).getRowIndex();
        assertEquals(-1, index);
    }
    @Test
    public void TwoTimesPageDownInFormWindow() {
        OpenFormWindow();
        relay.handleKeyEvent(400,34,'p'); //PageDown
        relay.handleKeyEvent(400,34,'p'); //PageDown
        int index =((UIFormModule) topWindow.getActiveSubWindow()).getRowIndex();
        assertEquals(-1, index);
    }
    @Test
    public void MultipleTimesPageDownInFormWindow() {
        OpenFormWindow();
        relay.handleKeyEvent(400,34,'p'); //PageDown
        relay.handleKeyEvent(400,34,'p'); //PageDown
        relay.handleKeyEvent(400,34,'p'); //PageDown
        relay.handleKeyEvent(400,34,'p'); //PageDown
        relay.handleKeyEvent(400,34,'p'); //PageDown
        relay.handleKeyEvent(400,34,'p'); //PageDown
        relay.handleKeyEvent(400,34,'p'); //PageDown
        relay.handleKeyEvent(400,34,'p'); //PageDown
        int index =((UIFormModule) topWindow.getActiveSubWindow()).getRowIndex();
        assertEquals(-1, index);
    }


    public void OpenFormWindow(){
        MoveWindowToUpperLeftCorner();

        relay.handleMouseEvent(500,96,43,2);
        //we should be in table name editing mode right now
        //assertEquals("edit",window.getCurrMode());
        relay.handleMouseEvent(500,260,120,2);
        relay.handleMouseEvent(500,261,120,2);

        relay.handleKeyEvent(400,17,'o'); //CTRL
        relay.handleKeyEvent(400,13,'e'); //enter

        relay.handleMouseEvent(500,260,120,2);
        relay.handleMouseEvent(500,261,120,2);


        relay.handleMouseEvent(500,56,124,1);
        //assertEquals("normal",window.getCurrMode());

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
