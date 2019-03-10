package Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Row {

    private List<String> columnList;
    private String rowName;

    public Row(String name){
        columnList = new ArrayList<String>();
        rowName = name;
        String value1 = "A " + name ;
        String value2 = "true" ;
        String value3 = "C " + name ;
        String value4 = "D " + name ;
        columnList.add(value1);
        columnList.add(value2);
        columnList.add(value3);
        columnList.add(value4);

    }
    public List<String> getColcumnList() {
        return columnList;
    }



}
