
import org.junit.jupiter.api.Test;
import Data.dataController;
import UserInterfaceElements.Controller;

public class Scenario7 {
    //initialise class variables
    private Controller bestuurder;
    private dataController dc;

    //get values for class variables
    public Scenario7() {
        bestuurder = new Controller();
        dc = bestuurder.getTableDataController();
        bestuurder.setCurrentMode("design");
    }
}
