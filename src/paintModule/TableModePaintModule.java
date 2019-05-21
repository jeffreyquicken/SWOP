package paintModule;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import Data.*;
import settings.CellVisualisationSettings;
import settings.scrollbar;

import static java.lang.Math.min;

/**
 * A class of TabkeModePaintModule involving methods for painting table view elements
 */
public class TableModePaintModule extends PaintModule {
    /**
     * margin between name column and query column
     */
    private int colMargin = 5;
	
	 /**
     * Method that paints a tabular view from a list of Tables
     * @param g graphics object
     * @param tableList list of tables to be painted in view
     * @param startXco  start X coordinate where tableview should be painted
     * @param startYco  start Y coordinate where tableview should be painted
     * @param setting   settings object for this view
     */
     public void paintTableView(Graphics g, List<Table> tableList, int startXco, int startYco, CellVisualisationSettings setting, int width, int height, scrollbar scrollbar, int heigth){
         int offsetHorizontal = (int) (width * scrollbar.getOffsetpercentageHorizontal());
         List<Integer> widthList = setting.getWidthList();
         int widthCells = widthList.get(0);
         int offset = (int) ((heigth-titleHeight) * scrollbar.getOffsetpercentageVertical());
         int tempHeight = -offset;


         //Check if scrollbar is scrolled
         if(widthCells - offsetHorizontal > width ){
             widthCells = width;
         }
         //Only paint header when not scrolled
         if (offset <= 0 ){
            //paint the header
            paintHeader(g, startXco, startYco, offset, widthCells - colMargin, offsetHorizontal, "Name");
             int tempWidth = widthList.get(0) + widthList.get(1);
             int xCo = startXco;

             xCo += widthList.get(0);
             widthCells = widthList.get(1);

             int newWidth = calculateNewWidth(offsetHorizontal,widthCells,width,tempWidth,startXco,xCo);

             paintHeader(g, xCo, startYco, offset, newWidth - colMargin , offsetHorizontal, "Query");
        }


        for(Table tableItem : tableList){
            if(tempHeight < (height-10  ) && tempHeight >= 0){
               startYco = printTableGetYco(g, startXco, startYco, setting, width, offsetHorizontal, tableItem);
            }
            else{
                startYco = startYco + cellHeight;
                //tempHeight += cellHeight;
            }
            tempHeight += cellHeight;
        }

    }

    /**
     * Method that calculates new width of header
     * @param offsetHorizontal the horizontal offset
     * @param widthCells the width of the cells
     * @param width the width
     * @param tempWidth the temp width
     * @param startXco the start x coordinate of the header
     * @param xCo the x coordinate
     * @return width of header
     */
    private int calculateNewWidth(int offsetHorizontal, int widthCells, int width, int tempWidth, int startXco, int xCo){
         int newWidth;
        if(offsetHorizontal > 0 && offsetHorizontal  - widthCells >=0 ){
            newWidth = widthCells;
        } else if (offsetHorizontal > 0 && offsetHorizontal  - widthCells < 0 ){
            newWidth = widthCells - offsetHorizontal;
        } else if(tempWidth  <= width  ){
            newWidth = widthCells;
        }else{
            newWidth =  (width + startXco) - xCo ;

            System.out.println("Old width=" + widthCells + " New width="+newWidth );
        }
        return newWidth;
    }

	/**
     * Method that paints a row and returns the y coordinate
	 * @param g
	 * @param startXco
	 * @param startYco
	 * @param setting
	 * @param width
	 * @param offsetHorizontal
	 * @param tableItem
	 * @return
	 */
	private int printTableGetYco(Graphics g, int startXco, int startYco, CellVisualisationSettings setting, int width,
			int offsetHorizontal, Table tableItem) {
		// this.paintRectTextOff(g,startXco, startYco - offset , widthCells - offsetHorizontal,cellHeight, tableItem.getTableName(), offsetHorizontal/4 );
		    //int xCo = startXco;
		    //xCo += widthCells + colMargin;
		    //this.paintRectTextOff(g, xCo, startYco - offset, widthList.get(1) - offsetHorizontal, cellHeight, tableItem.getQuery(), offsetHorizontal/4);
		   CellText cellName = new CellText( tableItem.getTableName());
		   CellText cellQuery = new CellText(tableItem.getQuery());

		   List<Cell> listCell = new ArrayList<>();
		   listCell.add(cellName);
		   listCell.add(cellQuery);
		   this.paintRowMargin(g,listCell,startXco,startYco,setting,width,offsetHorizontal,5);
		   
		    startYco = startYco + cellHeight;
		    //tempHeight += cellHeight;
		return startYco;
	}

    /**
     * Method that paints header of table
     * @param g
     * @param startXco
     * @param startYco
     * @param offset
     * @param widthCells
     * @param offsetHorizontal
     * @param title
     */
    public void paintHeader(Graphics g, int startXco, int startYco, int offset, int widthCells, int offsetHorizontal, String title ){
        g.setColor(Color.GRAY);
        g.fillRect(startXco+1, startYco-cellHeight+11 - offset, widthCells-1 - offsetHorizontal, 9 );
        g.setColor(Color.BLACK);
        Font oldFont = g.getFont();
        Font newFont = oldFont.deriveFont(oldFont.getSize() * 0.9F);
        g.setFont(newFont);
        if(widthCells < 25){title="";};
        this.paintRectText(g,startXco, startYco - cellHeight+10 - offset  , widthCells - offsetHorizontal,10, title );
        g.setFont(oldFont);
    }
}
