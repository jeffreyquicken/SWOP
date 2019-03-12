package Data;

public class CellBoolean extends Cell<Boolean>{
    protected Boolean value;
	
    CellBoolean(Boolean arg){
    	this.setValue(arg);
    }

	public void setBool(Boolean bool) {
        this.value = bool;
    }
	
	@Override
	public Boolean getValue() {
        return value;
    }
	
	@Override
	public void setValue(Boolean value){
            setBool(value);
    }
}
