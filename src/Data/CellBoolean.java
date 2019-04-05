package Data;

public class CellBoolean extends Cell<Boolean>{
    protected Boolean value;
    private String type;
	
    CellBoolean(Boolean arg){
    	this.setValue(arg);
    	this.setType("Boolean");
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

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }


}
