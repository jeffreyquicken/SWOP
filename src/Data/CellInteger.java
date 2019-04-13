package Data;

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


	public void addChar(char keyChar) {
    	this.setValue(Integer.parseInt(this.getValue().toString() + keyChar));
	}
	
	public void delChar() {
    	String stringValue = this.getValue().toString();
		this.setValue(Integer.parseInt( stringValue.substring(0,stringValue.length()-1)));
	}

}
