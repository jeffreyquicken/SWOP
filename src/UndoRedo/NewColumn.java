package UndoRedo;

import Data.Column;
import Data.dataController;

public class NewColumn implements Command {
    int cellID;
    Column col;
    dataController data;

    public NewColumn(int cell, Column c, dataController dc) {
        this.cellID = cell;
        this.col = c;
        this.data = dc;
    }

    @Override
    public void undo() {
        data.getTableList().get(cellID).deleteColumn(col);
    }

    @Override
    public void redo() {
        data.getTableList().get(cellID).addColumn(col);
    }
}
