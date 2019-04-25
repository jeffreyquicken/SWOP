
import Data.*;
import UserInterfaceElements.UIDesignModule;
import UserInterfaceElements.UISuperClass;
import UserInterfaceElements.UITopLevelWindow;
import org.junit.jupiter.api.Test;
import UserInterfaceElements.Controller;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//Use case: Edit Column Characteristic
public class Scenario6 {
    //initialise class variables
    private Controller bestuurder;
    private dataController dc;
    private UITopLevelWindow topWindow;
    private UISuperClass window;

    //get values for class variables
    public Scenario6() {
        bestuurder = new Controller(1);
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
        assertEquals("table",bestuurder.getCurrentMode());
    }

    //Test to see if double Clicks add new row(=collumn)


    //STEP 1 and 2
    @Test
    public void TestEditCollumnName(){
        switchToDesignMode();
        UIDesignModule UIDesignModule = (UIDesignModule) topWindow.getActiveSubWindow();
        String previousName = UIDesignModule.getTable().getColumnNames().get(0).getName();
        bestuurder.relayMouseEvent(500,300,75,1);
        bestuurder.relayKeyEvent(401, 65,'a');
        bestuurder.relayKeyEvent(401, 10,'e');
        String newName = UIDesignModule.getTable().getColumnNames().get(0).getName();;
        assertEquals(previousName + "a", newName );
    }
    //STEP 1 and 2

    @Test
    public void TestEditCollumnNameExistingName(){
        switchToDesignMode();
        UIDesignModule UIDesignModule = (UIDesignModule) topWindow.getActiveSubWindow();
        String previousName = UIDesignModule.getTable().getColumnNames().get(0).getName();

        bestuurder.relayMouseEvent(500,300,75,1);
        bestuurder.relayKeyEvent(401, 8,'d');
        bestuurder.relayKeyEvent(401, 98,'2');
        bestuurder.relayKeyEvent(401, 10,'e');

        String newName = UIDesignModule.getTable().getColumnNames().get(0).getName();;
        assertNotEquals(previousName + "2", newName );
    }
    //STEP 1 and 2
    @Test
    public void TestEditCollumnNameEmpty(){
        switchToDesignMode();
        UIDesignModule UIDesignModule = (UIDesignModule) topWindow.getActiveSubWindow();

        bestuurder.relayMouseEvent(500,300,75,1);
        bestuurder.relayKeyEvent(401, 8,'d');
        bestuurder.relayKeyEvent(401, 8,'d');
        bestuurder.relayKeyEvent(401, 8,'d');
        bestuurder.relayKeyEvent(401, 8,'d');
        bestuurder.relayKeyEvent(401, 8,'d');
        bestuurder.relayKeyEvent(401, 8,'d');
        bestuurder.relayKeyEvent(401, 8,'d');
        bestuurder.relayKeyEvent(401, 10,'e');

        String newName = UIDesignModule.getTable().getColumnNames().get(0).getName();;
        assertNotEquals("", newName );
    }
    //STEP 1a
    @Test
    public void TestEditCollumnTypeStringToEmail(){
        switchToDesignMode();
        UIDesignModule UIDesignModule = (UIDesignModule) topWindow.getActiveSubWindow();

        for (int i = 355; i <= 563; i++) {
            bestuurder.relayMouseEvent(506,i,185,1);
        }

        bestuurder.relayMouseEvent(500,437,111,1);
        String newType = UIDesignModule.getTable().getColumnNames().get(2).getType();


        assertEquals("Email", newType );
    }

    //STEP 1a
    @Test
    public void TestEditCollumnTypeEmailToBoolean(){
        switchToDesignMode();
        UIDesignModule UIDesignModule = (UIDesignModule) topWindow.getActiveSubWindow();

        for (int i = 355; i <= 563; i++) {
            bestuurder.relayMouseEvent(506,i,185,1);
        }

        bestuurder.relayMouseEvent(500,437,111,1);
        bestuurder.relayMouseEvent(500,437,111,1);
        String newType = UIDesignModule.getTable().getColumnNames().get(2).getType();


        assertEquals("Boolean", newType );
    }
    //STEP 1a
    @Test
    public void TestEditCollumnTypeBooleanToInteger(){
        switchToDesignMode();
        UIDesignModule UIDesignModule = (UIDesignModule) topWindow.getActiveSubWindow();

        for (int i = 355; i <= 563; i++) {
            bestuurder.relayMouseEvent(506,i,185,1);
        }

        bestuurder.relayMouseEvent(500,437,111,1);
        bestuurder.relayMouseEvent(500,437,111,1);
        bestuurder.relayMouseEvent(500,437,111,1);
        String newType = UIDesignModule.getTable().getColumnNames().get(2).getType();


        assertEquals("Integer", newType );
    }
    //STEP 1a
    @Test
    public void TestEditCollumnTypeIntegerToString(){
        switchToDesignMode();
        UIDesignModule UIDesignModule = (UIDesignModule) topWindow.getActiveSubWindow();

        for (int i = 355; i <= 563; i++) {
            bestuurder.relayMouseEvent(506,i,185,1);
        }

        bestuurder.relayMouseEvent(500,437,111,1);
        bestuurder.relayMouseEvent(500,437,111,1);
        bestuurder.relayMouseEvent(500,437,111,1);
        bestuurder.relayMouseEvent(500,437,111,1);
        String newType = UIDesignModule.getTable().getColumnNames().get(2).getType();


        assertEquals("String", newType );
    }
    //STEP 1b
    @Test
    public void TestEditBlanksAllowedTrueToFalse(){
        switchToDesignMode();
        UIDesignModule UIDesignModule = (UIDesignModule) topWindow.getActiveSubWindow();

        for (int i = 355; i <= 596; i++) {
            bestuurder.relayMouseEvent(506,i,185,1);
        }
        System.out.println(UIDesignModule.getTable().getColumnNames().get(0).getBlanksAllowed());
        bestuurder.relayMouseEvent(502,547,57,1);
        bestuurder.relayMouseEvent(500,547,57,1);
        System.out.println(UIDesignModule.getTable().getColumnNames().get(0).getBlanksAllowed());
       // assertFalse(UIDesignModule.getTable().getColumnNames().get(0).getBlanksAllowed());
    }
    //STEP 1b
    @Test
    public void TestEditBlanksAllowedFalseToTrue(){
        switchToDesignMode();
        UIDesignModule UIDesignModule = (UIDesignModule) topWindow.getActiveSubWindow();

        for (int i = 355; i <= 596; i++) {
            bestuurder.relayMouseEvent(506,i,185,1);
        }
        System.out.println(UIDesignModule.getTable().getColumnNames().get(0).getBlanksAllowed());
        bestuurder.relayMouseEvent(502,547,57,1);
        bestuurder.relayMouseEvent(500,547,57,1);
        bestuurder.relayMouseEvent(500,547,57,1);
        System.out.println(UIDesignModule.getTable().getColumnNames().get(0).getBlanksAllowed());
        // assertFalse(UIDesignModule.getTable().getColumnNames().get(0).getBlanksAllowed());
    }

    //Step 1c
    @Test
    public void TestEditDefaultValue(){
        switchToDesignMode();
        UIDesignModule UIDesignModule = (UIDesignModule) topWindow.getActiveSubWindow();


        bestuurder.relayMouseEvent(502,547,57,1);
        bestuurder.relayMouseEvent(500,547,57,1);
        bestuurder.relayMouseEvent(500,547,57,1);

    }



    //Old tests
    @Test
    public void EditColumnNameNormal() {
        MoveWindowToUpperLeftCorner();
        bestuurder.relayMouseEvent(500,110,100,1); //click on name column4
        for(int i=0;i<7;i++){
            bestuurder.relayKeyEvent(400,8,'o'); //Backspace
        }
        bestuurder.relayKeyEvent(400,97,'a');//press 'a'
        bestuurder.relayKeyEvent(400,98,'b');//press 'b'
        bestuurder.relayKeyEvent(400,99,'c');//press 'c'
        bestuurder.relayKeyEvent(400,10,'o'); //ENTER

        String colName = dc.getSelectedTable().getColumnNames().get(dc.getSelectedTable().getColumnNames().size()-1).getName();
        assertEquals("abc",colName);
    }

    @Test
    public void EditColumnNameToOtherColumnName() {
        MoveWindowToUpperLeftCorner();
        bestuurder.relayMouseEvent(500,110,100,1); //click on name column4
        bestuurder.relayKeyEvent(400,8,'o'); //Backspace
        bestuurder.relayKeyEvent(400,50,'2');//press '2'
        bestuurder.relayKeyEvent(400,10,'o'); //ENTER
        //Because the name is already taken the user should still be in edit mode
        assertEquals("edit",window.getCurrMode());
        bestuurder.relayKeyEvent(400,8,'o'); //Backspace
        bestuurder.relayKeyEvent(400,10,'o'); //ENTER
        String colName = dc.getSelectedTable().getColumnNames().get(dc.getSelectedTable().getColumnNames().size()-1).getName();
        assertEquals("Column",colName);
    }

    //EXTENSIONS
    @Test
    public void UserChangesTypeOfColumn() {
        MoveWindowToUpperLeftCorner();
        ResizeWindow();
        //type of column4 is standard string
        String type = dc.getSelectedTable().getColumnNames().get(dc.getSelectedTable().getColumnNames().size()-1).getType();
        assertEquals("String",type);

        bestuurder.relayMouseEvent(500,250,100,1); //Click ont type of column4
        type = dc.getSelectedTable().getColumnNames().get(dc.getSelectedTable().getColumnNames().size()-1).getType();
        assertEquals("Email",type);

        bestuurder.relayMouseEvent(500,250,100,1); //Click ont type of column4
        type = dc.getSelectedTable().getColumnNames().get(dc.getSelectedTable().getColumnNames().size()-1).getType();
        assertEquals("Boolean",type);

        bestuurder.relayMouseEvent(500,250,100,1); //Click ont type of column4
        type = dc.getSelectedTable().getColumnNames().get(dc.getSelectedTable().getColumnNames().size()-1).getType();
        assertEquals("Integer",type);

        bestuurder.relayMouseEvent(500,250,100,1); //Click ont type of column4
        type = dc.getSelectedTable().getColumnNames().get(dc.getSelectedTable().getColumnNames().size()-1).getType();
        assertEquals("String",type);
    }

    @Test
    public void UserChangesCheckboxBlanksAllowed() {
        MoveWindowToUpperLeftCorner();
        ResizeWindow();
        boolean blanksAllowed = dc.getSelectedTable().getColumnNames().get(1).getBlanksAllowed();
        bestuurder.relayMouseEvent(500,375,60,1); //Blank checkbox of col2 clicked
        boolean blanksAllowed2 = dc.getSelectedTable().getColumnNames().get(1).getBlanksAllowed();
        assertEquals(!blanksAllowed,blanksAllowed2);
    }

    @Test
    public void UserChangesCheckboxBlanksAllowedToInvalidStateAndTriesToAddColumn() {
        MoveWindowToUpperLeftCorner();
        ResizeWindow();
        int originalLen = dc.getTableList().get(0).getColumnNames().size();
        bestuurder.relayMouseEvent(500,375,80,1); //Blank checkbox of col3 clicked
        bestuurder.relayMouseEvent(501,80,130,2); //doubleclick under table
        int newLen = dc.getTableList().get(0).getColumnNames().size();
        assertEquals(originalLen,newLen);
    }


    @Test
    public void ChangeColumnDefaultValueBooleanBlanksAllowed() {
        MoveWindowToUpperLeftCorner();
        ResizeWindow();
        String value = dc.getSelectedTable().getColumnNames().get(0).getDefaultV().getString();
        assertEquals("True",value);

        bestuurder.relayMouseEvent(500,175,40,1);
        value = dc.getSelectedTable().getColumnNames().get(0).getDefaultV().getString();
        assertEquals("False",value);

        bestuurder.relayMouseEvent(500,175,40,1);
        value = dc.getSelectedTable().getColumnNames().get(0).getDefaultV().getString();
        assertEquals("empty",value);

        bestuurder.relayMouseEvent(500,175,40,1);
        value = dc.getSelectedTable().getColumnNames().get(0).getDefaultV().getString();
        assertEquals("True",value);
    }

    @Test
    public void ChangeColumnDefaultValueBooleanBlanksNotAllowed() {
        MoveWindowToUpperLeftCorner();
        ResizeWindow();
        bestuurder.relayMouseEvent(500,375,40,1); //Blanks clicked, now not allowed for col1
        String value = dc.getSelectedTable().getColumnNames().get(0).getDefaultV().getString();
        assertEquals("True",value);

        bestuurder.relayMouseEvent(500,175,40,1);
        value = dc.getSelectedTable().getColumnNames().get(0).getDefaultV().getString();
        assertEquals("False",value);

        bestuurder.relayMouseEvent(500,175,40,1);
        value = dc.getSelectedTable().getColumnNames().get(0).getDefaultV().getString();
        assertEquals("True",value);
    }

    @Test
    public void ChangeColumnDefaultValueStringBlanksNotAllowed() {
        MoveWindowToUpperLeftCorner();
        ResizeWindow();
        bestuurder.relayMouseEvent(500,175,80,1);//now editing DV
        bestuurder.relayKeyEvent(400,97,'a');//press 'a'
        bestuurder.relayKeyEvent(400,10,'o'); //ENTER

        bestuurder.relayMouseEvent(500,375,80,1); //Blanks clicked, now not allowed for col3
        bestuurder.relayMouseEvent(500,175,80,1);//now editing DV
        bestuurder.relayKeyEvent(400,8,'o'); //Backspace
        bestuurder.relayKeyEvent(400,10,'o'); //ENTER
        //We should still be in edit mode because blanks are not allowed
        assertEquals("edit",window.getCurrMode());

        int originalLen = dc.getTableList().get(0).getColumnNames().size();
        bestuurder.relayMouseEvent(501,110,100,2); //double click under column
        int newLen = dc.getTableList().get(0).getColumnNames().size();
        assertEquals(originalLen,newLen);
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
