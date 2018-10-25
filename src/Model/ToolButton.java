package Model;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/*
 * @author: XYF
 * @Date: 2018/9/16
 */
public class ToolButton extends JRadioButton {
    public ToolButton(){
    }
    public ToolButton(String name,String actionCommand){
        this.setText(name);
        this.setBorderPainted(false);
        this.setBorder(BorderFactory.createRaisedBevelBorder());
        this.setContentAreaFilled(false);
        this.setActionCommand(actionCommand);
        this.setMargin(new Insets(5,5,5,5));
        this.setPreferredSize(new Dimension(120,40));
        this.setFont(new Font("SimSun", 1, 20));

    }
}
