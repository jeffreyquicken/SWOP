package Data;

/**
 * Cell Class for email adresses. Inherits from Cell Superclass
 */
public class CellEmail implements CellEditable<String>{
    protected String value;
    private String type = "Email";


    public CellEmail(String arg){
    	this.setValue(arg);
    }
    
	public void setEmail(String email) {
	        this.value = email;
	    }
	
	
	public String getValue() {

	        return value;
	    }
	
	
	  public void setValue(String value){
	        if (isValidEmailAddress(value)){
	            setEmail(value);
	        } 
	  }

	
	public String getType() {
		return this.type;
	}


	/**
	 * Method that adds a character to the value
	 * @param keyChar the character to be added
	 */
	public void addChar(char keyChar) {
		this.setValue(this.getValue() + keyChar);
	}

	/**
	 * Method that deletes the last character from the value
	 */
	public void delChar() {
		this.setValue(this.getValue().substring(0,this.getValue().length()-1));
	}

	/**
	 *Returns a boolean value that states if the text string is a valid email,
	 *that is if it follows a classic email address pattern
	 * @param text	The string to be tested for email validity
	 * @return	whether the string is a valid email
	 */
	public boolean isValidEmailAddress(String text) {
	        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
	        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
	        java.util.regex.Matcher m = p.matcher(text);
	        return m.matches();
	    }
	@Override
	public String getString(){
		return value;
	}
}
