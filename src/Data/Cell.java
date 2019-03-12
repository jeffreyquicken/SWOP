package Data;

public abstract class Cell<T> {
	//Super class for cells for type checking
	//when working with the cell class, T cannot be changed to a primitive type. Instead use the wrappers provided by Java
	protected T value;
	
	public T getValue() {
		return value;
	}
	
	public void setValue(T arg){
		value = arg;
	}
}
