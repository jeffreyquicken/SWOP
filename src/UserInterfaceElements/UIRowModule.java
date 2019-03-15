package UserInterfaceElements;

import Data.Column;
import Data.Row;
import Data.Table;
import Data.dataController;
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
    private String tempText;
    private Boolean invalidInput = false;
    private int draggedColumn;
    private int draggedX;


    //Constructor that init/creates paintModule and an empty list with tablenames
    //Each UImodule has own paintmodule to save settings.settings (e.g. size, bg, ...)
    public UIRowModule() {
        paintModule = new paintModule();
        mouseEventHandler = new mouseEventHandler();
    }

    //Handles mousevent and returns if UImode need to change
    public List<String> handleMouseEvent2(int xCo, int yCo, int count, int ID, dataController data) {
        List<Integer> widthList = data.getSelectedTable().getRowSetting().getWidthList();


        int[] clickedCell = mouseEventHandler.getCellID(xCo, yCo, paintModule.getxCoStart(), paintModule.getyCoStart(),
                paintModule.getCellHeight(), paintModule.getCellWidth(), data.getSelectedTable().getTableRows().size(), data.getSelectedTable().getColumnNames().size(), widthList);
        int lowestY = (data.getSelectedTable().getColumnNames().size() * paintModule.getCellHeight()) + paintModule.getyCoStart();

        //check if leftmargin is clicked
        if(clickedCell[1] == 0 && currMode != "edit" && mouseEventHandler.marginLeftClicked(xCo,yCo,paintModule.getxCoStart(),
                paintModule.getyCoStart(), paintModule.getCellHeight(), paintModule.getCellWidth(),
                data.getSelectedTable().getTableRows().size(), 1, paintModule.getCellLeftMargin(), widthList) != null) {
            currMode = "delete";
            activeCell = clickedCell;
        }
        //EVENT DOUBLE CLICKS UNDER TABLE
        if (currMode == "normal" && mouseEventHandler.doubleClickUnderTable(yCo, count, ID, data.getSelectedTable().getLengthTable() + paintModule.getyCoStart())) {
            Row row = new Row(data.getSelectedTable().getColumnNames());
            data.getSelectedTable().addRow(row);
        }

        //EVENT CELL CLICKED (VALID INPUT)
        else if (!invalidInput && ID == 500 && currMode!="edit" && currMode != "delete" && clickedCell[1] != -1 && clickedCell[0] != -1) {
            activeCell = clickedCell;
            currMode = "edit";
            if (data.getSelectedTable().getColumnNames().get(activeCell[1]).getType().equals("Boolean")) {

                tempText = data.getSelectedTable().getTableRows().get(activeCell[0]).getColumnList().get(activeCell[1]);

                if (tempText.equals("true")) {
                    tempText = "false";
                } else if (tempText.equals("false")) {
                    if (data.getSelectedTable().getColumnNames().get(activeCell[1]).getBlanksAllowed()) {
                        tempText = "empty";
                    } else {
                        tempText = "true";
                    }

                } else {
                    tempText = "true";
                }
                saveText(data);

            } else {
                tempText = data.getSelectedTable().getTableRows().get(activeCell[0]).getColumnList().get(activeCell[1]);
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

        String nextUImode = "row";
        List<String> result = new ArrayList<>();
        result.add(currMode);
        result.add("row");
        return result;
    }


    protected List<String> handleKeyEditMode(int id, int keyCode, char keyChar, dataController data) {
        keyEventHandler eventHandler = new keyEventHandler();
        String currName = data.getSelectedTable().getTableRows().get(activeCell[0]).getColumnList().get(activeCell[1]);
        //EVENT: ASCSII char pressed
        if (eventHandler.isChar(keyCode)) {
            tempText = tempText + keyChar;

            invalidInput = !textIsValid(tempText, data, currName);
        }

        //EVENT BS pressed and in edit mode
        else if (eventHandler.isBackspace(keyCode)) {

            //Check if string is not empty
            if (tempText.length() != 0) {
                tempText = tempText.substring(0, tempText.length() - 1);
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
        result.add("row");
        return result;

    }

    protected List<String> handleKeyNormalMode(int id, int keyCode, char keyChar, dataController data) {
        //EVENT: t pressed
        String nextUIMode = "row";
        keyEventHandler eventHandler = new keyEventHandler();
        if (keyCode == 27) {
            nextUIMode = "table";
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

    protected List<String> handleKeyDeleteMode(int id, int keyCode, char keyChar, dataController data) {

        keyEventHandler eventHandler = new keyEventHandler();
        //DEL key pressed
        if(eventHandler.isDelete(keyCode) || keyChar == 'd'){



            Row row = data.getSelectedTable().getTableRows().get(activeCell[0]);
            data.getSelectedTable().deleteRow(row);
            currMode = "normal";
        }
        List<String> result = new ArrayList<>();
        result.add(currMode);
        result.add("row");
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
    public void paint(Graphics g, Table table, dataController data) {
        List<Integer> widthList = data.getSelectedTable().getRowSetting().getWidthList();

        //Creates title
        paintModule.paintTitle(g, "Row Mode");

        //print tables in tabular view
        paintModule.paintTable(g, table, xCoStart, yCoStart);


        //Check mode
        if (currMode == "edit") {
            paintModule.paintCursor(g, paintModule.getCellCoords(activeCell[0], activeCell[1], widthList)[0],
                    paintModule.getCellCoords(activeCell[0], activeCell[1], widthList)[1], widthList.get(activeCell[1]),
                    paintModule.getCellHeight(), tempText);
        }

        //check if there are warnings
        if (invalidInput || currMode == "delete") {
            paintModule.paintBorder(g, paintModule.getCellCoords(activeCell[0], activeCell[1], widthList)[0],
                    paintModule.getCellCoords(activeCell[0], activeCell[1], widthList)[1], widthList.get(activeCell[1]),
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
        data.getSelectedTable().getTableRows().get(activeCell[0]).setColumnCell(activeCell[1], tempText);
    }

    private boolean textIsValid(String text, dataController data, String currName) {


        String type = data.getSelectedTable().getColumnNames().get(activeCell[0]).getType();
        if (type.equals("String")) {
            if (!data.getSelectedTable().getColumnNames().get(activeCell[0]).getBlanksAllowed()) {
                if (text.length() == 0) {
                    return false;
                }
            }
            return true;
        } else if (type.equals("Boolean")) {
        } else if (type.equals("Email")) {
            if (text.contains("@")) {
                return true;
            } else return false;
        } else if (type.equals("Integer")) {

            try {
                Integer.parseInt(text);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }
}


