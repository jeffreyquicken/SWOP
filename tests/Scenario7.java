
import org.junit.jupiter.api.Test;
import Data.dataController;
import UserInterfaceElements.Controller;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Scenario7 {
    //initialise class variables
    private Controller bestuurder;
    private dataController dc;

    //get values for class variables
    public Scenario7() {
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
    public void UserDeletesColumn() {
        int originalLen = dc.getTableList().get(0).getColumnNames().size();
        bestuurder.relayMouseEvent(500,55,120,1); //Select left margin
        bestuurder.relayKeyEvent(400,127,'o'); //Delete
        int newLen = dc.getTableList().get(0).getColumnNames().size();
        assertEquals((originalLen-1),newLen);
    }

    @Test
    public void UserClicksLeftMarginAndPressesEscapeBeforeDelete() {
        int originalLen = dc.getTableList().get(0).getColumnNames().size();
        bestuurder.relayMouseEvent(500,55,120,1); //Select left margin
        bestuurder.relayKeyEvent(400,27,'o');//Escape
        bestuurder.relayKeyEvent(400,127,'o'); //Delete
        int newLen = dc.getTableList().get(0).getColumnNames().size();
        assertEquals(originalLen,newLen);
    }
}
