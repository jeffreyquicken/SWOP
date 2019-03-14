package settings;

import java.util.ArrayList;
import java.util.List;

public class settings {

    private List<Integer> widthList;
    private int height= 20;

    public int getDefaultWidth() {
        return defaultWidth;
    }

    private int defaultWidth = 100;

    public settings(){
        widthList = new ArrayList<>();

    }

    public List<Integer> getWidthList() {
        return widthList;
    }

    public void setWidthList(List<Integer> widthList) {
        this.widthList = widthList;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void removeFromWidthList(int index){
        widthList.remove(index);
    }



}
