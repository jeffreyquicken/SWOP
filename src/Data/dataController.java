package Data;

import java.util.ArrayList;
import java.util.List;

public class dataController {
    private List<Table> tableList;

    public dataController(){
        Table table1 = new Table("Table 1");
        Table table2 = new Table("Table 2");
        Table table3 = new Table("Table 3");
        tableList = new ArrayList<Table>();
        tableList.add(table1);
        tableList.add(table2);
        tableList.add(table3);
    }

    public List<Table> getTableList(){
        return tableList;
    }

    public void addTable(Table table){
        tableList.add(table);
    }
    public void deleteTable(Table table){
        tableList.remove(table);
    }
    public int getLowestY(){
        int lowestY = 50;
        try{
        for (Table table : this.getTableList()){
            lowestY += 20; //TODO WHERE TO STORE HEIGHT!
        }
            return lowestY;
        }catch (Exception e){
            return lowestY;
        }


    }




}
