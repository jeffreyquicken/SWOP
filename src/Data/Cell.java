package Data;

public abstract class Cell<T> {
	//Super class for cells for type checking
	//when working with the cell class, T cannot be changed to a primitive type. Instead use the wrappers provided by Java
	protected T value;
	private String type;
	
	public T getValue() {
		return value;
	}
	
	public void setValue(T arg){
		value = arg;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void addChar(char keyChar) {
	}
	public void delChar() {}


}
