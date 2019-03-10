package UserInterfaceElements;
import java.awt.*;
import Data.Table;
import Data.dataController;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private UITablesModule tablemodule ;
    private UIRowModule rowmodule;
    private UIDesignModule designModule;
    private Data.dataController tableDataController;
    private String currentMode;


    //Display key/mousevent TODO: Delete
    private String mouseEvent = "";
    private String keyEvent = "";

    //List with current databases

    //Constructor that creates/init our three different UI modules/controller
    //Constructor also creates empty table list
    //& sets current view to table-view
    public Controller(){
        //three UI modules
        tablemodule = new UITablesModule();
        rowmodule = new UIRowModule();
        designModule = new UIDesignModule();

        //dataController
        tableDataController = new dataController();

        //Sets defaultmode to table-mode
        currentMode = "table";
    }

    //HAndle MouseEvent
    public void relayMouseEvent(int id, int xCo, int yCo, int count){

        if (this.getCurrentMode() == "table"){
            //if mode will be swtitched mouseevent will tell
            this.setCurrentMode(this.getTablemodule().handleMouseEvent(xCo, yCo, count, id, tableDataController));
        }else if (this.getCurrentMode().equals("row")){
            this.setCurrentMode(this.rowmodule.handleMouseEvent(xCo,yCo,count,id,tableDataController));

        }
        mouseEvent = "Mouse eventID= " + id + " | Coordinates clicked" + xCo + ", " + yCo + "| Amount clicked: " + count;
    }

    //Handle keyevent
    public void relayKeyEvent(int id, int keyCode, char keyChar){
        if (this.getCurrentMode() == "table"){
            //if mode will be switched mouseevent will tell
            this.setCurrentMode(this.getTablemodule().handleKeyEvent(id, keyCode, keyChar, tableDataController));
        }else if (this.getCurrentMode() == "row"){
            //if mode will be swtitched mouseevent will tell
            this.setCurrentMode(this.getRowmodule().handleKeyEvent(id, keyCode, keyChar, tableDataController));
        }
        keyEvent = "Key eventID= " + id + " | Key pressed: " + keyChar +  " | KeyCode: " + Integer.toString(keyCode);
    }



  //Handle Paint Event
    public void paint (Graphics g){

        if (this.getCurrentMode() == "table"){
            //Let UImodule paint canvas
            this.getTablemodule().paint(g, tableDataController);
        }
        else if (this.getCurrentMode() == "row"){
            this.getRowmodule().paint(g, tableDataController.getSelectedTable());
        }
        else{
            this.getDesignModule().paint(g,tableDataController.getSelectedTable());

        }
        //drawing of mousevent, keyevent just for debugging
        g.drawString(mouseEvent, 10,400);
        g.drawString(keyEvent, 10 , 420);
    }



    //getters
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

    public dataController getTableDataController() {
        return tableDataController;
    }
}
