package UserInterfaceElements;

import settings.settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UITopLevelWindow {

    private List<UISuperClass> activeSubWindow;
    private settings setting;
    private int minWidth = 150;
    private int minHeight = 150;

    //EVENTS VARIABLES
    private String state = "normal";
    private int[] savedCoords = {-1,-1};

    /**
     * List with activeSubwindow in chronological order. The last active subwindow is in the first position in the list.
     */
    List<UISuperClass> subWindows;
    /**
     * Dictionary with subwindows as keys and a list with Xco,Yco,Width, Height as value
     */
    Map<UISuperClass, List<Integer>> subwindowInfo;

    /**
     * Constructor
     */
    public UITopLevelWindow(){
        setting = new settings();
        subWindows = new ArrayList<>();
        subwindowInfo = new HashMap<UISuperClass, List<Integer>>();
        activeSubWindow = new ArrayList<>();
        // Adds a null object to the list to avoid NullPointerException
        //activeSubWindow.add(null);

    }


    public UISuperClass getActiveSubWindow() {
        if (activeSubWindow.size() == 0) {
            return null;
        }
        return activeSubWindow.get(0);
    }

    public List<UISuperClass> getActiveSubWindowList(){
        return activeSubWindow;
    }

    /**
     * Sets the first element of the activeSubwindow list and removes any duplicates of the subwindow
     * @param subWindow the subwindow to be added
     */
    public void setActiveSubWindow(UISuperClass subWindow) {
        getActiveSubWindowList().remove(subWindow);
        activeSubWindow.add(0, subWindow);
        subWindow.setActive(true);
        if (activeSubWindow.size() >= 2) {
            activeSubWindow.get(1).setActive(false);
        }

    }

    public List<UISuperClass> getSubWindows() {
        return subWindows;
    }

    public void setSubWindows(List<UISuperClass> subWindows) {
        this.subWindows = subWindows;
    }







    /**
     * Removes a given subwindow from the list of subwindows
     * @param closingSubWindow The subwindow to be closed
     */
    public void closeSubWindow(UISuperClass closingSubWindow){
        if(closingSubWindow.getActive() && activeSubWindow.size() > 1) {

            activeSubWindow.get(1).setActive(true);
        }
        subWindows.remove(closingSubWindow);
        activeSubWindow.remove(closingSubWindow);
    }

    /**
     * Adds a given subwindow to the list of subwindows
     * @param addingSubWindow The subwindow to be added
     */
    public void addSubWindow(UISuperClass addingSubWindow){
        subWindows.add(addingSubWindow);
        setActiveSubWindow(addingSubWindow);
        List<Integer> values = new ArrayList<>();
        //TODO: only for debugging
        if(subWindows.size() == 1){
        values.add(20);
        values.add(20);}else{
            values.add(200);
            values.add(30);
        }
        //Default width
        //WARNING HARDCODED
        values.add(30+100+30);
        values.add(200);
        subwindowInfo.put(addingSubWindow,values);

    }


    /**
     * Calculate starting x and y coordinate of subwindow
     * @param subWindow subwindow
     * @return start X and Y coordinates
     */
    public Integer[] getStartCoords(UISuperClass subWindow){
        Integer[] result = {-1,-1};
        result[0] = subwindowInfo.get(subWindow).get(0);
        result[1] = subwindowInfo.get(subWindow).get(1);
        return result;

    }
    /**
     * Calculate starting width height of subwindow
     * @param subWindow subwindow
     * @return width height
     */
    public Integer[] getDimensions(UISuperClass subWindow){
        Integer[] result = {-1,-1};
        result[0] = subwindowInfo.get(subWindow).get(2);
        result[1] = subwindowInfo.get(subWindow).get(3);
        return result;

    }

    public Integer[] relayCoordinates(int xCo, int yCo, int id){
        Integer[] result = {-1,-1};
        if (id == 502){
            state = "normal";
        }
        if (state.equals("drag") && (id == 506 )){
            List<Integer> info = subwindowInfo.get(activeSubWindow.get(0));
            int deltaX = xCo- savedCoords[0];
            int deltaY = yCo- savedCoords[1];
            int newX = info.get(0)+deltaX;
            int newY = info.get(1)+deltaY;
            info.set(0,newX);
            info.set(1, newY);
            savedCoords[0] = xCo;
            savedCoords[1] = yCo;

        }
        else if (state.equals("resize") && id == 506){
            List<Integer> info = subwindowInfo.get(activeSubWindow.get(0));
            int deltaX = xCo- savedCoords[0];
            int deltaY = yCo- savedCoords[1];
            int newWidth = info.get(2)+deltaX;
            int newHeight = info.get(3)+deltaY;
            if (newWidth >= minWidth){
                info.set(2,newWidth);
            }
            if (newHeight >= minHeight){
                info.set(3, newHeight);
            }
            else{
                info.set(2, minWidth);
                info.set(3, minHeight);
            }

            savedCoords[0] = xCo;
            savedCoords[1] = yCo;
        }
        else {
            for (UISuperClass subWindow : activeSubWindow) {
                List<Integer> info = subwindowInfo.get(subWindow);
                int X = info.get(0);
                int Y = info.get(1);
                int width = info.get(2);
                int height = info.get(3);

                List<Integer> activeInfo = subwindowInfo.get(getActiveSubWindow());
                int activeX;
                int activeY;
                int activeWidth;
                int activeHeight;
                if (getActiveSubWindow() != null) {
                    activeX = activeInfo.get(0);
                    activeY = activeInfo.get(1);
                    activeWidth = activeInfo.get(2);
                    activeHeight = activeInfo.get(3);
                } else {
                    activeX = 0;
                    activeY = 0;
                    activeWidth = 0;
                    activeHeight = 0;
                }


                if (ClickedWithinWindow(X,Y,xCo,yCo,width,height)) {
                    if(!ClickedWithinWindow(activeX,activeY,xCo,yCo,activeWidth,activeHeight) && subWindow != getActiveSubWindow()) {
                        setActiveSubWindow(subWindow);
                    }
                    int relayX = xCo - X;
                    int relayY = yCo - Y;
                    result[0] = relayX;
                    result[1] = relayY;
                    if (isClosingButtonClicked(relayX, relayY) && id == 501) {
                        closeSubWindow(subWindow);
                        getActiveSubWindowList().removeIf(subWindow :: equals);

                        return result;
                    }

                    if (isTitleBarClicked(relayX, relayY, width)) {
                        state = "drag";
                        savedCoords[0] = xCo;
                        savedCoords[1] = yCo;
                    } else if (whichBorderClicked(relayX, relayX, width, height) != -1) {
                        state = "resize";
                        savedCoords[0] = xCo;
                        savedCoords[1] = yCo;
                    } else {
                        state = "normal";
                    }
                    System.out.println("x: "+result[0] +", y: "+ result[1]);
                    return result;
                }

            }
        }

        return result;
    }

    /**
     * Method that checks whether the closing button is clicked
     * @param relayXCo
     * @param relayYCo
     * @return whether the closing button is clicked
     */
    public boolean isClosingButtonClicked(int relayXCo, int relayYCo){
        if (relayXCo > 8 && relayXCo < 18 && relayYCo > 3 && relayYCo < 13) {
            return true;
        }
        return false;
    }

    /**
     * Method that checks whether the title bar is clicked
     * @param relayXco
     * @param relayYCo
     * @param width
     * @return whether the title bar is clicked
     */
    public boolean isTitleBarClicked(int relayXco, int relayYCo, int width){
        if (relayXco > 0 && relayXco < width && relayYCo > 0 && relayYCo < 15){
            return true;
        }
        return false;
    }

    /**
     * Method that returns a number corresponding to the clicked edge or corner.
     * The numbers are as shown in the figure
     *
     *  1___5___2
     *  |       |
     * 8|       |6
     *  |       |
     *  |_______|
     *  4   7   3
     *
     * @param relayXco
     * @param relayYco
     * @param width
     * @param height
     * @return the number corresponding to the clicked edge or corner
     */
    public int whichBorderClicked(int relayXco, int relayYco, int width, int height){
        if (relayXco == 0 && relayYco ==0){
            return 1;
        }
        else if(relayXco == width && relayYco ==0){
            return 2;
        }
        else if(relayXco == width && relayYco ==height){
            return 3;
        }
        else if(relayXco == 0 && relayYco ==height){
            return 4;
        }
        else if (relayYco == 0 && (relayXco > 0 && relayXco < width)){
            return 5;
        }
        else if (relayXco == width && (relayYco > 0 && relayYco < height)){
            return 6;
        }
        else if (relayYco == height && (relayXco > 0 && relayXco < width)){
            return 7;
        }
        else if (relayXco == 0 && (relayYco > 0 && relayYco < height)){
            return 8;
        }
        return -1;
    }

    public boolean ClickedWithinWindow(int X, int Y, int xCo, int yCo, int width, int height) {
        if (xCo >= X && xCo <= X + width && yCo >= Y && yCo <= Y + height) {
            return true;
        } else {
            return false;
        }

    }


}
