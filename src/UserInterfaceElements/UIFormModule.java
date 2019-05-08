package UserInterfaceElements;

import Data.Cell;
import Data.Row;
import Data.Table;
import Data.dataController;
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
