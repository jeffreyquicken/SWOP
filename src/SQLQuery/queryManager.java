package SQLQuery;

import Data.Column;
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

    /**
     * List of all collumns that are currently used in Queries
     */
    private  List<Column> queryDependendCollumns = new ArrayList<>();
    /**
     * Add new column to querydependColumns
     * @param col
     */
    public void addQueryDependendColumn(Column col){
        queryDependendCollumns.add(col);

    }
    /**
     * Method that returns all columns that are depenend to a query
     * @return query
     */
    public List<Column> getQueryDependColumns(){
        return queryDependendCollumns;
    }

    /**
     * Method that adds query depenend columns from a query
     * @param query query
     */
    public void addQueryDependendColumnsFromQuery(Query query){
        for(Column col: query.getColumns()){
            this.addQueryDependendColumn(col);
        }
    }
    /**
     * Method that adds query depenend tables from a query
     * @param query query
     */
    public void addQueryDependendTablesFromQuery(Query query){
        for(Table table: query.getTables()){
            this.addQueryDependendTable(table);
        }
    }


}
