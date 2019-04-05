package Data;

public class CellNumerical extends Cell<Integer>{
    protected Integer value;
    private String type;
    
    CellNumerical(Integer arg){
    	this.setValue(arg);
    	this.setType("Numerical");
    }

    @Override
	 public Integer getValue(){
	        return value;
	    }

	@Override
	public void setValue(Integer value){
            setNumber(value);
    }
	
	public void setNumber(Integer number) {
	        this.value = number;
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
    	this.setValue(Integer.parseInt(this.getValue().toString() + keyChar));
	}
	@Override
	public void delChar() {
    	String stringValue = this.getValue().toString();
		this.setValue(Integer.parseInt( stringValue.substring(0,stringValue.length()-1)));
	}

}
