[toc]

> 20200728
>
> 最近用swing做了一个小工具，它与web领域的编程方式相差较大，经常出现组件不显示或者其他诡异情况。
>
> 不过，虽然身为服务端开发人员，但在这个过程中，对桌面应用产生了小小的兴趣，希望能对这个领域有更多的了解。

# 常用的桌面应用开发工具

JavaFX11出来了Java的GUI有希望吗？ - 朴拙的瘦子的回答 - 知乎 https://www.zhihu.com/question/305434657/answer/621961635

Java有没有可能以后推出优秀的UI工具包？ - 圆胖肿的回答 - 知乎 https://www.zhihu.com/question/42079930/answer/831543398

## C++

### Qt

- 跨平台
- C++

在1991年由Qt Company开发的跨平台C++图形用户界面开发框架。

`Qt`的应用层主要是大型3D，虚拟现实，管理软件和器械嵌入软件。日常生活中所用的qt产品比较少。也就
`virtual box`，`google earth`等。但是大型系统就正好相反，这是`c++`决定的，而非`Qt`。

> MFC同样由C++开发。

## Java系

### AWT与Swing

Java桌面开发“臭名昭著”的元老。

而Java开发人员常用的两款工具`ecplise`与`idea`均是通过`Swing`开发的。

下面分享一个大佬使用Swing开发的数据库同步工具。

[http://github.com/rememberber/WeSync](https://link.zhihu.com/?target=http%3A//github.com/rememberber/DataSync)

- AWT

  即使是Sun公司也不推荐使用。

- Swing

  即使`idea`的开发人员，对`Swing`也有深深的怨念。

### Java FX

Java桌面开发新生儿。

结合了市场桌面开发的众多理念，但奈何起步已晚，市场竞争不见得有优势。

同时由于Java FX年纪较轻，依然存在许多被人吐槽的`bug`。

- Java FX中文官网

https://openjfx.cn/

- Java FX码云库

https://gitee.com/openjfxcn

> 作者：圆胖肿
> 链接：https://www.zhihu.com/question/42079930/answer/831543398
> 来源：知乎
> 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
>
> 
>
> scene builder什么，感觉很难用，一个主要问题是fxml带来的解析极为恶心，各种莫名其妙的bug，最早javafx是打算像web那样，弄一个fxml对应html，fxscript对应javascript，css，然后写，结果第一个凉掉的是fxscript，发现script的那种搞法不行，所以回去用java
>
> 现在看，至少我们在fxml上的实践，是不成功的，现在都彻底放弃fxml了，直接用代码写，这里说一下，javafx的布局，比flutter的布局的设计要好一点，swiftui的布局设计最好，它直接用json做配置，然后它就能在xcode里面拖拽出来，但是目前flutter和javafx似乎都还没有觉悟这一点，所以做javafx的johan vos和kotlin的那个roman在twitter上有过一次讨论，到底布局代码，该怎么写，从目前发展的情况看，*ml的方式根本就是个错误，应该回归原始代码，他们当时是在swiftui和flutter的主题下讨论的，roman认为应该用kotlin的dsl，而johan则认为应该用graal的多语言功能，引入新的语言来更好滴设计布局，而flutter则是直接将dart代码设计成表达ui布局更加合理的方式，然后搭配ide的高亮等特性予以展示

## 谷歌系

### Flutter

谷歌移动端框架。

## 苹果系

### SwiftUI

风评优于Flutter。似乎仅支持苹果设备，但也是苹果一贯作风。

说点题外话，`swift`也是苹果公司新推出的开发语言，专门针对`IOS`和`OS X`。兼容苹果原来使用的编程语言`Object-c`，且使用更容易。

而`SwiftUI`正是基于`swfit`开发的。

> 但是不管是哪一种做法，反正苹果，Google和Java都开始放弃*ml的做法，Java放弃得比较慢，但是苹果和Google已经明显不再这条路上继续了，一些老的gui框架，比如qt等还会提供这些东西，但是我建议你最好睁开眼睛看看世界的变化，现在是移动为王的年代，两个移动大boss苹果和Google都不约而同地放弃了*\*ml的话，那说明这货将来前途堪忧
>
> 
>
> 作者：圆胖肿
> 链接：https://www.zhihu.com/question/42079930/answer/831543398
> 来源：知乎
> 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

## microsoft

### MFC

桌面开发始皇帝。

最早的桌面应用往往在windows上使用，`MFC`对`Win32`做了一层封装，本质是操作系统提供原生控件。

这样导致安全问题，密码框可以用`Spy++`工具窥探。

### DirectUI

通过`GDI`画出控件。

#### windowless

国外开发者模仿`DirectUI`实现的桌面开发工具。

#### Duilib

国内开发者模仿`DirectUI`实现的桌面开发工具。

我们常用的网易云、钉钉、微信均是通过该工具开发。

这里有个有趣的题外话，微信通过开源工具实现，但腾讯QQ则是通过内部界面库，这也许是腾讯公司内部竞争的结果。

哈哈。

- 开源库地址

https://github.com/duilib/duilib

## Web单页应用

由于浏览器的性能蒸蒸日上，即使有了许多桌面应用开发工具，但是许多软件仍然是套个浏览器外壳，再通过Web渲染。

结合`CS`的运行效率与`BS`的开发快易发布的特点。即使是`QQ音乐PC版本`也采用这种模式。

### node.js

#### Electron

Electron的原理很简单，基本上就是使用我们常见的chrome浏览器的内核为基础通过nodejs和底层操作系统进行操作交互。

https://www.jianshu.com/p/752b469519b0

