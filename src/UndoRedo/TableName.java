package UndoRedo;

import Data.dataController;

public class TableName implements Command {
    int cellID;
    String newValue;
    String oldValue;
    dataController data;

    public TableName(int cell, String nv, String ov, dataController dc) {
        this.cellID = cell;
        this.newValue = nv;
        this.oldValue = ov;
        this.data = dc;
    }

    @Override
    public void undo() {
        data.getTableList().get(cellID).setTableName(oldValue);
    }

    @Override
    public void redo() {
        data.getTableList().get(cellID).setTableName(newValue);
    }
}
