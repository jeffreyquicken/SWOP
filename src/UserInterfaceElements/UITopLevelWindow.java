package UserInterfaceElements;
import javafx.util.Pair;
import settings.settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UITopLevelWindow {

    List<UISuperClass> activeSubWindow;
    settings setting;

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
        activeSubWindow.add(null);

    }


    public UISuperClass getActiveSubWindow() {
        return activeSubWindow.get(0);
    }

    /**
     * Sets the first element of the activeSubwindow list and removes any duplicates of the subwindow
     * @param subWindow the subwindow to be added
     */
    public void setActiveSubWindow(UISuperClass subWindow) {
     activeSubWindow.set(0, subWindow);

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
        subWindows.remove(closingSubWindow);
    }

    /**
     * Adds a given subwindow to the list of subwindows
     * @param addingSubWindow The subwindow to be added
     */
    public void addSubWindow(UISuperClass addingSubWindow){
        subWindows.add(addingSubWindow);
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

    public Integer[] relayCoordinates(int xCo, int yCo){
        Integer[] result = {-1,-1};
        for(UISuperClass subWindow:subWindows){
            List<Integer> info = subwindowInfo.get(subWindow);
            int X = info.get(0);
            int Y = info.get(1);
            int width = info.get(2);
            int height = info.get(3);
            if (xCo > X && xCo < X+width && yCo > Y && yCo < Y+height){
                setActiveSubWindow(subWindow);
                int relayX = xCo-X;
                int relayY = yCo-Y;
                result[0] = relayX;
                result[1] = relayY;
                return result;
            }

        }
        return result;
    }


}
