package settings;

/**
 * A class of scrollbar involving the offsets and percentages needed for the working of the scrollbar
 */
public class scrollbar {

    /**
     * The percentage the scrollbar takes up space in the window on the horizontal axis (length of scrollbar divided by width of window)
     */
    private double percentageHorizontal;

    /**
     * The percentage the scrollbar taken up space in the window on the vertical axis (length of scrollbar divied by height of window)
     */
    private double percentageVertical;

    /**
     * the percentage the horizontal scrollbar must be painted from the left of the window
     */
    private double offsetpercentageHorizontal;

    /**
     * the percentage the vertical scrollbar must be painted from the top of the window
     */
    private double offsetpercentageVertical;

    /**
     * whether the horizontal scrollbar is active
     */
    private Boolean isActiveHorizontal;

    /**
     * whether the vertical scrollbar is active
     */
    private Boolean isActiveVertical;

    /**
     * Integer for offset
     */
    private int offsetInt;

    /**
     * constructor for scrollbar
     */
    public scrollbar() {
        percentageHorizontal = 0;
        percentageVertical = 0;
        offsetpercentageHorizontal = 0;
        offsetpercentageVertical = 0;
        isActiveHorizontal = false;
        isActiveVertical = false;
    }

    /**
     * method that adds offset percentage for the vertical scrollbar
     */
    public void addOffsetPercentageVertical() {
        offsetpercentageVertical += 0.015;

        if (this.getOffsetpercentageVertical() + this.getPercentageVertical() > 1) {
            offsetpercentageVertical = (1 - percentageVertical);
        }
        offsetInt += 1;
    }

    /**
     * method that subtracts offset percentage for the horizontal scrollbar
     */
    public void substractOffsetPercentageHorizontal() {
        offsetpercentageHorizontal -= 0.015;
        if (this.getOffsetpercentageHorizontal() < 0) {
            offsetpercentageHorizontal = 0;
        }
    }

    /**
     * method that subtracts offset percentage for the vertical scrollbar
     */
    public void substractOffsetPercentageVertical() {
        offsetpercentageVertical -= 0.015;
        if (this.getOffsetpercentageVertical() < 0) {
            offsetpercentageVertical = 0;
        }
        offsetInt -= 1;
    }

    /**
     * method that adds offset percentage for the horizontal scrollbar
     */
    public void addOffsetPercentageHorizontal() {
        offsetpercentageHorizontal += 0.015;
        if (this.getOffsetpercentageHorizontal() + this.getPercentageHorizontal() > 1) {
            offsetpercentageHorizontal = (1 - percentageHorizontal);
        }
    }

    //Getters and setters
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
        this.offsetpercentageHorizontal = offsetpercentageHorizontal;
    }

    public double getOffsetpercentageVertical() {
        return offsetpercentageVertical;
    }

    public void setOffsetpercentageVertical(double offsetpercentageVertical) {
        this.offsetpercentageVertical = offsetpercentageVertical;
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


}
