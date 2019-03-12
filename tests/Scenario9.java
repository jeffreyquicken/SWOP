
import org.junit.jupiter.api.Test;
import Data.dataController;
import UserInterfaceElements.Controller;
import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void EditRowBooleanValue() {
        String originalValue = dc.getTableList().get(0).getTableRows().get(0).getColumnList().get(1);
        bestuurder.relayMouseEvent(500,199,58,1);
        String newValue = dc.getTableList().get(0).getTableRows().get(0).getColumnList().get(1);
        assertFalse(originalValue == newValue);
    }
}
