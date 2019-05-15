package Data;

/**
 * Cell Class for string values. Inherits from Cell Superclass
 * 
 * 
 * @invar  The value of this class must always be a valid String.
 *       | this.getValue() instanceof String
 */

public class CellText implements CellEditable<String>{
    protected String value;
    private final String type = "Text";
    
    
    /**
	 * Initialize CellInteger with given value.
	 *
	 * @param  arg
	 *         The value contained by the newly created CellString Object.
	 * @effect A CellString is initialized with the given arg as its value
	 * 		 | this.setValue(value)
	 */
    public CellText(String arg){
    	this.setValue(arg);
    }

	 public void setText(String text) {
	        this.value = text;
	    }

	 public String getValue() {
	        return value;
	 }
	 
	 public void setValue(String Value) {
	            setText(Value);
	    }

	
	public String getType() {
		return this.type;
	}

	/**
	 * Method that adds a character to the value
	 * @param keyChar
	 * 		  the character to be added
	 * @effect The value the character is appended to the end of the value
	 * 		   | value = value + keyChar
	 */
	public void addChar(char keyChar) {
    	this.setValue(this.getValue() + keyChar);
	}
	
	/**
	 * Method that deletes the last character from the value
	 * @effect The value is treated as a string and the last character is deleted
	 * 		   | value = value.substring(length() -1)
	 */
	public void delChar() {
    	this.setValue(this.getValue().substring(0,this.getValue().length()-1));
	}
	
	/**
	 * Method that returns the value as string
	 * @return the value as a string
	 * 		   | result == value
	 */	
	public String getString(){
		return value;
	}
}
