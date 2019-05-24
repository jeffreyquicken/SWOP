package SQLQuery;

import Data.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Class with query attributes
 */
public class Query {

    /**
     * the from clause of this query
     */
    private fromClause fromClause = new fromClause();

    /**
     * the select clause of this query
     */
    private selectClause selectClause = new selectClause();

    /**
     * the where clause of this query
     */
    private whereClause whereClause = new whereClause();

    /**
     * the join clause of this query
     */
    private joinClause joinClause = new joinClause();

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public List<Table> getTables() {
        return tables;
    }

    public SQLQuery.fromClause getFromClause() {
        return fromClause;
    }

    public SQLQuery.joinClause getJoinClause() {
        return joinClause;
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

    /**
     * Method that computes a table based on a given query
     *
     * @param data the datacontroller
     * @return the computed table
     * @throws IllegalArgumentException when expression is not valid
     */
    public Table getComputedTable(dataController data) throws IllegalArgumentException {

        Table selectedTable = this.fromClause.getTable(data);
        selectedTable = joinTables(data, selectedTable);
        this.getTables().add(selectedTable);
        Table resultTable = new Table("Computed_"+ this.getName());
        List<Column> selectedColumn = new ArrayList<>();
        Column exprColumn;
        String condition = this.whereClause.getCondition();
        List<Integer> indexSelectedColumn = new ArrayList<>();
        int indexExprColumn = -1;
        int i = 0;
        for (Column column : selectedTable.getColumnNames()) {
            for (int j = 0; j < this.selectClause.getSelectClauses().size(); j++) {
                if (column.getName().equals(this.selectClause.getSelectClauses().get(j).getAs().getId())) {
                    this.getColumns().add(column);
                    Column col = new Column(column.getName(), column.getDefaultV(), column.getType(), column.getBlanksAllowed());
                    selectedColumn.add(col);
                    indexSelectedColumn.add(i);
                }

            }
            if (column.getName().equals(this.whereClause.getId())) {
                this.getColumns().add(column);
                exprColumn = new Column(column.getName(), column.getDefaultV(), column.getType(), column.getBlanksAllowed());
                indexExprColumn = i;
            }
            i++;

        }
        int j = 0;
        for (Column addCol : selectedColumn) {
            resultTable.addColumn(addCol);
            resultTable.getColumnNames().get(j).setName(this.selectClause.getSelectClauses().get(j).getAs().getAlias());
            j++;
        }


        for (Row row : selectedTable.getTableRows()) {
            String leftExpr = row.getColumnList().get(indexExprColumn).getString();
            String rightExpr = condition;
            if (isNumeric(leftExpr) && isNumeric(rightExpr) && compareExpression(leftExpr, rightExpr)) {
                //conditie waar

                List<Column> addColumns = new ArrayList<>();
                int k = 0;
                for (Column addCol : selectedColumn) {
                    addColumns.add(addCol);
                    k++;
                }
                Row resultRow = new Row(addColumns);
                int m = 0;
                for (int l : indexSelectedColumn) {
                    CellText cell = new CellText(row.getColumnList().get(l).getString());
                    resultRow.setColumnCell(m, cell);
                    m++;
                }
                resultTable.addRow(resultRow);

            }
        }


        System.out.println("joined table");
        return resultTable;

    }

    /**
     * Method that compares two expressions based on the operator given in the where clause
     *
     * @param leftExpr  the first expression
     * @param rightExpr the second expression
     * @return whether the comparison is true
     * @throws IllegalArgumentException when the expression is not valid
     */
    public boolean compareExpression(String leftExpr, String rightExpr) throws IllegalArgumentException {
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
     * Method that joins two tables on a given column
     *
     * @param data          the datacontroller
     * @param selectedTable the table on which to join
     * @return the joined table
     */
    public Table joinTables(dataController data, Table selectedTable) {
        if (this.getJoinClause().getJoinItems().size() == 0) {
            return selectedTable;
        }
        Table joinedTable = new Table("join");
        Table table = this.getJoinClause().getTable(data, this.getJoinClause().getJoinItems().get(0));
        Table table1;
        Table table2;
        if (this.getJoinClause().getJoinItems().get(0).getAlias1().equals(this.getJoinClause().getJoinItems().get(0).getRowID())) {
            table1 = table;
            table2 = selectedTable;
        } else {
            table1 = selectedTable;
            table2 = table;
        }

        int indexTable1 = -1;
        int indexTable2 = -1;
        for (Column col : table1.getColumnNames()) {
            if (col.getName().equals(this.getJoinClause().getJoinItems().get(0).getCell1())) {
                indexTable1 = table1.getColumnNames().indexOf(col);
            }
        }
        for (Column col : table2.getColumnNames()) {
            if (col.getName().equals(this.getJoinClause().getJoinItems().get(0).getCell2())) {
                indexTable2 = table2.getColumnNames().indexOf(col);
            }
        }
        List<Column> columnList = new ArrayList<>();
        for (Column col : table1.getColumnNames()) {
            Column copyCol = new Column(col.getName(), col.getDefaultV(), col.getType(), col.getBlanksAllowed());
            columnList.add(copyCol);
            joinedTable.addColumn(copyCol);
        }
        int i = 0;
        for (Column col : table2.getColumnNames()) {
            if (i != indexTable2) {
                Column copyCol = new Column(col.getName(), col.getDefaultV(), col.getType(), col.getBlanksAllowed());
                columnList.add(copyCol);
                joinedTable.addColumn(copyCol);
            }
            i++;
        }

        int k = 0;
        for (Row row : table1.getTableRows()) {
            for (Row row2 : table2.getTableRows()) {
                if (row.getColumnList().get(indexTable1).getString().equals(row2.getColumnList().get(indexTable2).getString())) {
                    Row joinRow = new Row(columnList);
                    int j = 0;
                    for (Cell cell : joinRow.getColumnList()) {
                        Cell joinCell = null;
                        if (j < table1.getColumnNames().size()) {
                            joinCell = new CellText(table1.getTableRows().get(k).getColumnList().get(j).getString());
                        } else if (j != indexTable2) {
                            joinCell = new CellText(table2.getTableRows().get(k).getColumnList().get(j - table1.getColumnNames().size() + 1).getString());
                        }
                        joinRow.setColumnCell(j, joinCell);
                        j++;
                    }
                    joinedTable.addRow(joinRow);
                }
            }
            k++;
        }

        return joinedTable;
    }

    /**
     * Method to check if a string is a number
     *
     * @param toCheck the string to check
     * @throws NumberFormatException the input string is not a number
     */
    public boolean isNumeric(String toCheck) throws NumberFormatException {
        try {
            Double.parseDouble(toCheck);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
