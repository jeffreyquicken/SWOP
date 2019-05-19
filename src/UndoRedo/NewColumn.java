package UndoRedo;

import Data.Column;
import Data.dataController;

/**
 * class that represents the command of adding a new column
 */
public class NewColumn implements Command {
    // variables needed to implement undo/redo operations
    int cellID;
    Column col;
    dataController data;

    /**
     * inintializes new command representing the add new column
     * @param cell the table where to collumn is to be added
     * @param c the column that was added
     * @param dc data controller
     */
    public NewColumn(int cell, Column c, dataController dc) {
        this.cellID = cell;
        this.col = c;
        this.data = dc;
    }

    /**
     * undoes the add collumn command, thus removes the collumn
     */
    @Override
    public void undo() {
        data.getTableList().get(cellID).deleteColumn(col);
    }

    /**
     * redoes the add collumn command by adding the column again
     */
    @Override
    public void redo() {
        data.getTableList().get(cellID).addColumn(col);
    }
}
