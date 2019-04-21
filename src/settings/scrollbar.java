package settings;

public class scrollbar {

    double percentageHorizontal;
    double percentageVertical;
    int offsetInt;

    public double getPercentageHorizontal() {
        return percentageHorizontal;
    }

    public void setPercentageHorizontal(double percentageHorizontal) {
        this.percentageHorizontal = percentageHorizontal;
    }

    public double getPercentageVertical() {
        return percentageVertical;
    }

    public void setPercentageVertical(double percentageVertical) {
        this.percentageVertical = percentageVertical;
    }

    public double getOffsetpercentageHorizontal() {
        return offsetpercentageHorizontal;
    }

    public void setOffsetpercentageHorizontal(double offsetpercentageHorizontal) {
        this.offsetpercentageHorizontal += offsetpercentageHorizontal;
    }

    public double getOffsetpercentageVertical() {
        return offsetpercentageVertical;
    }

    public void setOffsetpercentageVertical(double offsetpercentageVertical) {
        this.offsetpercentageVertical += offsetpercentageVertical;
    }

    public Boolean getActiveHorizontal() {
        return isActiveHorizontal;
    }

    public void setActiveHorizontal(Boolean activeHorizontal) {
        isActiveHorizontal = activeHorizontal;
    }

    public Boolean getActiveVertical() {
        return isActiveVertical;
    }

    public void setActiveVertical(Boolean activeVertical) {
        isActiveVertical = activeVertical;
    }
    public void addOffsetPercentageVertical(){
        offsetpercentageVertical += 0.05;

        if(this.getOffsetpercentageVertical() + this.getPercentageVertical() >1){
            offsetpercentageVertical = (1 - percentageVertical) ;
        }
        offsetInt += 1;
    }
    public void substractOffsetPercentageHorizontal(){
       offsetpercentageVertical -= 0.05;
    }
    public void substractOffsetPercentageVertical(){
        offsetpercentageVertical -= 0.05;
        if(this.getOffsetpercentageVertical() < 0){
            offsetpercentageVertical = 0;
           }
        offsetInt -=1;
    }
    public void addOffsetPercentageHorizontal(){
        offsetpercentageVertical -= 0.05;
    }

    double offsetpercentageHorizontal;
    double offsetpercentageVertical;
    Boolean isActiveHorizontal;
    Boolean isActiveVertical;


    public scrollbar(){
        percentageHorizontal = 0;
        percentageVertical = 0;
        offsetpercentageHorizontal = 0;
        offsetpercentageVertical = 0;
        isActiveHorizontal = false;
        isActiveVertical = false;
        offsetInt = 0;
    }

}