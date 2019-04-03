package paintModule;

import Data.Column;
import Data.Row;
import Data.Table;
import settings.settings;


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








    /**
     * Method that paints a given table in tabular view
     * @param g graphics object
     * @param table table to be painted
     * @param startXco  start X coordinate where table should be painted
     * @param startYco  start Y coordinate wher table should be painted
     */
    public void paintTable(Graphics g, Table table, int startXco, int startYco){
        int headerXco = startXco;
        int i =0;
        settings setting = table.getRowSetting();
        List<Integer> widthList = setting.getWidthList();
        for(Column column: table.getColumnNames()){
            this.paintRectText(g,headerXco, startYco - cellHeight/2, widthList.get(i),cellHeight,column.getName());
            headerXco += widthList.get(i);
            i++;

        }
        for(Row row: table.getTableRows()){
            this.paintRow(g,row.getColumnList(),startXco,startYco, setting);
            startYco = startYco + 20;
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
     public void paintTableView(Graphics g, List<Table> tableList, int startXco, int startYco, settings setting){
         List<Integer> widthList = setting.getWidthList();
        this.paintRectText(g,startXco, startYco - cellHeight+10 , widthList.get(0),10, "" );
        g.setColor(Color.GRAY);
        g.fillRect(startXco+1, startYco-cellHeight+11, widthList.get(0)-1, 9 );
        g.setColor(Color.BLACK);
        for(Table tableItem : tableList){
            this.paintRectText(g,startXco, startYco , widthList.get(0),cellHeight, tableItem.getTableName() );
            startYco = startYco + cellHeight;
        }

    }

    /**
     * Method that paints the design view
     * It shows for each column the name, default value, type & if blanks are allowed
     * @param g graphics object
     * @param table table for which design view has to be painted
     */
    public void paintDesignView(Graphics g, Table table){
        int headerXco = getxCoStart();
        int headerYco = getyCoStart() - cellHeight;
        settings setting = table.getDesignSetting();
        List<Integer> widthList = setting.getWidthList();
        String[] names = {"Name", "Default value", "Type", "Blank?"};
        for(int i = 0; i <4; i++){
            this.paintRectText(g,headerXco, headerYco, widthList.get(i),cellHeight, names[i]);
            System.out.println(names[i]);
            headerXco += widthList.get(i);
        }
        int startYco = this.getyCoStart();
        for(Column column: table.getColumnNames()){
            List<String> rowInfo = column.getInfo();
            this.paintRow(g,rowInfo,this.getxCoStart(),startYco, setting);
            startYco = startYco + cellHeight;
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
    //TODO: should accept list with Row elements (instead of strings) and iterate over that list and get row elements
    public void paintRow(Graphics g, List<String> rowList, int startxCo, int startyCo, settings setting){
        int i = 0;
        List<Integer> widthList = setting.getWidthList();
        for(String rowItem : rowList){
            if (rowItem.equals("true")){
                this.checkBoxTrue(g,startxCo,startyCo, widthList.get(i));
            } else if (rowItem.equals("false")) {
                this.checkBoxFalse(g,startxCo,startyCo, widthList.get(i));
            }
            else if (rowItem.equals("empty")){
                checkBoxEmpty(g, startxCo, startyCo, widthList.get(i));
            }
            else {
                this.paintRectText(g, startxCo, startyCo, widthList.get(i), cellHeight, rowItem);
            }
            startxCo = startxCo + widthList.get(i);
            i++;
        }
    }

    /**
     * Method that paints the border around the subwindow and the title bar.
     * @param g Graphics object
     * @param coords Start coordinates of the subwindow
     * @param dimension Dimensions of the subwindow
     */
    public void paintBorderSubwindow(Graphics g, Integer[] coords, Integer[] dimension, String title){
        paintRectText(g, coords[0], coords[1], dimension[0], dimension[1], "");
        this.paintTitleBar(g, coords[0], coords[1], dimension[0], title);
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
    public  void paintTitleBar(Graphics g, int xCo, int yCo, int width, String title){
        paintRectText(g, xCo, yCo, width, titleHeight, "");
        paintText(g, xCo+30, yCo+13, title);
        g.setColor(Color.RED);
        paintClosingButton(g, xCo+5+titleHeight/4, yCo+titleHeight/4);
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


}
