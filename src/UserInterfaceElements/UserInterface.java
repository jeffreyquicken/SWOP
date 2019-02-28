package UserInterfaceElements;


public class UserInterface {
    /**
     * Class used for general UI data
     */
    private int cellHeight;
    private int cellWidth;
    private int defaultCellHeight = 20;
    private int defaultCellWidth = 20;

    public void setCellHeight(int cellHeight) {
        this.cellHeight = cellHeight;
    }

    public int getCellHeight() {
        return cellHeight;
    }

    public void setCellWidth(int cellWidth) {
        this.cellWidth = cellWidth;
    }

    public int getCellWidth() {
        return cellWidth;
    }

    public int getDefaultCellHeight() {
        return defaultCellHeight;
    }

    public int getDefaultCellWidth() {
        return defaultCellWidth;
    }

    public UserInterface(int cellH, int cellW){
        setCellHeight(cellH);
        setCellWidth(cellW);
    }

    public UserInterface(){
        UserInterface UI = new UserInterface(this.getDefaultCellHeight(), this.getDefaultCellWidth());
    }

}
