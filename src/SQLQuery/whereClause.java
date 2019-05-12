package SQLQuery;

import java.util.ArrayList;
import java.util.List;

public class whereClause {
    private List<String> whereClauses = new ArrayList<>();
    public  List<String> getWhereCLauses(){

        return whereClauses;

    }
    public void addWhereClause(String item){
        whereClauses.add(item);
    }

}
