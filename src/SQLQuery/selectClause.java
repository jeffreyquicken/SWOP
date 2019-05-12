package SQLQuery;

import java.util.ArrayList;
import java.util.List;

public class selectClause {
    private List<String> selectClauses = new ArrayList<>();
    public  List<String> getSelectClauses(){

        return selectClauses;

    }
    public void addSelectClause(String item){
        selectClauses.add(item);
    }

}
