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
        Table resultTable = new Table("Computed: ");
        List<Column> selectedColumn = new ArrayList<>();
        Column exprColumn;
        String condition = this.whereClause.getCondition();
        List<Integer> indexSelectedColumn = new ArrayList<>();
        int indexExprColumn = -1;
        int i =0;
        for (Column column:selectedTable.getColumnNames()){
            for (int j = 0; j<this.selectClause.getSelectClauses().size(); j++){
                if (column.getName().equals(this.selectClause.getSelectClauses().get(j).getAs().getId())){
                    Column col = new Column(column.getName(), column.getDefaultV(), column.getType(), column.getBlanksAllowed());
                    selectedColumn.add(col);
                    indexSelectedColumn.add(i);
                }

            }
            if(column.getName().equals(this.whereClause.getId())){
                exprColumn = new Column(column.getName(), column.getDefaultV(), column.getType(), column.getBlanksAllowed());
                indexExprColumn = i;
            }
            i++;

        }
        int j = 0;
        for (Column addCol:selectedColumn) {
            resultTable.addColumn(addCol);
            resultTable.getColumnNames().get(j).setName(this.selectClause.getSelectClauses().get(j).getAs().getAlias());
            j++;
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
        for (Row row:selectedTable.getTableRows()){
            String leftExpr = row.getColumnList().get(indexExprColumn).getString();
            String rightExpr = condition;
            if (isNumeric(leftExpr) && isNumeric(rightExpr) && Integer.parseInt(leftExpr) > Integer.parseInt(condition) ){
                List<Column> addColumns = new ArrayList<>();
                int k = 0;
                for (Column addCol:selectedColumn) {
                    addColumns.add(addCol);
                    k++;
                }
                Row resultRow = new Row(addColumns);
                int m = 0;
                for (int l:indexSelectedColumn) {
                    CellText cell = new CellText(row.getColumnList().get(l).getString());
                    resultRow.setColumnCell(m, cell);
                    m++;
                }
                resultTable.addRow(resultRow);

            }
        }

        return resultTable;
    }

    /**
     * Method to check if a string is a number
     * @param toCheck the string to check
     * @throws NumberFormatException the input string is not a number
     */
    public boolean isNumeric(String toCheck) throws NumberFormatException{
        try {
            Double.parseDouble(toCheck);
            return true;
        } catch (Exception e){
           return false;
        }
    }



}
