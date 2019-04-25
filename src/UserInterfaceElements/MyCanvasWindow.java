package UserInterfaceElements;
		import canvaswindow.CanvasWindow;
		import java.awt.*;
		import java.awt.geom.Point2D;


public class MyCanvasWindow extends CanvasWindow {

	private Controller controller  ;




	/**
	 *Constructor that creates a blank canvas and title and initialises the controller
	 * @param title title of the canvas
	 */
	public MyCanvasWindow(String title) {
		super(title);
		controller = new Controller();
	}

	public MyCanvasWindow(String title, int i) {
		super(title);
		controller = new Controller(i);
	}



	/**
	 * This method will be called at the start when "painting" canvaselements.
	 * It will also be called when we update screen (repaint) and it will call the controller to handle the painting
	 * @param g graphics object
	 */
	public void paint(Graphics g) {
		controller.paint(g);
	}


	//TODO: Add seperate eventhandler?
	/**
	 * Relays a mouseEvent to the controller
	 * @param id id of mouseEvent
	 * @param x X coordinate of click
	 * @param y Y coordinate of click
	 * @param clickCount number of clicks
	 */
	public void handleMouseEvent(int id, int x, int y, int clickCount) {
		//Controller handles Click event
		controller.relayMouseEvent(id,  x,  y,  clickCount);
		//After the changes canvas will be repainted
		this.repaint();

	}

	/**
	 * Relays a keyEvent to the controller
	 * @param id key id of key pressed
	 * @param keyCode keycode of key pressed
	 * @param keyChar keychar of key pressed
	 */
	public void handleKeyEvent(int id, int keyCode, char keyChar) {
		//Controller handles Keyevent
		controller.relayKeyEvent(id, keyCode, keyChar);
		this.repaint();

	}


	public Controller getController() {
		return this.controller;
	}

}