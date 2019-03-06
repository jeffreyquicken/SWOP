package paintModule;

import Data.Column;
import Data.Row;
import Data.Table;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class paintModule {
    private int cellHeight = 20;
    private int cellWidth = 50;
    private int cellMargin = 10;
    private int titleX = 10;
    private  int titleY = 10;


    public void paintTable(Graphics g, Table table, int startXco, int startyCo){
        for(Row row: table.getTableRows()){
            this.paintRow(g,row.getColcumnList(),startXco,startyCo);
            startyCo = startyCo + 20;
        }

    }

    //Method that draw list of table names in a collumn
    //For every element in its list it calls the printRectText method
    //TODO: should accept list with table elements (instead of strings) and iterate over that list and get table name
     public void paintTableView(Graphics g, List<Table> tableList, int startXco, int startyCo){
        for(Table tableItem : tableList){
            this.paintRectText(g,startXco, startyCo , cellHeight, tableItem.getTableName() );
            startyCo = startyCo + cellHeight;
        }
    }

    //Method that draws list of rownelements in a row
    //For every element in its list it calls the printRectText method
    //TODO: should accept list with Row elements (instead of strings) and iterate over that list and get row elements
    public void paintRow(Graphics g, List<String> rowList, int startxCo, int startyCo){
        for(String rowItem : rowList){
            this.paintRectText(g, startxCo, startyCo, 20 , rowItem );
            startxCo = startxCo + 60;
        }
    }

    //Simple Method that draws a rectangle and places text in it
    //TODO: margin, width, length are now hardcoded should be stored in variables
    public void paintRectText(Graphics g, int xCo, int yCo, int height, String text){
        //int rectWidth = (int)(text.length()*8.5);
        g.drawRect(xCo,yCo,cellWidth,height);
        int yCo2 = 0;
        int xCo2 = 0;
        yCo2 = yCo + height/2 + 2; // text in middle of height
        xCo2 = xCo + cellMargin; //margin left
        this.paintText(g, xCo2,yCo2, text);
    }

    //Simple method that draws a title in left upper corner
    public void paintTitle(Graphics g, String text){
        g.drawString("Current mode:" + text, titleX,titleY);
    }

    //Simple method that draws text on screen
    public void paintText(Graphics g, int xCo, int yCo, String text){
        g.drawString(text, xCo,yCo);
    }

}
