
import org.junit.jupiter.api.Test;
import Data.dataController;
import UserInterfaceElements.Controller;

public class Scenario4 {
    //initialise class variables
    private Controller bestuurder;
    private dataController dc;

    //get values for class variables
    public Scenario4() {
        bestuurder = new Controller();
        dc = bestuurder.getTableDataController();
        bestuurder.setCurrentMode("table");
    }
}
