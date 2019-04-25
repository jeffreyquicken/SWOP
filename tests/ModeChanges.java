import Data.dataController;
import UserInterfaceElements.Controller;
import UserInterfaceElements.MyCanvasWindow;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//Test if we can switch from mode properly
public class ModeChanges {
    //initialise class variables
    private Controller bestuurder;
    private dataController dc;
    private MyCanvasWindow relay;

    public ModeChanges() {
        relay = new MyCanvasWindow("testing", 1);
        bestuurder = relay.getController();
        dc = bestuurder.getTableDataController();
        bestuurder.setCurrentMode("table");
        relay.handleMouseEvent(500,115,60,2); //Now in row mode
    }

    //Precondition
    @Test
    public void IsInRightMode() {
        assertEquals("row",bestuurder.getCurrentMode());
    }

    @Test
    public void SwitchingBetweenRowsAndDesign() {
        String currMode = bestuurder.getCurrentMode();
        relay.handleKeyEvent(400,17,'o'); // CTRL
        relay.handleKeyEvent(400,10,'o'); //ENTER
        String newMode = bestuurder.getCurrentMode();
        assertEquals("design",newMode);
        relay.handleKeyEvent(400,17,'o'); // CTRL
        relay.handleKeyEvent(400,10,'o'); //ENTER
        assertEquals(currMode,bestuurder.getCurrentMode());
    }

    @Test
    public void SwitchingFromRowsToTableMode() {
        relay.handleKeyEvent(400,27,'o');//Escape
        assertEquals("table",bestuurder.getCurrentMode());
    }
    @Test
    public void SwitchingFromDesignToTableMode() {
        relay.handleKeyEvent(400,17,'o'); // CTRL
        relay.handleKeyEvent(400,10,'o'); //ENTER
        //We are now in designmode
        assertEquals("design",bestuurder.getCurrentMode());

        relay.handleKeyEvent(400,27,'o');//Escape
        assertEquals("table",bestuurder.getCurrentMode());
    }
}
