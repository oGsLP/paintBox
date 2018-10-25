package Model;

import javax.swing.*;
import java.awt.*;

/*
 * @author: XYF
 * @Date: 2018/9/17
 */
public class MenuButton extends JButton {
    public MenuButton(){

    }
    public MenuButton(String text,int fontSize,int width,int height){

        this.setText(text);
        this.setHorizontalAlignment(JButton.CENTER);
        this.setVerticalAlignment(JButton.CENTER);
        this.setPreferredSize(new Dimension(width,height));
        this.setFont(new Font("STHeiti",1,fontSize));
        this.setFocusPainted(false);
        this.setBorderPainted(false);
    }
}
