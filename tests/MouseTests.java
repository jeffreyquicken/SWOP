


import EventHandlers.mouseEventHandler;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MouseTests {

    mouseEventHandler clicker = new mouseEventHandler();

    //this test wil check that if lowest point in the table is clicked that it does not count as under the table
    @Test
    void ClickUnderTableUpperBoundaryShouldReturnFalse() {
        assertEquals(false, clicker.doubleClickUnderTable(50,2,500,50) );
    }

    //the following four tests will check if the boundary of a table still belongs to the table
    @Test
    void ClickTopOfTableShouldReturnTrue() {
        assertEquals(true, clicker.isInTableHeight(20,20,30,2));
    }

    @Test
    void ClickBottomOfTableShouldReturnTrue() {
        assertEquals(true, clicker.isInTableHeight(80,20,30,2));
    }

    @Test
    void ClickLeftOfTableShouldReturnTrue() {
       // assertEquals(true, clicker.isInTableWidth(20,20,30,2));
    }

    @Test
    void ClickRightOfTableShouldReturnTrue() {
   //    assertEquals(true, clicker.isInTableWidth(80,20,30,2));
    }
}