package settings;

import java.util.ArrayList;
import java.util.List;

public class CellVisualisationSettings {

    private List<Integer> widthList;
    private int cellHeight= 20;
   /* private int startX;
    private  int startY;*/
    private int width;
    private int height;
    private int defaultWidth = 100;


    public CellVisualisationSettings(){
        widthList = new ArrayList<>();
    }
    
    /* Unused?
    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }
    */

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

    public void setDefaultWidth(int defaultWidth) {
        this.defaultWidth = defaultWidth;
    }

    public int getDefaultWidth() {
        return defaultWidth;
    }


    public List<Integer> getWidthList() {
        return widthList;
    }

    public void setWidthList(List<Integer> widthList) {
        this.widthList = widthList;
    }

    public int getCellHeight() {
        return cellHeight;
    }

    public void setCellHeight(int height) {
        this.cellHeight = height;
    }

    public void removeFromWidthList(int index){
        widthList.remove(index);
    }



}
