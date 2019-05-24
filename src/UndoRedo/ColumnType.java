package UndoRedo;

import Data.dataController;

/**
 * Class that represents the command to change a column type
 */
public class ColumnType implements Command {
    // variables needed to implement the undo/redo operations
    int[] cellID;
    String newValue;
    String oldValue;
    dataController data;

    /**
     * initialises this command with the collumn of the type change, the previous type, the new type, and the data controller
     *
     * @param cell id of col
     * @param nv   the new type
     * @param ov   the old type
     * @param dc   data controller
     */
    public ColumnType(int[] cell, String nv, String ov, dataController dc) {
        this.cellID = cell;
        this.newValue = nv;
        this.oldValue = ov;
        this.data = dc;
    }

    /**
     * undoes the type change, reverts type back to its value befor this command
     */
    @Override
    public void undo() {
        data.getTableList().get(cellID[0]).getColumnNames().get(cellID[1]).setType(oldValue);
    }

    /**
     * redoes the type change, changes type back to the new value
     */
    @Override
    public void redo() {
        data.getTableList().get(cellID[0]).getColumnNames().get(cellID[1]).setType(newValue);
    }
}
