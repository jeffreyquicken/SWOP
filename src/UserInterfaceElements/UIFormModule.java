package UserInterfaceElements;

import Data.*;
import events.KeyEvent;
import events.MouseEvent;
import paintModule.DesignModePaintModule;
import paintModule.FormModePaintModule;
import settings.CellVisualisationSettings;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UIFormModule extends UISuperClass{
    private Table table;
    private Cell tempText;
    private FormModePaintModule paintModule;
    private events.MouseEvent mouseEventHandler;

    /**
     * whether the row index is in range of the number of rows
     */
    private boolean rowNotOutOfRange;

    /**
     * Index of row to be painted as a form
     */
    private int rowIndex;

    public UIFormModule(Table inputTable){
        paintModule = new FormModePaintModule();
        mouseEventHandler = new MouseEvent();
        invalidInput = false;
        table = inputTable;
        rowIndex = 0;
        rowNotOutOfRange = true;
    }

    /**
     * Eventhandler that takes care of a mouseclick, and returns the new state of the program
     *
     * @param xCo x-coordinate of click
     * @param yCo y-coordinate of click
     * @param count number of  clicked
     * @param ID Id of mouseclick
     * @param data datacontroller to make changes to the data
     * @return returns a list with the nextUIMode and the state of the UI
     */
    /*
     * TO REFACTOR
     * too long
     */
    public List<String> handleMouseEvent2(int xCo, int yCo, int count, int ID, dataController data, Integer[] dimensions) {
        String nextUImode = "";


        //EVENT CLICK CELL
        //TODO: check if margin clicked

        List<Integer> widthList = table.getFormSetting().getWidthList();
        int[] clickedCell = mouseEventHandler.getCellID(xCo, yCo, paintModule.getxCoStart(), paintModule.getyCoStart(),
                paintModule.getCellHeight(), paintModule.getCellWidth(),table.getColumnNames().size(), 1,widthList, scrollbar);
        System.out.println("CELL CLICKED: " + clickedCell);
        //int[] clickedCell = mouseEventHandler.getCellID(xCo, yCo, 10, 10,
        //  paintModule.getCellHeight(), paintModule.getCellWidth(), data.getTableList().size(), 1,widthList);
        //Checks if user is dragging border
        if(currMode == "drag"){
            handleDragEvent(xCo, ID, data, dimensions, widthList);
        }

        else if(currMode!= "edit" && scrollbarClicked(xCo,yCo,dimensions)){
        }
        else if (!invalidInput  && currMode!= "delete" && clickedCell[1] != -1 && clickedCell[0] != -1 && clickedCell[1] != 0) {
            handleClickOnCell(data, clickedCell);
        }

        //EVENT edit mode and clicked outside table
        else if (currMode == "edit"  && !invalidInput) {
            currMode = "normal";
            setTempText(tempText, data);
        }
        else if (currMode == "delete"){
            currMode = "normal";
        }


        List<String> result = new ArrayList<>();
        result.add(currMode);
        result.add(nextUImode);
        return result;
    }

    /**
     * @param xCo
     * @param ID
     * @param data
     * @param dimensions
     * @param widthList
     */
    private void handleDragEvent(int xCo, int ID, dataController data, Integer[] dimensions, List<Integer> widthList) {
        if(ID == 506 || ID == 502){
            int delta = xCo - draggedX;
            int previousWidth = widthList.get(draggedColumn);
            int newWidth = previousWidth +delta;
            int sum = widthList.stream().mapToInt(Integer::intValue).sum();
            if(newWidth >= paintModule.getMinCellWidth() && sum + delta < 590 - paintModule.getxCoStart() ){
                widthList.set(draggedColumn, newWidth);
                draggedX = xCo;
                recalculateScrollbar(data, dimensions);
            }
        }else{
            currMode ="normal";
        }
    }

    /**
     * @param data
     * @param clickedCell
     */
    private void handleClickOnCell(dataController data, int[] clickedCell) {
        activeCell = clickedCell;
        tempText = table.getTableRows().get(rowIndex).getColumnList().get(activeCell[0]);
        currMode = "edit";

    }

        /**
         * Method that saves the temporary text to the database depending on whether it is a table name or a query
         * @param tempText the text to be saved
         * @param data the datacontroller
         */
        public void setTempText(Cell tempText, dataController data){
            if (activeCell[0] == 1) {
                currMode = "normal";
                table.getTableRows().get(activeCell[1]).setColumnCell(activeCell[0], tempText);
            }
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
    /*
     * TO REFACTOR
     * too long
     */
    protected List<String> handleKeyEditMode(int id, int keyCode, char keyChar, dataController data) {
        KeyEvent eventHandler = new KeyEvent();
        String currName = table.getTableRows().get(activeCell[0]).getColumnList().get(activeCell[1]).getValue().toString(); // NOOIT GEBRUIKT, snap het nut niet
        //EVENT: ASCSII char pressed
        if (eventHandler.isChar(keyCode)) {
            ((CellEditable)tempText).addChar(keyChar);

            invalidInput = !textIsValid(tempText, data, currName);
        }

        //EVENT BS pressed and in edit mode
        else if (eventHandler.isBackspace(keyCode)) {

            //Check if string is not empty
            if (tempText.getValue().toString().length() != 0) {
                ((CellEditable)tempText).delChar();

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
     * Saves the edited text to the datacontroller
     *
     * @param data datacontroller
     */
    private void saveText(dataController data) {
        currMode = "normal";
        table.getTableRows().get(rowIndex).setColumnCell(activeCell[0], tempText);
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
        Boolean blanksAllowed = table.getColumnNames().get(activeCell[0]).getBlanksAllowed();
        if (type.equals("String")) {
            if (!blanksAllowed) {
                if (((CellText) text).getValue().length() == 0) {
                    return false;
                }
            }
            return true;
        } else if (type.equals("Boolean")) {

        } else if (type.equals("Email")) {
            if (blanksAllowed && text.getString().length() == 0) {
                return true;
            }
            if (text.getString().contains("@")) {
                return true;
            } else return false;

        } else if (type.equals("Integer")) {
            if (blanksAllowed && text.getString().length() == 0) {
                return true;
            }
            try {
                Integer.parseInt( text.getString() );
                return true;
            } catch (Exception e) {
                System.out.println(e);
                return false;
            }
        }
        return true;
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
    @Override
    protected List<String> handleKeyNormalMode(int id, int keyCode, char keyChar, dataController data){
        String nextUIMode = "";
        if (keyCode == 33){
            setRowIndex(rowIndex + 1);
        }
        else if (keyCode == 34) {
            setRowIndex(rowIndex - 1);
        }
        if (keyCode == 17){
            ctrlPressed = true;
        }
        else if (ctrlPressed) {
            if (keyCode == 78) {
                addNewRow();
                ctrlPressed = false;
            }
            else if (keyCode == 68){
                deleteRow();
            }
        }
        else{
            ctrlPressed = false;
        }

        List<String> result = new ArrayList<>();
        result.add("normal");
        result.add("");
        return result;
    }

    /**
     * Method that adds a new row to the table
     */
    public void addNewRow(){
        Row row = new Row(table.getColumnNames());
        table.addRow(row);
    }

    public void deleteRow(){
        Row row = table.getTableRows().get(rowIndex);
        table.deleteRow(row);
        if (rowIndex == table.getTableRows().size()){
            rowNotOutOfRange = false;
        }
    }

    /**
     * Method that paints the correct view on the canvas
     *
     * @param g graphics object
     * @param data datacontroller
     */
    @Override
    public void paint(Graphics g, dataController data, Integer[] coords, Integer[] dimensions) {
        CellVisualisationSettings setting;
        List<Integer> widthList = table.getFormSetting().getWidthList();

        int sum = widthList.stream().mapToInt(Integer::intValue).sum();

        paintWindowBasics(g, data, coords, dimensions, sum);

        //Check mode
        if (currMode == "edit") {
           paintEditMode(g,widthList,dimensions,coords);
            }

        //check if there are warnings
        if (invalidInput || currMode == "delete") {
            paintDeleteMode(g,widthList,dimensions,coords);
        } else {
            paintModule.setColor(g, Color.BLACK);
        }


    }

    /**
     * Method that paint when the user is editing
     * @param g graphics
     * @param widthList widthlist
     * @param dimensions dimensions
     * @param coords coordinates
     */
    private void paintEditMode(Graphics g, List<Integer> widthList, Integer[] dimensions, Integer[] coords){
        int[] coords1 = paintModule.getCellCoords(activeCell[0], activeCell[1], widthList,scrollbar, dimensions[1]);

        paintModule.paintCursor(g, coords1[0] + coords[0],
                coords1[1] + coords[1], widthList.get(activeCell[1]),
                paintModule.getCellHeight(), tempText.getValue().toString());
    }
    /**
     * Method that paint when the user is editing
     * @param g graphics
     * @param widthList widthlist
     * @param dimensions dimensions
     * @param coords coordinates
     */
    private void paintDeleteMode(Graphics g, List<Integer> widthList, Integer[] dimensions, Integer[] coords){
        int[] coords1 = paintModule.getCellCoords(activeCell[0], activeCell[1], widthList, scrollbar, dimensions[1]);
        paintModule.paintBorder(g, coords[0] + coords1[0],
                coords[1] + coords1[1],  widthList.get(activeCell[1]),
                paintModule.getCellHeight(), Color.RED);

    }




    /**
     * @param g
     * @param data
     * @param coords
     * @param dimensions
     * @param sum
     */
    private void paintWindowBasics(Graphics g, dataController data, Integer[] coords, Integer[] dimensions, int sum) {
        recalculateScrollbar(data, dimensions);
        paintModule.setBackground(g,coords[0], coords[1], dimensions[0], dimensions[1], Color.WHITE);
        if (rowNotOutOfRange) {
            paintModule.paintBorderSubwindow(g, coords, dimensions, "Form Mode (" + table.getTableName() + " Row: " + rowIndex + ")", this.getActive());
        }
        else{
            paintModule.paintBorderSubwindow(g, coords, dimensions, "Form Mode (" + table.getTableName() + " Row: No Row)", this.getActive());
        }
        recalculateScrollbar(data, dimensions);
        paintModule.paintHScrollBar(g,coords[0],coords[1] + dimensions[1]-10, dimensions[0], scrollbar.getPercentageHorizontal(), scrollbar);
        paintModule.paintVScrollBar(g, coords[0] + dimensions[0] -10, coords[1] + 15, dimensions[1] - 15, scrollbar.getPercentageVertical(), scrollbar);
        if(rowNotOutOfRange){
            paintModule.paintFormView(g, table, rowIndex, coords[0] +paintModule.getMargin(),
                    coords[1]+paintModule.getMargin(), table.getFormSetting(), dimensions[0] - 48,dimensions[1]-58,scrollbar,dimensions[0],sum);
        }


    }

    /**
     * Method that checks whether or not the row index is in range of the number of rows and sets the index
     */
    private void setRowIndex(int index){
        if (index < 0 ){
            rowIndex = -1;
            rowNotOutOfRange = false;
        }
        else if(index > table.getTableRows().size()-1){
            rowIndex = table.getTableRows().size();
            rowNotOutOfRange = false;
        }
        else {
            rowIndex = index;
            rowNotOutOfRange = true;
        }
    }






    /*
     * TO REFACTOR
     * too long
     */
    private void recalculateScrollbar(dataController data, Integer[] dimensions){
        //WIDTHLIST IS NOT SAME FOR EVERY MODULE !!!!!!!!!!!!!
        List<Integer> widthList = table.getDesignSetting().getWidthList();

        int sum = widthList.stream().mapToInt(Integer::intValue).sum();
        scrollbarActive = false;
        if(sum > dimensions[0] - 31 ){
            percentageHorizontal =  (Double.valueOf(dimensions[0]-30)/ Double.valueOf(sum));
            scrollbar.setPercentageHorizontal(percentageHorizontal);
            scrollbar.setActiveHorizontal(true);
            System.out.println(percentageHorizontal);
        }else{
            scrollbar.setActiveHorizontal(false);
            scrollbar.setPercentageHorizontal(0);
            scrollbar.setOffsetpercentageHorizontal(0);



        }
        //TABLE LIST
        if(table.getColumnNames().size() * paintModule.getCellHeight() > dimensions[1] - 46){
            percentageVertical = ( Double.valueOf(dimensions[1] - 46)/ Double.valueOf((table.getColumnNames().size() * paintModule.getCellHeight())));
            scrollbar.setPercentageVertical(percentageVertical);
            scrollbar.setActiveVertical(true);
            System.out.println(percentageVertical);
        }else{
            scrollbar.setPercentageVertical(0);
            scrollbar.setActiveVertical(false);
            scrollbar.setOffsetpercentageVertical(0);
        }}

}
