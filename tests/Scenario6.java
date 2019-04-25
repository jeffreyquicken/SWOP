
import Data.*;
import UserInterfaceElements.UIDesignModule;
import UserInterfaceElements.UISuperClass;
import UserInterfaceElements.UITopLevelWindow;
import org.junit.jupiter.api.Test;
import UserInterfaceElements.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
        window = topWindow.getActiveSubWindow();
        //initialise tables
        Table table1 = new Table("Table 1");
        Table table2 = new Table("Table 2");
        Table table3 = new Table("Table 3");
        dc.addTable(table1);
        dc.addTable(table2);
        dc.addTable(table3);
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

    @Test
    public void TestEditCheckboxBooleanCheckedToUnchecked(){
        switchToDesignMode();
        UIDesignModule UIDesignModule = (UIDesignModule) topWindow.getActiveSubWindow();

        for (int i = 355; i <= 563; i++) {
            bestuurder.relayMouseEvent(506,i,185,1);
        }


    }




    //Old tests
    @Test
    public void EditColumnNameNormal() {
        bestuurder.relayMouseEvent(500,120,120,1); //click on name column4
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
        bestuurder.relayMouseEvent(500,120,120,1); //click on name column4
        bestuurder.relayKeyEvent(400,8,'o'); //Backspace
        bestuurder.relayKeyEvent(400,50,'2');//press '2'
        bestuurder.relayKeyEvent(400,10,'o'); //ENTER
        //Because the name is already taken the user should still be in edit mode
        bestuurder.relayKeyEvent(400,8,'o'); //Backspace
        bestuurder.relayKeyEvent(400,10,'o'); //ENTER
        String colName = dc.getSelectedTable().getColumnNames().get(dc.getSelectedTable().getColumnNames().size()-1).getName();
        assertEquals("Column",colName);
    }

    //EXTENTIONS
    @Test
    public void UserChangesTypeOfColumn() {
        //type of column4 is standard string
        String type = dc.getSelectedTable().getColumnNames().get(dc.getSelectedTable().getColumnNames().size()-1).getType();
        assertEquals("String",type);

        bestuurder.relayMouseEvent(500,300,120,1); //Click ont type of column4
        type = dc.getSelectedTable().getColumnNames().get(dc.getSelectedTable().getColumnNames().size()-1).getType();
        assertEquals("Email",type);

        bestuurder.relayMouseEvent(500,300,120,1); //Click ont type of column4
        type = dc.getSelectedTable().getColumnNames().get(dc.getSelectedTable().getColumnNames().size()-1).getType();
        assertEquals("Boolean",type);

        bestuurder.relayMouseEvent(500,300,120,1); //Click ont type of column4
        type = dc.getSelectedTable().getColumnNames().get(dc.getSelectedTable().getColumnNames().size()-1).getType();
        assertEquals("Integer",type);

        bestuurder.relayMouseEvent(500,300,120,1); //Click ont type of column4
        type = dc.getSelectedTable().getColumnNames().get(dc.getSelectedTable().getColumnNames().size()-1).getType();
        assertEquals("String",type);
    }

    @Test
    public void UserChangesCheckboxBlanksAllowed() {
        boolean blanksAllowed = dc.getSelectedTable().getColumnNames().get(1).getBlanksAllowed();
        bestuurder.relayMouseEvent(500,400,80,1); //Blank checkbox of col2 clicked
        boolean blanksAllowed2 = dc.getSelectedTable().getColumnNames().get(1).getBlanksAllowed();
        assertEquals(!blanksAllowed,blanksAllowed2);
    }

    @Test
    public void UserChangesCheckboxBlanksAllowedToInvalidStateAndTriesToAddColumn() {
        int originalLen = dc.getTableList().get(0).getColumnNames().size();
        bestuurder.relayMouseEvent(500,400,100,1); //Blank checkbox of col3 clicked
        int newLen = dc.getTableList().get(0).getColumnNames().size();
        assertEquals(originalLen,newLen);
    }
    /**

    @Test
    public void ChangeColumnDefaultValueBooleanBlanksAllowed() {
        String value = dc.getSelectedTable().getColumnNames().get(0).getDefaultV();
        assertEquals("true",value);

        bestuurder.relayMouseEvent(500,200,60,1);
        value = dc.getSelectedTable().getColumnNames().get(0).getDefaultV();
        assertEquals("false",value);

        bestuurder.relayMouseEvent(500,200,60,1);
        value = dc.getSelectedTable().getColumnNames().get(0).getDefaultV();
        assertEquals("empty",value);

        bestuurder.relayMouseEvent(500,200,60,1);
        value = dc.getSelectedTable().getColumnNames().get(0).getDefaultV();
        assertEquals("true",value);
    }

    @Test
    public void ChangeColumnDefaultValueBooleanBlanksNotAllowed() {
        bestuurder.relayMouseEvent(500,400,60,1); //Blanks clicked, now not allowed for col1
        String value = dc.getSelectedTable().getColumnNames().get(0).getDefaultV();
        assertEquals("true",value);

        bestuurder.relayMouseEvent(500,200,60,1);
        value = dc.getSelectedTable().getColumnNames().get(0).getDefaultV();
        assertEquals("false",value);

        bestuurder.relayMouseEvent(500,200,60,1);
        value = dc.getSelectedTable().getColumnNames().get(0).getDefaultV();
        assertEquals("true",value);
    }

    @Test
    public void ChangeColumnDefaultValueStringBlanksNotAllowed() {
        bestuurder.relayMouseEvent(500,200,100,1);//now editing DV
        bestuurder.relayKeyEvent(400,97,'a');//press 'a'
        bestuurder.relayKeyEvent(400,10,'o'); //ENTER

        bestuurder.relayMouseEvent(500,400,100,1); //Blanks clicked, now not allowed for col3
        bestuurder.relayMouseEvent(500,200,100,1);//now editing DV
        bestuurder.relayKeyEvent(400,8,'o'); //Backspace
        bestuurder.relayKeyEvent(400,10,'o'); //ENTER
        //We should still be in edit mode because blanks are not allowed

        int originalLen = dc.getTableList().get(0).getColumnNames().size();
        bestuurder.relayMouseEvent(501,100,150,2); //double click under column
        int newLen = dc.getTableList().get(0).getColumnNames().size();
        assertEquals(originalLen,newLen);
    }

*/

}
