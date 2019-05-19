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

    /**
     * initializes the datacontroller
     * 
     * @effect the standard values of the dataController are set and three empty lists are created for demonstration purposes
     * 		   |new.setting == new CellVisualisationSettings()
     * 		   |new.tableList == new ArrayList()
     * 		   |this.addTable();
     *         |this.addTable();
     * 		   |this.addTable();
     *         |operations = new Operations();
     */	
    public dataController(){
        setting = new CellVisualisationSettings();
        //setting.getWidthList().add(setting.getDefaultWidth());
        setting.getWidthList().add(setting.getDefaultWidth());
        setting.getWidthList().add(setting.getDefaultWidth());
        //tableList = new ArrayList<>();

        tableList = new ArrayList<Table>();
        this.addTable();
        this.addTable();
        this.addTable();

        operations = new Operations();
    }

    /**
     * initializes the datacontroller
     * 
     * @effect the standard values of the dataController are set 
     * 		   |new.setting == new CellVisualisationSettings()
     * 		   |new.tableList == new ArrayList()
     *         |operations = new Operations();
     */	
    public dataController(int i) {
        setting = new CellVisualisationSettings();
        //setting.getWidthList().add(setting.getDefaultWidth());
        setting.getWidthList().add(setting.getDefaultWidth());
        setting.getWidthList().add(setting.getDefaultWidth());
        //tableList = new ArrayList<>();

        tableList = new ArrayList<Table>();

        operations = new Operations();
    }

    public List<Table> getTableList(){
        return tableList;
    }

    /**
     * Method that adds a new table to the table list and gives it a valid name
     * 
     * @effect creates a new table with a valid name and adds it to the list
     * 		 | new.newTable = new Table (Name) && isValidName(Name)
     * 		 | tableList.add(newTable)
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
     * @pre The given table is a valid table. if so, it is in the list.
     * @post The given table is removed from the table list
     * 	   | tableList.remove(table)
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
