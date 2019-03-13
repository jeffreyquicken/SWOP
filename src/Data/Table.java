package Data;

import java.util.ArrayList;
import java.util.List;

import settings.settings;

public class Table {
    private String tableName;
    private List<Row> tableRows;
    private List<Column> columnNames;
    private settings setting;
    private settings designSetting;

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

    public Table(String name){
        tableRows = new ArrayList<Row>();
        columnNames = new ArrayList<Column>();
        tableName = name;
        setting = new settings(4); //Todo: start with empty tableview
        designSetting = new settings(4);

        Column col1 = new Column("Column1","", "String", true);
        Column col2= new Column("Column2","", "String", true);
        Column col3 = new Column("Column3", "", "String", true);
        Column col4 = new Column("Column4","", "String", true);
        this.addColumn(col1);
        this.addColumn(col2);
        this.addColumn(col3);
        this.addColumn(col4);

        Row row1 = new Row("Row1" + tableName);
        Row row2 = new Row("Row2" + tableName);
        Row row3 = new Row("Row3" + tableName);

        this.addRow(row1);
        this.addRow(row2);
        this.addRow(row3);
    }

    /**
     *
     * @return
     */
    public settings getSetting() {
        return setting;
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




    }

    /**
     * Deletes a column
     * @param column
     */
    public void deleteCollumn(Column column){
        columnNames.remove(column);
    }


    public String getTableName() {
        return tableName;
    }

    public int getLengthTable() {
        int i = this.tableRows.size() * 20;
        return i;

    }

    public settings getDesignSetting() {
        return designSetting;
    }


}
