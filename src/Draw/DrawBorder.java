package Draw;

/**
 * @author: XYF
 * @Date: 2018/9/8
 */


import Listener.ButtonListener;
import Listener.DrawListener;
import Model.*;

import java.awt.*;

import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.BevelBorder;


public class DrawBorder extends JFrame {


    public Color cc = Color.BLACK;
    //按钮属性，便于其他类访问
    public JButton bt;
    public JTextField identifyResult;
    //容器

    public Picture picture=new Picture();
    public Figure figure =new Figure();
    public OneLine oneLine = new OneLine();

    public void initFrame() {

        //设置窗体相关属性
        this.setSize(1200, 800);
        this.setTitle("paintBox");
        this.setDefaultCloseOperation(3);
        this.setLocationRelativeTo(null);

        //窗体添加主面板
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        this.add(panel);


         JPanel panelCenter = new JPanel()
         {
            public void paint(Graphics g1) {
                Graphics2D g = (Graphics2D) g1;
                super.paint(g);
                String identification="";
                int width=1;
                Color color=Color.BLACK;
                for(int i=0;i<picture.getFiguresNum();i++) {
                    for (int j = 0; j < picture.get(i).getLineNum(); j++) {
                        OneLine line = picture.get(i).get(j);
                        width = line.getWidth();
                        color = line.getColor();
                        for (int k = 0; k < line.getCoordinateNum() - 1; k++) {
                            LinkLine linkLine = new LinkLine(line.getCoordinate(k), line.getCoordinate(k + 1), color, width);
                            linkLine.linkPoints(g);
                        }
                    }
                }
            }
        };

        panelCenter.setBackground(Color.white);
        panelCenter.add(new JTextArea("1.左侧工具栏分别为 直线、曲线、两点连线、橡皮、刷子、喷壶\r\n" +
                "其中直线用于画直边图形（三角形、矩形、长方形），曲线用于识别圆形与椭圆\r\n" +
                "后四个功能暂不支持识别标注\r\n" +
                "2.识别按钮可以自动识别出图形，然后在文本框中修改标注，点击确认则标注图形\r\n" +
                "3.点击清空按钮清除文字\r\n"));
        panel.add(panelCenter);


        //主面板添加左面板
        JPanel panelLeft = new JPanel();
        panelLeft.setPreferredSize(new Dimension(140, 0));
        panelLeft.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 60));
        panelLeft.setBackground(new Color(235, 233, 238));
        panel.add(panelLeft, BorderLayout.WEST);

        //面板中添加按钮
        //按钮归类，统一管路
        String [] toolNameGroup={"straight","curve","p2p","eraser","brush","inkJet"};
        ButtonGroup bg = new ButtonGroup();
        for (int i = 1; i <=6; i++) {
            ToolButton tb = new ToolButton(toolNameGroup[i-1],"tool"+i);
            if(i==1)
                tb.setSelected(true);
            bg.add(tb);
            panelLeft.add(tb);
        }


        //主面板添加下方面板
        JPanel panelDown = new JPanel();
        panelDown.setPreferredSize(new Dimension(0, 100));
        panelDown.setLayout(null);
        panelDown.setBackground(new Color(192,192,192));
        panel.add(panelDown, BorderLayout.SOUTH);

        //下方面板添加子面板
        JPanel panelDownColorBox = new JPanel();
        panelDownColorBox.setBackground(Color.cyan);
        panelDownColorBox.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panelDownColorBox.setBounds(15, 25, 350, 50);
        panelDown.add(panelDownColorBox);

        //按钮特效
        BevelBorder bb = new BevelBorder(0, Color.gray, Color.white);
        BevelBorder bb1 = new BevelBorder(1, Color.gray, Color.white);

        JPanel left = new JPanel();
        left.setBackground(Color.white);
        left.setLayout(null);
        left.setBorder(bb);
        left.setPreferredSize(new Dimension(50, 60));
        //左面板中的两棵颜色按钮
        bt = new JButton();
        bt.setBounds(5, 5, 25, 25);
        bt.setBorder(bb1);
        bt.setBackground(Color.black);
        bt.setSize(25, 25);
        JButton bt1 = new JButton();
        bt1.setBorder(bb1);
        bt1.setBounds(20, 20, 25, 25);
        left.add(bt);
        left.add(bt1);
        //右面板
        JPanel right = new JPanel();
        right.setBackground(Color.BLUE);
        right.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        right.setPreferredSize(new Dimension(300, 60));

        panelDownColorBox.add(left);
        panelDownColorBox.add(right);

        //给右面板的颜色按钮天添加监听器，注意传递this对象
        ButtonListener bl = new ButtonListener(this,panelCenter,"color",bg);
        //颜色数组，用来设置按钮的背景颜色
        Color[] colors = {new Color(0, 56, 67), new Color(89, 3, 14), new Color(189, 3, 14)
                , new Color(89, 93, 14), new Color(89, 113, 14), new Color(89, 73, 14)
                , new Color(89, 3, 14), new Color(89, 3, 14), new Color(29, 83, 14)
                , new Color(89, 3, 184), new Color(189, 233, 14), new Color(89, 253, 14)
                , new Color(89, 93, 14), new Color(89, 89, 94), new Color(1, 3, 14)
                , new Color(9, 83, 94), new Color(89, 178, 147), new Color(9, 33, 164)
                , new Color(34, 23, 14), new Color(89, 173, 154), new Color(8, 193, 194)
                , new Color(9, 253, 76), new Color(89, 240, 104), new Color(199, 73, 4)};

        //循环添加24个颜色按钮
        for (int i = 0; i < 24; i++) {
            JButton bt3 = new JButton();
            Color c = new Color(i * 10, 30 - i, i * 7 + 50);
            bt3.setBackground(colors[i]);
            bt3.setPreferredSize(new Dimension(25, 25));
            bt3.setBorder(bb);
            bt3.addActionListener(bl);
            right.add(bt3);
        }


        //识别块
        JPanel panelDownCenterBox = new JPanel();
        panelDownCenterBox.setBackground(new Color(245,245,245));
        panelDownCenterBox.setLayout(null);
        panelDownCenterBox.setBounds(400,10,400,80);

        identifyResult = new JTextField();
        identifyResult.setText("请点击下方按钮识别");
        identifyResult.setEditable(true);
        identifyResult.setHorizontalAlignment(JTextField.CENTER);
        identifyResult.setBounds(50,5,300,30);
        identifyResult.setPreferredSize(new Dimension(300,30));
        identifyResult.setMargin(new Insets(1,1,1,1));
        identifyResult.setFont(new Font("STHeiti",1,16));

        panelDownCenterBox.add(identifyResult);


        MenuButton identifyButton= new MenuButton("识别",18,100,35);
        MenuButton confirmButton= new MenuButton("确认",18,100,35);
        identifyButton.setBounds(60,40,100,35);
        confirmButton.setBounds(240,40,100,35);
        identifyButton.addActionListener(new ButtonListener(this,panelCenter,"identify",bg));
        confirmButton.addActionListener(new ButtonListener(this,panelCenter,"confirm",bg));
        panelDownCenterBox.add(identifyButton);
        panelDownCenterBox.add(confirmButton);

        panelDown.add(panelDownCenterBox);


        //功能块
        JPanel panelDownButtonBox = new JPanel();
        panelDownButtonBox.setBackground(new Color(192,192,192));
        panelDownButtonBox.setBounds(800,0,400,100);
        panelDownButtonBox.setLayout(null);

        MenuButton saveButton = new MenuButton("保存",20,80,40);
        MenuButton openButton = new MenuButton("打开",20,80,40);
        MenuButton clearButton = new MenuButton("清空",24,100,40);
        saveButton.setBounds(50,30,80,40);saveButton.setBackground(Color.WHITE);
        openButton.setBounds(130,30,80,40);openButton.setBackground(Color.black);openButton.setForeground(Color.WHITE);
        clearButton.setBounds(250,25,100,50);clearButton.setContentAreaFilled(false);clearButton.setBorderPainted(true);
        saveButton.addActionListener(new ButtonListener(this,panelCenter,"save",bg));
        openButton.addActionListener(new ButtonListener(this,panelCenter,"open",bg));
        clearButton.addActionListener(new ButtonListener(this,panelCenter,"clear",bg));

        panelDownButtonBox.add(saveButton);panelDownButtonBox.add(openButton);panelDownButtonBox.add(clearButton);
        panelDown.add(panelDownButtonBox);






        this.setVisible(true);


        //画笔必须在setVisible后才能拿
        Graphics g = panelCenter.getGraphics();

        //传递画笔，按钮组管理对象，以及this对象
        DrawListener dl = new DrawListener(g, bg, this);

        //添加普通鼠标监听器
        panelCenter.addMouseListener(dl);

        //添加鼠标拖动监听器
        panelCenter.addMouseMotionListener(dl);

    }
}