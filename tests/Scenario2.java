
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
    public void StandardEditTableNameByClickingNextToTable() {
        bestuurder.relayMouseEvent(500,115,60,1);
        //we should be in table name editing mode right now
        bestuurder.relayKeyEvent(400,101,'a');
        bestuurder.relayMouseEvent(500,350,60,1);
        List<Table> table = dc.getTableList();
        String tbleName = table.get(0).getTableName();
        assertEquals("Table 1a",tbleName);

    }

    @Test
    public void StandardEditTableNameByPressingEnter() {
        bestuurder.relayMouseEvent(500,115,60,1);
        //we should be in table name editing mode right now
        bestuurder.relayKeyEvent(400,101,'a');
        bestuurder.relayKeyEvent(400,10,'o');
        List<Table> table = dc.getTableList();
        String tbleName = table.get(0).getTableName();
        assertEquals("Table 1a",tbleName);
    }

    @Test
    public void StandardEditTableNameUsingBackspaceByClickingNextToTable() {
        bestuurder.relayMouseEvent(500,115,60,1);
        //we should be in table name editing mode right now
        for(int i=0; i<7;i++) {
            bestuurder.relayKeyEvent(400,8,'o');
        }


        bestuurder.relayKeyEvent(400,101,'a');
        bestuurder.relayKeyEvent(400,101,'b');
        bestuurder.relayKeyEvent(400,101,'c');
        bestuurder.relayMouseEvent(500,350,60,1);
        List<Table> table = dc.getTableList();
        String tbleName = table.get(0).getTableName();
        assertEquals("abc",tbleName);

    }

}
