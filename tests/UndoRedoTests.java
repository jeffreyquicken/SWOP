import Data.*;
import UserInterfaceElements.Controller;
import UserInterfaceElements.MyCanvasWindow;
import UserInterfaceElements.UISuperClass;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UndoRedoTests {
    //initialise class variables
    private Controller bestuurder;
    private dataController dc;
    private MyCanvasWindow relay;
    private UISuperClass window;

    public UndoRedoTests() {
        relay = new MyCanvasWindow("testing", 1);
        bestuurder = relay.getController();;
        dc = bestuurder.getTableDataController();
        bestuurder.setCurrentMode("table");
        window = bestuurder.getTopLevelWindow().getActiveSubWindow();


        //initialize tables
        dc.addTable();
        dc.addTable();
        dc.addTable();

    }
    //Tests concerning operations class, list of commands
    @Test
    public void MultipleCommands() {
        String val1 = dc.getTableList().get(0).getTableName();
        ChangeTableName();
        String val2 = dc.getTableList().get(0).getTableName();
        ChangeTableName();
        String val3 = dc.getTableList().get(0).getTableName();
        ChangeTableName();
        String val4 = dc.getTableList().get(0).getTableName();
        undo();
        assertEquals(val3,dc.getTableList().get(0).getTableName());
        undo();
        assertEquals(val2,dc.getTableList().get(0).getTableName());
        undo();
        assertEquals(val1,dc.getTableList().get(0).getTableName());
        redo();
        assertEquals(val2,dc.getTableList().get(0).getTableName());
        redo();
        assertEquals(val3,dc.getTableList().get(0).getTableName());
        redo();
        assertEquals(val4,dc.getTableList().get(0).getTableName());
    }


    //Tests concerninng individual commands for tables
    @Test
    public void TableNameCommand() {
        String oldName = dc.getTableList().get(0).getTableName();
        ChangeTableName();
        assertEquals("Table1a",dc.getTableList().get(0).getTableName());
        undo();
        assertEquals(oldName,dc.getTableList().get(0).getTableName());
        redo();
        assertEquals("Table1a",dc.getTableList().get(0).getTableName());
    }

    @Test
    public void NewTableCommand() {
        int originalSize = dc.getTableList().size();
        AddTable();
        assertEquals(originalSize+1,dc.getTableList().size());
        undo();
        assertEquals(originalSize,dc.getTableList().size());
        redo();
        assertEquals(originalSize+1,dc.getTableList().size());
    }

    //Tests concerning individual commands for rows
    @Test
    public void RowValueCommand() {
        OpenRowsWindow();
        assertEquals("", dc.getTableList().get(0).getTableRows().get(0).getColumnList().get(2).getString());
        ChangeRowValue();
        assertEquals("a", dc.getTableList().get(0).getTableRows().get(0).getColumnList().get(2).getString());
        undo();
        assertEquals("", dc.getTableList().get(0).getTableRows().get(0).getColumnList().get(2).getString());
        redo();
        assertEquals("a", dc.getTableList().get(0).getTableRows().get(0).getColumnList().get(2).getString());
    }

    @Test
    public void NewRowCommand() {
        OpenRowsWindow();
        int originalSize = dc.getTableList().get(0).getTableRows().size();
        AddRow();
        assertEquals(originalSize +1,dc.getTableList().get(0).getTableRows().size());
        undo();
        assertEquals(originalSize ,dc.getTableList().get(0).getTableRows().size());
        redo();
        assertEquals(originalSize +1,dc.getTableList().get(0).getTableRows().size());
    }

    //Tests concerning individual commands for Design
    @Test
    public void BlanksAllowedCommand() {
        OpenDesignWindow();
        boolean original = dc.getTableList().get(0).getColumnNames().get(0).getBlanksAllowed();
        ChangeBlanksAllowed();
        assertEquals(!original,dc.getTableList().get(0).getColumnNames().get(0).getBlanksAllowed());
        undo();
        assertEquals(original,dc.getTableList().get(0).getColumnNames().get(0).getBlanksAllowed());
        redo();
        assertEquals(!original,dc.getTableList().get(0).getColumnNames().get(0).getBlanksAllowed());
    }

    @Test
    public void ColumnNameCommand() {
        OpenDesignWindow();
        String original = dc.getTableList().get(0).getColumnNames().get(0).getName();
        ChangeColumnName();
        assertEquals(original+"a",dc.getTableList().get(0).getColumnNames().get(0).getName());
        undo();
        assertEquals(original,dc.getTableList().get(0).getColumnNames().get(0).getName());
        redo();
        assertEquals(original+"a",dc.getTableList().get(0).getColumnNames().get(0).getName());
    }

    @Test
    public void ColumnTypeCommand()
    {
        OpenDesignWindow();
        String originalType = dc.getTableList().get(0).getColumnNames().get(2).getType();
        ChangeColumnType();
        String newType = dc.getTableList().get(0).getColumnNames().get(2).getType();
        undo();
        assertEquals(originalType, dc.getTableList().get(0).getColumnNames().get(2).getType());
        redo();
        assertEquals(newType,dc.getTableList().get(0).getColumnNames().get(2).getType());
    }

    @Test
    public void DefaultValueCommand() {
        OpenDesignWindow();
        String original = dc.getTableList().get(0).getColumnNames().get(2).getDefaultV().getString();
        ChangeDefaultValue();
        String newDV = dc.getTableList().get(0).getColumnNames().get(2).getDefaultV().getString();
        undo();
        assertEquals(original,dc.getTableList().get(0).getColumnNames().get(2).getDefaultV().getString());
        redo();
        assertEquals(newDV,dc.getTableList().get(0).getColumnNames().get(2).getDefaultV().getString());
    }

    @Test
    public void NewColumnCommand() {
        OpenDesignWindow();
        int originalSize = dc.getTableList().get(0).getColumnNames().size();
        AddColumn();
        assertEquals(originalSize+1,dc.getTableList().get(0).getColumnNames().size());
        undo();
        assertEquals(originalSize,dc.getTableList().get(0).getColumnNames().size());
        redo();
        assertEquals(originalSize+1,dc.getTableList().get(0).getColumnNames().size());
    }

    //methods for actions to be used in tests
    void ChangeTableName() {
        MoveWindowToUpperLeftCorner();
        relay.handleMouseEvent(500,75,40,1);
        relay.handleKeyEvent(400,101,'a');
        relay.handleKeyEvent(400,10,'o');
    }

    void AddTable() {
        MoveWindowToUpperLeftCorner();
        relay.handleMouseEvent(501,110,100,2);
    }

    void ChangeRowValue() {
        OpenRowsWindow();
        bestuurder.relayMouseEvent(500,275,40,1);
        bestuurder.relayKeyEvent(400,101,'a');
        bestuurder.relayKeyEvent(400,10,'o'); //ENTER
    }

    void AddRow() {
        bestuurder.relayMouseEvent(501,110,100,2);//Double click under table
    }

    void ChangeBlanksAllowed() {
        relay.handleMouseEvent(500,375,40,1);
    }

    void ChangeColumnName () {
        relay.handleMouseEvent(500,100,40,1);
        relay.handleKeyEvent(401, 65,'a');
        relay.handleKeyEvent(401, 10,'e');
    }

    void ChangeColumnType() {
        relay.handleMouseEvent(500,275,80,1);
    }

    void ChangeDefaultValue() {
        relay.handleMouseEvent(500,175,80,1);
        relay.handleKeyEvent(401, 65,'a');
        relay.handleKeyEvent(401, 10,'e');
    }

    void AddColumn() {
        relay.handleMouseEvent(501,110,120,2);
    }

    public void MoveWindowToUpperLeftCorner() {
        List<Integer> info = bestuurder.getTopLevelWindow().getSubwindowInfo().get(window);
        info.set(0,0);
        info.set(1,0);
    }

    //Resizing the window so that everything is visible
    public void ResizeWindow() {
        List<Integer> info = bestuurder.getTopLevelWindow().getSubwindowInfo().get(window);
        info.set(2,500);
        info.set(3,150);
    }

    public void OpenRowsWindow() {
        //initialise rows + columns
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
        bestuurder.relayMouseEvent(502,115,60,2);
        bestuurder.relayMouseEvent(501,220,100,1); //Now in row mode
        window = bestuurder.getTopLevelWindow().getActiveSubWindow();
        MoveWindowToUpperLeftCorner();
        ResizeWindow();
    }

    public void OpenDesignWindow() {
        bestuurder.relayMouseEvent(502,115,60,2);
        bestuurder.relayMouseEvent(501,220,100,1); //Now in design mode
        for (int i = 0;i<3;i++) {
            Column col1 = new Column("Column1", new CellBoolean(true), "Boolean", true);
            Column col2 = new Column("Column2", new CellBoolean(true), "Boolean", true);
            Column col3 = new Column("Column3", new CellText(""), "String", true);
            Column col4 = new Column("Column4", new CellText(""), "String", true);
            dc.getTableList().get(i).addColumn(col1);
            dc.getTableList().get(i).addColumn(col2);
            dc.getTableList().get(i).addColumn(col3);
            dc.getTableList().get(i).addColumn(col4);
        }
        window = bestuurder.getTopLevelWindow().getActiveSubWindow();
        MoveWindowToUpperLeftCorner();
        ResizeWindow();
    }


    public void undo(){
        relay.handleKeyEvent(400,17,'o'); //CTRL
        relay.handleKeyEvent(400,90,'z'); //Z
    }

    public void redo() {
        relay.handleKeyEvent(400,17,'o'); //CTRL
        relay.handleKeyEvent(400,16,'o'); //SHIFT
        relay.handleKeyEvent(400,90,'z'); //Z
    }

}
