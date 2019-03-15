package UserInterfaceElements;

import java.awt.*;

import Data.Table;
import Data.dataController;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    private UITablesModule tablemodule;
    private UIRowModule rowmodule;
    private UIDesignModule designModule;
    private Data.dataController tableDataController;
    private String currentMode;


    //Display key/mousevent TODO: Delete
    private String mouseEvent = "";
    private String keyEvent = "";


    /**
     * Creates/init the three different UI modules/controller, creates empty table list and sets current view to table-view
     */
    public Controller() {
        //three UI modules
        tablemodule = new UITablesModule();
        rowmodule = new UIRowModule();
        designModule = new UIDesignModule();

        //dataController
        tableDataController = new dataController();

        //Sets defaultmode to table-mode
        currentMode = "table";
    }

    /**
     * Method that relays a mouseEvent to the right UIModule to be handled
     *
     * @param id    the id of the mouseEvent
     * @param xCo   X coordinate of click
     * @param yCo   Y coordinate of click
     * @param count number of clicks
     */
    public void relayMouseEvent(int id, int xCo, int yCo, int count) {

        if (this.getCurrentMode() == "table") {
            //if mode will be swtitched mouseevent will tell
            this.setCurrentMode(this.getTablemodule().handleMouseEvent(xCo, yCo, count, id, tableDataController));
        } else if (this.getCurrentMode().equals("row")) {
            this.setCurrentMode(this.rowmodule.handleMouseEvent(xCo, yCo, count, id, tableDataController));

        } else if (this.getCurrentMode().equals("design")) {
            this.setCurrentMode(this.getDesignModule().handleMouseEvent(xCo, yCo, count, id, tableDataController));
        }
        mouseEvent = "Mouse eventID= " + id + " | Coordinates clicked" + xCo + ", " + yCo + "| Amount clicked: " + count;
    }

    /**
     * Method that relays a keyEvent to the right UIModule to be handled
     *
     * @param id      id of the pressed key
     * @param keyCode keycode of the pressed key
     * @param keyChar keychar of the pressed key
     */
    public void relayKeyEvent(int id, int keyCode, char keyChar) {
        if (this.getCurrentMode() == "table") {
            //if mode will be switched mouseevent will tell
            this.setCurrentMode(this.getTablemodule().handleKeyEvent(id, keyCode, keyChar, tableDataController));
        } else if (this.getCurrentMode() == "row") {
            //if mode will be swtitched mouseevent will tell
            this.setCurrentMode(this.getRowmodule().handleKeyEvent(id, keyCode, keyChar, tableDataController));
        } else if (this.getCurrentMode() == "design") {
            this.setCurrentMode(this.getDesignModule().handleKeyEvent(id, keyCode, keyChar, tableDataController));
        }
        keyEvent = "Key eventID= " + id + " | Key pressed: " + keyChar + " | KeyCode: " + Integer.toString(keyCode);
    }


    /**
     * Method that calls the right paint method for the view based on the current mode of the system
     *
     * @param g graphics object
     */
    public void paint(Graphics g) {

        if (this.getCurrentMode() == "table") {
            //Let UImodule paint canvas
            this.getTablemodule().paint(g, tableDataController);
        } else if (this.getCurrentMode() == "row") {
            this.getRowmodule().paint(g, tableDataController.getSelectedTable(), tableDataController);
        } else if (this.getCurrentMode() == "design") {
            this.getDesignModule().paint(g, tableDataController);
        }
        //drawing of mousevent, keyevent just for debugging
        g.drawString(mouseEvent, 10, 400);
        g.drawString(keyEvent, 10, 420);
    }
    
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
