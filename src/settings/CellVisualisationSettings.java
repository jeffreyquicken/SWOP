package settings;

import java.util.ArrayList;
import java.util.List;

/**
 * A class of Cellvisualisationsettings involving the width and height of cells
 */
public class CellVisualisationSettings {

    /**
     * The widthlist of the cells
     */
    private List<Integer> widthList;
    private int cellHeight= 20;
   /* private int startX;
    private  int startY;*/

    /**
     * the width of a cell
     */
    private int width;

    /**
     * the height of a cell
     */
    private int height;

    /**
     * the default width of a cell
     */
    private int defaultWidth = 100;


    public CellVisualisationSettings(){
        widthList = new ArrayList<>();
    }


    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }


    public int getDefaultWidth() {
        return defaultWidth;
    }


    public List<Integer> getWidthList() {
        return widthList;
    }


    public void removeFromWidthList(int index){
        widthList.remove(index);
    }



}
