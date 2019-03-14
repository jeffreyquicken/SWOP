
import org.junit.jupiter.api.Test;
import Data.dataController;
import UserInterfaceElements.Controller;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Scenario4 {
    //initialise class variables
    private Controller bestuurder;
    private dataController dc;

    //get values for class variables
    public Scenario4() {
        bestuurder = new Controller();
        dc = bestuurder.getTableDataController();
        bestuurder.setCurrentMode("table");
    }

    //Precondition
    @Test
    public void IsInRightMode() {
        assertEquals("table",bestuurder.getCurrentMode());
    }

    @Test
    public void DoubleClickingTableNameGoesToTableRowsMode() {
        bestuurder.relayMouseEvent(500,115,60,2);
        String str = bestuurder.getCurrentMode();
        assertEquals("row",str);
    }
}
