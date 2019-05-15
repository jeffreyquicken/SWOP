package SQLQuery;

import Data.*;

import java.util.ArrayList;
import java.util.List;

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
        Table resultTable = new Table("Computed: " + selectedTable.getTableName());
        Column selectedColumn = null;
        Column exprColumn;
        String condition = this.whereClause.getCondition();
        int indexSelectedColumn = -1;
        int indexExprColumn = -1;
        int i =0;
        for (Column column:selectedTable.getColumnNames()){

            if (column.getName().equals(this.selectClause.getSelectClauses().get(0).getAs().getId())){
                selectedColumn = column;
                indexSelectedColumn = i;
            }
            else if(column.getName().equals(this.whereClause.getId())){
                exprColumn = column;
                indexExprColumn = i;
            }
            i++;

        }
        resultTable.addColumn(selectedColumn);
        resultTable.getColumnNames().get(0).setName(this.selectClause.getSelectClauses().get(0).getAs().getAlias());
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
        for (Row row:selectedTable.getTableRows()){
            if (Integer.parseInt(row.getColumnList().get(indexExprColumn).getString()) > Integer.parseInt(condition) ){
                List<Column> addColumns = new ArrayList<>();
                addColumns.add(selectedColumn);
                Row resultRow = new Row(addColumns);
                CellText cell = new CellText(row.getColumnList().get(indexSelectedColumn).getString());
                resultRow.setColumnCell(0, cell);
                resultTable.addRow(resultRow);
            }
        }

        return resultTable;
    }




}
