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


    public int getMinCellWidth() {
        return minCellWidth;
    }

    public void setMinCellWidth(int minCellWidth) {
        this.minCellWidth = minCellWidth;
    }

    //MINIMUM VALUES
    private int minCellWidth = 80;


    public int getCellLeftMargin() {
        return cellLeftMargin;
    }

    private int cellLeftMargin = 10;
    private int cellTopMargin = 5;
    private int titleX = 10;
    private int titleY = 10;
    private int xCoStart = 50 ;
    private int yCoStart = 50;



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




    public void paintTable(Graphics g, Table table, int startXco, int startYco){
        int headerXco = startXco;
        int i =0;
        settings setting = table.getRowSetting();
        List<Integer> widthList = setting.getWidthList();
        for(Column column: table.getColumnNames()){
            this.paintRectText(g,headerXco, startYco - cellHeight, widthList.get(i),cellHeight,column.getName());
            headerXco += widthList.get(i);
            i++;

        }
        for(Row row: table.getTableRows()){
            this.paintRow(g,row.getColumnList(),startXco,startYco, setting);
            startYco = startYco + 20;
        }

    }

    //Method that draw list of table names in a collumn
    //For every element in its list it calls the printRectText method
    //TODO: should accept list with table elements (instead of strings) and iterate over that list and get table name
     public void paintTableView(Graphics g, List<Table> tableList, int startXco, int startyCo, settings setting ){
         List<Integer> widthList = setting.getWidthList();
        this.paintRectText(g,startXco, startyCo - cellHeight , widthList.get(0),cellHeight, "HEADER" );
        for(Table tableItem : tableList){
            this.paintRectText(g,startXco, startyCo , widthList.get(0),cellHeight, tableItem.getTableName() );
            startyCo = startyCo + cellHeight;
        }
    }

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

    //Method that draws list of rowelements in a row
    //For every element in its list it calls the printRectText method
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

    //Simple Method that draws a rectangle and places text in it
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

    public void paintBorder(Graphics g, int xCo, int yCo, int width, int height, Color color){
        g.setColor(color);
        g.drawRect(xCo,yCo,width,height);
        g.drawRect(xCo-1, yCo-1, width+2, height+2);

    }

    public void paintCursor(Graphics g, int xCo, int yCo, int width, int height, String text){
        if(xCo != -1 || yCo != -1){
        clearCell(g, xCo, yCo, width, height);
        g.setColor(Color.BLACK);
        paintRectText(g, xCo, yCo, width, height, text + "|");}

    }

    public void clearCell(Graphics g, int xCo, int yCo, int width, int height){
        g.setColor(Color.WHITE);
        g.fillRect(xCo, yCo, width, height);
        g.setColor(Color.BLACK);
    }

    public int[] getCellCoords(int x,int y){
        int xCoord = this.getxCoStart() + y*this.getCellWidth();
        int yCoord = this.getyCoStart() + x*this.getCellHeight();
        int[] result = {xCoord,yCoord};
        return result;
    }

    //Simple method that draws a title in left upper corner
    public void paintTitle(Graphics g, String text){
        g.drawString("Current mode:" + text, titleX,titleY);
    }

    //Simple method that draws text on screen
    public void paintText(Graphics g, int xCo, int yCo, String text){
        g.drawString(text, xCo,yCo);
    }
    public void setColor(Graphics g, Color c){
        g.setColor(c);
    }
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
    public void checkBoxFalse(Graphics g, int xCo, int yCo, int width){
        clearCell(g,xCo,yCo,width, cellHeight);//cellheight needs to be given
        g.drawRect(xCo,yCo, width, cellHeight);
        g.drawRect(xCo + width/2 - 5,yCo + 5,8,8);
        g.setColor(Color.BLACK);
    }
    public void checkBoxEmpty(Graphics g, int xCo, int yCo, int width){

        clearCell(g,xCo,yCo,width, cellHeight);//cellheight needs to be given

        g.drawRect(xCo,yCo, width, cellHeight);
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(xCo + width/2 - 4,yCo  + 6,7,7);
        g.setColor(Color.BLACK);
        g.drawRect(xCo + width/2 - 5,yCo + 5,8,8);
        g.setColor(Color.BLACK);

    }


}
