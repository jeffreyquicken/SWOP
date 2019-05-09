package UndoRedo;

import Data.Row;
import Data.dataController;

public class NewRow implements Command {
    int cellID;
    Row row;
    dataController data;

    public NewRow(int cell, Row r, dataController dc) {
        this.cellID = cell;
        this.row = r;
        this.data = dc;
    }

    @Override
    public void undo() {
        data.getTableList().get(cellID).deleteRow(row);
    }

    @Override
    public void redo() {
        data.getTableList().get(cellID).addRow(row);
    }
}
