
import org.junit.jupiter.api.Test;
import Data.dataController;
import UserInterfaceElements.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Use case: Edit Column Characteristic
public class Scenario6 { /**
    //initialise class variables
    private Controller bestuurder;
    private dataController dc;

    //get values for class variables
    public Scenario6() {
        bestuurder = new Controller();
        dc = bestuurder.getTableDataController();
        bestuurder.setCurrentMode("table");
        bestuurder.relayMouseEvent(500,115,60,2); // now in rowmode
        bestuurder.relayKeyEvent(400,17,'o'); // CTRL
        bestuurder.relayKeyEvent(400,10,'o'); //ENTER
    }

    //Precondition
    @Test
    public void IsInRightMode() {
        assertEquals("design",bestuurder.getCurrentMode());
    }

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
