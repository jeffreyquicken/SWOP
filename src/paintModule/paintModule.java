package paintModule;

import Data.Cell;
import Data.Column;
import Data.Row;
import Data.Table;
import settings.CellVisualisationSettings;
import settings.scrollbar;


import java.awt.*;
import java.util.List;

public class paintModule {
    //DEFAULT VALUES
    private int cellHeight = 20;
    private int cellWidth = 100;
    private int titleHeight = 15;


    public int getMinCellWidth() {
        return minCellWidth;
    }

    public void setMinCellWidth(int minCellWidth) {
        this.minCellWidth = minCellWidth;
    }

    //MINIMUM VALUES
    private int minCellWidth = 80;
    private int cellLeftMargin = 10;
    private int cellTopMargin = 5;
    private int titleX = 10;
    private int titleY = 10;
    private int xCoStart = 30 ;
    private int yCoStart = 30;

    public int getMargin() {
        return margin;
    }

    public void setMargin(int margin) {
        this.margin = margin;
    }

    private  int margin = 30;


/*
 * 
 * TO REFACTOR
 * too many methods
 * assign more responsibilities to another class
 */




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
    public void paintTable(Graphics g, Table table, int startXco, int startYco, int width, int height, scrollbar scrollbar, int windowHeight, int sum){

        //Vertical offset
        int offset = (int) ((windowHeight-titleHeight) * scrollbar.getOffsetpercentageVertical());
        //Horizontal offset
        int offsetHorizontal = (int) (sum * scrollbar.getOffsetpercentageHorizontal());


        int headerXco = startXco;
        int i =0;

        CellVisualisationSettings setting = table.getRowSetting();
        List<Integer> widthList = setting.getWidthList();

        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() * 0.89F);
        g.setFont(newFont);

        int tempWidth = widthList.get(i);
        boolean cutCollumn = false;
        //ALS Vertical OFFSET > 0 --> GEEN HEADERS
        if (offset <= 0 ){
        //ITERATE over Collumns
        for(Column column: table.getColumnNames()){
            //ALS collum volledig verdwenen is
            if(offsetHorizontal > 0 && offsetHorizontal  - widthList.get(i) >=0 ){
                offsetHorizontal -= widthList.get(i);
            }
            //deel van collum is verdwenen
            else if(offsetHorizontal > 0 && offsetHorizontal  - widthList.get(i) < 0 ){
                //Nieuwe breedte is nu window tot nieuwe collumn
                int newWidth = widthList.get(i) - offsetHorizontal;
                String name = column.getName();
                //Naam wordt weggelaten als er te weinig plaats is
                if(newWidth < 100){
                    name = "";
                }
                g.setColor(Color.GRAY);
                g.fillRect(headerXco ,startYco - cellHeight/2, newWidth, cellHeight/2);
                g.setColor(Color.BLACK);
                this.paintRectText(g,headerXco , startYco - cellHeight/2, newWidth,cellHeight/2,name);
                headerXco += newWidth;
                offsetHorizontal -= widthList.get(i);
                tempWidth =newWidth;


            }
            //ALS opgetelde breedte nog niet breder als windowbreedte is
            else if(tempWidth < width + 45){
                g.setColor(Color.GRAY);
                g.fillRect(headerXco,startYco - cellHeight/2, widthList.get(i) , cellHeight/2);
                g.setColor(Color.BLACK);
                this.paintRectText(g,headerXco, startYco - cellHeight/2, widthList.get(i) ,cellHeight/2,column.getName());
                headerXco += widthList.get(i);
                if(i < widthList.size() -1){
                tempWidth += widthList.get(i +1) ;}
            }
            //Breedte is wel groter als windowbreedte ( en eerste keer = cutcollumn)
            else if(!cutCollumn){

            //Nieuwe breedte is nu tot de breedte van de window
           // int newWidth = width   -  (headerXco - startXco);
                int newWidth = (width + startXco) - headerXco ;
            String name = column.getName();
            //Naam wordt weggelaten als er te weinig plaats is
            if(newWidth < 100){
                name = "";
            }
            g.setColor(Color.GRAY);
            g.fillRect(headerXco,startYco - cellHeight/2, newWidth, cellHeight/2);
            g.setColor(Color.BLACK);
            this.paintRectText(g,headerXco, startYco - cellHeight/2, newWidth,cellHeight/2,name);
            headerXco += widthList.get(i);
            cutCollumn = true;

            }
            //iterating
            i++;
        }}

       offsetHorizontal = (int) (sum * scrollbar.getOffsetpercentageHorizontal());
        g.setFont(currentFont);
        int tempHeight = -offset;
        this.yCoStart =  startYco - offset;
        for(Row row: table.getTableRows()){


            if(tempHeight < (height-10  ) && tempHeight >= 0){
            this.paintRow(g,row.getColumnList(),startXco ,startYco - offset, setting, width, offsetHorizontal );
            startYco = startYco + 20;
            tempHeight+= 20;
            }

            else{
            startYco = startYco + cellHeight;
            tempHeight += cellHeight;
        }

        }

    }

    /**
     * Method that paints a tabular view from a list of Tables
     * @param g graphics object
     * @param tableList list of tables to be painted in view
     * @param startXco  start X coordinate where tableview should be painted
     * @param startYco  start Y coordinate where tableview should be painted
     * @param setting   settings object for this view
     */
    /*
     * TO REFACTOR
     * borderline too long
     */
     public void paintTableView(Graphics g, List<Table> tableList, int startXco, int startYco, CellVisualisationSettings setting, int width, int height, scrollbar scrollbar, int heigth){


         int offsetHorizontal = (int) (width * scrollbar.getOffsetpercentageHorizontal());

         List<Integer> widthList = setting.getWidthList();

         int widthCells = widthList.get(0);
         if(widthCells - offsetHorizontal > width ){
             widthCells = width;
         }

         int offset = (int) ((heigth-titleHeight) * scrollbar.getOffsetpercentageVertical());

         if (offset <= 0 ){
        this.paintRectText(g,startXco, startYco - cellHeight+10 - offset  , widthCells - offsetHorizontal,10, "" );
        g.setColor(Color.GRAY);
        g.fillRect(startXco+1, startYco-cellHeight+11 - offset, widthCells-1 - offsetHorizontal, 9 );
        g.setColor(Color.BLACK);}


        int tempHeight = -offset;
         //this.yCoStart =  startYco - offset;
        for(Table tableItem : tableList){
            if(tempHeight < (height-10  ) && tempHeight >= 0){

                this.paintRectTextOff(g,startXco, startYco - offset , widthCells - offsetHorizontal,cellHeight, tableItem.getTableName(), offsetHorizontal/4 );
                startYco = startYco + cellHeight;
                tempHeight += cellHeight;
            }
            else{
                startYco = startYco + cellHeight;
                tempHeight += cellHeight;
            }

        }

    }

    /**
     * Method that paints the design view
     * It shows for each column the name, default value, type & if blanks are allowed
     * @param g graphics object
     * @param table table for which design view has to be painted
     */
     /*
      * TO REFACTOR
      * too long
      */
    public void paintDesignView(Graphics g, Table table, int startXco, int startYco, CellVisualisationSettings setting, int width, int height){
        int headerXco = startXco;
        int headerYco = startYco - cellHeight/2;;
        List<Integer> widthList = setting.getWidthList();

        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() * 0.9F);
        g.setFont(newFont);
        String[] names = {"Name", "Default value", "Type", "Blank?"};
        int tempWidth = widthList.get(0);
        boolean cutCollumn = false;
        for(int i = 0; i <4; i++){
            if(tempWidth < width){
            g.setColor(Color.GRAY);
            g.fillRect(headerXco, headerYco, widthList.get(i),cellHeight/2);
            g.setColor(Color.BLACK);
            this.paintRectText(g,headerXco, headerYco, widthList.get(i),cellHeight/2, names[i]);
            System.out.println(names[i]);
            headerXco += widthList.get(i);
            if(i != 3){
            tempWidth += widthList.get(i +1);}
            }else if(!cutCollumn){
                int newWidth = width - (headerXco - startXco);
                String name = names[i];

                if(newWidth < name.length() * 7){
                    int delta = newWidth/8;
                    name = name.substring(0,delta);
                }
                g.setColor(Color.GRAY);
                g.fillRect(headerXco, headerYco,newWidth,cellHeight/2);
                g.setColor(Color.BLACK);
                this.paintRectText(g,headerXco, headerYco, newWidth,cellHeight/2, name);
                System.out.println(names[i]);
                headerXco += widthList.get(i);
                cutCollumn = true;
            }
        }
        g.setFont(currentFont);
        for(Column column: table.getColumnNames()){
            int tempHeight = 0;
            if(tempHeight < height){
            List<Cell> rowInfo = column.getInfo();
            this.paintRow(g,rowInfo,startXco,startYco, setting, width, 1);
            startYco = startYco + cellHeight;
            tempHeight += cellHeight;
            }
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
    /*
     * TO REFACTOR
     * too long
     */
    public void paintRow(Graphics g, List<Cell> rowList, int startxCo, int startyCo, CellVisualisationSettings setting, int width, int offset){
        int i = 0;
        int ogStartxCo = startxCo;
        List<Integer> widthList = setting.getWidthList();
        int tempWidth = widthList.get(i);


        boolean cutCollumn = false;
        if( widthList.get(i) - offset >= 0 ){


        for(Cell rowItem : rowList){
            if(tempWidth < width ){
                if (rowItem.getValue() == null){
                    checkBoxEmpty(g, startxCo, startyCo, widthList.get(i) - offset);

                } else if (rowItem.getValue().equals(false)) {
                    this.checkBoxFalse(g,startxCo,startyCo, widthList.get(i) - offset);
                }
                else if (rowItem.getValue().equals(true)){
                    this.checkBoxTrue(g,startxCo,startyCo, widthList.get(i) - offset);
                }
                else {
                    this.paintRectText(g, startxCo, startyCo, widthList.get(i) - offset, cellHeight, rowItem.getValue().toString());
                }

                startxCo = startxCo + widthList.get(i) - offset;
                offset = offset - widthList.get(i);
                if(i != rowList.size()-1){
                tempWidth += widthList.get(i+1);}
                i++;

            }else if(!cutCollumn){
                int newWidth =  width - (startxCo - ogStartxCo) ;
                if(newWidth < 20){
                    int pass;
                    g.drawRect( startxCo,startyCo,newWidth, cellHeight);
                }
                else if (rowItem.getValue() == null){
                    checkBoxEmpty(g, startxCo, startyCo, newWidth);

                } else if (rowItem.getValue().equals(false)) {
                    this.checkBoxFalse(g,startxCo,startyCo, newWidth);
                }
                else if (rowItem.getValue().equals(true)){
                    this.checkBoxTrue(g,startxCo,startyCo,newWidth);
                }
                else {
                    String name = rowItem.getValue().toString();
                    if(newWidth < name.length() * 7){
                        int delta = newWidth/9;
                        name = name.substring(0,delta);
                    }
                    this.paintRectText(g, startxCo, startyCo, newWidth, cellHeight, name );
                }
                startxCo = startxCo + widthList.get(i);
                cutCollumn = true;
                i++;
            } }


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
    public int[] getCellCoords(int x,int y, List<Integer> widthList){
        int xCoord = this.getxCoStart();
        for(int i= 0; i < y; i++){
            xCoord += widthList.get(i);
        }
        int yCoord = this.getyCoStart() + x*this.getCellHeight();
        int[] result = {xCoord,yCoord};
        return result;
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
        g.setColor(Color.BLACK);}



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


}
