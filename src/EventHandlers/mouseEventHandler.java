package EventHandlers;



public class mouseEventHandler {

    public Boolean doubleClickUnderTable(int Y, int count, int ID,int lowestY){
        if( count==2 && Y > lowestY && ID == 501){
            return true;
        } else{
            return false;
        }
    }
    // xco, yco = mouseclick coordinaates
    //firstX, firstY = coordinaten linker bovenhoek tabel
    //width, height = per cel
    // numberOf... = aantal kolommen/rijen
    //margin = margin per cel
    //returns rij en kolom nummer van de cel als er op de linker margin van een cel geklikt wordt
    //anders null
    // EXTRA: Use recursion to split table and search for margin clicked without for loops
    public int[] marginLeftClicked(int xCo, int yCo, int firstX, int firstY, int height, int width, int numberOfRows, int numberOfColumns, int margin ){
        int[] coordinates = new int[2];
        if (isInTableHeight(yCo, firstY, height, numberOfRows) && isInTableWidth(xCo, firstX, width, numberOfColumns)){
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
     * the number of columns. this determines the overall wifth od a table
     * @return CellID
     * CellID is an array containing which row and which column the user clicked in, determining the unique ID of a cell as followed: [Row, Column]
     */
    public int[] getCellID(int xCo, int yCo, int firstX, int firstY, int height, int width, int numberOfRows, int numberOfColumns){
    	int[] cellID = {-1,-1};
    	if(this.isInTableWidth(xCo, firstX, width, numberOfColumns) && this.isInTableHeight(yCo, firstY, height, numberOfRows)){
    		//calculates the in which column the user clicked
            cellID[1] = calculateColumnID(xCo, firstX, width, numberOfColumns);
            //calculates in which row the user clicked
            cellID[0] = calculateRowID(yCo, firstY, height, numberOfRows);
        }
    	return cellID;
    }

    private int calculateColumnID(int xCo, int firstX, int width, int numberOfColumns) {
        int colID = -1;
        for(int i = 0; i< numberOfColumns; i++) {
            if((xCo > firstX + i*width) && (xCo < firstX + (i+1)*width)) {
                colID = i;
            }
        }
        return colID;
    }

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
     * @param width
     * the width of a table cell
     * @param numberOfColumns
     * the number of columns. this determines the overall width of the table
     * @return validity 
     * a boolean describing whether or not the given x coordinate is inside of the table or not
     */
    public boolean isInTableWidth(int xCo, int firstX, int width, int numberOfColumns){
    	boolean validity = false;
    	if(xCo > firstX && xCo < (firstX + (numberOfColumns * width))){
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
    	if( yCo > firstY || yCo < (firstY + (numberOfRows * height))) {
    		validity = true;
    	}
    	return validity;
    }
    
}
