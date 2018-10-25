package Listener;

import Draw.DrawBorder;
import Model.*;


/*
 * @author: XYF
 * @Date: 2018/9/8
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;


public class DrawListener implements MouseListener,MouseMotionListener{

    private Graphics2D g;

    private Coordinate c1,c2,c3;
    private ButtonGroup bg;
    private String command;
    private DrawBorder db;
    
    private boolean flag=true;

    private static final  Stroke s1 = new BasicStroke(1);
    private static final  Stroke s2 = new BasicStroke(10);
    private static final  Stroke s3 = new BasicStroke(15);

    private Random r =new Random();

    //构造函数
    public DrawListener(Graphics g2, ButtonGroup bg2, DrawBorder db1) {
        this.g=(Graphics2D)g2;
        this.bg=bg2;
        this.db=db1;
    }

    //鼠标按下事件监听
    public void mousePressed(MouseEvent e) {
        //获取鼠标按下点的坐标
        c1=new Coordinate(e.getX(),e.getY());

        //判断选择的是左面板中的那个按钮被选中（前面已经设置每个按钮的名称了）
        ButtonModel bm=bg.getSelection();//拿到按钮组中被选中的按钮
        this.command=bm.getActionCommand();//拿到选中按钮的名字
    }

    public void mouseReleased(MouseEvent e) {
        //获取鼠标释放的坐标
        c2=new Coordinate(e.getX(),e.getY());
        //如果选中的是绘制直线的按钮，那么根据鼠标按下点的坐标和释放点的左边绘制直线（两点确定一条直线）
        if("tool1".equals(command))
        {
            LinkLine linkLine= new LinkLine(c1,c2,g.getColor(),1);
            linkLine.linkPoints(g);
            this.db.oneLine.add(linkLine);
        }
        else if("tool2".equals(command))
        {
            System.out.print("\r\n");
        }

     //点到点
        else if("tool3".equals(command)){
            //第一次画直线，设置标志
            if(flag){
                LinkLine linkLine=new LinkLine(c1,c2,g.getColor(),1);
                linkLine.linkPoints(g);
                this.db.oneLine.add(linkLine);
                flag=false;
                //记录这次鼠标释放的坐标，作为下次绘制直线的起点
                c3=c2;
            }
            else{
                LinkLine linkLine=new LinkLine(c3,c2,g.getColor(),1);
                linkLine.linkPoints(g);
                this.db.oneLine.add(linkLine);
                //记录上次鼠标释放的坐标
                c3=c2;
            }
        }
        this.db.figure.add(this.db.oneLine);
        this.db.oneLine=new OneLine();
    }

    public void mouseClicked(MouseEvent e) {
        //双击清空
        int count =e.getClickCount();
        if(count==2){
            flag=true;
        }
    }

    public void mouseEntered(MouseEvent e) {
        Color color = this.db.cc;
        this.g.setColor(color);
        this.g.setStroke(s1);
    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent e) {

        c3=new Coordinate(e.getX(),e.getY());

        //曲线
        if("tool2".equals(command)){
            LinkLine linkLine=new LinkLine(c1,c3,g.getColor(),1);
            linkLine.linkPoints(g);
            this.db.oneLine.add(linkLine);
            c1=c3;
        }
        //橡皮擦功能
        else if("tool4".equals(command)){
            db.cc=Color.white;
            g.setColor(db.cc);
            g.setStroke(s3);
            LinkLine linkLine=new LinkLine(c1,c3,g.getColor(),15);
            linkLine.linkPoints(g);
            this.db.oneLine.add(linkLine);
            c1=c3;

        }
        //刷子功能
        else if("tool5".equals(command)){
            g.setStroke(s2);//设置画笔 粗细

            LinkLine linkLine=new LinkLine(c1,c3,g.getColor(),10);
            linkLine.linkPoints(g);
            this.db.oneLine.add(linkLine);
            c1=c3;
        }
        //喷桶功能
        else if("tool6".equals(command)){
            //随机产生30个-15到15之间的整数
            for (int i = 0; i < 30; i++) {
                int xp=r.nextInt(31)-15;
                int yp=r.nextInt(31)-15;
                //在x，y附件绘制原点
                LinkLine linkLine=new LinkLine(new Coordinate(c3.getX()+xp,c3.getY()+yp),new Coordinate(c3.getX()+xp,c3.getY()+yp),g.getColor(),1);
                linkLine.linkPoints(g);
                this.db.oneLine.add(linkLine);
            }
        }
    }
    public void mouseMoved(MouseEvent e) {
    }
}
