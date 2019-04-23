package UserInterfaceElements;


import Data.dataController;
import EventHandlers.keyEventHandler;
import EventHandlers.mouseEventHandler;
import paintModule.paintModule;
import settings.settings;
import settings.scrollbar;

import java.awt.*;
import java.util.List;

public abstract class UISuperClass {
    protected paintModule paintModule;
    protected mouseEventHandler mouseEventHandler;
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



    /**
     * constructor for UISuperclass
     */
    public UISuperClass() {
        scrollbarActive = false;
        paintModule = new paintModule();
        mouseEventHandler = new mouseEventHandler();
        invalidInput = false;
        tempText = "Default_Text";
        draggedColumn = 1;
        draggedX = 1;
        active = false;
        scrollbar = new scrollbar();
    }


    //Handles keyevent and returns if UImode need to change
    public String handleKeyEvent(int id, int keyCode, char keyChar, dataController data) {
        keyEventHandler eventHandler = new keyEventHandler();
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
    protected void recalculateScrollbar(dataController data, Integer[] dimensions){
        settings setting = data.getSetting();
        List<Integer> widthList = setting.getWidthList();

        int sum = widthList.stream().mapToInt(Integer::intValue).sum();
        scrollbarActive = false;
        if(sum > dimensions[0] - 31 ){
            percentageHorizontal =  (Double.valueOf(dimensions[0]-30)/ Double.valueOf(sum));
            scrollbar.setPercentageHorizontal(percentageHorizontal);
            scrollbar.setActiveHorizontal(true);
            System.out.println(percentageHorizontal);
        }else{
            scrollbar.setActiveHorizontal(false);
            scrollbar.setPercentageHorizontal(0);
            scrollbar.setOffsetpercentageHorizontal(0);



        }

        if(data.getTableList().size() * 20 > dimensions[1] - 46){
            percentageVertical = ( Double.valueOf(dimensions[1] - 46)/ Double.valueOf((data.getTableList().size() * 20)));
            scrollbar.setPercentageVertical(percentageVertical);
            scrollbar.setActiveVertical(true);
            System.out.println(percentageVertical);
        }else{
            scrollbar.setPercentageVertical(0);
            scrollbar.setActiveVertical(false);
            scrollbar.setOffsetpercentageVertical(0);
        }}


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
