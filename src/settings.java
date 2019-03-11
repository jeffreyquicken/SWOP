import java.util.List;

public class settings {

    private List<Integer> widthList;
    private int height= 20;
    private int defaultWidth = 100;

    public settings(int numberOfCols){
        for ( int i = 0; i < numberOfCols; i++){
            widthList.add(defaultWidth);
        }
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



}
