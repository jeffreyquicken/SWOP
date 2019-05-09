package UndoRedo;

import java.util.LinkedList;
import java.util.List;

public class Operations {
    List<Command> history;
    List<Command> future;

    public Operations() {
        history = new LinkedList<>();
        future = new LinkedList<>();
    }

    public void add(Command c) {
        history.add(0,c);
        future.clear();
    }

    public void undo() {
        if (!history.isEmpty()) {
            Command c = history.get(0);
            c.undo();
            history.remove(0);
            future.add(0,c);
        }
    }

    public void redo() {
        if (!future.isEmpty()) {
            Command c = future.get(0);
            c.redo();
            future.remove(0);
            history.add(0,c);
        }
    }

}
