package paintModule;

import java.awt.*;

public class Square {

    public Square(Graphics g, int xCo, int yCo, int width){
        g.drawRect(xCo, yCo, width, width);
    }

}
