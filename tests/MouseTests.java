


import EventHandlers.mouseEventHandler;
import org.junit.jupiter.api.Test;

public class MouseTests {

    mouseEventHandler clicker = new mouseEventHandler();
    //this test wil check that if lowest point in the table is clicked that it does not count as under the table
    @Test
    public boolean testClickUnderTableUpperBoundary() {

        if (clicker.doubleClickUnderTable(50,2,500,50)) {
            return false;
        } else {
            return true;
        }
    }


    //the following four tests will check if the boundary of a table still belongs to the table
    @Test
    public boolean testClickTopOfTable() {
        if (clicker.isInTableHeight(20,20,30,2)) {
            return true;
        } else {
            return false;
        }
    }
    @Test
    public boolean testClickBottomOfTable() {
        if (clicker.isInTableHeight(80,20,30,2)) {
            return true;
        } else {
            return false;
        }
    }
    @Test
    public boolean testClickLeftOfTable() {
        if (clicker.isInTableWidth(20,20,30,2)) {
            return true;
        } else {
            return false;
        }
    }
    @Test
    public boolean testClickRightOfTable() {
        if (clicker.isInTableWidth(80,20,30,2)) {
            return true;
        } else {
            return false;
        }
    }
}