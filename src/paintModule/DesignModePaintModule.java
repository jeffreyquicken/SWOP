package paintModule;


import java.awt.Font;
import java.awt.Graphics;
import java.util.List;

import Data.Cell;
import Data.Column;
import Data.Table;
import settings.CellVisualisationSettings;
import settings.scrollbar;

public class DesignModePaintModule extends PaintModule {

	
	/**
     * Method that paints the design view
     * It shows for each column the name, default value, type & if blanks are allowed
     * @param g graphics object
     * @param table table for which design view has to be painted
     */

     //todo REFACTOR ASAP
    public void paintDesignView(Graphics g, Table table, int startXco, int startYco, CellVisualisationSettings setting, int width, int height, scrollbar scrollbar, int windowHeight, int sum){
    	//Vertical offset
        int offset = (int) ((windowHeight-titleHeight) * scrollbar.getOffsetpercentageVertical());
        //Horizontal offset
        int offsetHorizontal = (int) (sum * scrollbar.getOffsetpercentageHorizontal());
        int headerXco = startXco;
        int headerYco = startYco - cellHeight/2;
        List<Integer> widthList = setting.getWidthList();
        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() * 0.9F);
        String[] names = {"Name", "Default value", "Type", "Blank?"};
        int tempWidth = widthList.get(0);
        boolean cutCollumn = false;
        
        g.setFont(newFont);

        //NO headers if there is an offset
        if (offset <=0){
        //ITERATE over collumns names
        for(int i = 0; i <4; i++){
            if(offsetHorizontal > 0 && offsetHorizontal  - widthList.get(i) >=0 ){
                offsetHorizontal -= widthList.get(i);
            }
            //deel van collum is verdwenen
            else if(offsetHorizontal > 0 && offsetHorizontal  - widthList.get(i) < 0 ){
                //Nieuwe breedte is nu window tot nieuwe collumn
                int newWidth = widthList.get(i) - offsetHorizontal;
                String name = names[i];
        		//Naam wordt weggelaten als er te weinig plaats is
        		if(newWidth < 50){
        		    name = "";
        		}
        		paintHeader(g, startYco - cellHeight/2, headerXco, newWidth, name);
        		headerXco += newWidth;                
                offsetHorizontal -= widthList.get(i);

                tempWidth =newWidth;
                if(widthList.size() != i-1){
                tempWidth += widthList.get(i+1);}

            }
            //ALS opgetelde breedte nog niet breder als windowbreedte is
            else if(tempWidth < width -50){
    		paintHeader(g, headerYco, headerXco, widthList.get(i), names[i]);
            System.out.println(names[i]);
            headerXco += widthList.get(i);
            if(i != 3){
            tempWidth += widthList.get(i +1);}
            }
            else if(!cutCollumn) {

                int newWidth = (width +startXco) - headerXco;
                String name = names[i];

                if (newWidth < name.length() * 7) {
                    int delta = newWidth / 8;
                    name = name.substring(0, delta);
                }
        		paintHeader(g, headerYco, headerXco, newWidth, name);
                System.out.println(names[i]);
                headerXco += widthList.get(i);
                cutCollumn = true;
            }
        }}

        offsetHorizontal = (int) (sum * scrollbar.getOffsetpercentageHorizontal());
        g.setFont(currentFont);

        int tempHeight = -offset;

        for(Column column: table.getColumnNames()){
            if(tempHeight < (height - 10) && tempHeight >=0) {
            List<Cell> rowInfo = column.getInfo();
            this.paintRow(g,rowInfo,startXco,startYco, setting, width, offsetHorizontal);
            startYco = startYco + cellHeight;
            tempHeight += cellHeight;
            }
            else{
                startYco = startYco + cellHeight;
                tempHeight += cellHeight;
            }
        }

    }
//======================
    
}
