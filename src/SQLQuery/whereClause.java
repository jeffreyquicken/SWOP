package SQLQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for where clauses
 */
public class whereClause {
    /**
     * WHERE $tablename$.id OPERATOR condition
     */
    private String tableName;

    /**
     * WHERE tablename.$id$ OPERATOR condition
     */
    private String id;

    /**
     * WHERE tablename.id $OPERATOR$ condition
     */
    private String operator;

    /**
     * WHERE tablename.id OPERATOR $condition$
     */
    private String condition;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }


    /**
     * Method that adds the attributes to the where clause object
     * @param tableName the tablename
     * @param id the column id
     * @param operator the operator
     * @param condition the condition to check
     */
    public void addWhereClause(String tableName, String id, String operator, String condition){

        this.tableName = tableName;
        this.id = id;
        this.operator = operator;
        this.condition = condition;
    }

}
