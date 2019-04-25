import Data.Column;
import Data.*;
import Data.dataController;
import UserInterfaceElements.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UIDESIGNTEST {



        //initialise class variables
        private Controller bestuurder;
        private dataController dc;
        private UITopLevelWindow topWindow;
        private UISuperClass window;

        public UIDESIGNTEST() {
            bestuurder = new Controller(1);
            dc = bestuurder.getTableDataController();
            bestuurder.setCurrentMode("table");
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

        public void switchToDesignMode(){
            bestuurder.relayMouseEvent(502,122,64,2);
            bestuurder.relayMouseEvent(502,212,105,1);
            bestuurder.relayKeyEvent(401, 17,'\uFFFF');
            bestuurder.relayKeyEvent(401, 10,'\n');
            bestuurder.relayMouseEvent(502,213,131,1);
        }

        //Test to see if crl+Enter opens DesignModule
        @Test
        public void TestOpenDesignMode() {
            bestuurder.relayMouseEvent(502,122,64,2);
            bestuurder.relayMouseEvent(502,212,105,1);
            bestuurder.relayKeyEvent(401, 17,'\uFFFF');
            bestuurder.relayKeyEvent(401, 10,'\n');
            bestuurder.relayMouseEvent(502,213,131,1);
            String klasse = bestuurder.getTopLevelWindow().getActiveSubWindow().getClass() + "";
            assertTrue(klasse.endsWith("UIDesignModule"));
          //  assertEquals(window.getTable().getTableRows(), 4);

        }

         //Test to see if double Clicks add new row(=collumn)
        @Test
        public void TestDoubleClicks() {
            switchToDesignMode();

            UIDesignModule UIDesignModule = (UIDesignModule) topWindow.getActiveSubWindow();
            int previousColl = UIDesignModule.getTable().getColumnNames().size();

            bestuurder.relayMouseEvent(501,217,148,2);

            int newColl = UIDesignModule.getTable().getColumnNames().size();

            assertEquals((previousColl +1), newColl);

            //  assertEquals(window.getTable().getTableRows(), 4);

            }
    //Test to see if double Clicks add new row(=collumn)
    @Test
    public void TestDeleteRow() {
        switchToDesignMode();

        UIDesignModule UIDesignModule = (UIDesignModule) topWindow.getActiveSubWindow();

        int previousColl = UIDesignModule.getTable().getColumnNames().size();

        for (int i = 355; i <= 563; i++) {
            bestuurder.relayMouseEvent(506,i,185,1);
        }

        bestuurder.relayMouseEvent(501,437,111,1);
        bestuurder.relayKeyEvent(401, 127,'\u007F');


        int newColl = UIDesignModule.getTable().getColumnNames().size();

        assertEquals((previousColl -1), newColl);

        //  assertEquals(window.getTable().getTableRows(), 4);

    }



}
