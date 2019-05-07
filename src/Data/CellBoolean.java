package Data;

/**
 * Cell Class for boolean values. Inherits from Cell Superclass
 */
public class CellBoolean implements Cell<Boolean>{
    protected Boolean value;
    private final String type = "Boolean";
	
    public CellBoolean(Boolean arg){
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

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public String getString(){
        if (value){return "True";}else{return "False";}
    }

}
