
import org.junit.jupiter.api.Test;
import Data.dataController;
import UserInterfaceElements.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Use case: Edit Column Characteristic
public class Scenario6 {
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
    

}
