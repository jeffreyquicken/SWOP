package paintModule;

import Data.*;
import settings.CellVisualisationSettings;
import settings.scrollbar;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FormModePaintModule extends PaintModule{



    /**
     * Method that paints the form view
     * It shows for a specific row: the name, all the columns and a form field for every column
     * @param g graphics object
     * @param table table for which form view has to be painted
     */
    //todo REFACTOR ASAP
    public void paintFormView(Graphics g, Table table, int rowIndex, int startXco, int startYco, CellVisualisationSettings setting, int width, int height, scrollbar scrollbar, int windowHeight, int sum){
        //Vertical offset
        int offset = (int) ((windowHeight-titleHeight) * scrollbar.getOffsetpercentageVertical());
        //Horizontal offset
        int offsetHorizontal = (int) (sum * scrollbar.getOffsetpercentageHorizontal());
        int headerXco = startXco;
        int headerYco = startYco - cellHeight/2;;
        java.util.List<Integer> widthList = setting.getWidthList();

        String[] names = {"Column Name", "Field"};
        int tempWidth = widthList.get(0);
        boolean cutCollumn = false;


        //NO headers if there is an offset
        if (offset <=0){
            //ITERATE over collumns names
            for(int i = 0; i <2; i++){
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
                    if(i != 1){
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

        int tempHeight = -offset;


        for(Column column: table.getColumnNames()){
            if(tempHeight < (height - 10) && tempHeight >=0) {

                int colIndex = table.getColumnNames().indexOf(column);
                //this.paintRectText(g, startXco, startYco, widthList.get(0), cellHeight, column.getName());
                //this.paintRectText(g, startXco + widthList.get(0) + 5, startYco, widthList.get(0), cellHeight,
                  //      table.getTableRows().get(rowIndex).getColumnList().get(colIndex).getString());

                CellText cellName = new CellText( column.getName());
                CellText cellQuery = new CellText( table.getTableRows().get(rowIndex).getColumnList().get(colIndex).getString());

                List<Cell> listCell = new ArrayList<>();
                listCell.add(cellName);
                listCell.add(cellQuery);
                this.paintRowMargin(g,listCell,startXco,startYco,setting,width,offsetHorizontal,5);

                startYco = startYco + cellHeight;
                tempHeight += cellHeight;
                }

            else{
                startYco = startYco + cellHeight;
                tempHeight += cellHeight;
            }
        }

    }
}
