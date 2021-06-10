

[toc]

# 引言

> - **2021-1-27**
>
>   之前用`swing`做了一款`ReentrantReadWriteLock`的演示工具。
>
>   同时想将做把更多的并发工具做成演示程序，帮助自己与大家学习理解。
>
>   但发现自己原来写的代码太烂了。由于当时想以后慢慢再迭代，为求方便只考虑了`读写锁`的演示程序，再看代码觉得难以入手。其次自己对`swing`理解真的不深入，也觉得没有必要去细细研究。
>
>   所以打算学习一下`javafx`，看看是否能更优雅地完成这个项目。

-  **参考**
-  [JavaFX 15](https://openjfx.cn/index.html)
-  [JavaFX中文资料](http://www.javafxchina.net/blog)
- [JavaFX 教程 （中文）](https://code.makery.ch/zh-cn/library/javafx-tutorial/)
- [Client Technologies: Java Platform, Standard Edition (Java SE) 8 Release 8 (oracle.com)](https://docs.oracle.com/javase/8/javase-clienttechnologies.htm)

# 概述

- **什么是`javafx`**

  我觉得`javafx`就是一个`java`实现的ui类库。

  但是这个网站说了一大堆[DOC-01-01 JavaFX概览](http://www.javafxchina.net/blog/2015/06/doc01_overview/)。

  有兴趣的读者可以看看。
  
- **架构图**

  ![img](../images/javafx/2_1-jfxar_dt_001_arch-diag.png)

# Application

`Application.launch(args)`是阻塞方法。这样才能在显示窗口的程序不退出。

``` java
log.debug("******** start ********");
Application.launch(UIAlignmentApplication.class, args);
Application.launch(SameSizeButtonApplication.class, args);
log.debug("******** end ********");
```

# 场景图

## Stage 舞台

`Stage`是在`JavaFX`中最为常见的顶级容器，继承自`Window`类。

- **Window声明**

  `javafx`顶级容器的实现类，通常我们调用它的`setScene(Scene)`方法，把`场景`交给它托管。`Window objects`将被`JavaFX Application Thread`构造与创建。

-  **Stage声明**

  ``` java
  public class Stage extends Window {}
  ```

## Scene 场景图

`Scene Graph`，是构建`JavaFX`应用的入口。它是一个树状结构，包含一个根结点。

## Node

- **声明**

  ``` java
  @IDProperty("id")
  public abstract class Node implements EventTarget, Styleable {}
  ```

  每个节点都具有一个`id`、`样式类`(见`Styleable`)与`包围盒`。除了`Window`与`Scene`外，所有元素本质都是`Node`，即使是`Pane`。

# 控件

控件内容很多，简单得不想说。

# WebView

`javafx`内嵌浏览器，基于开源`Web`浏览器引擎`Webkit`。

## 作用

- 加载本地或远程`URL`的`HTML`内容
- 获取`Web`历史
- 执行`Javascript`脚本
- 执行由`Javascript`向上调用`javafx`
- 管理`web`上的弹出式`pop-up`窗口
- 为内嵌浏览器应用特效

## 架构

![4_2_1 webview-structure](../images/javafx/4_2_1-webview-structure.png)

### WebEngine

提供基本的`web`页面功能。

### WebView

是`Node`的扩展，封装了`WebEngine`。负责将`HTML`内容加入程序的场景中，并提供各种属性和方法来应用特效和变换。

# 布局

## 内置布局

面板是一种布局容器。

<hr>

### 基本介绍

- **BorderPane**

  从以`Border`命名来看，大概就是一个中心四条边的经典布局。

  通常，上方作为菜单来和工具栏，下方是状态栏，左边是导航栏，右边是附加信息面板，中间是核心工作区域。

  当`BorderPane`所在窗口比各区域内容所需空间大时，将多出的空间默认设置给中间区域。

  当窗口大小比各区域所需空间小时，各个区域就会重叠。重叠的顺序取决于各个区域设置的顺序。例如，如果各个区域设置的顺序是左、下、右，则当窗口变小时，下方区域会覆盖左边区域，而右边区域会覆盖下方区域。如果区域设置顺序是左、右、下，当窗口变小时，下方区域则会在覆盖到左边和右边区域之上。

- **水平盒子(HBox)**

- **垂直盒子(VBox)**

- **栈面板(StackPane)**

  将所有的节点放在一个堆栈中进行布局管理，后添加的节点会显示在前一个节点之上。

- **网格面板(GridPane)**

- **流面板(FlowPane)**

- **磁贴面板(TilePane)**

- **锚面板(AnchorPane)**

  `AnchorPane`布局面板可以让你将节点锚定到面板的顶部、底部、左边、右边或者中间位置。当窗体的大小变化时，节点会保持与其锚点之间的相对位置。一个节点可以锚定到一个或者多个位置，并且多个节点可以被锚定到同一个位置。

### HBox

通过设置`spacing`设置其子`Node`之间的间距。

``` xml
<HBox spacing="10">
    <Button text="Add"/>
    <Button text="Remove"'
</HBox>
```

##  调整节点大小与对齐

使用`javafx`内置布局面板的主要好处是<u>`node`的大小和对齐方式是由布局面板控制的</u>。

### 调整节点大小

布局通过以下两个方法得到`Node`的预设大小`PreferredSize`：

- **prefWidth(height)**
- **prefHeight(width)**

1. 在默认情况下，UI控件会根据其所包含的内容来自动计算**预设大小属性的默认值**。

   例如，按钮`(Button)`被计算出来的大小取决于标签文本的长度和字体大小，再加上按钮图标的大小。一般来说，计算出来的大小刚好能让控件及其标签完全显示可见。

2. UI控件根据其典型用途还提供了**默认的最小和最大值**。

   例如，`Button`的默认最大值就是其预设大小，因为一般来说你不会想让按钮任意变大。然而`ScrollPane`的最大值却是不受限制的，因为一般来说你会希望它们能够变大并充满所有的可用空间。

### 对齐内容

每个布局面板都有一个默认的对齐方式，比如说：在`VBox`中，`Node`是居上对齐；在`HBox`中，`Node`是居左对齐。

通常，我们可以使用`Pane`的`setAlignment(Pos)`来控制`Node`和`Pane`的对齐方式。对齐属性常量有以下枚举类型可选：

- **HPos**	水平对齐方式
- **Pos**	水平与垂直对齐方式
- **VPos**	垂直对齐方式

但实际上，`setAlignment(Pos)`并不支持传入参数为`VPos`或`HPos`，而且构造方法声明为`private`。我们并不能自定义`Pos`的属性，只能以下形式来传入`Pos`：

``` java
VBox pane = new VBox();
pane.getChildren().add(new Button("一个按钮"));
pane.setAlignment(POS.center);
```

挺好的，避免程序员乱来。说明`javafx`封装得还是不错的。

# CSS

`css`可用于布局或调整样式。

`javafx`默认的`stylesheet`是`modena.css`。

- **解压并查看`modena.css`**

``` bat
jar xf jfxrt.jar com/sun/javafx/scene/control/skin/modena/modena.css
```

- **覆盖.root样式类**

可以通过修改`.root`样式类快速更改界面外观。`.root`将应用到`Scene`实例的`root node`。由于`scene`的所有`node`都在该`root node`中，所以`.root`样式类中的`style`将被应用到所有的`node`上。

但需要注意的是，在我们根结点为`Group`类型时不生效。

## 添加样式表

``` java
Scene scene = new Scene(new Group(), 500, 400);
scene.getStylesheets().add(getClass().getResource("/css/test.css").toString());
```

<hr>

- **参考**

- [JavaFX CSS Reference Guide](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/doc-files/cssref.html)

# FXML

`FXML`时一种基于`XML`的语言，用于将用户界面与应用程序逻辑代码分离。

- `FXML`并未定义`schema`，它具有一个基本的预定义结构；

- `FXML`直接映射为`Java`代码；

  大多数`JavaFX`类可以被映射为`XML`元素，并且大多数的`Bean`属性都会被映射为元素的属性。

  从`MVC`设计模式的角度来看，`FXML`文件描述用户界面是其中的`View`部分。

  `Controller`是一个`Java`类，可以选择实现`Initializable`接口，用于将其声明为`FXML`文件的控制器。

  `Model`部分包括了领域模型对象，使用`Java`代码来定义并通过`Controller`来与`View`关联。

## 优势

- `Web`开发者更为熟悉的`UI`开发方式；
- 结构清晰
- `FXML`不是编译型语言，不需要重新编译即可看到用户界面的改变；
- 任何基于`JVM`的语言均可使用`FXML`；
- 可以在`FXML`可用于构建服务、任务或领域对象，以及使用`JavaScript`或其他脚本语言；

## FXML Project基本架构

- **.java**
- **jfxml**
- **Controller**

### 定义数据模型

- **SimpleStringProperty**

  我们通过这个类可以将`jfxml`中的元素与数据模型中的属性绑定。

比如我们有一个表格页面。

- **TableView**

  ``` xml
  <?import javafx.collections.*?>
  <?import javafx.geometry.*?>
  <?import javafx.scene.control.*?>
  <?import javafx.scene.control.cell.*?>
  <?import javafx.scene.layout.*?>
  <?import com.houc.javafx.fxml.*?>
  
  <GridPane xmlns:fx="http://javafx.com/fxml" xmlns="http://javafx.com/javafx" alignment="CENTER" hgap="10.0" vgap="10.0"
      fx:controller="com.houc.javafx.fxml.FXMLTableViewController">
      <padding>
          <Insets bottom="10.0" left="10.0" right="10.0" top="10.0"/>
      </padding>
      <Label style="-fx-font: NORMAL 20 Tahoma;" text="Address Book" GridPane.columnIndex="0" GridPane.rowIndex="0">
      </Label>
      <TableView fx:id="tableView" GridPane.columnIndex="0" GridPane.rowIndex="1" prefWidth="300">
          <columns>
              <TableColumn fx:id="firstNameColumn" text="First Name" prefWidth="100">
                  <cellValueFactory><PropertyValueFactory property="firstName"/>
                  </cellValueFactory>
                  <cellFactory>
                      <FormattedTableCellFactory alignment="CENTER" />
                  </cellFactory>
                  <cellValueFactory>
                      <PropertyValueFactory property="firstName" />
                  </cellValueFactory>
              </TableColumn>
  
              <TableColumn text="Last Name" prefWidth="100">
                  <cellValueFactory><PropertyValueFactory property="lastName" />
                  </cellValueFactory>
              </TableColumn>
  
              <TableColumn text="Email Address" >
                  <cellValueFactory><PropertyValueFactory property="email" />
                  </cellValueFactory>
              </TableColumn>
          </columns>
          <items>
              <FXCollections fx:factory="observableArrayList">
                  <Person email="jacob.smith@example.com" firstName="Jacob" lastName="Smith" />
                  <Person email="isabella.johnson@example.com" firstName="Isabella" lastName="Johnson" />
                  <Person email="ethan.williams@example.com" firstName="Ethan" lastName="Williams" />
                  <Person email="emma.jones@example.com" firstName="Emma" lastName="Jones" />
                  <Person email="michael.brown@example.com" firstName="Michael" lastName="Brown" />
              </FXCollections>
          </items>
          <!-- 启动时排序 -->
          <sortOrder>
              <fx:reference source="firstNameColumn" />
          </sortOrder>
      </TableView>
  
      <HBox spacing="10" alignment="bottom_right" GridPane.columnIndex="0"
            GridPane.rowIndex="2">
          <TextField fx:id="firstNameField" promptText="First Name"
                     prefWidth="90"/>
          <TextField fx:id="lastNameField" promptText="Last Name"
                     prefWidth="90"/>
          <TextField fx:id="emailField" promptText="email"
                     prefWidth="150"/>
          <Button text="Add" onAction="#addPerson"/>
      </HBox>
  </GridPane>
  ```

- **Person**

  ``` java
  public class Person {
  
      private final SimpleStringProperty firstName = new SimpleStringProperty("");
      private final SimpleStringProperty lastName = new SimpleStringProperty("");
      private final SimpleStringProperty email = new SimpleStringProperty("");
  
      public Person() {
          this("", "", "");
      }
  
      public Person(String firstName, String lastName, String email) {
          setFirstName(firstName);
          setLastName(lastName);
          setEmail(email);
      }
  
      public String getFirstName() {
          return firstName.get();
      }
  
      public void setFirstName(String fName) {
          firstName.set(fName);
      }
  
      public String getLastName() {
          return lastName.get();
      }
  
      public void setLastName(String fName) {
          lastName.set(fName);
      }
  
      public String getEmail() {
          return email.get();
      }
  
      public void setEmail(String fName) {
          email.set(fName);
      }
  
  }
  ```

- **Controller**

  ``` java
  public class FXMLTableViewController extends Application {
  	// @FXML标记该字段将映射到 ui上
      @FXML private TableView<Person> tableView;
      @FXML private TextField firstNameField;
      @FXML private TextField lastNameField;
      @FXML private TextField emailField;
  
      @FXML
      protected void addPerson(ActionEvent event) {
          ObservableList<Person> data = tableView.getItems();
          data.add(new Person(firstNameField.getText(), lastNameField.getText(), emailField.getText()));
          firstNameField.setText("");
          lastNameField.setText("");
          emailField.setText("");
      }
  
      public static void main(String[] args) {
          launch(args);
      }
  
      @Override
      public void start(Stage primaryStage) throws Exception {
          primaryStage.setTitle("FXML Table View example");
          Pane load = (Pane) FXMLLoader.load(getClass().getResource("/fxml/fxml_tableview.fxml"));
          Scene scene = new Scene(load);
          primaryStage.setScene(scene);
          primaryStage.show();
      }
  
  }
  ```

### Callback

我们可以通过该接口实现对单元格样式的回调。

``` java
public class FormattedTableCellFactory <S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

    private TextAlignment alignment;
    private Format format;

    public TextAlignment getAlignment() {
        return alignment;
    }

    public void setAlignment(TextAlignment alignment) {
        this.alignment = alignment;
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    @Override
    public TableCell<S, T> call(TableColumn<S, T> stTableColumn) {
        TableCell<S, T> cell = new TableCell<S, T>() {
            @Override
            public void updateItem(Object item, boolean empty) {
                if (item == getItem()) {
                    return;
                }
                super.updateItem((T) item, empty);
                if (item == null) {
                    super.setText(null);
                    super.setGraphic(null);
                } else if (format != null) {
                    super.setText(format.format(item));
                } else if (item instanceof Node) {
                    super.setText(null);
                    super.setGraphic((Node) item);
                } else {
                    super.setText(item.toString());
                    super.setGraphic(null);
                }
            }
        };
        cell.setTextAlignment(alignment);
        switch (alignment) {
            case CENTER:
                cell.setAlignment(Pos.CENTER);
                break;
            case RIGHT:
                cell.setAlignment(Pos.CENTER_RIGHT);
                break;
            default:
                cell.setAlignment(Pos.CENTER_LEFT);
                break;
        }
        return cell;
    }
}
```

### 使用脚本语言

在`fxml`文件中的`XML doctype`声明之后添加`Java Script`声明。

``` html
<?language javascript?>
<GridPane xmlns:fx="http://javafx.com/fxml" alignment="center" hgap="10" vgap="10" styleClass="root">
    <stypesheets>
    	<URL value="@Login.css"/>
    </stypesheets>
</GridPane>
<fx:script source="fxml_example.js"/>
<fx:script>
	function handleSubmitButtonAction() {
    	actiontarget.setText("Calling the JavaScript");
    }
</fx:script>
```

## 使用Css

### Node添加样式类

- **css**

  ``` css
  .myStyle {
      -fx-background-color: #CCFF99;
  }
  ```

- **fxml**

  ``` xml
  <Button styleClass="myStyle">
  	Hello world
  </Button>
  ```

### Node添加样式

- **fxml**

  ``` xml
  <Button style="-fx-background-color:  #CCFF99">
  	Hello world
  </Button>
  ```

## fxml中引入样式表

``` xml
<stylesheets>
	<URL value="@../style/frame.css"/>
</stylesheets>
```

# Controller

## 初始化

- **Callback**

  在`JavaFX2.0`中，对`Controller`的创建过程是没有任何控制的，导致应用无法使用依赖注入系统管理`Controller`的初始化，比如`Google Guice`或`Spring`。

  在`JavaFX2.1`中，增加一个`Callback`接口来对`Controller`的构造进行代理。

``` java
public interface Callback {
    public Object getController(Class<?> type);
}
```

​		向`FXMLLoader`对象提供一个`Controller Factory`，则加载器会将`Controller`的构造过程委托给`Factory`。对应的实现类应该返回一个`null`标志其有没有或者无法创建一个对应类型的`Controller`。此时加载器启用默认的`Controller`构造机制。实现类可以服用`Controller`，令其实例被多个`FXML`文档共享。

​		这样做的意义是**Controller属性的注入不应该以此种方式来进行，这样会导致在Controller的属性中仅包含最近加载的文档的值。**

- **Initializable**

  在`JavaFX2.1`及以前版本中，`Controller`类需要实现`Initializable`接口，令关联`FXML`加载完毕后获得通知。

  在`JavaFX2.2`这不是必须的，`FXMLLoader`仅会简单查找`Controller`的`initialize()`

  > 与其他`FXML`回调方法类似（比如事件处理器）。


# FXML标签

## TextArea

``` xml
<TextArea minHeight="Infinity" >我是一个TextArea</TextArea>
```

- **disabled**	

  在`fxml`中没有`readonly`属性，我们可以通过`disabeld`来取代`readonly`，使其不可编辑。

  ``` xml
  <TextArea disabled="true">不可编辑的TextArea</TextArea>
  ```

# FXML常见属性

### Infinity

表示无穷大，通常可以用于设置节点的长度或者宽度。也可以使用`-Infinity`表示负无穷大。

``` xml
<TextArea minHeight="Infinity" >我是一个TextArea</TextArea>
```

# FAQ

## 错误: 缺少 JavaFX 运行时组件, 需要使用该组件来运行此应用程序

在`debug`时发生这个错误，但是从`javafx-archetype-fxml`这个模板直接拉下来的项目并没有发生这个问题。同样的，在直接运行时也没有问题。

这是`Java 9`新特性模块化导致的。

- **参考**

  [在 Intellij IDEA 里使用 OpenJFX (JavaFX)](https://my.oschina.net/tridays/blog/2222909)

  [jdk11 + maven 打包JavaFX11](https://blog.csdn.net/xizi1103/article/details/104015406)

## maven clean compile package javafx:run 报错: -release无法解析

将`maven`所使用的`jdk`换成`jdk15`。

