
import Data.*;
import UserInterfaceElements.UIRowModule;
import UserInterfaceElements.UISuperClass;
import UserInterfaceElements.UITopLevelWindow;
import org.junit.jupiter.api.Test;
import UserInterfaceElements.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Use case: Delete Row
public class Scenario10 {
    //initialise class variables
    private Controller bestuurder;
    private dataController dc;
    private UITopLevelWindow topWindow;
    private UISuperClass window;

    //get values for class variables
    public Scenario10() {
        bestuurder = new Controller(1);
        dc = bestuurder.getTableDataController();
        bestuurder.setCurrentMode("table");
        bestuurder.relayMouseEvent(500,115,60,2);
        topWindow = bestuurder.getTopLevelWindow();
        window = topWindow.getActiveSubWindow();

        dc.addTable();
        dc.addTable();
        dc.addTable();
        //initialise rows + collumns
        for (int i = 0;i<3;i++) {

            Column col1 = new Column("Column1",new CellBoolean(true), "Boolean", true);
            Column col2 = new Column("Column2",new CellBoolean(true), "Boolean", true);
            Column col3 = new Column("Column3", new CellText(""), "String", true);
            Column col4 = new Column("Column4",new CellText(""), "String", true);
            dc.getTableList().get(i).addColumn(col1);
            dc.getTableList().get(i).addColumn(col2);
            dc.getTableList().get(i).addColumn(col3);
            dc.getTableList().get(i).addColumn(col4);

            Row row1 = new Row(dc.getTableList().get(i).getColumnNames());
            Row row2 = new Row(dc.getTableList().get(i).getColumnNames());
            Row row3 = new Row(dc.getTableList().get(i).getColumnNames());

            dc.getTableList().get(i).addRow(row1);
            dc.getTableList().get(i).addRow(row2);
            dc.getTableList().get(i).addRow(row3);
        }
    }

    //Precondition
    @Test
    public void IsInRightMode() {
        assertEquals(UIRowModule.class,window.getClass());
    }




    //Old tests
    @Test
    public void UserDeletesFirstRow() {
        int originalSize = dc.getTableList().get(0).getTableRows().size();
        bestuurder.relayMouseEvent(500,52,56,1); //Click left margin
        bestuurder.relayKeyEvent(400,127,'o'); //DELETE
        int newSize = dc.getTableList().get(0).getTableRows().size();
        assertEquals((originalSize-1), newSize);
    }

    @Test
    public void UserGoesInDeleteModeAndPressesDeleteAfterExitingThisMode() {
        int originalSize = dc.getTableList().get(0).getTableRows().size();
        bestuurder.relayMouseEvent(500,52,56,1); //Click left margin
        bestuurder.relayKeyEvent(400,27,'o'); //ESCAPE
        bestuurder.relayKeyEvent(400,127,'o'); //DELETE
        int newSize = dc.getTableList().get(0).getTableRows().size();
        assertEquals(originalSize,newSize);
    }
}
