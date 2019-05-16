package SQLQuery;

import Data.Table;
import Data.dataController;

/**
 * Class for select items. It stores the table name, column id and column alias.
 */
public class selectItem {


    private String tableName;
    private asItem as;

    public selectItem(String tableName, asItem as){
        this.tableName = tableName;
        this.as = as;

    }

    public String getTableName() {
        return tableName;
    }
    public Table getTable(dataController data){
       for (Table table:data.getTableList()){
           if (table.getTableName().equals(this.getTableName())){
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
