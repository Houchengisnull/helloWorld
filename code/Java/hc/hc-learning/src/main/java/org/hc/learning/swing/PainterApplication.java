package org.hc.learning.swing;

import javax.swing.*;
import java.awt.*;

public class PainterApplication extends JFrame{

    public static void main(String[]argv)
    {
        JFrame frame=new PainterApplication();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭操作
        frame.setSize(600, 600);
        frame.setVisible(true);//显示 设置大小
    }
    public void paint(Graphics g)//画图对象
    {
        g.drawString("Circle 99", 20, 20);//名称 横位置 纵位置
        int x0=getSize().width/2;
        int y0=getSize().height/2;//圆心
        for(int r=0;r<getSize().height/2;r+=10)
        {
            g.setColor(getRandomColor());
            g.drawOval(x0- r,y0- r,r*2,r*2);//椭圆
        }
    }

    Color getRandomColor()//随机数
    {
        return new Color(
                (int)(Math.random()*255),
                (int)(Math.random()*255),
                (int)(Math.random()*255)
        );
    }

}
