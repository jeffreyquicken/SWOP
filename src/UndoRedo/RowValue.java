package UndoRedo;

import Data.Cell;
import Data.dataController;

/**
 * class representing the command of changing a row value
 */
public class RowValue implements Command {
    // variables needed to support undo/redo operations
    int[] cellID;
    Cell newValue;
    Cell oldValue;
    dataController data;

    /**
     * initializes new row value command, representing a change in the value of a row
     *
     * @param cell the id of the cell where the value was changed
     * @param nv   the new value
     * @param ov   the old value
     * @param dc   data controller
     */
    public RowValue(int[] cell, Cell nv, Cell ov, dataController dc) {
        this.cellID = cell;
        this.newValue = nv;
        this.oldValue = ov;
        this.data = dc;
    }

    /**
     * undoes the rowvalue command, sets the value of the cell back to its previous value
     */
    @Override
    public void undo() {
        data.getTableList().get(cellID[0]).getTableRows().get(cellID[1]).getColumnList().set(cellID[2], oldValue);
    }

    /**
     * redoes the command, setting the value back to its new value
     */
    @Override
    public void redo() {
        data.getTableList().get(cellID[0]).getTableRows().get(cellID[1]).getColumnList().set(cellID[2], newValue);
    }
}
