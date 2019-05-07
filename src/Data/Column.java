package Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for table columns
 */
public class Column {
    /**
     * Column name
     */
    private String name;

    /**
     * Default value for column data
     */
    private Cell defaultV;

    /**
     * Cell type (Bool, email, int, text)
     */
    private String type;

    /**
     * Whether blanks are allowed in the column
     */
    private Boolean blanksAllowed;

    /**
     * Column width
     */
    public int width;

    /**
     * List with column info
     */
    List<String> colInfo = new ArrayList<>();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    /**
     * Constructor that initiates a column object with a name, default value, type and blank allowance
     * @param name  the name of the column
     * @param defaultValue  the default value of all cells in this column
     * @param Type  the type of values of all cells in this column (string, boolean, integer or email)
     * @param blanks    whether blanks are allowed in the cells of this column
     */
    public Column(String name, Cell defaultValue, String Type, Boolean blanks){
        this.name = name;
        defaultV = defaultValue;
        type = Type;
        blanksAllowed = blanks;
    }

    /**
     * Method to get basic column info
     * @return list with name,default value, type and if blanks are allowed
     */
    public List<Cell>  getInfo(){
        List<Cell> info = new ArrayList<>();
        info.add(new CellText(name));
        info.add(defaultV);
        info.add(new CellText(type));
        info.add(new CellBoolean(blanksAllowed));
        return info;
    }


    /**
     * Getter for column name
     * @return column name
     */
    public String getName() {
        return name;
    }



    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    public Cell getDefaultV() {
        return this.defaultV;
    }

    public Boolean getBlanksAllowed() {
        return blanksAllowed;
    }

    public void setBlanksAllowed(Boolean blanksAllowed) {
        this.blanksAllowed = blanksAllowed;
    }
    public void setDefaultV(Cell defaultV) {
        this.defaultV = defaultV;
    }







}
