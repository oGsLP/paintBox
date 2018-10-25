package Model;

import java.util.ArrayList;

/*
 * @author: XYF
 * @Date: 2018/9/20
 */

public class Picture {
    private static final String WORD_BREAK = " ";
    private static final String LINE_BREAK ="\r\n" ;
    private ArrayList<Figure> figures;

    public Picture(){
        this.figures=new ArrayList<Figure>();
    }
    public void Picture(ArrayList<Figure> figures) {
        this.figures = figures;
    }


    public void add(Figure figure){
        this.figures.add(figure);
    }
    public int getFiguresNum(){
        return this.figures.size();
    }
    public Figure get(int i){
        if(i>this.getFiguresNum()-1||i<0)
            return null;
        else return this.figures.get(i);
    }
    public void printPicture(){
        for(int i=0;i<this.getFiguresNum();i++){
            System.out.print("Figure"+WORD_BREAK+i+LINE_BREAK);
            System.out.print("Color : "+this.get(i).get(0).getColor()+LINE_BREAK);
            System.out.print("Width : "+this.get(i).get(0).getWidth()+LINE_BREAK);
            System.out.print("Identification : "+this.get(i).getIdentification()+LINE_BREAK);
            for(int j=0;j<this.get(i).getLineNum();j++){
                System.out.print("Line"+WORD_BREAK+j+" : ");
                for(int k=0;k<this.get(i).get(j).getCoordinateNum();k++){
                    Coordinate c=this.get(i).get(j).getCoordinate(k);
                    System.out.print(c.getX()+","+c.getY()+WORD_BREAK);
                }
                System.out.print(LINE_BREAK);
            }
        }
    }
}
