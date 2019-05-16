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

    public void addAsClause(String id, String as){
        asItem asItem = new asItem(id, as);
        asClauses.add(asItem);

    }





}
