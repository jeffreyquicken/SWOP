package SQLQuery;

import java.util.ArrayList;
import java.util.List;

public class asClause {

    private List<asItem> asClauses = new ArrayList<>();

    public List<asItem> getAsClauses() {
        return asClauses;
    }

    public void addAsClause(String id, String as){
        asItem asItem = new asItem(id, as);
        asClauses.add(asItem);

    }





}
