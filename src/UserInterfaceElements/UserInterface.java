package UserInterfaceElements;

/**
 * Class used for general UI data
 */
public class UserInterface {
    /**
     * height of a cell
     */
    private int cellHeight;
    /**
     * width of a cell
     */
    private int cellWidth;

    /**
     * default height of a cell
     */
    private int defaultCellHeight = 20;
    /**
     * default width of a cell
     */
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

    /**
     * constructor for userinterface
     *
     * @param cellH
     * @param cellW
     */
    public UserInterface(int cellH, int cellW) {
        setCellHeight(cellH);
        setCellWidth(cellW);
    }

    public UserInterface() {
        UserInterface UI = new UserInterface(this.getDefaultCellHeight(), this.getDefaultCellWidth());
    }

}
