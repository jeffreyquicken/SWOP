package SQLQuery;

import Data.Table;
import Data.dataController;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for from clauses of a query
 */
public class fromClause {

    /**
     * List with asItems for queries with multiple AS statements
     */
    private List<asItem> fromClauses = new ArrayList<>();
    public  List<asItem> getFromClauses(){

        return fromClauses;

    }

    /**
     * Method that adds select item to from clause with its corresponding alias
     * @param id the id of the from clause (FROM ID AS ALIAS)
     * @param alias the alias of the select from (FROM ID AS ALIAS)
     */
    public void addFromClause(String id, String alias){
        asItem item = new asItem(id, alias);
        fromClauses.add(item);
    }

    public Table getTable(dataController data) throws IllegalArgumentException{
        for (Table table:data.getTableList()){
            if (table.getTableName().equals(this.fromClauses.get(0).getId())){
                return table;
            }
        }
        return null;
    }

}
