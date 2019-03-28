package UserInterfaceElements;
import settings.settings;

import java.util.List;

public class UITopLevelWindow {

    UISuperClass activeSubWindow;
    settings setting;
    List<UISuperClass> subWindows;

    /**
     * Constructor
     */
    public UITopLevelWindow(){
        setting = new settings();
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
    }

}
