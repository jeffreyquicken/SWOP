
import Data.Column;
import org.junit.jupiter.api.Test;
import Data.dataController;
import UserInterfaceElements.Controller;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Scenario5 {
    //initialise class variables
    private Controller bestuurder;
    private dataController dc;

    //get values for class variables
    public Scenario5() {
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
    public void UserAddsNewColumn() {
        int originalLen = dc.getTableList().get(0).getColumnNames().size();
        bestuurder.relayMouseEvent(501,100,150,2); //doubleclick under table
        int newLen = dc.getTableList().get(0).getColumnNames().size();
        assertEquals((originalLen+1),newLen);
    }

    @Test
    public void UserAddsNewColumnWithAlreadyTakenName() {
        bestuurder.relayMouseEvent(500,120,120,1); //click on name column4
        bestuurder.relayKeyEvent(400,8,'o'); //Backspace
        bestuurder.relayKeyEvent(400,53,'5');//press '5'
        bestuurder.relayKeyEvent(400,10,'o');//Enter
        //new column should be namen column6 instead of column5 because that is now taken
        bestuurder.relayMouseEvent(501,100,150,2); //doubleclick under table
        String colName = dc.getSelectedTable().getColumnNames().get(dc.getSelectedTable().getColumnNames().size()-1).getName();
        assertEquals("Column6", colName);
    }

    @Test
    public void UserAddsAColumnDeletesItAndAddsAnotherOne() {
        int originalLen = dc.getTableList().get(0).getColumnNames().size();
        bestuurder.relayMouseEvent(501,100,150,2); //double click under table
        bestuurder.relayMouseEvent(500,55,140,1); //Select left margin
        bestuurder.relayKeyEvent(400,127,'o'); //Delete
        bestuurder.relayMouseEvent(501,100,150,2); //double click under table
        int newLen = dc.getTableList().get(0).getColumnNames().size();
        assertEquals((originalLen+1),newLen);

    }
}
