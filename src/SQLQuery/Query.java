package SQLQuery;

import Data.Column;
import Data.Table;
import Data.dataController;

/**
 * Class with query attributes
 */
public class Query {

    private fromClause fromClause = new fromClause();
    private selectClause selectClause = new selectClause();
    private whereClause whereClause = new whereClause();







    public SQLQuery.fromClause getFromClause() {
        return fromClause;
    }

    public void setFromClause(SQLQuery.fromClause fromClause) {
        this.fromClause = fromClause;
    }

    public SQLQuery.selectClause getSelectClause() {
        return selectClause;
    }

    public void setSelectClause(SQLQuery.selectClause selectClause) {
        this.selectClause = selectClause;
    }

    public SQLQuery.whereClause getWhereClause() {
        return whereClause;
    }

    public void setWhereClause(SQLQuery.whereClause whereClause) {
        this.whereClause = whereClause;
    }


    public Table getComputedTable(dataController data){
        Table selectedTable = this.fromClause.getTable(data);
        Column selectedColumn;
        for (Column column:selectedTable.getColumnNames()){
            if (column.getName().equals(this.selectClause.getSelectClauses().get(0).getAs().getId())){
                selectedColumn = column;
            }
            else{
                return null;
            }
        }
        Column exprColumn;
    }




}
