package UndoRedo;

import Data.dataController;

/**
 * Class that represents the command for changing the name of the collumn
 */
public class ColumnName implements Command {
    //variables needed to perform undo/redo operations
    int[] cellID;
    String newValue;
    String oldValue;
    dataController data;

    /**
     * Initiaalize new command with the old name, new name, collumn to xich names belong and the data controller to change this information
     * @param cell Place where collumn name was changed
     * @param nv New Name
     * @param ov Old Name
     * @param dc datacontroller
     */
    public ColumnName(int[] cell, String nv, String ov, dataController dc) {
        this.cellID = cell;
        this.newValue = nv;
        this.oldValue = ov;
        this.data = dc;
    }

    /**
     * undoes the name change, sets name back to old value
     */
    @Override
    public void undo() {
        data.getTableList().get(cellID[0]).getColumnNames().get(cellID[1]).setName(oldValue);
    }

    /**
     * redoes name change, sets name to the new value
     */
    @Override
    public void redo() {
        data.getTableList().get(cellID[0]).getColumnNames().get(cellID[1]).setName(newValue);
    }
}
