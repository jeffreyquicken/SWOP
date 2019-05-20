package UserInterfaceElements;

import java.awt.*;

import Data.Table;
import Data.dataController;
import UndoRedo.Command;
import UndoRedo.Operations;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Controller {
    private UITablesModule tablemodule;
    private UIRowModule rowmodule;
    private UIDesignModule designModule;
    private Data.dataController tableDataController;
    private String currentMode;

    private UITopLevelWindow topLevelWindow;
    //ctrlPressed var to detect if new tables module needs to be added
    private boolean ctrlPressed;
    //shiftPressed var to detect if shift is pressedf, for redo functionaity
    private boolean shiftPressed;
    //Display key/mousevent
    private String mouseEvent = "";
    private String keyEvent = "";

    /**
     * Creates/init the three different UI modules/controller, creates the topLevelWindow, creates empty table list and sets current view to table-view
     */
    public Controller() {
        //rowmodule = new UIRowModule();
        //designModule = new UIDesignModule();


        //topLevelWindow
        topLevelWindow = new UITopLevelWindow();

        //three UI modules
        tablemodule = new UITablesModule();
        UITablesModule tablemodule2 = new UITablesModule();

        topLevelWindow.addSubWindow(tablemodule);
        topLevelWindow.getActiveSubWindowList().remove(null);
        //dataController
        tableDataController = new dataController();

        //Sets defaultmode to table-mode
        currentMode = "table";



    }

    public Controller(int i) {
        //topLevelWindow
        topLevelWindow = new UITopLevelWindow();

        //three UI modules
        tablemodule = new UITablesModule();
        UITablesModule tablemodule2 = new UITablesModule();

        topLevelWindow.addSubWindow(tablemodule);
        topLevelWindow.getActiveSubWindowList().remove(null);
        //dataController
        tableDataController = new dataController(i);

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
        //if mode will be swtitched mouseevent will tell
        Integer[] result =  topLevelWindow.relayCoordinates(xCo,yCo, id);
        if(topLevelWindow.getActiveSubWindow() != null  ) {

            String nextUIMode = topLevelWindow.getActiveSubWindow().handleMouseEvent(result[0], result[1], count, id, tableDataController, topLevelWindow.getDimensions(topLevelWindow.getActiveSubWindow()));
            if (nextUIMode.equals("row")){
                if(tableDataController.getSelectedTable().getQuery().equals("")){
                    UIRowModule rowModule = new UIRowModule(tableDataController.getSelectedTable());
                    topLevelWindow.addSubWindow(rowModule);
                }
                else if (tableDataController.getSelectedTable().getQuery().length() > 0 && id==500){
                    UIComputedModule computeModule = new UIComputedModule(tableDataController.getSelectedTable().getComputedTable(tableDataController), tableDataController);
                    computeModule.getTable().setQuery(tableDataController.getSelectedTable().getComputedTable(tableDataController).getQuery());
                    tableDataController.getQueryManager().addQueryDependendTablesFromQuery(tableDataController.getSelectedTable().getLastQuery());
                    tableDataController.getQueryManager().addQueryDependendColumnsFromQuery(tableDataController.getSelectedTable().getLastQuery());
                    topLevelWindow.addSubWindow(computeModule);
                }
            }
            else if(nextUIMode.equals("design")){
                UIDesignModule designModule = new UIDesignModule(tableDataController.getSelectedTable());
                topLevelWindow.addSubWindow(designModule);
            }
        }
        mouseEvent = "Mouse eventID= " + id + " | Coordinates clicked" + xCo + ", " + yCo + "| Amount clicked: " + count;
        System.out.println(mouseEvent);
    }

    /**
     * Method that relays a keyEvent to the right UIModule to be handled
     *
     * @param id      id of the pressed key
     * @param keyCode keycode of the pressed key
     * @param keyChar keychar of the pressed key
     */
    //TO REFACTOR
    public void relayKeyEvent(int id, int keyCode, char keyChar) {
        String nextUIMode;


        if (topLevelWindow.getActiveSubWindow() != null ){
            nextUIMode = topLevelWindow.getActiveSubWindow().handleKeyEvent(id, keyCode, keyChar, tableDataController);
        } else {
            nextUIMode = "nothing";
        }


        //if else statement to check if ctrl+t is clicked
        if (keyCode == 17 || keyCode == 16) {
            if(keyCode == 17) {
                setCtrlPressed(true);
            } else {
                setShiftPressed(true);
            }
        } else if (getCtrlPressed() && !getShiftPressed()) {
            if (keyCode == 84) {
                nextUIMode = "table";
                ctrlPressed = false;
            } else if (keyCode == 90) { //CTRL+Z pressed
                this.tableDataController.undo();
                setCtrlPressed(false);
            }
        } else if (getCtrlPressed() && getShiftPressed()) {
            if (keyCode == 90) { //CTRL+Shift+Z pressed
                this.tableDataController.redo();
                setCtrlPressed(false);
                setShiftPressed(false);
            }
        } else {
            setCtrlPressed(false);
            setShiftPressed(false);
        }


        if (nextUIMode.equals("row")){
            UIRowModule rowModule = new UIRowModule(tableDataController.getSelectedTable());
            topLevelWindow.addSubWindow(rowModule);
        }
        else if(nextUIMode.equals("design")){
            UIDesignModule designModule = new UIDesignModule(tableDataController.getSelectedTable());
            topLevelWindow.addSubWindow(designModule);
        }else if(nextUIMode.equals("table")){
            UITablesModule tablesModule = new UITablesModule();
            topLevelWindow.addSubWindow(tablesModule);
        }else if(nextUIMode.equals("form")){
            UIFormModule formModule = new UIFormModule(tableDataController.getSelectedTable());
            topLevelWindow.addSubWindow(formModule);
        }

//        if (this.getCurrentMode() == "table") {
//            //if mode will be switched mouseevent will tell
//            this.setCurrentMode(this.getTablemodule().handleKeyEvent(id, keyCode, keyChar, tableDataController));
//        } else if (this.getCurrentMode() == "row") {
//            //if mode will be swtitched mouseevent will tell
//            this.setCurrentMode(this.getRowmodule().handleKeyEvent(id, keyCode, keyChar, tableDataController));
//        } else if (this.getCurrentMode() == "design") {
//            this.setCurrentMode(this.getDesignModule().handleKeyEvent(id, keyCode, keyChar, tableDataController));
//        }
        keyEvent = "Key eventID= " + id + " | Key pressed: " + keyChar + " | KeyCode: " + Integer.toString(keyCode);
        System.out.println(keyEvent);
    }


    /**
     * Method that calls the right paint method for the view based on the current mode of the system
     *
     * @param g graphics object
     */
    public void paint(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0,0,10000, 10000);
        g.setColor(Color.BLACK);
        List<UISuperClass> subWindows;
        if (topLevelWindow.getActiveSubWindowList().size() > 0) {
            subWindows = topLevelWindow.getActiveSubWindowList();
        } else {
            subWindows = topLevelWindow.getSubWindows();
        }
        ListIterator listIterator = subWindows.listIterator(subWindows.size());
        while (listIterator.hasPrevious()) {
            UISuperClass subWindow = (UISuperClass) listIterator.previous();
            subWindow.paint(g, tableDataController,topLevelWindow.getStartCoords(subWindow), topLevelWindow.getDimensions(subWindow));

        }

        UISuperClass subWindow = topLevelWindow.getActiveSubWindow();
        if(subWindow != null){
            subWindow.paint(g, tableDataController,topLevelWindow.getStartCoords(subWindow), topLevelWindow.getDimensions(subWindow));
        }




        /**
        if (this.getCurrentMode() == "table") {
            //Let UImodule paint canvas
            this.getTablemodule().paint(g, tableDataController);
        } else if (this.getCurrentMode() == "row") {
            this.getRowmodule().paint(g, tableDataController.getSelectedTable(), tableDataController);
        } else if (this.getCurrentMode() == "design") {
            this.getDesignModule().paint(g, tableDataController);
        }
        //drawing of mousevent, keyevent just for debugging
     //   g.drawString(mouseEvent, 10, 400);
     //   g.drawString(keyEvent, 10, 420);*/
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

    public void setCtrlPressed(boolean ctrlPressed) {
        this.ctrlPressed = ctrlPressed;
    }

    public boolean getCtrlPressed() {
        return ctrlPressed;
    }
    public void setShiftPressed(boolean sp) {
        this.shiftPressed = sp;
    }

    public boolean getShiftPressed() {
        return shiftPressed;
    }

    public UITopLevelWindow getTopLevelWindow() {
        return topLevelWindow;
    }
}
