import org.junit.jupiter.api.Test;
import UserInterfaceElements.Controller;
import Data.dataController;

public class Scenario2 {
    //initialise class variables
    private Controller bestuurder;
    private dataController dc;

    //get values for class variables
    public Scenario2() {
        bestuurder = new Controller();
        dc = bestuurder.getTableDataController();
        bestuurder.setCurrentMode("table");
    }
}
