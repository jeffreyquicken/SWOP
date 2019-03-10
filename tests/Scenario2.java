
import Data.Table;
import org.junit.jupiter.api.Test;
import UserInterfaceElements.Controller;
import Data.dataController;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Use case: Edit Table Name
public class Scenario2 {
    //initialise class variables
    private Controller bestuurder;
    private dataController dc;

    //get values for class variables
    public Scenario2() {
        bestuurder = new Controller();
        dc = bestuurder.getTableDataController();
        bestuurder.setCurrentMode("table");
    }

    @Test
    public void StandardEditTableNameByPressingEnter() {
        bestuurder.relayMouseEvent(500,115,60,1);
        //we should be in table name editing mode right now
        char car = 'a';
        bestuurder.relayKeyEvent(400,0,car);
        bestuurder.relayMouseEvent(500,350,60,1);
        List<Table> table = dc.getTableList();
        String tbleName = table.get(0).getTableName();
        assertEquals("Table 1a",tbleName);


    }

}
