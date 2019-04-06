package UserInterfaceElements;

import Data.*;
import EventHandlers.keyEventHandler;
import EventHandlers.mouseEventHandler;
import paintModule.paintModule;
import settings.settings;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UIRowModule extends UISuperClass {
    private paintModule paintModule;
    private EventHandlers.mouseEventHandler mouseEventHandler;
    private int xCoStart = 50;
    private int yCoStart = 50;
    private String currMode = "normal";
    private int[] activeCell;
    private Cell<?> tempText;
    private Boolean invalidInput = false;
    private int draggedColumn;
    private int draggedX;
    private Table table;


    //Constructor that init/creates paintModule and an empty list with tablenames
    //Each UImodule has own paintmodule to save settings.settings (e.g. size, bg, ...)
    public UIRowModule(Table tableInput) {
        paintModule = new paintModule();
        mouseEventHandler = new mouseEventHandler();
        table = tableInput;
    }

    //Handles mousevent and returns if UImode need to change
    /**
     * Eventhandler that takes care of a mouseclick, and returns the new state of the program and the nextUIview
     *
     * @param xCo x-coordinate of click
     * @param yCo y-coordinate of click
     * @param count number of  clicked
     * @param ID Id of mouseclick
     * @param data datacontroller to make changes to the data
     * @return returns a list with the nextUIMode and the state of the UI
     */
    public List<String> handleMouseEvent2(int xCo, int yCo, int count, int ID, dataController data) {
        List<Integer> widthList = table.getRowSetting().getWidthList();


        int[] clickedCell = mouseEventHandler.getCellID(xCo, yCo, paintModule.getxCoStart(), paintModule.getyCoStart(),
                paintModule.getCellHeight(), paintModule.getCellWidth(), table.getTableRows().size(), table.getColumnNames().size(), widthList);
        int lowestY = (table.getColumnNames().size() * paintModule.getCellHeight()) + paintModule.getyCoStart();

        //check if leftmargin is clicked
        if(clickedCell[1] == 0 && currMode != "edit" && mouseEventHandler.marginLeftClicked(xCo,yCo,paintModule.getxCoStart(),
                paintModule.getyCoStart(), paintModule.getCellHeight(), paintModule.getCellWidth(),
                table.getTableRows().size(), 1, paintModule.getCellLeftMargin(), widthList) != null) {
            currMode = "delete";
            activeCell = clickedCell;
        }
        //EVENT DOUBLE CLICKS UNDER TABLE
        if (currMode == "normal" && mouseEventHandler.doubleClickUnderTable(yCo, count, ID, table.getLengthTable() + paintModule.getyCoStart())) {
            Row row = new Row(table.getColumnNames());
            table.addRow(row);
        }

        //EVENT CELL CLICKED (VALID INPUT)
        else if (!invalidInput && ID == 500 && currMode!="edit" && currMode != "delete" && clickedCell[1] != -1 && clickedCell[0] != -1) {
            activeCell = clickedCell;
            currMode = "edit";
            if (table.getColumnNames().get(activeCell[1]).getType().equals("Boolean")) {

                tempText = table.getTableRows().get(activeCell[0]).getColumnList().get(activeCell[1]);

                if (tempText.getValue()== null) {
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
            }
        }


        //Check if header is clicked
        if (mouseEventHandler.rightBorderClicked(xCo, yCo, paintModule.getxCoStart(), paintModule.getyCoStart(), widthList.size(), paintModule.getCellHeight(), widthList) != -1) {
            System.out.println("RIGHT BORDER CLICKED");
            currMode = "drag";
            draggedColumn = mouseEventHandler.rightBorderClicked(xCo, yCo, paintModule.getxCoStart(), paintModule.getyCoStart(), widthList.size(), paintModule.getCellHeight(), widthList);
            draggedX = xCo;
            //Checks if user is dragging border
        } else if (currMode == "drag") {
            if (ID == 506) {
                int delta = xCo - draggedX;
                int previousWidth = widthList.get(draggedColumn);
                int newWidth = previousWidth + delta;
                int sum = widthList.stream().mapToInt(Integer::intValue).sum();
                if (newWidth >= paintModule.getMinCellWidth() && sum + delta < 590 - paintModule.getxCoStart()) {
                    widthList.set(draggedColumn, newWidth);
                    draggedX = xCo;
                }
            } else {
                currMode = "normal";
            }
        }

        //EVENT EXIT EDIT MODE
        else if(!invalidInput && currMode == "edit" && (clickedCell[0] == -1 || clickedCell[1] == -1)){
            saveText(data);
            currMode = "normal";
        }

        String nextUImode = "";
        List<String> result = new ArrayList<>();
        result.add(currMode);
        result.add("");
        return result;
    }


    /**
     * Method that handles key event when the UI is in edit-mode and returns state of program
     *
     * @param id id of key pressed
     * @param keyCode keycde of key pressed
     * @param keyChar keychar of key pressed
     * @param data datacontroller
     * @return returns a list with the nextUImode and the current mode of the UI
     */
    protected List<String> handleKeyEditMode(int id, int keyCode, char keyChar, dataController data) {
        keyEventHandler eventHandler = new keyEventHandler();
        String currName = table.getTableRows().get(activeCell[0]).getColumnList().get(activeCell[1]).getValue().toString(); // NOOIT GEBRUIKT, snap het nut niet
        //EVENT: ASCSII char pressed
        if (eventHandler.isChar(keyCode)) {
            tempText.addChar(keyChar);

            invalidInput = !textIsValid(tempText, data, currName);
        }

        //EVENT BS pressed and in edit mode
        else if (eventHandler.isBackspace(keyCode)) {

            //Check if string is not empty
            if (tempText.getValue().toString().length() != 0) {
                tempText.delChar();

                invalidInput = !textIsValid(tempText, data, currName);

            }
            //empty string, display red border
        }
        //EVENT ENTER pressed
        else if (eventHandler.isEnter(keyCode) && !invalidInput) {
            saveText(data);
            currMode = "normal";
        }

        List<String> result = new ArrayList<>();
        result.add("edit");
        result.add("");
        return result;

    }

    /**
     * Method that handles key event when the UI is in normal-mode and returns state of program
     *
     * @param id id of key pressed
     * @param keyCode keycde of key pressed
     * @param keyChar keychar of key pressed
     * @param data datacontroller
     * @return returns a list with the nextUImode and the current mode of the UI
     */
    protected List<String> handleKeyNormalMode(int id, int keyCode, char keyChar, dataController data) {
        //EVENT: t pressed
        String nextUIMode = "";
        keyEventHandler eventHandler = new keyEventHandler();
        if (keyCode == 27) {
            nextUIMode = "table";
        } else if(keyChar=='d'){
            nextUIMode = "design";

        }else if (keyCode == 17) {
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
     * @param id id of key pressed
     * @param keyCode keycde of key pressed
     * @param keyChar keychar of key pressed
     * @param data datacontroller
     * @return returns a list with the nextUImode and the current mode of the UI
     */
    protected List<String> handleKeyDeleteMode(int id, int keyCode, char keyChar, dataController data) {

        keyEventHandler eventHandler = new keyEventHandler();
        //DEL key pressed
        if(eventHandler.isDelete(keyCode) || keyChar == 'd'){



            Row row = table.getTableRows().get(activeCell[0]);
            table.deleteRow(row);
            currMode = "normal";
        }
        List<String> result = new ArrayList<>();
        result.add(currMode);
        result.add("");
        return result;
    }

    //Method that takes care of painting the canvas
    //It calls method from paintModule
    /**
     * Method that paints the correct view on the canvas
     *
     * @param g graphics object
     * @param data datacontroller
     */
    @Override
    public void paint(Graphics g,  dataController data, Integer[] coords, Integer[] dimensions) {
        List<Integer> widthList = table.getRowSetting().getWidthList();
        paintModule.setBackground(g,coords[0], coords[1], dimensions[0], dimensions[1], Color.WHITE);
        paintModule.paintBorderSubwindow( g, coords, dimensions, "Row Mode (" + table.getTableName() + ")");



        //print tables in tabular view
        paintModule.paintTable(g, table,coords[0] + paintModule.getMargin() +10, coords[1] + paintModule.getMargin() +10);


        //Check mode
        if (currMode == "edit" ) {
            int[] coords1 = paintModule.getCellCoords(activeCell[0], activeCell[1], widthList);
            if (tempText.getValue() != null) {
                paintModule.paintCursor(g, coords1[0] + coords[0],
                        coords1[1] + coords[1], widthList.get(activeCell[1]),
                        paintModule.getCellHeight(), tempText.getValue().toString());
            }



        }

        //check if there are warnings
        if (invalidInput || currMode == "delete") {
            int[] coords1 = paintModule.getCellCoords(activeCell[0], activeCell[1], widthList);
            paintModule.paintBorder(g, coords[0] + coords1[0],
                    coords[1] + coords1[1],  widthList.get(activeCell[1]),
                    paintModule.getCellHeight(), Color.RED);
        } else {
            paintModule.setColor(g, Color.BLACK);
        }

    }

    /**
     * Saves the edited text to the datacontroller
     *
     * @param data datacontroller
     */
    private void saveText(dataController data) {
        currMode = "normal";
        table.getTableRows().get(activeCell[0]).setColumnCell(activeCell[1], tempText);
    }

    /**
     * Checks if updated text is valid according to the place in the table it is updated
     *
     * @param text text to be validated
     * @param data datacontroller
     * @param currName old name
     * @return Wheter the text is in the correct fromat according to the type of it's cell
     */
    private boolean textIsValid(Cell text, dataController data, String currName) {



        String type = table.getColumnNames().get(activeCell[0]).getType();
        if (type.equals("String")) {
            if (!table.getColumnNames().get(activeCell[0]).getBlanksAllowed()) {
                if (((CellText) text).getValue().length() == 0) {
                    return false;
                }
            }
            return true;
        } else if (type.equals("Boolean")) {
        } else if (type.equals("Email")) {
            if (((CellEmail) text).getValue().contains("@")) {
                return true;
            } else return false;
        } else if (type.equals("Integer")) {

            try {
                Integer.parseInt( ((CellNumerical) text).getValue().toString() );
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }
}


