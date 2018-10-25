package Model;

import java.awt.*;
import java.util.ArrayList;

/*
 * @author: XYF
 * @Date: 2018/9/18
 */
public class OneLine {
    private ArrayList<Coordinate> coordinateArrayList;
    private Color color;
    private int width;
    public OneLine(){
        this.coordinateArrayList=new ArrayList<Coordinate>();

    }
    public OneLine(ArrayList<Coordinate> coordinates,Color color,int width){
        this.coordinateArrayList=coordinates;
        this.color=color;
        this.width=width;
    }
    public void drawOneLine(Graphics2D graphics2D){
        int num=this.getCoordinateNum();
        for(int i=0;i<num-1;i++){
            LinkLine linkLine=new LinkLine(this.getCoordinate(i),this.getCoordinate(i+1),this.color,this.width);
            linkLine.linkPoints(graphics2D);
        }
    }

    public int getCoordinateNum(){
        return this.coordinateArrayList.size();
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
    public Coordinate getCoordinate(int i){
        if(i<0||i>=this.getCoordinateNum()){
            return null;
        }
        else return this.coordinateArrayList.get(i);
    }
    public LinkLine getLinkLine(int i){
        if(i<0||i>this.getCoordinateNum()-2||this.getCoordinateNum()<2){
            return null;
        }
        else return new LinkLine(this.getCoordinate(i),this.getCoordinate(i+1),this.color,this.width);
    }
    public void add(Coordinate c){
        this.coordinateArrayList.add(c);
    }
    public void add(LinkLine l) {
        if (this.getCoordinateNum() == 0){
            this.setColor(l.getColor());
            this.setWidth(l.getWidth());
            }
        this.coordinateArrayList.add(l.getC1());
        this.coordinateArrayList.add(l.getC2());
    }

    Coordinate getLastCoordinate(){
        return this.coordinateArrayList.get(this.getCoordinateNum()-1);
    }
}
