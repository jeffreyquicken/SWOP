package UserInterfaceElements;

import Data.Cell;
import Data.Table;
import Data.dataController;
import events.MouseEvent;
import paintModule.DesignModePaintModule;
import paintModule.FormModePaintModule;
import settings.CellVisualisationSettings;

import java.awt.*;
import java.util.List;

public class UIFormModule extends UISuperClass{
    private Table table;
    private Cell tempText;
    private FormModePaintModule paintModule;
    private events.MouseEvent mouseEventHandler;

    public UIFormModule(Table inputTable){
        paintModule = new FormModePaintModule();
        mouseEventHandler = new MouseEvent();
        invalidInput = false;
        table = inputTable;
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
        paintModule.paintFormView(g, table, 1, coords[0] +paintModule.getMargin(), coords[1]+paintModule.getMargin(), table.getDesignSetting(), dimensions[0] - 48,dimensions[1]-58,scrollbar,dimensions[0],sum);

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
