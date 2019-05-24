package UndoRedo;

import Data.Table;
import Data.dataController;

/**
 * class that represents the command of adding a new table
 */
public class NewTable implements Command {
    // variables needed to implement undo/redo operations
    Table table;
    dataController data;

    /**
     * initialises new command representing the add new table
     *
     * @param t  the table that was added
     * @param dc data controller
     */
    public NewTable(Table t, dataController dc) {
        this.table = t;
        this.data = dc;
    }

    /**
     * undoes the add table command, removes the table from table list
     */
    @Override
    public void undo() {
        data.getTableList().remove(table);
    }

    /**
     * redoes the add table command, adds the table to the table list
     */
    @Override
    public void redo() {
        data.getTableList().add(table);
    }

}
