package paintModule;


import java.awt.Font;
import java.awt.Graphics;
import java.util.List;

import Data.Cell;
import Data.Column;
import Data.Table;
import settings.CellVisualisationSettings;
import settings.scrollbar;

/**
 * A class of DesignModePaintModule involving methods for painting design view elements
 */
public class DesignModePaintModule extends PaintModule {


    /**
     * Method that paints the design view
     * It shows for each column the name, default value, type & if blanks are allowed
     *
     * @param g     graphics object
     * @param table table for which design view has to be painted
     */
    public void paintDesignView(Graphics g, Table table, int startXco, int startYco, CellVisualisationSettings setting, int width, int height, scrollbar scrollbar, int windowHeight, int sum) {
        int offsetVertical = (int) ((windowHeight - titleHeight) * scrollbar.getOffsetpercentageVertical());
        int offsetHorizontal = (int) (sum * scrollbar.getOffsetpercentageHorizontal());
        int headerXco = startXco;
        int headerYco = startYco - cellHeight / 2;
        List<Integer> widthList = setting.getWidthList();
        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() * 0.9F);
        String[] names = {"Name", "Default value", "Type", "Blank?"};
        int tempWidth = widthList.get(0);
        boolean cutCollumn = false;

        g.setFont(newFont);

        //NO headers if there is an offset
        if (offsetVertical <= 0) {
            //ITERATE over collumns names
            for (int i = 0; i < 4; i++) {
                if (offsetHorizontal > 0 && offsetHorizontal - widthList.get(i) >= 0) {
                    offsetHorizontal -= widthList.get(i);
                }
                //deel van collum is verdwenen
                else if (offsetHorizontal > 0 && offsetHorizontal - widthList.get(i) < 0) {
                    //Nieuwe breedte is nu window tot nieuwe collumn
                    int newWidth = widthList.get(i) - offsetHorizontal;
                    tempWidth = printHeaderGetTempWidth(g, startYco, headerXco, widthList, names, i, newWidth);
                    headerXco += newWidth;
                    offsetHorizontal -= widthList.get(i);

                }
                //ALS opgetelde breedte nog niet breder als windowbreedte is
                else if (tempWidth < width - 50) {
                    paintHeader(g, headerYco, headerXco, widthList.get(i), names[i]);
                    System.out.println(names[i]);
                    headerXco += widthList.get(i);
                    if (i != 3) {
                        tempWidth += widthList.get(i + 1);
                    }
                } else if (!cutCollumn) {
                    headerXco = printHeaderGetHeaderXCo(g, startXco, width, headerXco, headerYco, widthList, names, i);
                    cutCollumn = true;
                }
            }
        }

        offsetHorizontal = (int) (sum * scrollbar.getOffsetpercentageHorizontal());
        g.setFont(currentFont);

        int tempHeight = -offsetVertical;

        for (Column column : table.getColumnNames()) {
            startYco = paintRowGetYco(g, startXco, startYco, setting, width, height, offsetHorizontal, tempHeight,
                    column);
            tempHeight += cellHeight;
        }

    }


    /**
     * Method that prints the header and gets the  coordinate
     * @param g graphics object
     * @param startXco start x coordinate
     * @param width width of window
     * @param headerXco header x coordinate
     * @param headerYco header y coordinate
     * @param widthList widthlist
     * @param names names
     * @param i index
     * @return headerXco
     */
    private int printHeaderGetHeaderXCo(Graphics g, int startXco, int width, int headerXco, int headerYco, List<Integer> widthList,
                                        String[] names, int i) {
        int newWidth = (width + startXco) - headerXco;
        String name = names[i];

        if (newWidth < name.length() * 7) {
            int delta = newWidth / 8;
            name = name.substring(0, delta);
        }
        paintHeader(g, headerYco, headerXco, newWidth, name);
        System.out.println(names[i]);
        headerXco += widthList.get(i);
        return headerXco;
    }

    /**
     * Method that prints the header and gets the temporary width
     * @param g graphics object
     * @param startYco start Y coordinate
     * @param headerXco header x coordinate
     * @param widthList the widthlist
     * @param names names
     * @param i index
     * @param newWidth the new width
     * @return the temporary wodth
     */
    private int printHeaderGetTempWidth(Graphics g, int startYco, int headerXco, List<Integer> widthList,
                                        String[] names, int i, int newWidth) {
        int tempWidth;
        String name = names[i];
        //Naam wordt weggelaten als er te weinig plaats is
        if (newWidth < 50) {
            name = "";
        }
        paintHeader(g, startYco - cellHeight / 2, headerXco, newWidth, name);
        //headerXco += newWidth;
        //offsetHorizontal -= widthList.get(i);

        tempWidth = newWidth;
        if (widthList.size() != i - 1) {
            tempWidth += widthList.get(i + 1);
        }
        return tempWidth;
    }

    /**
     * method that paints the row and gets the starting y coordinate
     * @param g graphics object
     * @param startXco start x coordinate
     * @param startYco start Y coordinate
     * @param setting settings object
     * @param width the width
     * @param height the height
     * @param offsetHorizontal the horizontal offset
     * @param tempHeight the temporary height
     * @param column the column
     * @return the start Y coordinate
     */
    private int paintRowGetYco(Graphics g, int startXco, int startYco, CellVisualisationSettings setting, int width,
                               int height, int offsetHorizontal, int tempHeight, Column column) {
        if (tempHeight < (height - 10) && tempHeight >= 0) {
            List<Cell> rowInfo = column.getInfo();
            this.paintRow(g, rowInfo, startXco, startYco, setting, width, offsetHorizontal);
            startYco = startYco + cellHeight;
        } else {
            startYco = startYco + cellHeight;
        }
        return startYco;
    }

}
