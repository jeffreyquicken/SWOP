package UserInterfaceElements;

import Data.*;
import UndoRedo.Command;
import UndoRedo.NewRow;
import UndoRedo.RowValue;
import events.MouseEvent;
import events.KeyEvent;
import paintModule.PaintModule;
import paintModule.RowModePaintModule;
import settings.CellVisualisationSettings;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class of UIRowmodule extending the UISuperclass. Involving methods for painting and handling key and mouse events.
 */
public class UIRowModule extends UISuperClass {

    /**
     * The paintmodule for the rows
     */
    private RowModePaintModule paintModule;

    /**
     * the mouse event handler
     */
    private events.MouseEvent mouseEventHandler;

    private int xCoStart = 50;
    private int yCoStart = 50;
    //private String currMode = "normal";

    /**
     * the old value cell
     */
    private Cell oldValue;

    /**
     * The active cell
     */
    private int[] activeCell;

    /**
     * the temporary text cell
     */
    private Cell<?> tempText;
    /**
     * whether there is false input in the window
     */
    private Boolean invalidInput = false;
    /**
     * the index of the column that is dragged
     */
    private int draggedColumn;
    /**
     * the x coordinate of the mouse when dragging
     */
    private int draggedX;

    /**
     * the table associated with this module
     */
    private Table table;

    public int[] getActiveCell() {
        return activeCell;
    }


    /**
     * Constructor that init/creates paintModule and an empty list with tablenames
     *
     * @param tableInput the table associated with this module
     */
    public UIRowModule(Table tableInput) {
        paintModule = new RowModePaintModule();
        mouseEventHandler = new MouseEvent();
        table = tableInput;

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
        if (isClickedLeftMargin(xCo, yCo, widthList, clickedCell)) {
            currMode = "delete";
            activeCell = clickedCell;
        } else if (currMode != "edit" && scrollbarClicked(xCo, yCo, dimensions)) {
        }
        //EVENT DOUBLE CLICKS UNDER TABLE
        else if (currMode == "normal" && mouseEventHandler.doubleClickUnderTable(yCo, count, ID, table.getLengthTable() + paintModule.getyCoStart())) {
            DoubleClickUnderRowMode(data);
        }
        //EVENT CELL CLICKED (VALID INPUT)
        else if (!invalidInput && ID == 500 && currMode != "edit" && currMode != "delete" && clickedCell[1] != -1 && clickedCell[0] != -1) {
            handleCellClickedValidInput(data, clickedCell);
        }
        if (mouseEventHandler.rightBorderClicked(xCo, yCo, paintModule.getxCoStart(), paintModule.getyCoStart(), widthList.size(), paintModule.getCellHeight(), widthList) != -1) {
            startDrag(xCo, yCo, widthList);
            //Checks if user is dragging border
        } else if (currMode == "drag") {
            handleBorderDragging(xCo, ID, data, dimensions, widthList);
        }
        //EVENT EXIT EDIT MODE
        else if (!invalidInput && currMode == "edit" && (clickedCell[0] == -1 || clickedCell[1] == -1)) {
            exitEditMode(data);
        }
        String nextUImode = "";
        List<String> result = new ArrayList<>();
        result.add(currMode);
        result.add("");
        return result;
    }

    /**
     * Method that exits edit mode and saves temptext to database
     *
     * @param data datacontroller
     */
    public void exitEditMode(dataController data) {
        saveText(data);
        //action needs to be added to operations list
        int[] cid = new int[3];
        cid[0] = data.getTableList().indexOf(table);
        cid[1] = activeCell[0];
        cid[2] = activeCell[1];
        Command c = new RowValue(cid, tempText, oldValue, data);
        data.addCommand(c);
        currMode = "normal";
    }

    /**
     * Method that handles double click under table
     *
     * @param data
     */
    public void DoubleClickUnderRowMode(dataController data) {
        Row row = new Row(table.getColumnNames());
        table.addRow(row);
        //action needs to be added to operations list
        Command c = new NewRow(data.getTableList().indexOf(table), row, data);
        data.addCommand(c);
    }

    /**
     * Method that starts the dragevent
     *
     * @param xCo       xcoordinate of the click
     * @param yCo       y coordinate of the click
     * @param widthList the widthlist
     */
    private void startDrag(int xCo, int yCo, List<Integer> widthList) {
        System.out.println("RIGHT BORDER CLICKED");
        currMode = "drag";
        draggedColumn = mouseEventHandler.rightBorderClicked(xCo, yCo, paintModule.getxCoStart(), paintModule.getyCoStart(), widthList.size(), paintModule.getCellHeight(), widthList);
        draggedX = xCo;
    }

    /**
     * Method that checks whether the left margin is clicked
     *
     * @param xCo         the x coordinate
     * @param yCo         the y coordinate of the module
     * @param widthList   the widthlist
     * @param clickedCell clicked cell coordinates
     * @return whether the left margin is clicked
     */
    private boolean isClickedLeftMargin(int xCo, int yCo, List<Integer> widthList, int[] clickedCell) {
        return clickedCell[1] == 0 && currMode != "edit" && mouseEventHandler.marginLeftClicked(xCo, yCo, paintModule.getxCoStart(),
                paintModule.getyCoStart(), paintModule.getCellHeight(), paintModule.getCellWidth(),
                table.getTableRows().size(), 1, paintModule.getCellLeftMargin(), widthList) != null;
    }

    /**
     * Method that handles a clicked cell when there is valid input
     *
     * @param data        datacontroller
     * @param clickedCell clicked cell coordinates
     */
    private void handleCellClickedValidInput(dataController data, int[] clickedCell) {
        activeCell = clickedCell;
        currMode = "edit";
        if (table.getColumnNames().get(activeCell[1]).getType().equals("Boolean")) {
            tempText = table.getTableRows().get(activeCell[0]).getColumnList().get(activeCell[1]);
            oldValue = new CellBoolean(((CellBoolean) tempText).getValue());
            if (tempText.getValue() == null) {
                ((CellBoolean) tempText).setValue(true);
            } else if (tempText.getValue().equals(false)) {
                if (table.getColumnNames().get(activeCell[1]).getBlanksAllowed()) {
                    tempText.setValue(null);
                } else {
                    ((CellBoolean) tempText).setValue(true);
                }
            } else {
                ((CellBoolean) tempText).setValue(false);
            }
            saveText(data);
        } else {
            tempText = table.getTableRows().get(activeCell[0]).getColumnList().get(activeCell[1]);
            if (tempText.getClass() == CellText.class) {
                oldValue = new CellText(((CellText) tempText).getValue());
            } else if (tempText.getClass() == CellEmail.class) {
                oldValue = new CellEmail(((CellEmail) tempText).getValue());
            } else if (tempText.getClass() == CellInteger.class) {
                oldValue = new CellInteger(((CellInteger) tempText).getValue());
            }
        }
    }

    /**
     * Method that handles the dragging of a window border
     *
     * @param xCo        x coordinate of the drag
     * @param ID         mouse id
     * @param data       datacontroller
     * @param dimensions dimensions of window
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
     * Method that handles key event when the UI is in edit-mode and returns state of program
     *
     * @param id      id of key pressed
     * @param keyCode keycde of key pressed
     * @param keyChar keychar of key pressed
     * @param data    datacontroller
     * @return returns a list with the nextUImode and the current mode of the UI
     */
    protected List<String> handleKeyEditMode(int id, int keyCode, char keyChar, dataController data) {
        KeyEvent eventHandler = new KeyEvent();
        List<String> result = new ArrayList<>();
        result.add("edit");
        result.add("");
        //String currName = table.getTableRows().get(activeCell[0]).getColumnList().get(activeCell[1]).getValue().toString(); // NOOIT GEBRUIKT, snap het nut niet
        //EVENT: ASCSII char pressed
        if (eventHandler.isChar(keyCode)) {
            ((CellEditable) tempText).addChar(keyChar);
            invalidInput = !textIsValid(tempText, data);
            result.add(0, "edit");
        }

        //EVENT BS pressed and in edit mode
        else if (eventHandler.isBackspace(keyCode)) {

            //Check if string is not empty
            if (tempText.getValue().toString().length() != 0) {
                ((CellEditable) tempText).delChar();

                invalidInput = !textIsValid(tempText, data);

            }
            result.add(0, "edit");
            //empty string, display red border
        }
        //EVENT ENTER pressed
        else if (eventHandler.isEnter(keyCode) && !invalidInput) {
            saveText(data);
            //action needs to be added to operations list
            int[] cid = new int[3];
            cid[0] = data.getTableList().indexOf(table);
            cid[1] = activeCell[0];
            cid[2] = activeCell[1];
            Command c = new RowValue(cid, tempText, oldValue, data);
            data.addCommand(c);
            currMode = "normal";
            result.add(0, "normal");
        }
        result.add("");
        return result;

    }

    /**
     * Method that handles key event when the UI is in normal-mode and returns state of program
     *
     * @param id      id of key pressed
     * @param keyCode keycde of key pressed
     * @param keyChar keychar of key pressed
     * @param data    datacontroller
     * @return returns a list with the nextUImode and the current mode of the UI
     */
    protected List<String> handleKeyNormalMode(int id, int keyCode, char keyChar, dataController data) {
        //EVENT: t pressed
        String nextUIMode = "";
        KeyEvent eventHandler = new KeyEvent();
        if (keyCode == 27) {
            nextUIMode = "table";
        } else if (keyChar == 'd') {
            nextUIMode = "design";

        } else if (keyCode == 17) {
            ctrlPressed = true;
        } else if (ctrlPressed) {
            if (eventHandler.isEnter(keyCode)) {
                nextUIMode = "design";
                ctrlPressed = false;
            }
        } else {
            ctrlPressed = false;
        }
        List<String> result = new ArrayList<>();
        result.add("normal");
        result.add(nextUIMode);
        return result;

    }

    /**
     * Method that handles key event when the UI is in delete-mode and returns state of program
     *
     * @param id      id of key pressed
     * @param keyCode keycde of key pressed
     * @param keyChar keychar of key pressed
     * @param data    datacontroller
     * @return returns a list with the nextUImode and the current mode of the UI
     */
    protected List<String> handleKeyDeleteMode(int id, int keyCode, char keyChar, dataController data) {

        KeyEvent eventHandler = new KeyEvent();
        //DEL key pressed
        if (eventHandler.isDelete(keyCode) || keyChar == 'd') {


            Row row = table.getTableRows().get(activeCell[0]);
            table.deleteRow(row);
            currMode = "normal";
        }
        List<String> result = new ArrayList<>();
        result.add(currMode);
        result.add("");
        return result;
    }

    /**
     * Method that paints the correct view on the canvas
     *
     * @param g    graphics object
     * @param data datacontroller
     */

    @Override
    public void paint(Graphics g, dataController data, Integer[] coords, Integer[] dimensions) {
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
     * Method that paints the basiw window elements
     *
     * @param g          graphics object
     * @param data       datacontroller
     * @param coords     coordinates of the window
     * @param dimensions dimensions of the window
     * @param sum        sum of all the column widths that should be displayed in the window
     */
    private void paintWindowBasics(Graphics g, dataController data, Integer[] coords, Integer[] dimensions, int sum) {
        recalculateScrollbar(data, dimensions);
        paintModule.setBackground(g, coords[0], coords[1], dimensions[0], dimensions[1], Color.WHITE);
        paintModule.paintBorderSubwindow(g, coords, dimensions, "Row Mode (" + table.getTableName() + ")", this.getActive());
        recalculateScrollbar(data, dimensions);
        paintModule.paintHScrollBar(g, coords[0], coords[1] + dimensions[1] - 10, dimensions[0], scrollbar.getPercentageHorizontal(), scrollbar);
        paintModule.paintVScrollBar(g, coords[0] + dimensions[0] - 10, coords[1] + 15, dimensions[1] - 15, scrollbar.getPercentageVertical(), scrollbar);
        paintModule.paintRowModeView(g, table, coords[0] + paintModule.getMargin(), coords[1] + paintModule.getMargin(), dimensions[0] - 48, dimensions[1] - 58, scrollbar, dimensions[1], sum); // removed the "+10" behind the getmargin so that marggins are the same as tables mode
    }

    /**
     * Saves the edited text to the datacontroller
     *
     * @param data datacontroller
     */
    private void saveText(dataController data) {
        currMode = "normal";
        //table.getTableRows().get(activeCell[0]).setColumnCell(activeCell[1], tempText);
        table.getTableRows().get(activeCell[0]).getColumnList().get((activeCell[1])).setValue(tempText.getString());
    }

    /**
     * Checks if updated text is valid according to the place in the table it is updated
     *
     * @param text text to be validated
     * @param data datacontroller
     * @return Whether the text is in the correct format according to the type of its cell
     */
    private boolean textIsValid(Cell text, dataController data) {
        String type = table.getColumnNames().get(activeCell[0]).getType();

        if (table.getColumnNames().get(activeCell[0]).getBlanksAllowed() && text.getString().length() == 0) {
            return true;
        }
        if (type.equals("String")) {
            if (!table.getColumnNames().get(activeCell[0]).getBlanksAllowed()) {
                if (text.getString().length() == 0) {
                    return false;
                }
            }
            return true;
        } else if (type.equals("Boolean")) {
            if (text.getString().equals("true") || text.getString().equals("True") || text.getString().equals("false") || text.getString().equals("False") || text.getString().equals("empty")) {
                return true;
            } else {
                return false;
            }

        } else if (type.equals("Email")) {
            if (text.getString().contains("@")) {
                return true;
            } else return false;
        } else if (type.equals("Integer")) {

            try {
                Integer.parseInt(text.getString());
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }


    /**
     * Method that recalculates the size and location of the scrollbar
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


