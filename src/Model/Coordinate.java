package Model;

/*
 * @author: XYF
 * @Date: 2018/9/18
 */
public class Coordinate {
    private int x;
    private int y;
    public Coordinate(){
        this.x=0;
        this.y=0;
    }
    public Coordinate(int x,int y){
        this.x=x;
        this.y=y;
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }

}
