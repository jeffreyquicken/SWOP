package UndoRedo;

import Data.Cell;
import Data.dataController;

public class RowValue implements Command {
    int[] cellID;
    Cell newValue;
    Cell oldValue;
    dataController data;

    public RowValue(int[] cell, Cell nv, Cell ov, dataController dc) {
        this.cellID = cell;
        this.newValue = nv;
        this.oldValue = ov;
        this.data = dc;
    }

    @Override
    public void undo() {
        data.getTableList().get(cellID[0]).getTableRows().get(cellID[1]).getColumnList().set(cellID[2],oldValue);
    }

    @Override
    public void redo() {
        data.getTableList().get(cellID[0]).getTableRows().get(cellID[1]).getColumnList().set(cellID[2], newValue);
    }
}
