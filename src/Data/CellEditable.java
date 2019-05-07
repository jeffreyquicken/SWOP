package Data;

/**
 * Superclass for editable cells (cells whose values can be edited by the user
 * @param <T> The cell type
 */
public interface CellEditable<T> extends Cell<T> {
	public void addChar(char keyChar);
	public void delChar(); 
}
