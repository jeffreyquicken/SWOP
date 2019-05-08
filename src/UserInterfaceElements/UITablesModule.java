package UserInterfaceElements;

import Data.Table;
import Data.dataController;
import events.*;
import paintModule.RowModePaintModule;
import paintModule.TableModePaintModule;
import settings.CellVisualisationSettings;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class UITablesModule extends UISuperClass{
	
	private TableModePaintModule paintModule;

    //Constructor that init/creates paintModule and an empty list with tablenames
    //Each UImodule has own paintmodule to save settings.settings (e.g. size, bg, ...)
	//constructor inherited from SuperClass
    public UITablesModule() {
        super();
        paintModule = new TableModePaintModule();
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

        //EVENT DOUBLE CLICKS UNDER TABLE
        if (currMode == "normal" && mouseEventHandler.doubleClickUnderTable(yCo, count, ID, data.getLowestY(30)) ) {
            handleDoubleClickUnderTable(data);
        }
        //EVENT CLICK CELL
        //TODO: check if margin clicked
        CellVisualisationSettings setting = data.getSetting();
        List<Integer> widthList = setting.getWidthList();
       int[] clickedCell = mouseEventHandler.getCellID(xCo, yCo, paintModule.getxCoStart(), paintModule.getyCoStart(),
                paintModule.getCellHeight(), paintModule.getCellWidth(), data.getTableList().size(), 1,widthList, scrollbar);

        //int[] clickedCell = mouseEventHandler.getCellID(xCo, yCo, 10, 10,
              //  paintModule.getCellHeight(), paintModule.getCellWidth(), data.getTableList().size(), 1,widthList);
       //Checks if user is dragging border
            if(currMode == "drag"){
                handleDragEvent(xCo, ID, data, dimensions, widthList);
        }
        //check if leftmargin is clicked
        else if(isClickedLeftMargin(xCo, yCo, data, widthList)) {
            currMode = "delete";
            activeCell = clickedCell;
        }
        else if(currMode!= "edit" && scrollbarClicked(xCo,yCo,dimensions)){
            }
        else if (!invalidInput  && currMode!= "delete" && clickedCell[1] != -1 && clickedCell[0] != -1) {
            if (count != 2){handleClickOnCell(data, clickedCell);}
            else{nextUImode = handleDoubleClickOnCell(ID, data, nextUImode, clickedCell);}
            }
        //Check if header is clicked
        else if(isCellRightBorderClicked(xCo, yCo, widthList)){
            handleCellRightBorderClicked(xCo, yCo, widthList);
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
	 * @param yCo
	 * @param data
	 * @param widthList
	 * @return
	 */
	private boolean isClickedLeftMargin(int xCo, int yCo, dataController data, List<Integer> widthList) {
		return currMode != "edit" && mouseEventHandler.marginLeftClicked(xCo,yCo,paintModule.getxCoStart(),
                    paintModule.getyCoStart(), paintModule.getCellHeight(), paintModule.getCellWidth(),
                    data.getTableList().size(), 1, paintModule.getCellLeftMargin(), widthList) != null;
	}

	/**
	 * @param data
	 */
	private void handleDoubleClickUnderTable(dataController data) {

		data.addTable();
	}

	/**
	 * @param xCo
	 * @param yCo
	 * @param widthList
	 */
	private void handleCellRightBorderClicked(int xCo, int yCo, List<Integer> widthList) {
		System.out.println("RIGHT BORDER CLICKED");
		currMode ="drag";
		draggedColumn= mouseEventHandler.rightBorderClicked(xCo,yCo,paintModule.getxCoStart(),
		        paintModule.getyCoStart(), widthList.size(), paintModule.getCellHeight(), widthList);
		draggedX = xCo;
	}

	/**
	 * @param ID
	 * @param data
	 * @param nextUImode
	 * @param clickedCell
	 * @return
	 */
	private String handleDoubleClickOnCell(int ID, dataController data, String nextUImode, int[] clickedCell) {
		data.setSelectedTable(data.getTableList().get(clickedCell[0]));
		if((data.getSelectedTable().getColumnNames().size() == 0) && (ID == 502)){
		    nextUImode = "design";
		}else if (ID == 502) {
		nextUImode = "row";}
		return nextUImode;
	}

	/**
	 * @param data
	 * @param clickedCell
	 */
	private void handleClickOnCell(dataController data, int[] clickedCell) {
		activeCell = clickedCell;
		currMode = "edit";
		if (activeCell[1] == 0) {
            tempText = data.getTableList().get(activeCell[0]).getTableName();
        }
        else if (activeCell[1] == 1){
		    tempText = data.getTableList().get(activeCell[0]).getQuery();
        }
	}

	/**
	 * @param xCo
	 * @param yCo
	 * @param widthList
	 * @return
	 */
	private boolean isCellRightBorderClicked(int xCo, int yCo, List<Integer> widthList) {
		return mouseEventHandler.rightBorderClicked(xCo,yCo,paintModule.getxCoStart(), paintModule.getyCoStart(),
                    widthList.size(), paintModule.getCellHeight(), widthList) != -1;
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

    //Method that takes care of painting the canvas
    //It calls method from paintModule

    /**
     * Method that paints the correct view on the canvas
     *
     * @param g graphics object
     * @param data datacontroller
     */
    /*
     * TO REFACTOR
     * too long
     */
    @Override
    public void paint(Graphics g, dataController data, Integer[] coords, Integer[] dimensions) {
        CellVisualisationSettings setting;
        setting = data.getSetting();
        List<Integer> widthList = setting.getWidthList();

        paintWindowBasics(g, data, coords, dimensions, setting);
 
        //Check mode
        if (currMode == "edit" ) {
            int[] coords1 = paintModule.getCellCoords(activeCell[0] , activeCell[1] , widthList, scrollbar, dimensions[1] );
                paintModule.paintCursor(g, coords1[0] + coords[0],
                        coords1[1] + coords[1], widthList.get(activeCell[1]),
                        paintModule.getCellHeight(), tempText);

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
        //paintModule.paintBorder(g,paintModule.getxCoStart(), paintModule.getyCoStart(), 80, 20, "red");
    }

	/**
	 * @param g
	 * @param data
	 * @param coords
	 * @param dimensions
	 * @param setting
	 */
    /*
     * TO REFACTOR
     * put in superclass and maybe use for all UImodules? - change specifics
     */
	private void paintWindowBasics(Graphics g, dataController data, Integer[] coords, Integer[] dimensions,
			CellVisualisationSettings setting) {
		recalculateScrollbar(data, dimensions);
        paintModule.setBackground(g,coords[0], coords[1], dimensions[0], dimensions[1], Color.WHITE);
        paintModule.paintBorderSubwindow( g, coords, dimensions, "Table Mode",this.getActive());
        recalculateScrollbar(data, dimensions);
        paintModule.paintHScrollBar(g,coords[0],coords[1] + dimensions[1]-10, dimensions[0], scrollbar.getPercentageHorizontal(), scrollbar);
        paintModule.paintVScrollBar(g, coords[0] + dimensions[0] -10, coords[1] + 15, dimensions[1] - 15, scrollbar.getPercentageVertical(), scrollbar);
        //print tables in tabular view
        paintModule.paintTableView(g, data.getTableList(), coords[0] + paintModule.getMargin(), coords[1] +paintModule.getMargin(), setting, dimensions[0] - 45, dimensions[1] - 58, scrollbar, dimensions[1]);
	}


    /**
     * Checks if updated text is valid
     *
     * @param text text to be validated
     * @param data datacontroller
     * @param currName old name
     * @return Wheter the name of the table is valid (hence unique and non-empty)
     */
    public boolean textIsValid(String text, dataController data, String currName) {
        if (activeCell[1] == 0) {
            return nameIsValid(text, data, currName);
        }
        else if (activeCell[1] == 1){
            return queryIsValid(text, data);
        }
        return false;

    }

    /**
     * Method that checks the validity of a table name
     * @param text the text to be checked
     * @param data the datacontroller
     * @param currName the old table name
     * @return whether the name is valid
     */
    public boolean nameIsValid(String text, dataController data, String currName){
        for (Table table : data.getTableList()) {
            if (table.getTableName().equals(text)) {
                if (!table.getTableName().equals(currName)) {
                    return false;
                }
            }
        }
        if (text.length() == 0) {
            return false;
        }
        return true;
    }

    /**
     * Method that checks the validity of a table query
     * @param query the query to be checked
     * @param data the datacontroller
     * @return whether the query is valid
     */
    public boolean queryIsValid(String query, dataController data){
        return true; //TODO validator
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
    /*
     * TO REFACTOR
     * too long
     */
    @Override
    protected List<String> handleKeyEditMode(int id, int keyCode, char keyChar, dataController data){
    	KeyEvent eventHandler = new KeyEvent();
        //EVENT: ASCSII char pressed
        if (eventHandler.isChar(keyCode)) {
            tempText = tempText + keyChar;
            String currName = data.getTableList().get(activeCell[0]).getTableName();
            invalidInput = !textIsValid(tempText, data, currName);
        }

        //EVENT BS pressed and in edit mode
        else if (eventHandler.isBackspace(keyCode)) {

            //Check if string is not empty
            if (tempText.length() != 0) {
                tempText = tempText.substring(0, tempText.length() - 1);
                String currName = data.getTableList().get(activeCell[0]).getTableName();
                invalidInput = !textIsValid(tempText, data, currName);

            }
            //empty string, display red border
        }
        //EVENT ENTER pressed
        else if (eventHandler.isEnter(keyCode) && !invalidInput) {
            currMode = "normal";
            setTempText(tempText, data);

        }
        //ESCAPE
        if(keyCode == 27){
        	if (eventHandler.isEscape(keyCode)) {
                currMode = "normal";
                tempText = "default_text";
        		}        
        	}

        List<String> result = new ArrayList<>();
        result.add(currMode);
        result.add("");
        return result;


    }

    /**
     * Method that saves the temporary text to the database depending on whether it is a table name or a query
     * @param tempText the text to be saved
     * @param data the datacontroller
     */
    public void setTempText(String tempText, dataController data){
        if (activeCell[1] == 0){
            data.getTableList().get(activeCell[0]).setTableName(tempText);
        }
        else if (activeCell[1] == 1){
            data.getTableList().get(activeCell[0]).setQuery(tempText);
        }
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
        if (keyCode == 17){
            ctrlPressed = true;
        }
        else if (ctrlPressed) {
            if (keyCode == 70) {
                nextUIMode = "form";
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
    @Override
    protected List<String> handleKeyDeleteMode(int id, int keyCode, char keyChar, dataController data){
    	KeyEvent eventHandler = new KeyEvent();
    	//DEL key pressed
        if(eventHandler.isDelete(keyCode) || keyChar == 'd'){
        List<Table> list = data.getTableList();
        Table selectedTable = list.get(activeCell[0]);
        data.deleteTable(selectedTable);
        currMode = "normal";
        } else if(eventHandler.isEscape(keyCode)) {
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
     * large similarities with the other UI modules. maybe put in superclass or in another class altogether
     */
    private void recalculateScrollbar(dataController data, Integer[] dimensions){
        CellVisualisationSettings setting = data.getSetting();
        //WIDTHLIST IS NOT SAME FOR EVERY MODULE !!!!!!!!!!!!!
        List<Integer> widthList = setting.getWidthList();

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
        if(data.getTableList().size() * 20 > dimensions[1] - 46){
            percentageVertical = ( Double.valueOf(dimensions[1] - 46)/ Double.valueOf((data.getTableList().size() * 20)));
            scrollbar.setPercentageVertical(percentageVertical);
            scrollbar.setActiveVertical(true);
            System.out.println(percentageVertical);
        }else{
            scrollbar.setPercentageVertical(0);
            scrollbar.setActiveVertical(false);
            scrollbar.setOffsetpercentageVertical(0);
        }}
    @Override
    protected void handleNonModeDependantKeys (int id, int keyCode, char keyChar, dataController data){
    }


    }


