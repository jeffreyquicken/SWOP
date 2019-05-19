package UndoRedo;

import java.util.LinkedList;
import java.util.List;

/**
 * Class that keeps two lists of operations, a list of commands that are executed and a list of commands that are undone.
 *
 */

public class Operations {
    // variables containing the two lists of commands
    List<Command> history;
    List<Command> future;

    /**
     * Initializes two new lists for storing commands
     * These lists are both linkedlists
     */
    public Operations() {
        history = new LinkedList<>();
        future = new LinkedList<>();
    }

    /**
     * Adds a command to the command history, clears the future list of commands
     * @param c
     *        The command to be added to the list
     */
    public void add(Command c) {
        history.add(0,c);
        future.clear();
    }

    /**
     * Undoes the last executed command, removes it from history list and adds it the the future list
     * @pre there has to be a command in the history, otherwise nothing will happen
     */
    public void undo() {
        if (!history.isEmpty()) {
            Command c = history.get(0);
            c.undo();
            history.remove(0);
            future.add(0,c);
        }
    }

    /**
     * redoes the last undone command, removes it from the future list and adds it to the history list
     * @pre there hass to be a command in the future list, a command that was undone
     */
    public void redo() {
        if (!future.isEmpty()) {
            Command c = future.get(0);
            c.redo();
            future.remove(0);
            history.add(0,c);
        }
    }

}
