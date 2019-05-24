package UserInterfaceElements;


import Data.dataController;
import events.MouseEvent;
import events.KeyEvent;
import paintModule.PaintModule;
import settings.CellVisualisationSettings;
import settings.scrollbar;

import java.awt.*;
import java.util.List;

/**
 * superclass for UIModules involving super methods for painting and handling keyevents
 */
public abstract class UISuperClass {
    /**
     * The paintmodule
     */
    protected PaintModule paintModule;

    /**
     * The mouseeventhandler
     */
    protected MouseEvent mouseEventHandler;

    /**
     * the current mode of the system
     */
    protected String currMode = "normal";

    /**
     * active cell coordinates
     */
    protected int[] activeCell;

    /**
     * temporary text
     */
    protected String tempText;

    /**
     * whether there is invalid input
     */
    protected Boolean invalidInput;

    /**
     * The index of the dragged column
     */
    protected int draggedColumn;

    /**
     * The x x coordinate of the drag
     */
    protected int draggedX;

    /**
     * whether control key is pressed
     */
    protected boolean ctrlPressed;

    /**
     * whether the module is the active window
     */
    protected boolean active;

    /**
     * whether the scrolbar is active
     */
    protected boolean scrollbarActive;

    /**
     * the horizontal sroll percentage
     */
    protected double percentageHorizontal;

    /**
     * the vertical scroll percentage
     */
    protected double percentageVertical;

    /**
     * the scrollbar
     */
    protected scrollbar scrollbar;

    /**
     * constructor for UISuperclass
     */
    public UISuperClass() {
        scrollbarActive = false;
        paintModule = new PaintModule();
        mouseEventHandler = new MouseEvent();
        invalidInput = false;
        tempText = "Default_Text";
        draggedColumn = 1;
        draggedX = 1;
        active = false;
        scrollbar = new scrollbar();
    }


    /**
     * Method that handles the keyevents
     *
     * @param id      ket id
     * @param keyCode keycode
     * @param keyChar key chararcter
     * @param data    datacontroller
     * @return the next ui mode
     */
    public String handleKeyEvent(int id, int keyCode, char keyChar, dataController data) {
        KeyEvent eventHandler = new KeyEvent();
        String nextUIMode = "";


        if (currMode == "edit") {
            List<String> result = this.handleKeyEditMode(id, keyCode, keyChar, data);
            currMode = result.get(0);
            nextUIMode = result.get(1);
            ctrlPressed = false;

        } else if (currMode == "delete") {
            List<String> result = this.handleKeyDeleteMode(id, keyCode, keyChar, data);
            currMode = result.get(0);
            nextUIMode = result.get(1);
            ctrlPressed = false;
        } else if (currMode == "normal") {
            List<String> result = this.handleKeyNormalMode(id, keyCode, keyChar, data);
            currMode = result.get(0);
            nextUIMode = result.get(1);

        } else if (currMode == "drag") {
            List<String> result = this.handleKeyNormalMode(id, keyCode, keyChar, data);
            currMode = result.get(0);
            nextUIMode = result.get(1);
        }
        //this.handleNonModeDependantKeys(id, keyCode, keyChar, data);
        return nextUIMode;
    }

    /**
     * Method for painting elelments
     *
     * @param g          graphics object
     * @param data       datacontroller
     * @param coords     the coordinates to paint
     * @param dimensions the dimensions
     */
    protected void paint(Graphics g, dataController data, Integer[] coords, Integer[] dimensions) {
    }

    /**
     * Method that checks whhether the scrollbar is clicked
     *
     * @param xco        the x coordinate of the click
     * @param yco        the y coorsinate of the click
     * @param dimensions the dimensions of the window
     * @return whether the scrollbar is clicked
     */
    protected Boolean scrollbarClicked(int xco, int yco, Integer[] dimensions) {
        if (scrollbar.getActiveVertical()) {
            System.out.println("yes");
        }
        if (scrollbar.getActiveVertical() && xco > (dimensions[0] - 15) && xco < dimensions[0] && yco < dimensions[1] - 15) {

            if (scrollbar.getPercentageVertical() * dimensions[1] - 15 < yco) {
                System.out.println("Under vertical Scrollbar clicked!!!!!!!!!!!!!!!!!!");
                scrollbar.addOffsetPercentageVertical();

            } else {
                System.out.println("on Vertical Scrollbar clicked!!!!!!!!!!!!!!!!!!");
                scrollbar.substractOffsetPercentageVertical();
            }


        } else if (scrollbar.getActiveHorizontal() && yco > (dimensions[1] - 15) && yco < dimensions[1] && xco < dimensions[0] - 15) {
            if (scrollbar.getOffsetpercentageHorizontal() * dimensions[0] < xco) {
                System.out.println("Under hor Scrollbar clicked!!!!!!!!!!!!!!!!!!");
                scrollbar.addOffsetPercentageHorizontal();
            } else {
                System.out.println("on hor Scrollbar clicked!!!!!!!!!!!!!!!!!!");
                scrollbar.substractOffsetPercentageHorizontal();
            }
        } else {
            System.out.println("Scrollbar Inactive");
        }
        return false;
    }


    protected List<String> handleKeyEditMode(int id, int keyCode, char keyChar, dataController data) {
        return null;
    }

    protected List<String> handleKeyNormalMode(int id, int keyCode, char keyChar, dataController data) {
        return null;
    }

    protected List<String> handleKeyDeleteMode(int id, int keyCode, char keyChar, dataController data) {
        return null;
    }

    protected String handleMouseEvent(int xCo, int yCo, int count, int ID, dataController data, Integer[] dimensions) {

        List<String> result = this.handleMouseEvent2(xCo, yCo, count, ID, data, dimensions);
        currMode = result.get(0);
        return result.get(1);

    }

    protected List<String> handleMouseEvent2(int xCo, int yCo, int count, int ID, dataController data, Integer[] dimensions) {

        return null;
    }

    protected void handleNonModeDependantKeys(int id, int keyCode, char keyChar, dataController data) {
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    //methods for testing
    public String getCurrMode() {
        return this.currMode;
    }

    public scrollbar getScrollbar() {
        return this.scrollbar;
    }
}
