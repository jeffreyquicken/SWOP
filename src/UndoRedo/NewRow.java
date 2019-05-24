package UndoRedo;

import Data.Row;
import Data.dataController;

/**
 * class that represents the command of adding a new row
 */
public class NewRow implements Command {
    // variables needed to implement undo/redo operations
    int cellID;
    Row row;
    dataController data;

    /**
     * initialises new command representing the add new row
     *
     * @param cell the table where to row is to be added
     * @param r    the row that was added
     * @param dc   data controller
     */
    public NewRow(int cell, Row r, dataController dc) {
        this.cellID = cell;
        this.row = r;
        this.data = dc;
    }

    /**
     * undoes the add row command, thus removes the row
     */
    @Override
    public void undo() {
        data.getTableList().get(cellID).deleteRow(row);
    }

    /**
     * redoes the add row command by adding the row again
     */
    @Override
    public void redo() {
        data.getTableList().get(cellID).addRow(row);
    }
}
