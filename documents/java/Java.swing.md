[toc]

# AWT

`Abstract Window Toolkit`，抽象窗口工具包。

# JFrame

## 设置图标

- `*.ico`不适用，可以先将***ico***文件转换成***png***格式

``` java
JFrame frame = new JFrame();
frame.setIconImage(new ImageIcon("icon.png"));
```

## 重绘

- 方法一

  ``` java 
  JFrame frame = new JFrame();
  // 一系列修改后
  frame.validate();
  frame.repaint();
  ```

- 方法二

  ``` java
  JFrame frame = new JFrame();
  // 一系列修改后
  frame.revalidate();
  ```

# Layout

## BorderLayout

将元素划分为东南西北中五块区域。

对应`BorderLayout`中的五个常量：

- `BorderLayout.EAST`
- `BorderLayout.WEST`
- `BorderLayout.South`
- `BorderLayout.North`
- `BorderLayout.CENTER`

### 修改各区域控件大小

当使用`BorderLayout`时，直接调用控件的`setSize(width, height)`方法时会失效，此时我们需要调用控件的`setPreferredSize(Dimension)`方法控制大小。

> 这样使各区域大小依赖控件的大小，而无法直接控制区域大小，略显笨重。

## FlowLayout

`流式布局`

 - 构造方法

   ``` java
   public FlowLayout();
   public FlowLayout(int align, int hgap, int vgap);
   ```

   - **align**:	对齐方式
   - **hgap**:	水平间隔
   - **vgap**:	垂直间隔

# 滚动条 JScrollPane

``` java
JPanel content = new JContent();
/*
	该构造方法三个参数代表 viewportView, 垂直滚动条总是显示， 水平滚动条从不显示
*/
JScroll scroll = new JScroll(content, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLL_NEVER);
// 重新设置content的长宽，当content长宽大于scroll长宽时滚动条才生效
content.setPreferredSize(new Dimension(width, height));
// 非必须
scroll.revalidate()
```

重新绘制的代码应该放在scroll依赖父组件（如`JFrame`）设置完成之后。总之`swing`框架在使用方便存在许多难以理解的部分。

- 参考

- 终于搞明白如何将一个JPanel放置到JScrollPane里面并显示滚动条了

  https://blog.csdn.net/leongod/article/details/5967838?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.channel_param&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.channel_param

# Graphics

`swing`中绘图抽象类。

- 定制边框粗细`setStroke(BasicStroke stroke)`

  ``` java
  public class MyPanel extends JPanel {
      
      @Override
      protected void paintBorder(Graphics g) {
          Graphics2D g2d = (Graphics) g;
          g2d.setColor(Color.LIGHT_GRAP);
          g2d.setStroke(new BasicStroke(3));
          g2d.drawLine(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, 0);
      }
      
  }
  ```

  # FAQ
  
  ## java.awt.HeadlessException: null
  
  - **headless**	无界面的
  
  <hr>
  
  在使用一些重量级的容器时，如果缺乏某些设备支持，将抛出`HeadlessException`。
  
  但像`Canvas`，`Panel`，`Image`组件作为轻量级容器，可以被赋予空的`peer`，
  
  并不会抛出`HeadlessException`。
  
  > 我也不知道`peer`在这里应该怎么理解。
  
  我在实现`threadFrame project`时发生了这个异常，只要将在启动界面前设置`JVM`的无界模式即可，
  
  ``` java
  System.setProperty("java.awt.headless", "false");
  ```
  
  应该是结合了`Spring boot`之后引入了这个问题，直接调用界面组件是ok的。
  
  <hr>
  
  - **参考**
  
    <a href='https://www.oracle.com/technical-resources/articles/javase/headless.html'>Using Headless Mode in the Java SE Platform</a>
  
  