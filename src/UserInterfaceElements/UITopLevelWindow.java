package UserInterfaceElements;
import javafx.util.Pair;
import settings.settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UITopLevelWindow {

    UISuperClass activeSubWindow;
    settings setting;

    List<UISuperClass> subWindows;
    Map<UISuperClass, List<Integer>> subwindowInfo;

    /**
     * Constructor
     */
    public UITopLevelWindow(){
        setting = new settings();
        subWindows = new ArrayList<>();
        subwindowInfo = new HashMap<UISuperClass, List<Integer>>();

    }


    public UISuperClass getActiveSubWindow() {
        return activeSubWindow;
    }

    public void setActiveSubWindow(UISuperClass activeSubWindow) {
        this.activeSubWindow = activeSubWindow;
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
        values.add(50);}else{
            values.add(180);
            values.add(50);
        }
        //Default width
        values.add(100);
        values.add(100);
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


}
