package UserInterfaceElements;

import Data.*;
import events.MouseEvent;
import paintModule.DesignModePaintModule;
import paintModule.PaintModule;
import events.KeyEvent;
import settings.CellVisualisationSettings;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UIDesignModule extends UISuperClass {
    private DesignModePaintModule paintModule;
    private events.MouseEvent mouseEventHandler;
    private String prevColName = "";

    @Override
    public String getCurrMode() {
        return currMode;
    }

    public void setCurrMode(String currMode) {
        this.currMode = currMode;
    }

    //private int xCoStart = 50;
    //private int yCoStart = 50;
    private String currMode = "normal";

    public int[] getActiveCell() {
        return activeCell;
    }

    public void setActiveCell(int[] activeCell) {
        this.activeCell = activeCell;
    }

    private int[] activeCell = {-1,-1};
    private Cell tempText;

    public Boolean getInvalidInput() {
        return invalidInput;
    }

    public void setInvalidInput(Boolean invalidInput) {
        this.invalidInput = invalidInput;
    }

    private Boolean invalidInput;
    private int draggedColumn;
    private int draggedX;

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    private Table table;



    /**
     * Creates/init a paintmodule and a mouseventhandler
     */
    public UIDesignModule(Table inputTable) {
        paintModule = new DesignModePaintModule();
        mouseEventHandler = new MouseEvent();
        invalidInput = false;
        table = inputTable;

    }



    //Handles mousevent and returns if UImode need to change

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
    
    public List<String> handleMouseEvent2(int xCo, int yCo, int count, int ID, dataController data, Integer[] dimensions) {

        CellVisualisationSettings setting;
        if (table == null) {
            setting = table.getDesignSetting();
        } else {
            setting = table.getDesignSetting();
        }
        List<Integer> widthList = setting.getWidthList();
        int[] clickedCell = mouseEventHandler.getCellID(xCo, yCo, paintModule.getxCoStart(), paintModule.getyCoStart(),
                paintModule.getCellHeight(), paintModule.getCellWidth(), table.getColumnNames().size(), 1, widthList, scrollbar);
        int lowestY = (table.getColumnNames().size() * paintModule.getCellHeight()) + paintModule.getyCoStart();

        //check if leftmargin is clicked
        if(isClickedLeftMargin(xCo, yCo, widthList, clickedCell)) {
            currMode = "delete";
            activeCell = clickedCell;
        }
        //EVENT DOUBLE CLICKS UNDER TABLE
        else if(currMode!= "edit" && scrollbarClicked(xCo,yCo,dimensions)){
        }
        else if (currMode == "normal" && mouseEventHandler.doubleClickUnderTable(yCo, count, ID, lowestY)) {
            handleDoubleClickUnderTable(data);
        }
        //EVENT CELL CLICKED (VALID INPUT)
        else if (!invalidInput && ID == 500 && currMode!="edit" && currMode != "delete" && clickedCell[1] != -1 && clickedCell[0] != -1) {

            handleCellClickedValidInput(count, data, clickedCell);

        }
        //Check if header is clicked
        //ADDED ELSE : 15 Mar 2019
       else  if (isClickedRightMargin(xCo, yCo, widthList)) {
            startDrag(xCo, yCo, widthList);
            //Checks if user is dragging border
        } else if (currMode == "drag") {
            handleDrag(xCo, ID, data, dimensions, widthList);
        }

        //EVENT TYPE CLICKED (INVALID INPUT)
        else if (invalidInput && ID == 500 && clickedCell[1] == 2){
            handleTypeClickedInvalidInput(data, clickedCell);
        }

        //EVENT CHECKBOX CLICKED (INVALID INPUT)
        else if (invalidInput && ID == 500 && clickedCell[1] == 3 && clickedCell[0] == activeCell[0] && clickedCell[1] == activeCell[1]) {
            table.getColumnNames().get(clickedCell[0]).setBlanksAllowed(true);
            invalidInput = false;
        }

        //EVENT EXIT EDIT MODE
        else if(!invalidInput && currMode == "edit" && (clickedCell[0] == -1 || clickedCell[1] == -1)){
            saveText(data);
            currMode = "normal";
        }
        List<String> result = new ArrayList<>();
        result.add(currMode);
        result.add("");
        return result;
    }



	/**
	 * @param data
	 * @param clickedCell
	 */
	public void handleTypeClickedInvalidInput(dataController data, int[] clickedCell) {
		if (currMode == "edit" || currMode=="invalid"){
		    if (activeCell[1] == 2 && clickedCell[0] == activeCell[0]){
		        Cell prevType = new CellText(table.getColumnNames().get(clickedCell[0]).getType());
		        Cell newType;
		        if (prevType.getValue().equals("String")){
		            newType = new CellText("Email");
		        }else if(prevType.getValue().equals("Email")){
		            newType = new CellText("Boolean");
		        }else if(prevType.getValue().equals("Boolean")){
		            newType = new CellText("Integer");
		        }else{
		            newType = new CellText("String");
		        }
		        activeCell = clickedCell;
		        invalidInput = !textIsValid(newType, data, null);
		        if (invalidInput){
		            currMode = "invalid";
		        }
		        else{
		            currMode = "normal";
		        }
		        table.getColumnNames().get(clickedCell[0]).setType(((CellText) newType).getValue());
		    }
		}
	}



	/**
	 * @param xCo
	 * @param ID
	 * @param data
	 * @param dimensions
	 * @param widthList
	 */
	private void handleDrag(int xCo, int ID, dataController data, Integer[] dimensions, List<Integer> widthList) {
		if (ID == 506 ||  ID == 502) {
		    int delta = xCo - draggedX;
		    int previousWidth = widthList.get(draggedColumn);
		    int newWidth = previousWidth + delta;
		    int sum = widthList.stream().mapToInt(Integer::intValue).sum();
		    if ((newWidth >= paintModule.getMinCellWidth()) && (sum + delta < 590 - paintModule.getxCoStart())) {
		        widthList.set(draggedColumn, newWidth);
		        draggedX = xCo;
		        recalculateScrollbar(data, dimensions);

		    }
		} else {
		    currMode = "normal";
		}
	}



	/**
	 * @param xCo
	 * @param yCo
	 * @param widthList
	 */
	private void startDrag(int xCo, int yCo, List<Integer> widthList) {
		System.out.println("RIGHT BORDER CLICKED");
		currMode = "drag";
		draggedColumn = mouseEventHandler.rightBorderClicked(xCo, yCo, paintModule.getxCoStart(), paintModule.getyCoStart(), widthList.size(), paintModule.getCellHeight(), widthList);
		draggedX = xCo;
	}



	/**
	 * @param xCo
	 * @param yCo
	 * @param widthList
	 * @return
	 */
	private boolean isClickedRightMargin(int xCo, int yCo, List<Integer> widthList) {
		return mouseEventHandler.rightBorderClicked(xCo, yCo, paintModule.getxCoStart(), paintModule.getyCoStart(), widthList.size(), paintModule.getCellHeight(), widthList) != -1;
	}



	/**
	 * @param count
	 * @param data
	 * @param clickedCell
	 */
	
	private void handleCellClickedValidInput(int count, dataController data, int[] clickedCell) {
		//check which collumn
		if (count != 2 && clickedCell[1] == 0) {
		    activeCell = clickedCell;
		    currMode = "edit";
		    tempText = new CellText(table.getColumnNames().get(activeCell[0]).getName());
		    prevColName = table.getColumnNames().get(activeCell[0]).getName();
		}
		//DEFAULTVALUE CLICKED
		else if(clickedCell[1] == 1){
		    handleDefaultValueClickedValidInput(data, clickedCell);
		}
		//TYPE CLICKED
		else if(clickedCell[1] == 2){
		        handleTypeClickedValidInput(data, clickedCell);
		}
		//CHECKBOX BLANKS CLICKED
		else if (clickedCell[1] == 3) {
		    handleBlankCheckboxClicked(clickedCell);
		}
	}



	/**
	 * @param clickedCell
	 */
	private void handleBlankCheckboxClicked(int[] clickedCell) {
		Cell prevBool = new CellBoolean(table.getColumnNames().get(clickedCell[0]).getBlanksAllowed());

		table.getColumnNames().get(clickedCell[0]).setBlanksAllowed(!(((CellBoolean) prevBool).getValue()));

		Boolean val = ((CellBoolean) prevBool).getValue();

		prevBool.setValue(val);
		tempText = prevBool;

		//if default is false
		if (((CellBoolean) prevBool).getValue()) {

		    if (table.getColumnNames().get(clickedCell[0]).getDefaultV().getString().equals("")) {
		        invalidInput = true;
		        activeCell = clickedCell;
		    }
		    else {

		        List<Row> rowList = table.getTableRows();
		        int index = clickedCell[0];
		        for (Row row : rowList) {
		            if (row.getColumnList().get(index).getString().equals("")) {
		                invalidInput = true;
		                activeCell = clickedCell;
		            }
		        }
		    }
		}
	}



	/**
	 * @param data
	 * @param clickedCell
	 */
	private void handleTypeClickedValidInput(dataController data, int[] clickedCell) {
		String prevType = table.getColumnNames().get(clickedCell[0]).getType();
		Cell newType;
		if (prevType.equals("String")) {
		    newType = new CellText("Email");
		} else if (prevType.equals("Email")) {
		    newType = new CellText("Boolean");
		} else if (prevType.equals("Boolean")) {
		    newType = new CellText("Integer");
		} else {
		    newType = new CellText("String");
		}
		activeCell = clickedCell;
		invalidInput = !textIsValid(newType, data, null);
		if (invalidInput) {
		    currMode = "edit";
		} else {
		    currMode = "normal";
            table.getColumnNames().get(clickedCell[0]).setType(((CellText) newType).getValue());
		}

	}



	/**
	 * @param data
	 * @param clickedCell
	 */
	private void handleDefaultValueClickedValidInput(dataController data, int[] clickedCell) {
		activeCell = clickedCell;
		currMode = "edit";

		if (table.getColumnNames().get(activeCell[0]).getType().equals("Boolean")){
		    tempText = table.getColumnNames().get(activeCell[0]).getDefaultV();
		    if (tempText.getValue().equals(true)){
		        tempText.setValue(false);
		    }
		}
		if (table.getColumnNames().get(activeCell[0]).getType().equals("Boolean")){
		    tempText = table.getColumnNames().get(activeCell[0]).getDefaultV();
		    if (tempText.getValue().equals(true)){
		        tempText.setValue(false);
		    }
		    else if (tempText.getValue().equals(false)){
		        if (table.getColumnNames().get(activeCell[0]).getBlanksAllowed()){
		            tempText.setValue(null);
		        }
		        else{
		            tempText.setValue(true);
		        }
		    }
		    else{
		        tempText.setValue(true);
		    }
		    saveText(data);
		}
		else {
		    tempText = table.getColumnNames().get(activeCell[0]).getDefaultV();
		}
	}



	/**
	 * @param data
	 */
	private void handleDoubleClickUnderTable(dataController data) {
		int numberOfCols = table.getColumnNames().size() + 1;
		Cell newName = new CellText("Column" + numberOfCols);
		int i = numberOfCols;
		while (!nameIsValid(newName, data, null)) {
		    i++;
		    newName.setValue("Column" + i);
		}

		Column newCol = new Column(((CellText) newName).getValue(), new CellText(""), "String", true);
		table.addColumn(newCol);
	}



	/**
	 * @param xCo
	 * @param yCo
	 * @param widthList
	 * @param clickedCell
	 * @return
	 */
	private boolean isClickedLeftMargin(int xCo, int yCo, List<Integer> widthList, int[] clickedCell) {
		return clickedCell[1] == 0 && currMode != "edit" && mouseEventHandler.marginLeftClicked(xCo,yCo,paintModule.getxCoStart(),
                paintModule.getyCoStart(), paintModule.getCellHeight(), paintModule.getCellWidth(),
                table.getColumnNames().size(), 1, paintModule.getCellLeftMargin(), widthList) != null;
	}

    private boolean nameIsValid(Cell text, dataController data, String currName) {
        //Checks if name is valid

            String currentText = text.getString();
            if(currentText.length() == 0){
                return false;
            }
            for(Column col :table.getColumnNames()){
                if (currentText.equals(col.getName())){
                    if(col.getName().equals(prevColName)){

                    }else{
                    return false;}
                }

            }return true;
	}


	private boolean defaultValueIsConsistent(String value , Boolean blanks, Cell text){
                //every string is accepted
                if (text.getString().equals("String")){
                    return true;
                }
                //only allow false or true
                else if (text.getString().equals("Boolean")){
                    if (value.equals("true") || value.equals("false")){
                        //return true;
                    }else{
                        if(blanks && value.length() == 0){

                        }else{
                            return false;}}
                }
                //only allow integer
                else if (text.getString().equals("Integer")){

                    try{
                        Integer.parseInt(value);
                        //return true;
                    }
                    catch (Exception e){
                        if(blanks && value.length() ==0){

                        }else{
                            return false;}
                    }
                }
                //only allow @ in name
                else if (text.getString().equals("Email")){
                    if (!value.contains("@")){
                        if(blanks && value.length() ==0){
                        }else{ return false;}
                        // return true;
                    }
                }
            return true;
    }

    /**
     * Checks if updated text is valid according to the type of it's cell
     *
     * @param text text to be validated
     * @param data datacontroller
     * @param currName old name
     * @return Wheter the text is in the correct fromat according to the type of it's cell
     */
    /*
     * TO REFACTOR
     * too long
     */
    private boolean textIsValid(Cell text, dataController data, String currName) {

        //Checks if name is valid
        if (activeCell[1] == 0) {
         return nameIsValid(text,data,currName);

        }
        //Cheks if default value is consistent with type and balnks

        else if (activeCell[1] == 1) {
            String type = table.getColumnNames().get(activeCell[0]).getType();
            if (table.getColumnNames().get(activeCell[0]).getBlanksAllowed()) {
                if (((CellText)text).getString().length() == 0) {
                    return true;
                }
            }
            if (type.equals("String")) {

                if (!table.getColumnNames().get(activeCell[0]).getBlanksAllowed()) {
                    if (((CellText)text).getString().length() == 0) {

                        return false;
                    }
                }
                return true;
                //TODO: check blanks
            } else if (type.equals("Boolean")) {
                    if(text.getString().equals("true") || text.getString().equals("false")){
                        return true;
                    }else {
                        return false;
                    }

            } else if (type.equals("Email")) {
               // if (((CellEmail) text).getValue().contains("@")) {
                    if (text.getString().contains("@")) {
                    return true;
                } else return false;
            } else if (type.equals("Integer")) {

                try {
                    Integer.parseInt( text.getString() );
                    return true;
                } catch (Exception e) {
                    System.out.println(e);
                    return false;
                }
            }

        }
        else if (activeCell[1] == 2){
            String value = table.getColumnNames().get(activeCell[0]).getDefaultV().getString();
            Boolean blanks = table.getColumnNames().get(activeCell[0]).getBlanksAllowed();

            if (!defaultValueIsConsistent(value, blanks, text)){
                return false;
            }

            for (Row row : table.getTableRows()){
                String colValue = row.getColumnList().get(activeCell[0]).getString();
                 //every string is accepted
                if (text.getString().equals("String")){
                    return true;
                }
                //only allow false or true
                else if (text.getString().equals("Boolean")){
                    if ((colValue.equals("true") || colValue.equals("false"))){
                        //return true;
                    }else{
                        if(blanks && colValue.length() == 0){

                        }else{
                    return false;}}
                }
                //only allow integer
                else if (text.getString().equals("Integer")){

                    try{
                        Integer.parseInt(colValue);
                        //return true;
                    }
                    catch (Exception e){
                        if(blanks && colValue.length() ==0){

                        }else{
                        return false;}
                    }
                }
                //only allow @ in name
                else if (text.getString().equals("Email")){
                    if (!colValue.contains("@")){
                        if(blanks && colValue.length() ==0){
                        }else{ return false;}
                       // return true;
                    }
                }
            }

        }
        else {
            for (Column col : table.getColumnNames()) {
                if (col.getName().equals(text)) {
                    if (!col.getName().equals(currName)) {
                        return false;
                    }
                }
            }
            if (text.getValue().toString().length() == 0) {
                return false;
            }
            return true;
        }
        return true;
    }

    /**
     * Saves the edited text to the datacontroller
     *
     * @param data datacontroller to make changes to the data
     */
    private void saveText(dataController data){
        currMode = "normal";
        if (activeCell[1] == 1) {
            table.getColumnNames().get(activeCell[0]).setDefaultV(tempText);
        }
        else if (activeCell[1] == 0){
            table.getColumnNames().get(activeCell[0]).setName(tempText.getValue().toString());
        }
    }

    //Method that takes care of painting the canvas
    //It calls method from paintModules

    /**
     * Method that paints the correct view on the canvas
     *
     * @param g graphics object
     * @param data datacontroller
     */
    @Override
    public void paint(Graphics g,  dataController data, Integer[] coords, Integer[] dimensions) {
        CellVisualisationSettings setting;
        List<Integer> widthList = table.getDesignSetting().getWidthList();

        int sum = widthList.stream().mapToInt(Integer::intValue).sum();

        paintWindowBasics(g, data, coords, dimensions, sum);
        
        //Check mode
        if (currMode == "edit") {
            if (activeCell[1] == 0){
                int[] coords1 = paintModule.getCellCoords(activeCell[0], activeCell[1], widthList,scrollbar, dimensions[1]);
                paintModule.paintCursor(g, coords1[0] + coords[0],
                        coords1[1] + coords[1], widthList.get(activeCell[1]),
                        paintModule.getCellHeight(), tempText.getValue().toString());
            }
            else if(activeCell[1] == 1){
                if (!table.getColumnNames().get(activeCell[0]).getType().equals("Boolean")){
                    int[] coords1 = paintModule.getCellCoords(activeCell[0], activeCell[1], widthList, scrollbar, dimensions[1]);
                    paintModule.paintCursor(g, coords1[0] + coords[0],
                            coords1[1] + coords[1], widthList.get(activeCell[1]),
                            paintModule.getCellHeight(), tempText.getValue().toString());
                }
            }
            else if(activeCell[1] == 2){
            }
            else if (activeCell[1] == 3){
            }
        }
        //check if there are warnings
        if (invalidInput || currMode == "delete") {
            int[] coords1 = paintModule.getCellCoords(activeCell[0], activeCell[1], widthList, scrollbar, dimensions[1]);
            paintModule.paintBorder(g, coords[0] + coords1[0],
                    coords[1] + coords1[1],  widthList.get(activeCell[1]),
                    paintModule.getCellHeight(), Color.RED);
        } else {
            paintModule.setColor(g, Color.BLACK);
        }


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
        paintModule.paintBorderSubwindow( g, coords, dimensions, "Design Mode (" + table.getTableName() + ")", this.getActive());
        recalculateScrollbar(data, dimensions);
        paintModule.paintHScrollBar(g,coords[0],coords[1] + dimensions[1]-10, dimensions[0], scrollbar.getPercentageHorizontal(), scrollbar);
        paintModule.paintVScrollBar(g, coords[0] + dimensions[0] -10, coords[1] + 15, dimensions[1] - 15, scrollbar.getPercentageVertical(), scrollbar);
        paintModule.paintDesignView(g, table,coords[0] +paintModule.getMargin(), coords[1]+paintModule.getMargin(), table.getDesignSetting(), dimensions[0] - 48,dimensions[1]-58,scrollbar,dimensions[0],sum);
	}

    /*
     * (non-Javadoc)
     * @see UserInterfaceElements.UISuperClass#handleKeyEditMode(int, int, char, Data.dataController)
     *
     * the next methods will be called using the superclass method handleKeyEvent
     * the method in the superclass works as a flow controller but doesn't have the functionality needed for the specific UI modules
     * this is implemented in the next few methods
     */

    /**
     * Method that handles key event when the UI is in edit-mode and returns state of program
     *
     * @param id id of key pressed
     * @param keyCode keycde of key pressed
     * @param keyChar keychar of key pressed
     * @param data datacontroller
     * @return returns a list with the nextUImode and the current mode of the UI
     */
    @Override
    protected List<String> handleKeyEditMode(int id, int keyCode, char keyChar, dataController data) {
        KeyEvent eventHandler = new KeyEvent();
        //EVENT: ASCSII char pressed
        if (eventHandler.isChar(keyCode)) {
            ((CellEditable)tempText).addChar(keyChar);
            String currName = table.getColumnNames().get(activeCell[0]).getName();

            invalidInput = !textIsValid(tempText, data, currName);
        }

        //EVENT BS pressed and in edit mode
        else if (eventHandler.isBackspace(keyCode)) {

            //Check if string is not empty
            if (tempText.getValue().toString().length() != 0) {
                ((CellEditable)tempText).delChar();
                String currName = table.getColumnNames().get(activeCell[0]).getName();

                invalidInput = !textIsValid(tempText, data, currName);

            }
            //empty string, display red border
        }
        //EVENT ENTER pressed
        else if (eventHandler.isEnter(keyCode) && !invalidInput) {
            saveText(data);
            currMode ="normal";
        }
        List<String> result = new ArrayList<>();
        result.add(currMode);
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
        String nextUIMode = "";
        KeyEvent eventHandler = new KeyEvent();
        //ESCAPE
        if(keyCode == 27){
            nextUIMode = "table";
        }

        else if (keyCode == 17){
            ctrlPressed = true;
        }
        else if (ctrlPressed) {
            if (eventHandler.isEnter(keyCode)) {
                nextUIMode = "row";
                ctrlPressed = false;
            }
        }
        else{
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
       KeyEvent eventHandler = new KeyEvent();
        //DEL key pressed
        if(eventHandler.isDelete(keyCode) || keyChar == 'd'){
            List<Row> rowList = table.getTableRows();
            for (Row row: rowList){
                row.deleteColumnCell(activeCell[0]);
            }
            table.getRowSetting().removeFromWidthList(activeCell[0]);
            Column col = table.getColumnNames().get(activeCell[0]);
            table.deleteColumn(col);
            currMode = "normal";
        }
        List<String> result = new ArrayList<>();
        result.add(currMode);
        result.add("");
        return result;
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
