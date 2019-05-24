package SQLQuery;

import Data.Table;
import Data.dataController;

/**
 * Class for select items. It stores the table name, column id and column alias.
 */
public class selectItem {

    /**
     * The table name
     */
    private String tableName;

    /**
     * The as item belonging to the select clause (SELECT $x AS y$)
     */
    private asItem as;

    /**
     * Constructor for the select item
     *
     * @param tableName the table name
     * @param as        the as item
     */
    public selectItem(String tableName, asItem as) {
        this.tableName = tableName;
        this.as = as;

    }

    public String getTableName() {
        return tableName;
    }

    /**
     * Method that returns the table to which the select item refers
     *
     * @param data the datacontroller
     * @return the table on which the select should be executed
     */
    public Table getTable(dataController data) {
        for (Table table : data.getTableList()) {
            if (table.getTableName().equals(this.getTableName())) {
                return table;
            }
        }
        return null;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public asItem getAs() {
        return as;
    }

    public void setAs(asItem as) {
        this.as = as;
    }


}
