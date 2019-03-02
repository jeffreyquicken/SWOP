package paintModule;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class paintModule {

    //Method that draw list of table names in a collumn
    //For every element in its list it calls the printRectText method
    //TODO: should accept list with table elements (instead of strings) and iterate over that list and get table name
     public void paintTableView(Graphics g, List<String> tableList, int startXco, int startyCo){
        for(String tableItem : tableList){
            this.paintRectText(g,startXco, startyCo, 20 , tableItem );
            startyCo = startyCo + 20;
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
        int rectWidth = 60;
        g.drawRect(xCo,yCo,rectWidth,height);
        int yCo2;
        int xCo2;
        yCo2 = yCo + height/2 + 2; // text in middle of height
        xCo2 = xCo + 10; //margin left
        this.paintText(g, xCo2,yCo2, text);
    }
    //Simple method that draws a title in left upper corner
    public void paintTitle(Graphics g, String text){
        g.drawString("Current mode:" + text, 10,10);
    }

    //Simple method that draws text on screen
    public void paintText(Graphics g, int xCo, int yCo, String text){
        g.drawString(text, xCo,yCo);
    }

}
