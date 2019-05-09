package UndoRedo;

import Data.dataController;

public class BlanksAllowed implements Command {
    int[] cellID;
    dataController data;

    public BlanksAllowed(int[] cell, dataController dc) {
        this.cellID = cell;
        this.data = dc;
    }

    @Override
    public void undo() {
        boolean oldValue = data.getTableList().get(cellID[0]).getColumnNames().get(cellID[1]).getBlanksAllowed();
        data.getTableList().get(cellID[0]).getColumnNames().get(cellID[1]).setBlanksAllowed(!oldValue);
    }

    @Override
    public void redo() {
        boolean oldValue = data.getTableList().get(cellID[0]).getColumnNames().get(cellID[1]).getBlanksAllowed();
        data.getTableList().get(cellID[0]).getColumnNames().get(cellID[1]).setBlanksAllowed(!oldValue);
    }
}
