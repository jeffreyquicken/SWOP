package Data;

/**
 * Cell Class for integer values. Inherits from Cell Superclass
 */
public class CellInteger implements CellEditable<Integer>{
    protected Integer value;
    private final String type = "Integer";
    
    public CellInteger(Integer arg){
    	this.setValue(arg);
    }

    
	 public Integer getValue(){
	        return value;
	    }

	
	public void setValue(Integer value){
            setNumber(value);
    }
	
	public void setNumber(Integer number) {
	        this.value = number;
	    }


	
	public String getType() {
		return this.type;
	}

	/**
	 * Method that adds a character to the value
	 * @param keyChar the character to be added
	 */
	public void addChar(char keyChar) {
    	this.setValue(Integer.parseInt(this.getValue().toString() + keyChar));
	}

	/**
	 * Method that deletes the last character from the value
	 */
	public void delChar() {
    	String stringValue = this.getValue().toString();
		this.setValue(Integer.parseInt( stringValue.substring(0,stringValue.length()-1)));
	}

	/**
	 * Method that returns the value as string
	 * @return the value as a string
	 */
	@Override
	public String getString(){
		return value.toString();
	}

}
