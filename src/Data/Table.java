package Data;

import java.util.ArrayList;
import java.util.List;

import SQLQuery.Query;
import settings.CellVisualisationSettings;

import static SQLQuery.SQLParser.parseQuery;

/**
 * Class for tables
 */
public class Table {
    private String tableName;
    /**
     * Rows of the table
     */
    private List<Row> tableRows;
    /**
     * List with columns
     */
    private List<Column> columnNames;
    /**
     * settings for row module
     */
    private CellVisualisationSettings rowSetting;
    /**
     * settings for design module
     */
    private CellVisualisationSettings designSetting;
    /**
     * settings for form module
     */
    private CellVisualisationSettings formSetting;
    /**
     * Query string that affects the table.
     */
    private String query;

    public Query getLastQuery() {
        return lastQuery;
    }

    public void setLastQuery(Query lastQuery) {
        this.lastQuery = lastQuery;
    }

    /**
     * Last known query to a table
     */
   private Query lastQuery;


    public CellVisualisationSettings getFormSetting() {
        return formSetting;
    }

    public void setFormSetting(CellVisualisationSettings formSetting) {
        this.formSetting = formSetting;
    }

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
     * @effect initializes the Table with the given name and standard values
     */
    public Table(String name){
        tableRows = new ArrayList<Row>();
        columnNames = new ArrayList<Column>();
        tableName = name;
        rowSetting = new CellVisualisationSettings();
        designSetting = new CellVisualisationSettings();
        formSetting = new CellVisualisationSettings();
        for (int i = 0; i<2;i++){
            formSetting.getWidthList().add(formSetting.getDefaultWidth());
        }
        for(int i = 0; i< 4;i++ ){
            designSetting.getWidthList().add(designSetting.getDefaultWidth());
        }
        query = "";
    }

    /**
     * Method that gets the row setting
     * @return the row setting
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

    /**
     * Method that computes a table from a Query
     * @param data datacontroller
     * @return computed table
     */
    public Table getComputedTable(dataController data){
        //Query query = parseQuery("SELECT movie.title AS title FROM movies AS movie WHERE movie.imdb_score < 7");
        Query query = parseQuery(this.getQuery());
        this.setLastQuery(query);
        Table computedTable = query.getComputedTable(data);
        System.out.println(computedTable);
        data.getTableList().add(computedTable);
        return computedTable;

    }



}
