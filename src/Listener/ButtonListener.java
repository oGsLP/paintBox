package Listener;

/*
 * @author: XYF
 * @Date: 2018/9/8
 */

import Draw.DrawBorder;
import Model.Coordinate;
import Model.Figure;
import Model.OneLine;
import Model.Picture;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;

//按钮监听器类
public class ButtonListener implements ActionListener{

    private final static String LINE_BREAK="\r\n";
    private final static String WORD_BREAK=" ";
    //持有对方引用，可以拿到对方的所有属性
    private DrawBorder db;
    private String command;
    private ButtonGroup bg;

    private JPanel centerPanel;

    //常在方法
    public ButtonListener(DrawBorder db,JPanel centerPanel,String command,ButtonGroup bg){
        this.db=db;
        this.centerPanel=centerPanel;
        this.command=command;
        this.bg=bg;
    }

    //按钮监听事件的具体实现

    public void actionPerformed(ActionEvent e) {

        ButtonModel bm=this.bg.getSelection();//拿到按钮组中被选中的按钮
        String tool=bm.getActionCommand();//拿到选中按钮的名字
        //调色
        switch (command) {
            case "color":
                JButton bt = (JButton) e.getSource();
                Color c = bt.getBackground();
                this.db.cc = c;
                this.db.bt.setBackground(c);
                break;
            //自动识别
            case "identify":
                String identifiedResult = "无法识别";
                int edges = this.db.figure.getLineNum();
                if (edges == 0) {
                    System.out.println("No lines");
                    this.db.identifyResult.setText("请绘画直线");
                    return;
                }
                //to judge
                double rate = this.db.figure.getXYRate();
                if ("tool1".equals(tool) || "tool3".equals(tool)) {
                    if (edges > 2) {
                        if (edges == 3)
                            identifiedResult = "三角形";
                        else {
                            if (rate > 1.12)
                                identifiedResult = "长方形";
                            else
                                identifiedResult = "正方形";
                        }
                    }
                } else if ("tool2".equals(tool)) {
                    if (rate > 1.18)
                        identifiedResult = "椭圆";
                    else
                        identifiedResult = "圆";
                }
                this.db.identifyResult.setText(identifiedResult);
                break;
            //确认识别结果
            case "confirm":
                String identification = this.db.identifyResult.getText();
                this.centerPanel.add(this.db.figure.setIdentification(identification));
                this.db.identifyResult.setText("标注完成");
                this.db.picture.add(this.db.figure);
                this.db.figure = new Figure();
                break;
            //保存文件
            case "save": {
                String folderPath = "F:\\JAVA_0.0.0\\paintBox\\src\\Files";
                String picListName = "PicList" + getDate() + ".ptb";
                System.out.println(picListName);
                File file = new File(folderPath, picListName);     //选中文件写入·哪里

                try {
                    BufferedWriter out = new BufferedWriter(new FileWriter(file));
                    out.write("写入文件" + WORD_BREAK + file + LINE_BREAK); //
                    //to do
                    Picture picture = this.db.picture;
                    if (picture.getFiguresNum() == 0)
                        System.out.println("no pic error");
                    else {
                        for (int i = 0; i < picture.getFiguresNum(); i++) {
                            out.write("Figure" + WORD_BREAK + i + LINE_BREAK);
                            out.write("Color : " + picture.get(i).get(0).getColor() + LINE_BREAK);
                            out.write("Width : " + picture.get(i).get(0).getWidth() + LINE_BREAK);
                            out.write("Identification : " + picture.get(i).getIdentification() + LINE_BREAK);
                            for (int j = 0; j < picture.get(i).getLineNum(); j++) {
                                out.write("Line" + WORD_BREAK + j + " : ");
                                for (int k = 0; k < picture.get(i).get(j).getCoordinateNum(); k++) {
                                    Coordinate coordinate = picture.get(i).get(j).getCoordinate(k);
                                    out.write(coordinate.getX() + "," + coordinate.getY() + WORD_BREAK);
                                }
                                out.write(LINE_BREAK);
                            }
                        }
                    }
                    out.close();
                    JOptionPane.showMessageDialog(this.db, "已保存文件" + file, "标题", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                break;
            }

            //打开文件
            case "open": {
                JFileChooser jf = new JFileChooser();
                jf.setCurrentDirectory(new File("F:\\JAVA_0.0.0\\paintBox\\src\\Files"));
                jf.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                jf.showDialog(null, null);
                File file = jf.getSelectedFile();
                String filename = file.getAbsolutePath();
                System.out.println("open: " + filename);
                try {
                    InputStreamReader reader = new InputStreamReader(
                            new FileInputStream(filename));
                    BufferedReader br = new BufferedReader(reader);
                    this.db.picture = new Picture();
                    Picture picture = this.db.picture;
                    br.readLine();
                    String line = br.readLine();
                    while (line != null) {
                        Figure figure = new Figure();
                        Color color = Color.BLACK;
                        int width = 1;
                        identification = "";
                        line = br.readLine();
                        if (line.substring(0, 5).equals("Color")) {
                            String col = line.substring(8);
                            color = getColor(col);
                        }
                        line = br.readLine();
                        if (line.substring(0, 5).equals("Width")) {
                            width = Integer.parseInt(line.substring(8));
                        }
                        line = br.readLine();
                        if (line.substring(0, 14).equals("Identification")) {
                            identification = line.substring(17);
                        }
                        int flag = 1;
                        while (flag == 1) {
                            line = br.readLine();
                            if (line == null || line.substring(0, 6).equals("Figure")) {
                                flag = 0;
                            } else {
                                if (line.substring(0, 4).equals("Line")) {
                                    String lineC = line.substring(9);
                                    figure.add(getLine(lineC, color, width));
                                }
                            }
                        }
                        figure.setIdentification(identification);
                        picture.add(figure);
                    }
                    picture.printPicture();
                    JOptionPane.showMessageDialog(this.db, "已打开文件" + file, "标题", JOptionPane.INFORMATION_MESSAGE);
                    this.centerPanel.removeAll();
                    this.db.repaint();
                    this.db.identifyResult.setText("请点击下方按钮识别");
                    this.db.picture = picture;
                    this.db.figure = new Figure();
                    this.db.oneLine = new OneLine();
                    this.centerPanel.paint(this.db.getGraphics());
                    for (int h = 0; h < this.db.picture.getFiguresNum(); h++)
                        this.centerPanel.add(this.db.picture.get(h).setIdentification(this.db.picture.get(h).getIdentification()));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                break;
            }
            case "clear":
                //clear
                this.db.repaint();
                this.centerPanel.removeAll();
                this.db.picture = new Picture();
                this.db.figure = new Figure();
                this.db.oneLine = new OneLine();
                this.db.identifyResult.setText("请点击下方按钮识别");
                break;
        }
    }

    public String getDate(){
        Date date=new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String temp=format.format(date);
        return temp.substring(5,7)+temp.substring(8,10)
                +temp.substring(11,13)+temp.substring(14,16)+temp.substring(17,19);

    }
    private Color getColor(String c) {

        String cc=c.substring(17);
        int add = 1;
        while (!cc.substring(add-1,add).equals(",") ) {
            add++;
        }
        int r = Integer.parseInt(cc.substring(0, add-1));
        cc=cc.substring(add+2);add=1;
        while (!cc.substring(add-1,add).equals(",") ) {
            add++;
        }
        int g = Integer.parseInt(cc.substring(0, add-1));
        cc=cc.substring(add+2);add=1;
        while (!cc.substring(add-1,add).equals("]") ) {
            add++;
        }
        int b = Integer.parseInt(cc.substring(0, add-1));

        return new Color(r,g,b);
    }
    private OneLine getLine(String c,Color color,int width){
        OneLine oneLine=new OneLine();
        oneLine.setWidth(width);oneLine.setColor(color);
        String cs[]=c.split(" ");
        for (String c1 : cs) {
            Coordinate coordinate = new Coordinate(Integer.parseInt(c1.split(",")[0]), Integer.parseInt(c1.split(",")[1]));
            oneLine.add(coordinate);
        }
        return oneLine;
    }
}
