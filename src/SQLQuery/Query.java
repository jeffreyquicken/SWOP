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

    public List<Column> getColumns() {
        return columns;
    }

    /**
     * all columns used in this query
     */
    private List<Column> columns = new ArrayList<>();
    /**
     * all tables used in this query
     */
    private List<Table> tables = new ArrayList<>();

    public List<Table> getTables() {
        return tables;
    }

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


    public Table getComputedTable(dataController data) throws IllegalArgumentException{
        Table selectedTable = this.fromClause.getTable(data);
        this.getTables().add(selectedTable);
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
                    this.getColumns().add(column);
                    Column col = new Column(column.getName(), column.getDefaultV(), column.getType(), column.getBlanksAllowed());
                    selectedColumn.add(col);
                    indexSelectedColumn.add(i);
                }

            }
            if(column.getName().equals(this.whereClause.getId())){
                this.getColumns().add(column);
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


        for (Row row:selectedTable.getTableRows()){
            String leftExpr = row.getColumnList().get(indexExprColumn).getString();
            String rightExpr = condition;
            if (isNumeric(leftExpr) && isNumeric(rightExpr) && compareExpression(leftExpr,rightExpr) ){
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

    public boolean compareExpression(String leftExpr, String rightExpr) throws IllegalArgumentException{
        switch (this.whereClause.getOperator()) {
            case "<":
                return Integer.parseInt(leftExpr) < Integer.parseInt(rightExpr);
            case ">":
                return Integer.parseInt(leftExpr) > Integer.parseInt(rightExpr);
            case "=":
                return Integer.parseInt(leftExpr) == Integer.parseInt(rightExpr);
            case "OR":
                return Boolean.parseBoolean(leftExpr) || Boolean.parseBoolean(rightExpr);
            case "AND":
                return Boolean.parseBoolean(leftExpr) && Boolean.parseBoolean(rightExpr);
            default:
                throw new IllegalArgumentException("Expression not supported");
        }
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
