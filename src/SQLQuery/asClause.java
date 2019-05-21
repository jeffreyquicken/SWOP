package SQLQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for as clauses of a query
 */
public class asClause {

    /**
     * List with asItems for queries with multiple AS statements
     */
    private List<asItem> asClauses = new ArrayList<>();

    public List<asItem> getAsClauses() {
        return asClauses;
    }

    /**
     * Method that adds an AS item to the list with AS clauses
     * @param id the id of the AS clause
     * @param as the alias of the AS clause
     */
    public void addAsClause(String id, String as){
        asItem asItem = new asItem(id, as);
        asClauses.add(asItem);

    }





}
