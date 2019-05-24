package UndoRedo;

import Data.Cell;
import Data.dataController;

/**
 * class representing the default value changed
 */
public class DefaultValue implements Command {
    // variables needed to implement the undo/redo operations
    int[] cellID;
    Cell newValue;
    Cell oldValue;
    dataController data;

    /**
     * initializes new command that represents a changed default value
     *
     * @param cell id of the collumn where the default value was changed
     * @param nv   the new value
     * @param ov   the old value
     * @param dc   data controller
     */
    public DefaultValue(int[] cell, Cell nv, Cell ov, dataController dc) {
        this.cellID = cell;
        this.newValue = nv;
        this.oldValue = ov;
        this.data = dc;
    }

    /**
     * method that reverts the default value back to its original value
     */
    @Override
    public void undo() {
        data.getTableList().get(cellID[0]).getColumnNames().get(cellID[1]).setDefaultV(oldValue);
    }

    /**
     * method that redoes this command, changing the default value back to its new value
     */
    @Override
    public void redo() {
        data.getTableList().get(cellID[0]).getColumnNames().get(cellID[1]).setDefaultV(newValue);
    }
}

