package SQLQuery;

import Data.Table;
import Data.dataController;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for select clauses
 */
public class joinClause {

    /**
     * List with joinItems for querie with multiple INNER JOIN
     */
    private List<joinItem> joinItems = new ArrayList<>();

    public List<joinItem> getJoinItems() {

        return joinItems;

    }

    /**
     * Method that adds a joinItem to the joinClause
     *
     * @param tableName the table name
     * @param id        the row id
     * @param cell1     cell 1 of join
     * @param cell2     cell of join
     */
    public void addJoinItem(String tableName, String id, String cell1, String cell2) {
        joinItems.add(new joinItem(tableName, id, cell1, cell2));
    }

    /**
     * Method that returns the table to which the join clause refers
     *
     * @param data     datacontroller
     * @param joinItem the join item
     * @return the table on which the join should be executed
     */
    public Table getTable(dataController data, joinItem joinItem) {
        for (Table table : data.getTableList()) {
            if (table.getTableName().equals(joinItem.getTableName())) {
                return table;
            }
        }
        return null;
    }


}
