package UserInterfaceElements;

import java.awt.*;
import Data.Table;

import java.util.ArrayList;
import java.util.List;


public class Controller {
    private UITablesModule tablemodule ;
    private UIRowModule rowmodule;
    private UIDesignModule designModule;
    private String currentMode;
    private List<Table> list;

    public List<Table> getList() {
        return list;
    }

    private Table emptyTable;

    //Constructor that creates/init our three different UI modules/controller
    //Constructor also creates empty table list
    //& sets current view to table-view
    public Controller(){
        //three UI modules
        tablemodule = new UITablesModule();
        rowmodule = new UIRowModule();
        designModule = new UIDesignModule();

        //List with our tables in
        list = new ArrayList<Table>();
        //Add empty table
        //TODO: empty table is just for debuggin, need to be removed later
        emptyTable = this.createEmptyTable();
        list.add(emptyTable);

        //Sets mode to table-mode
        currentMode = "table";
    }

    //Method that decide which module can paint the canvas
    //It calls appropiate module
    public void paint (Graphics g){
        //TODO: Only tablemode is implemented different calls need to change to right change
        if (this.getCurrentMode() == "table"){
            this.getTablemodule().paint(g, this.getList());
        }
        else if (this.getCurrentMode() == "row"){
            this.getTablemodule().paint(g,  this.getList());
        }
        else{
            this.getTablemodule().paint(g,  this.getList());
        }

    }
    //method that creates an amptytable
    public Table createEmptyTable(){
        Table table;
        table = new Table("emptyTable");
        return table;
    }
    //getter mode
    public String getCurrentMode() {
        return currentMode;
    }

    public void setCurrentMode(String currentMode) {
        this.currentMode = currentMode;
    }
    public UITablesModule getTablemodule() {
        return tablemodule;
    }

    public UIRowModule getRowmodule() {
        return rowmodule;
    }

    public UIDesignModule getDesignModule() {
        return designModule;
    }
}
