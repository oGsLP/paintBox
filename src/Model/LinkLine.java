package Model;

import java.awt.*;

/*
 * @author: XYF
 * @Date: 2018/9/19
 */
public class LinkLine {
    private Coordinate c1;
    private Coordinate c2;
    private Color color;
    private int width;

    public LinkLine(){

    }
    public LinkLine(Coordinate c1,Coordinate c2,Color color,int width){
        this.c1=c1;
        this.c2=c2;
        this.color=color;
        this.width=width;
    }
    public void linkPoints(Graphics2D graphics2D){
        graphics2D.setColor(this.color);
        graphics2D.setStroke(new BasicStroke(this.width));
        graphics2D.drawLine(this.c1.getX(),this.c1.getY(),this.c2.getX(),this.c2.getY());
    }

    Coordinate getC1() {
        return c1;
    }

    public void setC1(Coordinate c1) {
        this.c1 = c1;
    }

    Coordinate getC2() {
        return c2;
    }

    public void setC2(Coordinate c2) {
        this.c2 = c2;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
