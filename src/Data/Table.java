package Data;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private String tableName;
    private List<Row> tableRows;

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<Row> getTableRows() {
        return tableRows;
    }
    public void addRow(Row row){
        tableRows.add(row);
    }

    public Table(String name){
        tableRows = new ArrayList<Row>();
        tableName = name;
        Row row1 = new Row("Row1" + tableName);
        Row row2 = new Row("Row2" + tableName);
        Row row3 = new Row("Row3" + tableName);
        this.addRow(row1);
        this.addRow(row2);
        this.addRow(row3);
    }

    public String getTableName() {
        return tableName;
    }


}
