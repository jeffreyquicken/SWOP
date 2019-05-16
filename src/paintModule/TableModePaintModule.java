package paintModule;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import Data.*;
import settings.CellVisualisationSettings;
import settings.scrollbar;

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

         if(widthCells - offsetHorizontal > width ){
             widthCells = width;
         }
         if (offset <= 0 ){
            //paint the header
            paintHeader(g, startXco, startYco, offset, widthCells, offsetHorizontal, "Name");
             int xCo = startXco;
             xCo += widthCells + colMargin;
             paintHeader(g, xCo, startYco, offset, widthCells, offsetHorizontal, "Query");
        }
         System.out.println("previous YCOSTART = " + (this.yCoStart));
       //  this.yCoStart -=  offset;
         System.out.println("new YCOSTART = " + (this.yCoStart));
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

    public void paintHeader(Graphics g, int startXco, int startYco, int offset, int widthCells, int offsetHorizontal, String title){
        g.setColor(Color.GRAY);
        g.fillRect(startXco+1, startYco-cellHeight+11 - offset, widthCells-1 - offsetHorizontal, 9 );
        g.setColor(Color.BLACK);
        Font oldFont = g.getFont();
        Font newFont = oldFont.deriveFont(oldFont.getSize() * 0.9F);
        g.setFont(newFont);
        this.paintRectText(g,startXco, startYco - cellHeight+10 - offset  , widthCells - offsetHorizontal,10, title );
        g.setFont(oldFont);

    }
}
