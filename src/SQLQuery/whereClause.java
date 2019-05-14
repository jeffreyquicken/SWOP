package SQLQuery;

import java.util.ArrayList;
import java.util.List;

public class whereClause {
    private String tableName;
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
