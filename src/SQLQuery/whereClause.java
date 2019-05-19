package SQLQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for where clauses
 */
public class whereClause {
    private String tableName;

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

    private String id;
    private String operator;
    private String condition;


    public void addWhereClause(String tableName, String id, String operator, String condition){

        this.tableName = tableName;
        this.id = id;
        this.operator = operator;
        this.condition = condition;
    }

}
