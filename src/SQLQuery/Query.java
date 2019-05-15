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
        Column exprColumn;
        for (Column column:selectedTable.getColumnNames()){
            if (column.getName().equals(this.selectClause.getSelectClauses().get(0).getAs().getId())){
                selectedColumn = column;
            }
            else if(column.getName().equals(this.whereClause.getId())){
                exprColumn = column;
            }
            else{
                return null;
            }
        }
        switch (this.whereClause.getOperator()) {
            case "<":
                System.out.println("> - operator");
            case ">":
                System.out.println("< - operator");
            case "=":
                System.out.println("= - operator");
            case "OR":
                System.out.println("OR - operator");
            case "AND":
                System.out.println("AND - operator");
            case "+":
                System.out.println("+ - operator");
            case "-":
                System.out.println("- - operator");

        }


        return null;
    }




}
