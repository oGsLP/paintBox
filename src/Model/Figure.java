package Model;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/*
 * @author: XYF
 * @Date: 2018/9/18
 */
public class Figure {
    private ArrayList<OneLine> oneLines;
    private String identification;
    public Figure(){
        this.oneLines=new ArrayList<>();
    }
    public Figure( ArrayList<OneLine> oneLines, String identification){
        this.oneLines=oneLines;
        this.identification=identification;
    }

    public void add(OneLine oneLine){
        this.oneLines.add(oneLine);
    }
    public int getLineNum(){
        return this.oneLines.size();
    }
    public OneLine get(int i){
        if(this.getLineNum()!=0)
            return this.oneLines.get(i);
        else
            return null;
    }

    public String getIdentification() {
        return identification;
    }

    public JTextField setIdentification(String identification) {
        this.identification = identification;
        Coordinate lastC=this.getLastLine().getLastCoordinate();
        JTextField j=new JTextField(identification);
        j.setForeground(new Color(220,20,60));
        j.setFont(new Font("SimSun", Font.ITALIC, 12));
        j.setBounds(lastC.getX(),lastC.getY(),40,15);
        j.setHorizontalAlignment(JButton.CENTER);
        j.setOpaque(false);
        return j;
    }

    public double getXYRate(){
        int xMin=this.oneLines.get(0).getCoordinate(0).getX();int xMax=xMin;
        int yMin=this.oneLines.get(0).getCoordinate(0).getY();int yMax=yMin;
        int lines=this.getLineNum();
        for(int i=0;i<lines;i++){
            for(int j=0;j<this.oneLines.get(i).getCoordinateNum();j++){
                int xTemp=this.oneLines.get(i).getCoordinate(j).getX();
                int yTemp=this.oneLines.get(i).getCoordinate(j).getY();

                if(xTemp>xMax)
                    xMax=xTemp;
                if(xTemp<xMin)
                    xMin=xTemp;
                if(yTemp>yMax)
                    yMax=yTemp;
                if(yTemp<yMin)
                    yMin=yTemp;
            }
        }
        double xExtreme= xMax-xMin;double yExtreme = yMax-yMin;
        double rate;
        if((xExtreme/yExtreme)>1)
            rate=(xExtreme/yExtreme);
        else
            rate=(yExtreme/xExtreme);
        return rate;
    }



    private OneLine getLastLine(){
        return this.oneLines.get(this.getLineNum()-1);
    }
}
