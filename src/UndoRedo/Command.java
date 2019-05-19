package UndoRedo;

import Data.dataController;

/**
 * Superclass, which is an interface that dictates that all its children must implement a redo and undo
 * this class structure uses the command design pattern
 */
public interface Command {
     void undo();
     void redo();
}
