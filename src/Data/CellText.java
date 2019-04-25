package Data;

public class CellText implements CellEditable<String>{
    protected String value;
    private final String type = "Text";
    
    public CellText(String arg){
    	this.setValue(arg);
    }

	 public String getText() { //waarom was er alleen getText en geen getValue ?
	        return value;
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

	
	public void addChar(char keyChar) {
    	this.setValue(this.getValue() + keyChar);
	}
	
	public void delChar() {
    	this.setValue(this.getValue().substring(0,this.getValue().length()-1));
	}
	@Override
	public String getString(){
		return value;
	}
}
