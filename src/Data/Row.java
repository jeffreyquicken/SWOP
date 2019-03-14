package Data;

import com.sun.xml.internal.ws.api.config.management.policy.ManagementAssertion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Row {

    private List<String> columnList;
    private String rowName;

    public Row( List<Column> colList){
        columnList = new ArrayList<String>();
        for(Column col: colList){
            columnList.add(col.getDefaultV());
        }


        /**
        rowName = name;
        String value1 = "" ;
        String value2 = "true" ;
        String value3 = "C " + name ;
        String value4 = "D " + name ;
        columnList.add(value1);
        columnList.add(value2);
        columnList.add(value3);
        columnList.add(value4);**/

    }
    public List<String> getColumnList() {
        return columnList;
    }
    public void addColumn(String value){
        columnList.add(value);
    }

    public void deleteColumnCell(int index){
        columnList.remove(index);
    }
    public void setColumnCell(int index, String newvalue){
        columnList.set(index, newvalue);
    }



}
