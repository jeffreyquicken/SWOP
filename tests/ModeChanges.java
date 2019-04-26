import Data.*;
import UserInterfaceElements.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//Test if we can switch from mode properly
public class ModeChanges {
    //initialise class variables
    private Controller bestuurder;
    private dataController dc;
    private MyCanvasWindow relay;
    private UITopLevelWindow topWindow;
    private UISuperClass window;

    public ModeChanges() {
        relay = new MyCanvasWindow("testing", 1);
        bestuurder = relay.getController();
        dc = bestuurder.getTableDataController();
        bestuurder.setCurrentMode("table");
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
        assertEquals(UITablesModule.class,window.getClass());
    }

    @Test
    public void SwitchingBetweenRowsAndDesign() {
        bestuurder.relayMouseEvent(502,115,60,2);
        bestuurder.relayMouseEvent(501,220,100,1); //Now in row mode
        window = topWindow.getActiveSubWindow();
        UISuperClass originalWindow = window;
        assertEquals(UIRowModule.class,originalWindow.getClass());
        relay.handleKeyEvent(400,17,'o'); // CTRL
        relay.handleKeyEvent(400,10,'o'); //ENTER
        window = topWindow.getActiveSubWindow();
        UISuperClass newWindow = window;
        assertEquals(UIDesignModule.class,newWindow.getClass());
        relay.handleKeyEvent(400,17,'o'); // CTRL
        relay.handleKeyEvent(400,10,'o'); //ENTER
        window = topWindow.getActiveSubWindow();
        assertEquals(originalWindow.getClass(),window.getClass());
    }

    @Test
    public void SwitchingFromRowsToTableMode() {
        assertEquals(UITablesModule.class,window.getClass());
        bestuurder.relayMouseEvent(502,115,60,2);
        bestuurder.relayMouseEvent(501,220,100,1); //Now in row mode
        window = topWindow.getActiveSubWindow();
        assertEquals(UIRowModule.class,window.getClass());
        bestuurder.relayMouseEvent(501,215,40,1); //closed row module
        window = topWindow.getActiveSubWindow();
        assertEquals(UITablesModule.class,window.getClass());
    }
    @Test
    public void SwitchingFromDesignToTableMode() {
        ClearTables();
        bestuurder.relayMouseEvent(502,115,60,2);
        //We are now in designmode
        window = topWindow.getActiveSubWindow();
        assertEquals(UIDesignModule.class,window.getClass());
        bestuurder.relayMouseEvent(501,215,40,1); //closed design module
        window = topWindow.getActiveSubWindow();
        assertEquals(UITablesModule.class,window.getClass());
    }

    public void ClearTables() {
        List<Column> cols = dc.getTableList().get(0).getColumnNames();
        List<Row> rows = dc.getTableList().get(0).getTableRows();
        for(int i= 0;i<3;i++) {
            dc.getTableList().get(0).deleteColumn(cols.get(0));
            dc.getTableList().get(0).deleteRow(rows.get(0));
        }
        dc.getTableList().get(0).deleteColumn(cols.get(0));
    }


}
