import Data.Column;
import Data.*;
import Data.dataController;
import UserInterfaceElements.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UIDESIGNTEST {



        //initialise class variables
        private Controller bestuurder;
        private dataController dc;
        private UITopLevelWindow topWindow;
        private UIDesignModule window;

        public UIDESIGNTEST() {
            bestuurder = new Controller();
            dc = bestuurder.getTableDataController();
            bestuurder.setCurrentMode("design");

            topWindow = bestuurder.getTopLevelWindow();
            window = (UIDesignModule) topWindow.getActiveSubWindow();
        }

        //bottom right corner of table subwindow is 180,180
        @Test
        public void DoubleClickUnderTable() {
            topWindow.setActiveSubWindow(window);

            bestuurder.relayMouseEvent(506,270,165,2);

            assertEquals(window.getTable().getTableRows(), 4);

        }
}
