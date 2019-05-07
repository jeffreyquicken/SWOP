package Data;

import java.util.ArrayList;
import java.util.List;
import settings.CellVisualisationSettings;

public class dataController {
    private List<Table> tableList;
    private Table selectedTable;
    private CellVisualisationSettings setting;

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
    }

    public dataController(int i) {
        setting = new CellVisualisationSettings();
        //setting.getWidthList().add(setting.getDefaultWidth());
        setting.getWidthList().add(setting.getDefaultWidth());
        setting.getWidthList().add(setting.getDefaultWidth());
        tableList = new ArrayList<>();

        tableList = new ArrayList<Table>();

    }

    public List<Table> getTableList(){
        return tableList;
    }

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





}
