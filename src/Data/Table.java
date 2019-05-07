package Data;

import java.util.ArrayList;
import java.util.List;

import settings.CellVisualisationSettings;

public class Table {
    private String tableName;
    private List<Row> tableRows;
    private List<Column> columnNames;
    private CellVisualisationSettings rowSetting;
    private CellVisualisationSettings designSetting;
    /**
     * Query string that affects the table.
     */
    private String query;


    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    public List<Column> getColumnNames(){return columnNames;}
    public List<Row> getTableRows() {
        return tableRows;
    }
    public void addRow(Row row){
        tableRows.add(row);
    }
    public void deleteRow(Row row){
        tableRows.remove(row);
    }



    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }


    /**
     * Initiates a table object with a given name and creates th right settings objects for that table
     * @param name  the name of the table
     */
    public Table(String name){
        tableRows = new ArrayList<Row>();
        columnNames = new ArrayList<Column>();
        tableName = name;
        rowSetting = new CellVisualisationSettings();
        designSetting = new CellVisualisationSettings();
        for(int i = 0; i< 4;i++ ){
            designSetting.getWidthList().add(designSetting.getDefaultWidth());
        }
        query = "";
    }

    /**
     *
     * @return
     */
    public CellVisualisationSettings getRowSetting() {
        return rowSetting;
    }

    /**
     * Adds a collumn to table
     * @param column
     */
    public void addColumn(Column column){


        columnNames.add(column);
        for(Row row: tableRows){
            row.addColumn(column.getDefaultV());
        }
        int defaultWidth = rowSetting.getDefaultWidth();
        rowSetting.getWidthList().add(defaultWidth);
    }



    /**
     * Deletes a column
     * @param column
     */
    public void deleteColumn(Column column){
        columnNames.remove(column);
    }


    public String getTableName() {
        return tableName;
    }

    public int getLengthTable() {
        int i = this.tableRows.size() * 20;
        return i;

    }

    public CellVisualisationSettings getDesignSetting() {
        return designSetting;
    }


}
