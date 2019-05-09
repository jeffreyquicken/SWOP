package UndoRedo;

import Data.dataController;

public class ColumnType implements Command {
    int[] cellID;
    String newValue;
    String oldValue;
    dataController data;

    public ColumnType(int[] cell, String nv, String ov, dataController dc) {
        this.cellID = cell;
        this.newValue = nv;
        this.oldValue = ov;
        this.data = dc;
    }

    @Override
    public void undo() {
        data.getTableList().get(cellID[0]).getColumnNames().get(cellID[1]).setType(oldValue);
    }

    @Override
    public void redo() {
        data.getTableList().get(cellID[0]).getColumnNames().get(cellID[1]).setType(newValue);
    }
}
