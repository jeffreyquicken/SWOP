
import Data.dataController;
import UserInterfaceElements.Controller;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class Scenario3 {
    //initialise class variables
    private Controller bestuurder;
    private dataController dc;

    //get values for class variables
    public Scenario3() {
        bestuurder = new Controller();
        dc = bestuurder.getTableDataController();
        bestuurder.setCurrentMode("table");
    }

    @Test
    public void UserDeletesFirstTable() {
        int lenOriginal = dc.getTableList().size();
        bestuurder.relayMouseEvent(500,55,60,1);
        //we have now clicked the left margin
        bestuurder.relayKeyEvent(400,127,'o');
        int lenNew = dc.getTableList().size();
        assertEquals((lenOriginal - 1),lenNew);
    }

    @Test
    public void ClicksLeftMarginThenPressesEscapeThenDelete() {
        int lenOriginal = dc.getTableList().size();
        bestuurder.relayMouseEvent(500,55,60,1);
        //we have now clicked the left margin
        bestuurder.relayKeyEvent(400,27,'o');
        bestuurder.relayKeyEvent(400,127,'o');
        int lenNew = dc.getTableList().size();
        assertEquals(lenOriginal,lenNew);
    }


}
