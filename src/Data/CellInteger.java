package Data;

/**
 * Cell Class for integer values. Inherits from Cell Superclass
 * 
 * 
 * @invar  The value of this class must always be a valid integer.
 *       | this.getValue() instanceof Integer
 */
public class CellInteger implements CellEditable<Integer>{
    protected Integer value;
    private final String type = "Integer";
    
    /**
	 * Initialize CellInteger with given value.
	 *
	 * @param  arg
	 *         The value contained by the newly created CellInteger Object.
	 * @effect A CellInteger is initialized with the given arg as its value
	 * 		 | this.setValue(value)
	 */
    public CellInteger(Integer arg){
    	this.setValue(arg);
    }

    
	public Integer getValue(){
	        return value;
	    }

	
	public void setValue(Integer value){
        this.value = value;
    }
	
	public String getType() {
		return this.type;
	}

	/**
	 * Method that adds a character to the value
	 * @param keyChar
	 * 		  the character to be added
	 * @effect The value the character is appended to the end of the value
	 * 		   | new.value = parseInt(value.toString() + keyChar)
	 */
	public void addChar(char keyChar) {
    	this.setValue(Integer.parseInt(this.getValue().toString() + keyChar));
	}

	/**
	 * Method that deletes the last character from the value
	 * @effect The value is treated as a string and the last character is deleted
	 * 		   | new.value == value.toString.substring(length() -1)
	 */
	public void delChar() {
    	String stringValue = this.getValue().toString();
		this.setValue(Integer.parseInt( stringValue.substring(0,stringValue.length()-1)));
	}

	/**
	 * Method that returns the value as string
	 * @return the value as a string
	 * 		   | result == value.toString()
	 */		   
	public String getString(){
		return value.toString();
	}

}
