import org.junit.Test;
import UserInterfaceElements.Controller;
import Data.dataController;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Scenario1 {
    Controller bestuurder = new Controller();
    dataController dc = bestuurder.getTableDataController();
    @Test
    public void doubleClickUnderTableAddsNewTableWithCorrectName() {
       bestuurder.setCurrentMode("table");
       int origineleLengte = dc.getTableList().size();
       bestuurder.relayMouseEvent(501,100,200,2);
       int eL = dc.getTableList().size() -1;

       assertEquals(origineleLengte, eL);


    }


}
