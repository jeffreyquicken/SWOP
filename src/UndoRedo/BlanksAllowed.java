package UndoRedo;

import Data.dataController;

/**
 * Class that represents the command of enabling/disabling the blanks allowed checkbox
 */
public class BlanksAllowed implements Command {
    // variable keeping the cell id of the checkbox
    int[] cellID;
    //variable containing datacontroller to acces the data
    dataController data;

    /**
     * Initialisez this command with given parameters
     * @param cell Cellid of checkbox
     * @param dc datacontroller to acces the data
     */
    public BlanksAllowed(int[] cell, dataController dc) {
        this.cellID = cell;
        this.data = dc;
    }

    /**
     * undoes this command, changes the checkbox back to its previous value
     */
    @Override
    public void undo() {
        boolean oldValue = data.getTableList().get(cellID[0]).getColumnNames().get(cellID[1]).getBlanksAllowed();
        data.getTableList().get(cellID[0]).getColumnNames().get(cellID[1]).setBlanksAllowed(!oldValue);
    }

    /**
     * redoes this command, changes the checkbox to the value that it had when this command was initialized
     */
    @Override
    public void redo() {
        boolean oldValue = data.getTableList().get(cellID[0]).getColumnNames().get(cellID[1]).getBlanksAllowed();
        data.getTableList().get(cellID[0]).getColumnNames().get(cellID[1]).setBlanksAllowed(!oldValue);
    }
}
