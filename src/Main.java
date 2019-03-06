import UserInterfaceElements.MyCanvasWindow;

public class Main {

	//Main of program
	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(() -> {
			 new MyCanvasWindow("Tables").show();
		});
		
	}

}
