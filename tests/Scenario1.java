
import org.junit.jupiter.api.Test;
import UserInterfaceElements.Controller;
import Data.dataController;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Scenario1 {

    //initialise class variables
    private Controller bestuurder;
    private dataController dc;

    //get values for class variables
    public Scenario1() {
        bestuurder = new Controller();
        dc = bestuurder.getTableDataController();
        bestuurder.setCurrentMode("table");
    }

    @Test
    public void doubleClickUnderTableAddsNewTable() {

       int origineleLengte = dc.getTableList().size();
       bestuurder.relayMouseEvent(501,100,200,2);
       int eL = dc.getTableList().size() -1;

       assertEquals(origineleLengte, eL);


    }


}
