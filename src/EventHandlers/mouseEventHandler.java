package EventHandlers;


import java.util.List;

public class mouseEventHandler {

    /**
     * @param yCo
     * Y coordinate of the click to check
     * @param count
     * Number of clicks
     * @param ID
     * Type of click
     * @param lowestY
     * Lowest Y coordinate of table
     * @return boolean
     * Returns if the click is a double click under the table
     */
    public Boolean doubleClickUnderTable(int yCo, int count, int ID,int lowestY){
        if( count==2 && yCo > lowestY && ID == 501){
            return true;
        } else{
            return false;
        }
    }

    /**
     * @param xCo
     * X coordinate of the click to check
     * @param yCo
     * Y coordinate of the click to check
     * @param firstX
     * X coordinate of leftmost part of table
     * @param firstY
     * Y coordinate of uppermost part of table
     * @param height
     * Height of table cell
     * @param width
     * Width of table cell
     * @param numberOfRows
     * Number of rows
     * @param numberOfColumns
     * Number of columns of table
     * @param margin
     * Left margin of cell of table
     * @return
     * Returns coordinates of cell if margin is clicked, else NULL
     */
    // TODO: Use recursion to split table and search for margin clicked without for loops
    public int[] marginLeftClicked(int xCo, int yCo, int firstX, int firstY, int height, int width, int numberOfRows, int numberOfColumns, int margin, List<Integer> widthList ){
        int[] coordinates = new int[2];
        if (isInTableHeight(yCo, firstY, height, numberOfRows) && isInTableWidth(xCo, firstX, widthList)){
            for( int i = 0; i < numberOfColumns; i++){
                if(firstX + i * width  < xCo && xCo < firstX + i*width + margin){
                    coordinates[0] = i;
                    break;
                }else{
                    return null;
                }
            }

            coordinates[1] = calculateRowID(yCo, firstY, height, numberOfRows);
            return coordinates;
        }else{
          return null;
        }
    }
    
    
    /**
     * @param xCo
     * the X coordinate to check
     * @param yCo
     * the Y coordinate to check
     * @param firstX
     * the X coordinate in the upper left of the table
     * @param firstY
     * the Y coordinate in the upper left of the table
     * @param height
     * the height of a cell
     * @param width
     * the width of a cell
     * @param numberOfRows
     * the number of rows. this determines the overall height of a table
     * @param numberOfColumns
     * the number of columns. this determines the overall width of a table
     * @return CellID
     * CellID is an array containing which row and which column the user clicked in, determining the unique ID of a cell as followed: [Row, Column]
     */
    public int[] getCellID(int xCo, int yCo, int firstX, int firstY, int height, int width, int numberOfRows, int numberOfColumns, List<Integer> widthList){
    	int[] cellID = {-1,-1};
    	if(this.isInTableWidth(xCo, firstX, widthList) && this.isInTableHeight(yCo, firstY, height, numberOfRows)){
    		//calculates the in which column the user clicked
            cellID[1] = calculateColumnID(xCo, firstX, widthList);
            //calculates in which row the user clicked
            cellID[0] = calculateRowID(yCo, firstY, height, numberOfRows);
        }
    	return cellID;
    }

    /**
     * @param xCo
     * X coordinate to check
     * @param firstX
     * First X coordinate of table
     * @return colID
     * returns the number of the column that corresponds to the X coordinate
     */
    private int calculateColumnID(int xCo, int firstX, List<Integer> widthList) {
        int colID = -1;
        int tempWidth = firstX;
        for(int i = 0; i< widthList.size(); i++) {
            if((xCo > (tempWidth) ) && (xCo < tempWidth + widthList.get(i))) {
                colID = i;
            }
            tempWidth += widthList.get(i);
        }
        return colID;
    }

    /**
     * @param yCo
     * Y coordinate to check
     * @param firstY
     * First Y coordinate of table
     * @param height
     * Height of table cell
     * @param numberOfRows
     * Number of rows
     * @return rowID
     * returns the number of the row that corresponds to the Y coordinate
     */
    private int calculateRowID(int yCo, int firstY, int height, int numberOfRows) {
        int rowID = -1;
        for (int i = 0; i < numberOfRows; i++) {
            if ((yCo > firstY + i * height) && (yCo < firstY + (i + 1) * height)) {
                rowID = i;
            }
        }
        return rowID;
    }


    /**
     * @param xCo
     * the X coordinate to check
     * @param firstX
     * the X coordinate in the upper left corner of the table
     * @return validity 
     * a boolean describing whether or not the given x coordinate is inside of the table or not
     */
    public boolean isInTableWidth(int xCo, int firstX, List<Integer> widthList){
    	boolean validity = false;
        int sum = widthList.stream().mapToInt(Integer::intValue).sum();
    	if(xCo >= firstX && xCo <= (firstX + sum)){
    		validity = true;
    	}
    	return validity;
    }
    
    /**
     * @param yCo
     * the Y coordinate to check
     * @param firstY
     * the Y coordinate in the upper left corner of the table
     * @param height
     * the height of a table cell
     * @param numberOfRows
     * the number of rows in a table. this determines the overall height of the table
     * @return validity
     * a boolean describing whether or not the given Y coordinate is inside the table
     */
    public boolean isInTableHeight(int yCo, int firstY, int height, int numberOfRows){
    	boolean validity = false;
    	if( yCo >= firstY && yCo <= (firstY + (numberOfRows * height))) {
    		validity = true;
    	}
    	return validity;
    }

    /**
     * Checks of right header border is clicked
     *
     * @param xCo
     * X coordinate
     * @param yCo
     * Ycoordinate
     * @param startX
     * @param startY
     * @param numberOfColumns
     * Number of columns in table
     * @param height
     * Height of cell
     * @param widthList
     * @return
     * Returns column if right border is clicked, if false returns -1
     */
    public int rightBorderClicked(int xCo, int yCo, int startX, int startY, int numberOfColumns, int height, List<Integer> widthList){
        if (headerClicked(xCo,yCo,startX,startY,numberOfColumns,height,widthList)) {
            int length =0 ;
            for(int i = 0; i < numberOfColumns; i++){
                length = length + widthList.get(i);
                if(xCo == startX + length){
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Check if header is clicked, returns boolean
     * @param xCo
     * @param yCo
     * @param startX
     * @param startY
     * @param numberofColums
     * @param height
     * @param widthList
     * @return
     */
    public boolean headerClicked(int xCo, int yCo, int startX, int startY, int numberofColums, int height, List<Integer> widthList)
    {
        int sum = widthList.stream().mapToInt(Integer::intValue).sum();
        if (yCo > (startY - height) && yCo < startY && xCo > startX && xCo - startX <= sum ){
            return true;

        }else{
        return false;}}
    
}
