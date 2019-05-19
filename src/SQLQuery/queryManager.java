package SQLQuery;

import Data.Table;

import java.util.ArrayList;
import java.util.List;

public class queryManager {
    /**
     * List of all tables that are currently used in Queries
     */
    private List<Table> queryDependTables = new ArrayList<>();

    /**
     * Add new table to querydependTables
     * @param table
     */
    public void addQueryDependendTable(Table table){
        queryDependTables.add(table);

    }

    /**
     * Method that returns all tables that are depenend to a query
     * @return query
     */
    public List<Table> getQueryDependTables(){
        return queryDependTables;
    }
}
