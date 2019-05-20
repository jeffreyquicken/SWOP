package SQLQuery;

public class joinItem {

    private String tableName;
    private String rowID;
    private String cell1;
    private String cell2;
    private String alias1;
    private String alias2;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getRowID() {
        return rowID;
    }

    public void setRowID(String rowID) {
        this.rowID = rowID;
    }

    public String getCell1() {
        return cell1;
    }

    public void setCell1(String cell1) {
        this.cell1 = cell1;
    }

    public String getCell2() {
        return cell2;
    }

    public void setCell2(String cell2) {
        this.cell2 = cell2;
    }



    public String getAlias1() {
        return alias1;
    }

    public void setAlias1(String alias1) {
        this.alias1 = alias1;
    }

    public String getAlias2() {
        return alias2;
    }

    public void setAlias2(String alias2) {
        this.alias2 = alias2;
    }



    public joinItem(String tableName, String rowID, String cell1, String cell2){
        this.tableName = tableName;
        this.rowID = rowID;

        this.alias1 = cell1.split("\\.")[0];
        this.cell1 = cell1.split("\\.")[1];

        this.alias2 = cell2.split("\\.")[0];
        this.cell2 = cell2.split("\\.")[1];
    }
}
