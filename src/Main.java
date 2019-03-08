import Tests.MouseTests;
import UserInterfaceElements.MyCanvasWindow;

public class Main {

	//Main of program
	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(() -> {
			 new MyCanvasWindow("Tables").show();
		});

		MouseTests test = new MouseTests();
		int testsSucc = 0;
		int totTests = 5;

		if (test.testClickUnderTableUpperBoundary()) {
			testsSucc++;
		}
		if (test.testClickTopOfTable()) {
			testsSucc++;
		}
		if (test.testClickBottomOfTable()) {
			testsSucc++;
		}
		if (test.testClickLeftOfTable()) {
			testsSucc++;
		}
		if (test.testClickRightOfTable()) {
			testsSucc++;
		}

		System.out.println("Results of testing: "+ testsSucc +"/"+totTests);
	}

}
