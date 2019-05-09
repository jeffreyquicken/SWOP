package UndoRedo;

import Data.dataController;

public class ColumnName implements Command {
    int[] cellID;
    String newValue;
    String oldValue;
    dataController data;

    public ColumnName(int[] cell, String nv, String ov, dataController dc) {
        this.cellID = cell;
        this.newValue = nv;
        this.oldValue = ov;
        this.data = dc;
    }

    @Override
    public void undo() {
        data.getTableList().get(cellID[0]).getColumnNames().get(cellID[1]).setName(oldValue);
    }

    @Override
    public void redo() {
        data.getTableList().get(cellID[0]).getColumnNames().get(cellID[1]).setName(newValue);
    }
}
