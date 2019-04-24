package UserInterfaceElements;


import Data.dataController;
import events.MouseEvent;
import events.KeyEvent;
import paintModule.paintModule;
import settings.CellVisualisationSettings;
import settings.scrollbar;

import java.awt.*;
import java.util.List;

public abstract class UISuperClass {
    protected paintModule paintModule;
    protected MouseEvent mouseEventHandler;
    protected String currMode = "normal";
    protected int[] activeCell;
    protected String tempText;
    protected Boolean invalidInput;
    protected int draggedColumn;
    protected int draggedX;
    protected boolean ctrlPressed;
    protected boolean active;
    protected boolean scrollbarActive;
    protected double percentageHorizontal;
    protected double percentageVertical;
    protected scrollbar scrollbar;
    /*
     * TO REFACTOR
     * too many responsibilities (/as shown by the amount of variables)?
     * split up in more classes to to lighten workload and increase readablity
     */


    /**
     * constructor for UISuperclass
     */
    public UISuperClass() {
        scrollbarActive = false;
        paintModule = new paintModule();
        mouseEventHandler = new MouseEvent();
        invalidInput = false;
        tempText = "Default_Text";
        draggedColumn = 1;
        draggedX = 1;
        active = false;
        scrollbar = new scrollbar();
    }


    //Handles keyevent and returns if UImode need to change
    public String handleKeyEvent(int id, int keyCode, char keyChar, dataController data) {
        KeyEvent eventHandler = new KeyEvent();
        String nextUIMode = "";



        if (currMode == "edit") {
            List<String> result = this.handleKeyEditMode(id, keyCode, keyChar, data);
            currMode = result.get(0);
            nextUIMode = result.get(1);
            ctrlPressed = false;

        }
        else if (currMode == "delete" ){
            List<String> result = this.handleKeyDeleteMode(id, keyCode, keyChar, data);
            currMode = result.get(0);
            nextUIMode = result.get(1);
            ctrlPressed = false;
        }
        else if (currMode == "normal"){
            List<String> result = this.handleKeyNormalMode(id, keyCode, keyChar, data);
            currMode = result.get(0);
            nextUIMode = result.get(1);

        }
        else if (currMode == "drag"){
            List<String> result = this.handleKeyNormalMode(id, keyCode, keyChar, data);
            currMode = result.get(0);
            nextUIMode = result.get(1);
        }
        //this.handleNonModeDependantKeys(id, keyCode, keyChar, data);
        return nextUIMode;
    }

    protected void paint(Graphics g, dataController data, Integer[] coords, Integer[] dimensions){ }

    protected Boolean scrollbarClicked(int xco, int yco, Integer[] dimensions){
        if(scrollbar.getActiveVertical() && xco > (dimensions[0] - 15) && xco < dimensions[0]){

            if(scrollbar.getPercentageVertical() * dimensions[1] -15 < yco){
                System.out.println("Under vertical Scrollbar clicked!!!!!!!!!!!!!!!!!!");
                scrollbar.addOffsetPercentageVertical();

            }else{
                System.out.println("on Vertical Scrollbar clicked!!!!!!!!!!!!!!!!!!");
                scrollbar.substractOffsetPercentageVertical();
            }


        }else if(scrollbar.getActiveHorizontal() && yco > (dimensions[1] - 15) && yco < dimensions[1]){
            if(scrollbar.getOffsetpercentageHorizontal() * dimensions[0] < xco){
                System.out.println("Under hor Scrollbar clicked!!!!!!!!!!!!!!!!!!");
                scrollbar.addOffsetPercentageHorizontal();
            }else{
                System.out.println("on hor Scrollbar clicked!!!!!!!!!!!!!!!!!!");
                scrollbar.substractOffsetPercentageHorizontal();
            }
        }

        else{
            System.out.println("Scrollbar Inactive");
        }
        return false;
    }



    protected List<String> handleKeyEditMode(int id, int keyCode, char keyChar, dataController data){return null;}
    protected List<String> handleKeyNormalMode(int id, int keyCode, char keyChar, dataController data){return null;}
    protected List<String> handleKeyDeleteMode(int id, int keyCode, char keyChar, dataController data){return null;}
    protected String handleMouseEvent(int xCo, int yCo, int count, int ID, dataController data, Integer[] dimensions){

        List<String> result = this.handleMouseEvent2(xCo,  yCo,  count, ID,  data, dimensions);
        currMode = result.get(0);
        return result.get(1);

    }

    protected List<String> handleMouseEvent2(int xCo, int yCo, int count, int ID, dataController data, Integer[] dimensions){

        return null;}
    protected void handleNonModeDependantKeys (int id, int keyCode, char keyChar, dataController data){}

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
