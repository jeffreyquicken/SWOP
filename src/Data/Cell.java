package Data;

public interface Cell<T> {
	//Super class for cells for type checking
	//when working with the cell class, T cannot be changed to a primitive type. Instead use the wrappers provided by Java
	public T getValue() ;
	public void setValue(T arg);
	
	public String getType();
}
