package Data;

/**
 * Superclass for cells to do type checking
 * when working with the cell class, T cannot be changed to a primitive type. Instead use the wrappers provided by Java
 * @param <T> cell type
 */
public interface Cell<T> {

	public T getValue() ;
	public void setValue(T arg);
	
	public String getType();
	public String getString();
}
