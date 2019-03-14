
import org.junit.jupiter.api.Test;
import Data.*;
import UserInterfaceElements.Controller;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Use case: AddRow
public class Scenario8 {
    //initialise class variables
    private Controller bestuurder;
    private dataController dc;

    //get values for class variables
    public Scenario8() {
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
    public void UserDoubleClicksUnderTableAndAddsNewRow() {
        int l1 = dc.getTableList().get(0).getTableRows().size();
        bestuurder.relayMouseEvent(501,120,120,2);
        int l2 = dc.getTableList().get(0).getTableRows().size();
        assertEquals((l1+1),l2);
    }

    @Test
    public void UserAddsNewRowWithCorrectDefaultValues() {
        bestuurder.relayMouseEvent(501,120,120,2);
        int size = dc.getTableList().get(0).getTableRows().size();
        List<String> newRow = dc.getTableList().get(0).getTableRows().get(size-1).getColumnList();
        List<Column> kols = dc.getTableList().get(0).getColumnNames();

        for(int i=0;i<(newRow.size()-1);i++) {
            assertEquals(kols.get(0).getDefaultV(),newRow.get(i));
        }



    }
}
