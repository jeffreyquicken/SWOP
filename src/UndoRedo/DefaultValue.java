package UndoRedo;

import Data.Cell;
import Data.dataController;

public class DefaultValue implements Command {
    int[] cellID;
    Cell newValue;
    Cell oldValue;
    dataController data;

    public DefaultValue(int[] cell, Cell nv, Cell ov, dataController dc) {
        this.cellID = cell;
        this.newValue = nv;
        this.oldValue = ov;
        this.data = dc;
    }

    @Override
    public void undo() {
        data.getTableList().get(cellID[0]).getColumnNames().get(cellID[1]).setDefaultV(oldValue);
    }

    @Override
    public void redo() {
        data.getTableList().get(cellID[0]).getColumnNames().get(cellID[1]).setDefaultV(newValue);
    }
}

