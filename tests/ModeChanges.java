import Data.dataController;
import UserInterfaceElements.Controller;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//Test if we can switch from mode properly
public class ModeChanges {
    //initialise class variables
    private Controller bestuurder;
    private dataController dc;

    public ModeChanges() {
        bestuurder = new Controller();
        dc = bestuurder.getTableDataController();
        bestuurder.setCurrentMode("table");
        bestuurder.relayMouseEvent(500,115,60,2); //Now in row mode
    }

    //Precondition
    @Test
    public void IsInRightMode() {
        assertEquals("row",bestuurder.getCurrentMode());
    }

    @Test
    public void SwitchingBetweenRowsAndDesign() {
        String currMode = bestuurder.getCurrentMode();
        bestuurder.relayKeyEvent(400,17,'o'); // CTRL
        bestuurder.relayKeyEvent(400,10,'o'); //ENTER
        String newMode = bestuurder.getCurrentMode();
        assertEquals("design",newMode);
        bestuurder.relayKeyEvent(400,17,'o'); // CTRL
        bestuurder.relayKeyEvent(400,10,'o'); //ENTER
        assertEquals(currMode,bestuurder.getCurrentMode());
    }

    @Test
    public void SwitchingFromRowsToTableMode() {
        bestuurder.relayKeyEvent(400,27,'o');//Escape
        assertEquals("table",bestuurder.getCurrentMode());
    }
    @Test
    public void SwitchingFromDesignToTableMode() {
        bestuurder.relayKeyEvent(400,17,'o'); // CTRL
        bestuurder.relayKeyEvent(400,10,'o'); //ENTER
        //We are now in designmode
        assertEquals("design",bestuurder.getCurrentMode());

        bestuurder.relayKeyEvent(400,27,'o');//Escape
        assertEquals("table",bestuurder.getCurrentMode());
    }
}
