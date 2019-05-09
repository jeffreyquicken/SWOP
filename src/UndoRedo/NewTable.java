package UndoRedo;

import Data.Table;
import Data.dataController;

public class NewTable implements Command {
    int cellID;
    Table table;
    dataController data;

    public NewTable(int cell, Table t, dataController dc) {
        this.cellID = cell;
        this.table = t;
        this.data = dc;
    }

    @Override
    public void undo() {
        data.getTableList().remove(table);
    }

    @Override
    public void redo() {
        data.getTableList().add(table);
    }

}
