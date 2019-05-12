package SQLQuery;

import java.util.ArrayList;
import java.util.List;

public class fromClause {
    private List<String> fromClauses = new ArrayList<>();
    public  List<String> getFromCLauses(){

        return fromClauses;

    }
    public void addFromClause(String item){
        fromClauses.add(item);
    }

}
