package Data;

import java.util.ArrayList;
import java.util.List;

import UndoRedo.Command;
import UndoRedo.Operations;
import settings.CellVisualisationSettings;

/**
 * Class that controls the data (add & delete tables, get selected tables, ...)
 */
public class dataController {

    /**
     * List with all tables
     */
    private List<Table> tableList;

    /**
     * The selected table
     */
    private Table selectedTable;

    /**
     * Settings file for the visualisation of settings
     */
    private CellVisualisationSettings setting;

    // List of operations for undo/redo functionality
    Operations operations;

    public Table getSelectedTable() {
        return selectedTable;
    }

    public void setSelectedTable(Table selectedTable) {
        this.selectedTable = selectedTable;
    }

    public dataController(){
        setting = new CellVisualisationSettings();
        //setting.getWidthList().add(setting.getDefaultWidth());
        setting.getWidthList().add(setting.getDefaultWidth());
        setting.getWidthList().add(setting.getDefaultWidth());
        tableList = new ArrayList<>();



        tableList = new ArrayList<Table>();
        this.addTable();
        this.addTable();
        this.addTable();

        operations = new Operations();
    }

    public dataController(int i) {
        setting = new CellVisualisationSettings();
        //setting.getWidthList().add(setting.getDefaultWidth());
        setting.getWidthList().add(setting.getDefaultWidth());
        setting.getWidthList().add(setting.getDefaultWidth());
        tableList = new ArrayList<>();

        tableList = new ArrayList<Table>();

        operations = new Operations();
    }

    public List<Table> getTableList(){
        return tableList;
    }

    /**
     * Method that adds a new table to the table list and gives it a valid name
     */
    public void addTable(){
        int numberOfTable = this.getTableList().size() + 1;
        String newName = "Table" + numberOfTable;
        int i = numberOfTable;
        while (!isValidName(newName, null)){
            i++;
            newName = "Table" + i;
        }
        //Table newTable = new Table(newName);
        Table newTable = new Table(newName);
        tableList.add(newTable);
    }
    /**
     * Checks if updated text is valid
     *
     * @param text text to be validated
     * @param currName old name
     * @return Wheter the name of the table is valid (hence unique and non-empty)
     */
    public boolean isValidName(String text, String currName) {
        for (Table table : this.getTableList()) {
            if (table.getTableName().equals(text)) {
                if (!table.getTableName().equals(currName)) {
                    return false;
                }
            }
        }
        if (text.length() == 0) {
            return false;
        }
        return true;
    }

    /**
     * Method that deletes a table from the table list
     * @param table the table to be deleted
     */
    public void deleteTable(Table table){
        tableList.remove(table);
    }

    /**
     * Method that returns the lowest Y coordinate based on how many tables there are
     * and the set height for these tables
     * @return the lowest Y coordinate
     */
    public int getLowestY(int i){
        int lowestY = i;
        try{
        for (Table table : this.getTableList()){
            lowestY += 20;
        }
            return lowestY;
        }catch (Exception e){
            return lowestY;
        }


    }

    /**
     * Method that computes a table based on a given query
     * @param query the query to be used when computing the table
     * @return the computed table
     */
    public Table computeTable(String query){
       Table table = new Table("computed table: " + selectedTable.getTableName());
       return table;
    }

    public CellVisualisationSettings getSetting(){
        return setting;
    }

    // adds command to operations class
    public void addCommand(Command c) {
        operations.add(c);
    }

    public void undo() {
        operations.undo();
    }

    public void redo() {
        operations.redo();
    }



}
