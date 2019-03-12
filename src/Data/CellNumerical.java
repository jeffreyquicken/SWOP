package Data;

public class CellNumerical extends Cell<Float>{
    protected float value;
    
    CellNumerical(Float arg){
    	this.setValue(arg);
    }

    @Override
	 public Float getValue(){
	        return value;
	    }

	@Override
	public void setValue(Float value){
            setNumber(value);
    }
	
	   public void setNumber(Float number) {
	        this.value = number;
	    }
}
