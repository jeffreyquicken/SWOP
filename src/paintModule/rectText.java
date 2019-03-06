package paintModule;

import java.awt.*;

public class rectText {

    public void paintRectText(Graphics g, int xCo, int yCo, int width,int height, String text){
        g.drawRect(xCo,yCo,width,height);
        int yCo2;
        int xCo2;
        yCo2 = yCo + height/2; // text in middle of height
        xCo2 = xCo + 2; //margin left
        g.drawString(text,xCo2,yCo2);
    }

}
