package paintModule;

import Data.Cell;
import Data.Column;
import Data.Row;
import Data.Table;
import settings.CellVisualisationSettings;
import settings.scrollbar;


import java.awt.*;
import java.util.List;

/**
 * Superclass that handles the painting.
 */
public class PaintModule {
    //DEFAULT VALUES
	protected int cellHeight = 20;
    protected int cellWidth = 100;
    protected int titleHeight = 15;

    //MINIMUM VALUES
    protected int minCellWidth = 80;
    protected int cellLeftMargin = 10;
    protected int cellTopMargin = 5;
    protected int titleX = 10;
    protected int titleY = 10;
    protected int xCoStart = 30 ;
    protected int yCoStart = 30;
    
    private  int margin = 30;


    public int getMinCellWidth() {
        return minCellWidth;
    }

    public void setMinCellWidth(int minCellWidth) {
        this.minCellWidth = minCellWidth;
    }

    public int getMargin() {
        return margin;
    }

    public void setMargin(int margin) {
        this.margin = margin;
    }



    public void paintCell(int newWidth, Graphics g, int startxCo, int startyCo, Cell rowItem){
        if(newWidth < 20){
            g.drawRect( startxCo,startyCo,newWidth, cellHeight);
        }
        else if (rowItem == null || rowItem.getValue() == null){
            checkBoxEmpty(g, startxCo, startyCo, newWidth);

        } else if (rowItem.getString().equals("false")|| rowItem.getValue().equals(false)) {
            this.checkBoxFalse(g,startxCo,startyCo, newWidth);
        }
        else if (rowItem.getString().equals("true") || rowItem.getValue().equals(true)){
            this.checkBoxTrue(g,startxCo,startyCo,newWidth);
        }
        else if(rowItem.getString().equals("greyed")){
            this.checkBoxEmpty(g, startxCo,startyCo,newWidth);
        }
        else {
            String name = rowItem.getValue().toString();
            if(newWidth < name.length() * 7){
                int delta = newWidth/9;
                name = name.substring(0,delta);
            }
            this.paintRectText(g, startxCo, startyCo, newWidth, cellHeight, name );
        }
    }

    /**
     * Method that paints a single row given a List of strings
     * @param g graphics object
     * @param rowList list of items in row
     * @param startxCo  start X coordinate where row should be painted
     * @param startyCo  start Y coordinate where row should be painted
     * @param setting settings object for this row view
     */
    public void paintRow(Graphics g, List<Cell> rowList, int startxCo, int startyCo, CellVisualisationSettings setting, int width, int offsetHorizontal){

        int i = 0;
        int ogStartxCo = startxCo;
        List<Integer> widthList = setting.getWidthList();
        int tempWidth = widthList.get(i);
        boolean cutCollumn = false;

        //Iterate over cells
        for(Cell rowItem : rowList){
            //ALS cellcollumn volledig verdwenen is
            if(offsetHorizontal > 0 && offsetHorizontal  - widthList.get(i) >=0 ){
                offsetHorizontal -= widthList.get(i);
            }
            //deel van collum is verdwenen
            else if(offsetHorizontal > 0 && offsetHorizontal  - widthList.get(i) < 0 ){
                //Nieuwe breedte is nu window tot nieuwe collumn
                int newWidth = widthList.get(i) - offsetHorizontal;

               paintCell(newWidth,g,startxCo,startyCo,rowItem);
               
                startxCo += newWidth;
                offsetHorizontal -= widthList.get(i);
                tempWidth =newWidth;
                tempWidth += widthList.get(i+1);
            }
            
            //ALS opgetelde breedte nog niet breder als windowbreedte is
            else if(tempWidth < width - 50 ){
                paintCell(widthList.get(i),g,startxCo,startyCo,rowItem);
                
                startxCo = startxCo + widthList.get(i) ;
                if(i != rowList.size()-1){
                tempWidth += widthList.get(i+1);}

            }else if(!cutCollumn){
                int newWidth =  (width + ogStartxCo) - startxCo;
                paintCell(newWidth,g,startxCo,startyCo,rowItem);
                
                startxCo = startxCo + widthList.get(i);
                cutCollumn = true;
                
                
            }
            i++;
        }
        }

    /**
     * Method that paints a single row given a List of strings
     * @param g graphics object
     * @param rowList list of items in row
     * @param startxCo  start X coordinate where row should be painted
     * @param startyCo  start Y coordinate where row should be painted
     * @param setting settings object for this row view
     * @param margin margin
     */
    public void paintRowMargin(Graphics g, List<Cell> rowList, int startxCo, int startyCo, CellVisualisationSettings setting, int width, int offsetHorizontal, int margin){

        int i = 0;
        int ogStartxCo = startxCo;
        List<Integer> widthList = setting.getWidthList();
        int tempWidth = widthList.get(i);
        boolean cutCollumn = false;

        //Iterate over cells
        for(Cell rowItem : rowList){
            //ALS cellcollumn volledig verdwenen is
            if(offsetHorizontal > 0 && offsetHorizontal  - widthList.get(i) >=0 ){
                offsetHorizontal -= widthList.get(i);
            }
            //deel van collum is verdwenen
            else if(offsetHorizontal > 0 && offsetHorizontal  - widthList.get(i) < 0 ){
                //Nieuwe breedte is nu window tot nieuwe collumn
                int newWidth = widthList.get(i) - offsetHorizontal;

                paintCell(newWidth - margin,g,startxCo,startyCo,rowItem);
                
                startxCo += newWidth;
                offsetHorizontal -= widthList.get(i);
                tempWidth =newWidth;
                tempWidth += widthList.get(i+1);

            }
            //ALS opgetelde breedte nog niet breder als windowbreedte is
            else if(tempWidth < width - 50 ){
                paintCell(widthList.get(i)-margin,g,startxCo,startyCo,rowItem);

                startxCo = startxCo + widthList.get(i) ;
                if(i != rowList.size()-1){
                    tempWidth += widthList.get(i+1);}

            }else if(!cutCollumn){
                int newWidth =  (width + ogStartxCo) - startxCo;
                paintCell(newWidth-margin,g,startxCo,startyCo,rowItem);
                
                startxCo = startxCo + widthList.get(i);
                cutCollumn = true;
            }
            i++;
        }


    }

    /**
     * Method that paints the border around the subwindow and the title bar.
     * @param g Graphics object
     * @param coords Start coordinates of the subwindow
     * @param dimension Dimensions of the subwindow
     */
    public void paintBorderSubwindow(Graphics g, Integer[] coords, Integer[] dimension, String title, boolean active){
        paintRectText(g, coords[0], coords[1], dimension[0], dimension[1], "");
        this.paintTitleBar(g, coords[0], coords[1], dimension[0], title, active);
    }

    /**
     * Method that draws a rectangle and places text in it
     * @param g graphics object
     * @param xCo   X coordinate where should be painted
     * @param yCo   Y coordinate where should be painted
     * @param width width of rectangle
     * @param height    height of rectangle
     * @param text  string text to be painted in the rectangle
     */
    //TODO: margin, width, length are now hardcoded should be stored in variables
    public void paintRectText(Graphics g, int xCo, int yCo,int width, int height, String text){
        /**if (width> 200) {
            System.out.println("sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");

        }*/
        //int rectWidth = (int)(text.length()*8.5);
        g.drawRect(xCo,yCo,width,height);
        int yCo2 = 0;
        int xCo2 = 0;
        yCo2 = yCo + height/2 + cellTopMargin; // text in middle of height
        xCo2 = xCo + cellLeftMargin; //margin left
        this.paintText(g, xCo2,yCo2, text);
    }
    //TODO: margin, width, length are now hardcoded should be stored in variables
    public void paintRectTextOff(Graphics g, int xCo, int yCo,int width, int height, String text, int offset){
        //int rectWidth = (int)(text.length()*8.5);
        g.drawRect(xCo,yCo,width,height);
        int yCo2 = 0;
        int xCo2 = 0;
        yCo2 = yCo + height/2 + cellTopMargin; // text in middle of height
        xCo2 = xCo + cellLeftMargin + offset; //margin left
        this.paintText(g, xCo2,yCo2, text);
    }

    /**
     * Method that paints a border
     * @param g graphics object
     * @param xCo X coordinate where border should be painted
     * @param yCo Y coordinate where border should be painted
     * @param width width of border
     * @param height height of border
     * @param color color if border
     */
    public void paintBorder(Graphics g, int xCo, int yCo, int width, int height, Color color){
        g.setColor(color);
        g.drawRect(xCo,yCo,width,height);
        g.drawRect(xCo-1, yCo-1, width+2, height+2);
        g.setColor(Color.BLACK);

    }

    public void setBackground(Graphics g, int xco, int yco, int width, int height, Color color){
        g.setColor(color);
        g.fillRect(xco, yco, width, height);
        g.setColor(Color.BLACK);
    }

    /**
     * Method that paints a box and cursor behind a string of text in the box
     * @param g graphics object
     * @param xCo X coordinate where cursor should be painted
     * @param yCo Y coordinate where cursor should be painted
     * @param width width of border
     * @param height height of border
     * @param text string text to which cursor is added
     */
    public void paintCursor(Graphics g, int xCo, int yCo, int width, int height, String text){
        if(xCo != -1 || yCo != -1){
        clearCell(g, xCo, yCo, width, height);
        g.setColor(Color.BLACK);
        paintRectText(g, xCo, yCo, width, height, text + "|");}

    }

    /**
     * Method that clears a specific cell, by painting it white
     * @param g graphics object
     * @param xCo X coordinate
     * @param yCo Y coordinate
     * @param width width of cell
     * @param height height of cell
     */
    public void clearCell(Graphics g, int xCo, int yCo, int width, int height){
        g.setColor(Color.WHITE);
        g.fillRect(xCo, yCo, width, height);
        g.setColor(Color.BLACK);
    }

    /**
     * Method to get the coordinates of a specific painted cell
     * @param x
     * @param y
     * @return
     */
    public int[] getCellCoords(int x,int y, List<Integer> widthList, scrollbar scrollbar, int windowHeight){
        int xCoord = this.getxCoStart();
        int sum = widthList.stream().mapToInt(Integer::intValue).sum();
        //Vertical offset
        int offsetVertical = (int) ((windowHeight-titleHeight) * scrollbar.getOffsetpercentageVertical());
        //Horizontal offset
        int offsetHorizontal = (int) (sum * scrollbar.getOffsetpercentageHorizontal());


        for(int i= 0; i < y; i++){
            if(offsetHorizontal == 0){
                xCoord += widthList.get(i);
            }
            else if(offsetHorizontal >= widthList.get(i)){
                offsetHorizontal -= widthList.get(i);
            }
            else if(offsetHorizontal < widthList.get(i) ){
                xCoord += (widthList.get(i) - offsetHorizontal);
                offsetHorizontal =0;
            }
        }
        int yCoord = getYFromOffset(x, offsetVertical);

        int[] result = {xCoord,yCoord};
        return result;
    }

	/**
	 * @param x
	 * @param offsetVertical
	 * @return yCoord 
	 */
	private int getYFromOffset(int x, int offsetVertical) {
		int yCoord = -1;
        if(offsetVertical <=0 ){
             yCoord = this.getyCoStart() + x*this.getCellHeight();
        }
        else if(offsetVertical > 0){
            int i = 0;
            while(offsetVertical >0){
                i++;
                offsetVertical -= cellHeight;
            }
             yCoord = this.getyCoStart() + (x - i)*this.getCellHeight();
        }
		return yCoord;
	}

    //Simple method that draws a title in left upper corner

    /**
     * Method to paint a title
     * @param g graphics object
     * @param text string text to be painted
     */
    public void paintTitle(Graphics g, String text){
        g.drawString("Current mode:" + text, titleX,titleY);
    }

    /**
     * Method that draws text on the screen
     * @param g graphics object
     * @param xCo X coordinate
     * @param yCo Y coordinate
     * @param text string text to be painted
     */
    public void paintText(Graphics g, int xCo, int yCo, String text){
        g.drawString(text, xCo,yCo);
    }

    /**
     * Method to change the color
     * @param g graphics object
     * @param c color to be set
     */
    public void setColor(Graphics g, Color c){
        g.setColor(c);
    }

    /**
     * Method that draws a checked checkbox
     * @param g graphics g
     * @param xCo X coordinate
     * @param yCo Y coordinate
     * @param width width of cell
     */
    public void checkBoxTrue(Graphics g, int xCo, int yCo, int width){
        g.drawRect(xCo,yCo, width, cellHeight);
        g.drawRect(xCo + width/2 - 5,yCo + 5,8,8);
        xCo = xCo + width/2 - 5;
        yCo = yCo + 5;
        g.setColor(Color.BLACK);
        g.drawLine(xCo+2, yCo+2, xCo+4, yCo+6);
        g.drawLine(xCo+4, yCo+6, xCo+6, yCo+2);
        g.setColor(Color.BLACK);
    }

    /**
     * Method that draws an unchecked checkbox
     * @param g graphics object
     * @param xCo X coordinate
     * @param yCo Y coordinate
     * @param width width of cell
     */
    public void checkBoxFalse(Graphics g, int xCo, int yCo, int width){
        clearCell(g,xCo,yCo,width, cellHeight);//cellheight needs to be given
        g.drawRect(xCo,yCo, width, cellHeight);
        g.drawRect(xCo + width/2 - 5,yCo + 5,8,8);
        g.setColor(Color.BLACK);
    }

    /**
     * Method that draws a greyed out checkbox
     * @param g graphics object
     * @param xCo X coordinate
     * @param yCo Y coordinate
     * @param width width of cell
     */
    public void checkBoxEmpty(Graphics g, int xCo, int yCo, int width){

        clearCell(g,xCo,yCo,width, cellHeight);//cellheight needs to be given

        g.drawRect(xCo,yCo, width, cellHeight);
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(xCo + width/2 - 4,yCo  + 6,7,7);
        g.setColor(Color.BLACK);
        g.drawRect(xCo + width/2 - 5,yCo + 5,8,8);
        g.setColor(Color.BLACK);

    }

    /**
     * Method that paints the title bar of a subwindow
     * @param g Graphics object
     * @param xCo Start X coordinate
     * @param yCo   Start Y coordinate
     * @param width width of the subwindow
     * @param title text to be displayed as title
     */
    public  void paintTitleBar(Graphics g, int xCo, int yCo, int width, String title, boolean active){
        Color myColor1;
        if (active) {
            myColor1 = new Color(0,248,255);
        } else {
            myColor1 = new Color(240,248,255);
        }
        g.setColor(myColor1);
        g.fillRect(xCo,yCo,width,titleHeight);
        g.setColor(Color.BLACK);
        paintRectText(g, xCo, yCo, width, titleHeight, "");
        paintText(g, xCo+30, yCo+13, title);
        g.setColor(Color.RED);
        paintClosingButton(g, xCo+5+titleHeight/4, yCo+titleHeight/4);
        g.setColor(Color.BLACK);
    }

    /**
     * Method that paints a horizantal scrollbar, with a button to slide
     * @param g
     * @param xCo
     * @param yCo
     * @param width
     * @param percentage
     */
    public void paintHScrollBar(Graphics g, int xCo, int yCo, int width, double percentage, scrollbar scrollbar){
        g.drawRect(xCo,yCo,width ,10);
        int newWidth = (int) (percentage * (width-15) ) ;

        int offset = xCo +  (int) (scrollbar.getOffsetpercentageHorizontal() * (width));

        width = newWidth;
        g.drawRect( offset ,yCo,width,10);
        Color myColor = new Color(153, 153, 255);
        g.setColor(myColor);
        g.fillRect(offset+1, yCo+1, width -1,9);
        g.setColor(Color.BLACK);
    }

    /**
     * Method that paints a vertical scrollbar, with a button to slide
     * @param g
     * @param xCo
     * @param yCo
     * @param height
     * @param percentage
     */
    public void paintVScrollBar(Graphics g, int xCo, int yCo, int height, double percentage, scrollbar scrollbar ){

        g.drawRect(xCo,yCo ,10,height);
        int newHeight = (int) (percentage * (height-15)) ;

        int offset = yCo +  (int) (scrollbar.getOffsetpercentageVertical() * (height -15));
        height = newHeight;

        g.drawRect( xCo,offset ,10,height);
        Color myColor = new Color(153, 153, 255);
        g.setColor(myColor);
        g.fillRect(xCo+1, offset+1, 9,height -1);
        g.setColor(Color.BLACK);
    }



    public void paintClosingButton(Graphics g, int xCo, int yCo){
        g.fillOval(xCo, yCo, titleHeight-5, titleHeight-5);
    }

    public int getxCoStart() {
        return xCoStart;
    }

    public int getyCoStart() {
        return yCoStart;
    }

    public int getCellHeight() {
        return cellHeight;
    }

    public int getCellWidth() {
        return cellWidth;
    }
    public int getCellLeftMargin() {
        return cellLeftMargin;
    }

    /**
	 * @param g
	 * @param headerYco
	 * @param headerXco
	 * @param Width
	 * @param name
	 */
	protected void paintHeader(Graphics g, int headerYco, int headerXco, int Width, String name) {
		g.setColor(Color.GRAY);
		g.fillRect(headerXco ,headerYco, Width, cellHeight/2);
		g.setColor(Color.BLACK);
		this.paintRectText(g,headerXco , headerYco, Width,cellHeight/2,name);
	}

}
