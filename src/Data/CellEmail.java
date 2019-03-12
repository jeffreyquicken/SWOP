package Data;

public class CellEmail extends Cell<String>{
    protected String value;

    CellEmail(String arg){
    	this.setValue(arg);
    }
    
	public void setEmail(String email) {
	        this.value = email;
	    }
	
	@Override
	public String getValue() {

	        return value;
	    }
	
	@Override
	  public void setValue(String value){
	        if (isValidEmailAddress(value)){
	            setEmail(value);
	        }
	  }
	  
	  public boolean isValidEmailAddress(String text) {
	        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
	        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
	        java.util.regex.Matcher m = p.matcher(text);
	        return m.matches();
	    }
}