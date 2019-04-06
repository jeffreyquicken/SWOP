package Data;


import java.util.ArrayList;

import java.util.List;


public class Row {

    private List<Cell> columnList;
    private String rowName;

    /**
     * Constructor that initiates a row object with a given list of columns
     * @param colList   the list of columns that belong to the row
     */
    public Row( List<Column> colList){
        columnList = new ArrayList<Cell>();
        for(Column col: colList){
            Cell val = col.getDefaultV();
            if(val.getType() == "Numerical") {
                columnList.add(new CellNumerical(((CellNumerical) val).getValue()));
            } else if(val.getType() == "Email") {
                columnList.add(new CellEmail(((CellEmail) val).getValue()));
            } else if(val.getType() == "Text") {
                columnList.add(new CellText(((CellText) val).getValue()));
             }else if(val.getType() == "Boolean") {
                columnList.add(new CellBoolean(((CellBoolean) val).getValue()));
            }




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

    public List<Cell> getColumnList() {
        return columnList;
    }
    public void addColumn(Cell value){
        columnList.add(value);
    }

    public void deleteColumnCell(int index){
        columnList.remove(index);
    }
    public void setColumnCell(int index, Cell newvalue){
        columnList.set(index, newvalue);
    }



}
