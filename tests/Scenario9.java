
import org.junit.jupiter.api.Test;
import Data.dataController;
import UserInterfaceElements.Controller;
import static org.junit.jupiter.api.Assertions.assertEquals;

//Use case = Edit Row Value
public class Scenario9 {
    //initialise class variables
    private Controller bestuurder;
    private dataController dc;

    //get values for class variables
    public Scenario9() {
        bestuurder = new Controller();
        dc = bestuurder.getTableDataController();
        bestuurder.setCurrentMode("table");
        bestuurder.relayMouseEvent(500,115,60,2);
    }

    @Test
    public void EditRowValueFirstRowFirstColumn() {
        String originalValue = dc.getTableList().get(0).getTableRows().get(0).getColumnList().get(0);
        bestuurder.relayMouseEvent(500,100,60,1);
        bestuurder.relayKeyEvent(400,101,'a');
        bestuurder.relayKeyEvent(400,10,'o'); //ENTER
        String newValue = dc.getTableList().get(0).getTableRows().get(0).getColumnList().get(0);
        assertEquals((originalValue + "a"), newValue);
    }
}
