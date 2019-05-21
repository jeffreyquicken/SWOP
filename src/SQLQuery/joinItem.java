package SQLQuery;

/**
 * A class of join items involving the necessary attributes to represent a join clause
 * such as the table name, row id, cells and aliases
 */
public class joinItem {

    /**
     * $tableName$ INNER JOIN rowid ON cell1.alias1 = cell2.alias2
     */
    private String tableName;

    /**
     * tableName INNER JOIN $rowid$ ON cell1.alias1 = cell2.alias2
     */
    private String rowID;

    /**
     * tableName INNER JOIN rowid ON $cell1$.alias1 = cell2.alias2
     */
    private String cell1;

    /**
     * tableName INNER JOIN rowid ON cell1$.alias1 = $cell2$.alias2
     */
    private String cell2;

    /**
     * tableName INNER JOIN rowid ON cell1.$alias1$ = cell2.alias2
     */
    private String alias1;

    /**
     * tableName INNER JOIN rowid ON cell1.alias1 = cell2.$alias2$
     */
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


    /**
     * Constructor for join item that splits the given strings into the proper attributes
     * @param tableName the tablename
     * @param rowID row id
     * @param cell1 cell1
     * @param cell2 cell 2
     */
    public joinItem(String tableName, String rowID, String cell1, String cell2){
        this.tableName = tableName;
        this.rowID = rowID;

        this.alias1 = cell1.split("\\.")[0];
        this.cell1 = cell1.split("\\.")[1];

        this.alias2 = cell2.split("\\.")[0];
        this.cell2 = cell2.split("\\.")[1];
    }
}
