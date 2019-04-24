
import UserInterfaceElements.*;
import org.junit.jupiter.api.Test;
import Data.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Use case: AddRow
public class Scenario8 {
    //initialise class variables
    private Controller bestuurder;
    private dataController dc;
    private UITopLevelWindow topWindow;
    private UISuperClass window;

    //get values for class variables
    public Scenario8() {
        bestuurder = new Controller(1);
        dc = bestuurder.getTableDataController();
        bestuurder.setCurrentMode("table");
        bestuurder.relayMouseEvent(500,115,60,2); //Now in row mode

        topWindow = bestuurder.getTopLevelWindow();
        window = topWindow.getActiveSubWindow();

        //initialise tables
        Table table1 = new Table("Table 1");
        Table table2 = new Table("Table 2");
        Table table3 = new Table("Table 3");
        dc.addTable(table1);
        dc.addTable(table2);
        dc.addTable(table3);
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
    public void UserDoubleClicksUnderTableAndAddsNewRow() {
        int l1 = dc.getTableList().get(0).getTableRows().size();
        bestuurder.relayMouseEvent(501,120,120,2);
        int l2 = dc.getTableList().get(0).getTableRows().size();
        assertEquals((l1+1),l2);
    }

    /**
    @Test
    public void UserAddsNewRowWithCorrectDefaultValues() {
        bestuurder.relayMouseEvent(501,120,120,2);
        int size = dc.getTableList().get(0).getTableRows().size();
        List<String> newRow = dc.getTableList().get(0).getTableRows().get(size-1).getColumnList();
        List<Column> kols = dc.getTableList().get(0).getColumnNames();

        for(int i=0;i<(newRow.size()-1);i++) {
            assertEquals(kols.get(0).getDefaultV(),newRow.get(i));
        }



    } */
}
