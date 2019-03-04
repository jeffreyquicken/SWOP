package EventHandlers;



public class mouseEventHandler {

    public Boolean doubleClickUnderTable(int Y, int count, int lowestY){
        if( count==2 && Y > lowestY ){
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
    public int[] marginLeftClicked(int xCo, int yCo, int firstX, int firstY, int height, int width, int numberOfRows, int numberOfColumns, int margin ){
        int[] coordinates = new int[2];
        if (xCo > firstX || xCo < (firstX + (numberOfRows * width)) || yCo > firstY || yCo < (firstY + (numberOfColumns * height))){
            for( int i = 0; i < numberOfColumns; i++){
                if(firstX + i * width  < xCo && xCo < firstX + i*width + margin){
                    coordinates[0] = i;
                    break;
                }else{
                    return null;
                }
            }
            for (int i = 0; i < numberOfRows; i++){
                if(firstY + i * height  < yCo && yCo < firstY + ((i + 1) *width) ){
                    coordinates[1] = i;
                    break;
                }else{
                    return null;
                }
            }
            return coordinates;
        }else{
          return null;
        }
    }
}
