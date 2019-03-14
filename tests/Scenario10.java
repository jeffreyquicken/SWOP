
import org.junit.jupiter.api.Test;
import Data.dataController;
import UserInterfaceElements.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Use case: Delete Row
public class Scenario10 {
    //initialise class variables
    private Controller bestuurder;
    private dataController dc;

    //get values for class variables
    public Scenario10() {
        bestuurder = new Controller();
        dc = bestuurder.getTableDataController();
        bestuurder.setCurrentMode("table");
        bestuurder.relayMouseEvent(500,115,60,2);
    }

    //Precondition
    @Test
    public void IsInRightMode() {
        assertEquals("row",bestuurder.getCurrentMode());
    }

    @Test
    public void UserDeletesFirstRow() {
        int originalSize = dc.getTableList().get(0).getTableRows().size();
        bestuurder.relayMouseEvent(500,52,56,1); //Click left margin
        bestuurder.relayKeyEvent(400,127,'o'); //DELETE
        int newSize = dc.getTableList().get(0).getTableRows().size();
        assertEquals((originalSize-1), newSize);
    }

    @Test
    public void UserGoesInDeleteModeAndPressesDeleteAfterExitingThisMode() {
        int originalSize = dc.getTableList().get(0).getTableRows().size();
        bestuurder.relayMouseEvent(500,52,56,1); //Click left margin
        bestuurder.relayKeyEvent(400,27,'o'); //ESCAPE
        bestuurder.relayKeyEvent(400,127,'o'); //DELETE
        int newSize = dc.getTableList().get(0).getTableRows().size();
        assertEquals(originalSize,newSize);
    }
}
