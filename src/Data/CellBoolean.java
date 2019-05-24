package Data;

/**
 * Cell Class for boolean values. Inherits from Cell Superclass
 *
 * @invar The value of this class must always be a valid integer.
 * | this.getValue() instanceof Boolean
 */
public class CellBoolean implements Cell<Boolean> {
    protected Boolean value;
    private final String type = "Boolean";

    /**
     * Initialize CellBoolean with given value.
     *
     * @param arg The value contained by the newly created CellInteger Object.
     * @effect A CellInteger is initialized with the given arg as its value
     * | this.setValue(value)
     */
    public CellBoolean(Boolean arg) {
        this.setValue(arg);
    }

    public void setBool(Boolean bool) {
        this.value = bool;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        setBool(value);
    }

    public String getType() {
        return this.type;
    }

    /**
     * Method that returns the value as string
     *
     * @return the value as a string
     * |if (value == true)
     * |  result == "True"
     * |else result == "False"
     */
    public String getString() {
        if (value) {
            return "True";
        } else {
            return "False";
        }
    }

}
