package UserInterfaceElements;
		import canvaswindow.CanvasWindow;
		import java.awt.*;
		import java.awt.geom.Point2D;


public class MyCanvasWindow extends CanvasWindow {

	private Controller controller  ;


	//Contstructor that creates a blank canvas and title
	//Initialisation of our controller
	public MyCanvasWindow(String title) {
		super(title);
		controller = new Controller();
	}

	//This method will be called at start when "painting" canvaselements
	//This method will also be called when we update screen (repaint)
	//This method will call our controller to handle the painting
	public void paint(Graphics g) {
		controller.paint(g);
	}

	//if mouseevent happens (click, drag, doubleclick,...) it calls controller to handle event
	//TODO: Add seperate eventhandler?
	public void handleMouseEvent(int id, int x, int y, int clickCount) {
		//Controller handles Click event
		controller.relayMouseEvent(id,  x,  y,  clickCount);
		//After the changes canvas will be repainted
		this.repaint();
	}

	public void handleKeyEvent(int id, int keyCode, char keyChar) {
		//Controller handles Keyevent
		controller.relayKeyEvent(id, keyCode, keyChar);
		this.repaint();

	}
}