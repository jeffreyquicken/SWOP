package Data;

public class CellText extends Cell<String>{
    protected String value;
    
    CellText(String arg){
    	this.setValue(arg);
    }

	 public String getText() {
	        return value;
	    }

	    public void setText(String text) {
	        this.value = text;
	    }
	    
	    public void setValue(String Value) {
	            setText(Value);
	    }
}
