package UndoRedo;

import Data.dataController;

/**
 * class representing the command for changing a table name
 */
public class TableName implements Command {
    // variables needed to implement the undo and redo operations
    int cellID;
    String newValue;
    String oldValue;
    dataController data;

    /**
     * initializes new table name change command
     * @param cell id of the table
     * @param nv new name
     * @param ov old name
     * @param dc data controller
     */
    public TableName(int cell, String nv, String ov, dataController dc) {
        this.cellID = cell;
        this.newValue = nv;
        this.oldValue = ov;
        this.data = dc;
    }

    /**
     * undoes the table name change, setting the name back to its old value
     */
    @Override
    public void undo() {
        data.getTableList().get(cellID).setTableName(oldValue);
    }

    /**
     * redoes the table name change, setting the name back to its new value
     */
    @Override
    public void redo() {
        data.getTableList().get(cellID).setTableName(newValue);
    }
}
