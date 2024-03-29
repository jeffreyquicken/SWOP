package UserInterfaceElements;

import Data.*;
import SQLQuery.Query;
import UndoRedo.Command;
import UndoRedo.NewRow;
import UndoRedo.RowValue;
import events.KeyEvent;
import events.MouseEvent;
import paintModule.RowModePaintModule;
import settings.scrollbar;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static SQLQuery.SQLParser.parseQuery;

/**
 * Class of UIComputedmodule, extending the UISuperclass. Involving methods for painting and getting the computed table
 */
public class UIComputedModule extends UISuperClass {
    /**
     * the paintmodule for the rows
     */
    private RowModePaintModule paintModule;

    /**
     * mouse eventhandler
     */
    private events.MouseEvent mouseEventHandler;

    /**
     * starting x coordinate
     */
    private int xCoStart = 50;

    /**
     * starting y coordinate
     */
    private int yCoStart = 50;

    /**
     * the current mode
     */
    private String currMode = "normal";

    /**
     * old value
     */
    private Cell oldValue;

    /**
     * query object
     */
    private String Query;

    /**
     * teh active cell coordinates
     */
    private int[] activeCell;

    /**
     * temporary text
     */
    private Cell<?> tempText;

    /**
     * whether there is invalid input
     */
    private Boolean invalidInput = false;

    /**
     * the index of the dragged column
     */
    private int draggedColumn;

    /**
     * The x coordinate of the drag
     */
    private int draggedX;

    /**
     * the table associated with this module
     */
    private Table table;

    /**
     * String format of query
     */
    private String queryString;

    public String getQuery() {
        return Query;
    }

    public void setQuery(String query) {
        Query = query;
    }


    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }


    public int[] getActiveCell() {
        return activeCell;
    }

    //

    /**
     * Constructor that init/creates paintModule and an empty list with tablenames
     * Each UImodule has own paintmodule to save settings.settings (e.g. size, bg, ...)
     *
     * @param tableInput the table associated with this module
     * @param data       datacontroller
     * @param qString    the query string
     */
    public UIComputedModule(Table tableInput, dataController data, String qString) {
        paintModule = new RowModePaintModule();
        mouseEventHandler = new MouseEvent();
        table = tableInput;
        queryString = qString;


    }


    /**
     * Eventhandler that takes care of a mouseclick, and returns the new state of the program and the nextUIview
     *
     * @param xCo   x-coordinate of click
     * @param yCo   y-coordinate of click
     * @param count number of  clicked
     * @param ID    Id of mouseclick
     * @param data  datacontroller to make changes to the data
     * @return returns a list with the nextUIMode and the state of the UI
     */
    public List<String> handleMouseEvent2(int xCo, int yCo, int count, int ID, dataController data, Integer[] dimensions) {
        List<Integer> widthList = table.getRowSetting().getWidthList();
        int[] clickedCell = mouseEventHandler.getCellID(xCo, yCo, paintModule.getxCoStart(), paintModule.getyCoStart(),
                paintModule.getCellHeight(), paintModule.getCellWidth(), table.getTableRows().size(), table.getColumnNames().size(), widthList, scrollbar);

        int lowestY = (table.getColumnNames().size() * paintModule.getCellHeight()) + paintModule.getyCoStart();
        //check if leftmargin is clicked

        if (currMode != "edit" && scrollbarClicked(xCo, yCo, dimensions)) {
        }


        if (mouseEventHandler.rightBorderClicked(xCo, yCo, paintModule.getxCoStart(), paintModule.getyCoStart(), widthList.size(), paintModule.getCellHeight(), widthList) != -1) {
            startDrag(xCo, yCo, widthList);
            //Checks if user is dragging border
        } else if (currMode == "drag") {
            handleBorderDragging(xCo, ID, data, dimensions, widthList);
        }

        String nextUImode = "";
        List<String> result = new ArrayList<>();
        result.add(currMode);
        result.add("");
        return result;
    }

    /**
     * Method that starts the dragevent
     *
     * @param xCo       x coordinate of click
     * @param yCo       y coordinate of click
     * @param widthList widthlist
     */
    private void startDrag(int xCo, int yCo, List<Integer> widthList) {
        System.out.println("RIGHT BORDER CLICKED");
        currMode = "drag";
        draggedColumn = mouseEventHandler.rightBorderClicked(xCo, yCo, paintModule.getxCoStart(), paintModule.getyCoStart(), widthList.size(), paintModule.getCellHeight(), widthList);
        draggedX = xCo;
    }


    /**
     * Method that handles the border dragging
     *
     * @param xCo        x coordinate of click
     * @param ID         click id
     * @param data       datacontroller
     * @param dimensions dimensions
     * @param widthList  widthlist
     */
    private void handleBorderDragging(int xCo, int ID, dataController data, Integer[] dimensions,
                                      List<Integer> widthList) {
        if (ID == 506) {
            int delta = xCo - draggedX;
            int previousWidth = widthList.get(draggedColumn);
            int newWidth = previousWidth + delta;
            int sum = widthList.stream().mapToInt(Integer::intValue).sum();
            if (newWidth >= paintModule.getMinCellWidth() && sum + delta < 590 - paintModule.getxCoStart()) {
                widthList.set(draggedColumn, newWidth);
                draggedX = xCo;
                recalculateScrollbar(data, dimensions);
            }
        } else {
            currMode = "normal";
        }
    }


    /**
     * Method that paints the correct view on the canvas
     *
     * @param g    graphics object
     * @param data datacontroller
     */
    public void paint(Graphics g, dataController data, Integer[] coords, Integer[] dimensions) {

        recomputeTable(data);

        List<Integer> widthList = table.getRowSetting().getWidthList();
        int sum = widthList.stream().mapToInt(Integer::intValue).sum();
        paintWindowBasics(g, data, coords, dimensions, sum);
        //Check mode
        if (currMode == "edit") {
            paintEditMode(g, widthList, dimensions, coords);

        }
        //check if there are warnings
        if (invalidInput || currMode == "delete") {
            paintDeleteMode(g, widthList, dimensions, coords);

        } else {
            paintModule.setColor(g, Color.BLACK);
        }

    }

    /**
     * Method that paint when the user is editing
     *
     * @param g          graphics
     * @param widthList  widthlist
     * @param dimensions dimensions
     * @param coords     coordinates
     */
    private void paintEditMode(Graphics g, List<Integer> widthList, Integer[] dimensions, Integer[] coords) {
        int[] coords1 = paintModule.getCellCoords(activeCell[0], activeCell[1], widthList, scrollbar, dimensions[1]);
        if (tempText.getValue() != null) {
            paintModule.paintCursor(g, coords1[0] + coords[0],
                    coords1[1] + coords[1], widthList.get(activeCell[1]),
                    paintModule.getCellHeight(), tempText.getValue().toString());
        }
    }

    /**
     * Method that paint when the user is editing
     *
     * @param g          graphics
     * @param widthList  widthlist
     * @param dimensions dimensions
     * @param coords     coordinates
     */
    private void paintDeleteMode(Graphics g, List<Integer> widthList, Integer[] dimensions, Integer[] coords) {
        int[] coords1 = paintModule.getCellCoords(activeCell[0], activeCell[1], widthList, scrollbar, dimensions[1]);
        paintModule.paintBorder(g, coords[0] + coords1[0],
                coords[1] + coords1[1], widthList.get(activeCell[1]),
                paintModule.getCellHeight(), Color.RED);
    }


    /**
     * Method that paints the basic window elements
     *
     * @param g          graphics
     * @param data       datacontroller
     * @param coords     coordinates of window
     * @param dimensions dimensions of window
     * @param sum        sum of widths of columns to be painted in the windwo
     */
    private void paintWindowBasics(Graphics g, dataController data, Integer[] coords, Integer[] dimensions, int sum) {
        recalculateScrollbar(data, dimensions);
        paintModule.setBackground(g, coords[0], coords[1], dimensions[0], dimensions[1], Color.WHITE);
        paintModule.paintBorderSubwindow(g, coords, dimensions, "Computed Mode (" + table.getTableName() + ")", this.getActive());
        recalculateScrollbar(data, dimensions);
        paintModule.paintHScrollBar(g, coords[0], coords[1] + dimensions[1] - 10, dimensions[0], scrollbar.getPercentageHorizontal(), scrollbar);
        paintModule.paintVScrollBar(g, coords[0] + dimensions[0] - 10, coords[1] + 15, dimensions[1] - 15, scrollbar.getPercentageVertical(), scrollbar);
        paintModule.paintRowModeView(g, table, coords[0] + paintModule.getMargin(), coords[1] + paintModule.getMargin(), dimensions[0] - 48, dimensions[1] - 58, scrollbar, dimensions[1], sum); // removed the "+10" behind the getmargin so that marggins are the same as tables mode
    }

    /**
     * method that recomputes the table with a query
     *
     * @param data datacontroller
     */
    public void recomputeTable(dataController data) {

        this.setTable(this.getComputedTable(data));

    }

    /**
     * Method that parses the query and returns the computed table for a query
     *
     * @param data the datacontroller
     * @return the computed table
     */
    public Table getComputedTable(dataController data) {

        //Query query = parseQuery("SELECT movie.title AS title FROM movies AS movie WHERE movie.imdb_score < 7");
        SQLQuery.Query query = parseQuery(queryString);
        Table computedTable = query.getComputedTable(data);
        return computedTable;

    }

    /**
     * Method that recaculates the scrollbar position and percentages
     *
     * @param data       datacontroller
     * @param dimensions dimension of window
     */
    private void recalculateScrollbar(dataController data, Integer[] dimensions) {
        //WIDTHLIST IS NOT SAME FOR EVERY MODULE !!!!!!!!!!!!!
        List<Integer> widthList = table.getRowSetting().getWidthList();

        int sum = widthList.stream().mapToInt(Integer::intValue).sum();
        scrollbarActive = false;
        if (sum > dimensions[0] - 31) {
            percentageHorizontal = (Double.valueOf(dimensions[0] - 30) / Double.valueOf(sum));
            scrollbar.setPercentageHorizontal(percentageHorizontal);
            scrollbar.setActiveHorizontal(true);
            System.out.println(percentageHorizontal);
        } else {
            scrollbar.setActiveHorizontal(false);
            scrollbar.setPercentageHorizontal(0);
            scrollbar.setOffsetpercentageHorizontal(0);


        }
        //TABLE LIST
        if (table.getTableRows().size() * paintModule.getCellHeight() > dimensions[1] - 46) {
            percentageVertical = (Double.valueOf(dimensions[1] - 46) / Double.valueOf((table.getTableRows().size() * paintModule.getCellHeight())));
            scrollbar.setPercentageVertical(percentageVertical);
            scrollbar.setActiveVertical(true);
            System.out.println(percentageVertical);
        } else {
            scrollbar.setPercentageVertical(0);
            scrollbar.setActiveVertical(false);
            scrollbar.setOffsetpercentageVertical(0);
        }
    }
}
