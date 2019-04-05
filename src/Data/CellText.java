package Data;

public class CellText extends Cell<String>{
    protected String value;
    private String type;
    
    public CellText(String arg){
    	this.setValue(arg);
    	this.setType("Text");
    }

	 public String getText() { //waarom was er alleen getText en geen getValue ?
	        return value;
	    }
	 public void setText(String text) {
	        this.value = text;
	    }


	 @Override
	 public String getValue() {
    	return value;
	 }
	 @Override
	 public void setValue(String Value) {
	            setText(Value);
	    }

	@Override
	public String getType() {
		return this.type;
	}

	@Override
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public void addChar(char keyChar) {
    	this.setValue(this.getValue() + keyChar);
	}
	@Override
	public void delChar() {
    	this.setValue(this.getValue().substring(0,this.getValue().length()-1));
	}

}
