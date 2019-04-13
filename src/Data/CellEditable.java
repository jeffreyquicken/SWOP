package Data;

public interface CellEditable<T> extends Cell<T> {
	public void addChar(char keyChar);
	public void delChar(); 
}
