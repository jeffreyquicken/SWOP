package SQLQuery;

import java.util.ArrayList;
import java.util.List;

public class selectClause {
    private List<selectItem> selectClauses = new ArrayList<>();

    public  List<selectItem> getSelectClauses(){

        return selectClauses;

    }

    /**
     * Method that adds select item to select clause with its corresponding alias
     * @param id the id of the select clause (SELECT ID AS ALIAS)
     * @param alias the alias of the select clause (SELECT ID AS ALIAS)
     */
    public void addSelectItem(String tableName, String id, String alias){
        asItem asItem = new asItem(id, alias);
        selectItem selectItem = new selectItem(tableName, asItem);
        selectClauses.add(selectItem);
    }




}
