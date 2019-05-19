package paintModule;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;

import Data.Column;
import Data.Row;
import Data.Table;
import settings.CellVisualisationSettings;
import settings.scrollbar;

public class RowModePaintModule extends PaintModule {

	/**
	 * Method that paints a given table in tabular view
	 * @param g graphics object
	 * @param table table to be painted
	 * @param startXco  start X coordinate where table should be painted
	 * @param startYco  start Y coordinate wher table should be painted
	 */
	/*
	 * TO REFACTOR
	 * too long
	 */
	public void paintRowModeView(Graphics g, Table table, int startXco, int startYco, int width, int height, scrollbar scrollbar,
			int windowHeight, int sum) {
				int verticalOffset = (int) ((windowHeight-titleHeight) * scrollbar.getOffsetpercentageVertical());
				int horizontalOffset = (int) (sum * scrollbar.getOffsetpercentageHorizontal());		
				int headerXco = startXco;
				int i =0;
				CellVisualisationSettings setting = table.getRowSetting();
				List<Integer> widthList = setting.getWidthList();
				Font currentFont = g.getFont();
				Font newFont = currentFont.deriveFont(currentFont.getSize() * 0.89F);
				int tempWidth = widthList.get(i);
				boolean cutCollumn = false;
			    
			    g.setFont(newFont);

			    //ALS Vertical OFFSET > 0 --> GEEN HEADERS
			    if (verticalOffset <= 0 ){
			    //ITERATE over Collumns
			    for(Column column: table.getColumnNames()){
			        //ALS collum volledig verdwenen is
			        if(horizontalOffset > 0 && horizontalOffset  - widthList.get(i) >=0 ){
			            horizontalOffset -= widthList.get(i);
			        }
			        //deel van collum is verdwenen
			        else if(horizontalOffset > 0 && horizontalOffset  - widthList.get(i) < 0 ){
			            //Nieuwe breedte is nu window tot nieuwe collumn
			            int newWidth = widthList.get(i) - horizontalOffset;
			            tempWidth = printHeaderGetTempWidth(g, startYco, headerXco, i, widthList, column, newWidth);
			            horizontalOffset -= widthList.get(i);
			            headerXco += newWidth;

			        }
			        //ALS opgetelde breedte nog niet breder als windowbreedte is
			        else if(tempWidth < width -50){
				        this.paintHeader(g, startYco - cellHeight/2, headerXco, widthList.get(i), column.getName());
			            headerXco += widthList.get(i);
			            if(i < widthList.size() -1){
			            tempWidth += widthList.get(i +1) ;}
			        }
			        //Breedte is wel groter als windowbreedte ( en eerste keer = cutcollumn)
			        else if(!cutCollumn){
			        headerXco = printHeaderGetHeaderXCo(g, startXco, startYco, width, headerXco, i, widthList, column);
			        cutCollumn = true;
			        }
			        //iterating
			        i++;
			    }}
			
			
			    horizontalOffset = (int) (sum * scrollbar.getOffsetpercentageHorizontal());
			    g.setFont(currentFont);
			    systemPrintStartCoords();
			    paintRows(g, table, startXco, startYco, width, height, verticalOffset, horizontalOffset, setting);
			
			
			}
	//=====================

	/**
	 * @param g
	 * @param startYco
	 * @param headerXco
	 * @param i
	 * @param widthList
	 * @param column
	 * @param newWidth
	 * @return
	 */
	private int printHeaderGetTempWidth(Graphics g, int startYco, int headerXco, int i, List<Integer> widthList, Column column,
			int newWidth) {
		int tempWidth;
		String name = column.getName();
		//Naam wordt weggelaten als er te weinig plaats is
		if(newWidth < 50){
		    name = "";
		}
		this.paintHeader(g, startYco - cellHeight/2, headerXco, newWidth, name);
		//headerXco += newWidth;
		//horizontalOffset -= widthList.get(i);

		tempWidth =newWidth;
		tempWidth += widthList.get(i+1);
		return tempWidth;
	}

	/**
	 * @param g
	 * @param startXco
	 * @param startYco
	 * @param width
	 * @param headerXco
	 * @param i
	 * @param widthList
	 * @param column
	 * @return
	 */
	private int printHeaderGetHeaderXCo(Graphics g, int startXco, int startYco, int width, int headerXco, int i, List<Integer> widthList,
			Column column) {
		//Nieuwe breedte is nu tot de breedte van de window
		// int newWidth = width   -  (headerXco - startXco);
		    int newWidth = (width + startXco) - headerXco ;

		String name = column.getName();
		//Naam wordt weggelaten als er te weinig plaats is
		if(newWidth < 50){
		    name = "";
		}
		this.paintHeader(g, startYco - cellHeight/2, headerXco, newWidth, name);
		headerXco += widthList.get(i);
		return headerXco;
	}

	/**
	 * 
	 */
	private void systemPrintStartCoords() {
		System.out.println("previous XCOSTART = " + (this.xCoStart));
		System.out.println("previous YCOSTART = " + (this.yCoStart));
		////this.yCoStart -= offset;
		//this.xCoStart =  startXco - offsetHorizontal;
		System.out.println("new XCOSTART = " + (this.xCoStart));
		System.out.println("new YCOSTART = " + (this.yCoStart));
	}

	/**
	 * @param g
	 * @param table
	 * @param startXco
	 * @param startYco
	 * @param width
	 * @param height
	 * @param verticalOffset
	 * @param horizontalOffset
	 * @param setting
	 */
	private void paintRows(Graphics g, Table table, int startXco, int startYco, int width, int height,
			int verticalOffset, int horizontalOffset, CellVisualisationSettings setting) {
		int tempHeight = -verticalOffset;
		for(Row row: table.getTableRows()){
		    if(tempHeight < (height-10  ) && tempHeight >= 0){
		    this.paintRow(g,row.getColumnList(),startXco ,startYco - verticalOffset, setting, width, horizontalOffset );
		    }
		    startYco = startYco + cellHeight;
		    tempHeight+= cellHeight;
		}
	}
	
}
