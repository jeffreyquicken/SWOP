package Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import Data.Column;

public class Table {
    private String tableName;
    private List<Row> tableRows;
    private List<Column> columnNames;

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

        Column col1 = new Column("Col 1");
        Column col2= new Column("Col 2");
        Column col3 = new Column("Col 3");
        Column col4 = new Column("Col 4");
        this.addCollumn(col1);
        this.addCollumn(col2);
        this.addCollumn(col3);
        this.addCollumn(col4);

        Row row1 = new Row("Row1" + tableName);
        Row row2 = new Row("Row2" + tableName);
        Row row3 = new Row("Row3" + tableName);

        this.addRow(row1);
        this.addRow(row2);
        this.addRow(row3);
    }

    /**
     * Adds a collum to table
     * @param column
     */
    public void addCollumn(Column column){
        columnNames.add(column);
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


}
