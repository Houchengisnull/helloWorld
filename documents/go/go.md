[toc]

# 依赖

## 下载依赖

- **设置代理**

``` shell
go env -w GOSUMDB=off
go env -w GOPROXY=https://goproxy.cn,direct
```

## 导入

``` go
// 仅使用包中的init函数
import _ "golang.org/x/image/bmp"
```

# Command

## Call Another Mod

``` bat
go mod edit -replace example.com/greetings=../greetings
go mod tidy	
```

# Project

## go.sum

记录项目依赖的版本和hash，以保证项目在构建与部署过程中的一致性。

## go.work

创建一个`workspace`，快速搭建模块。

``` shell
go work init ./hello
```

执行该命令后，会出现一个`go.work`的文件。

再执行：

``` shell
go work use hello
```

说明在该`workspace`将使用`hello`模块。

> 但是看其他项目并没有该文件...其他人是在Go中是如何使用multipart module的呢？很疑惑。

# 语法

## 函数

### 指针接收者

``` go
package main

import (
	"fmt"
	"math"
)

type Vertex struct {
	X, Y float64
}

func (v Vertex) Abs() float64 {
	return math.Sqrt(v.X*v.X + v.Y*v.Y)
}

// 指针接收者
func (v *Vertex) Scale(f float64) {
	v.X = v.X * f
	v.Y = v.Y * f
}

// 值接收者 对X和Y的副本进行操作
func (v Vertex) ScaleValue(f float64) {
	v.X = v.X * f
	v.Y = v.Y * f
}

func main() {
	v := Vertex{3, 4}
	v.Scale(10)
	v.ScaleValue(10)
	fmt.Println(v.Abs())
}
```

在这段代码中，变量`v`是`Scale`与`ScaleValue`的接收者，它的类型是`Vertex`。

在`func (v *Vertex) Scale(f float64)`中它是指针接收者，而在`func (v Vertex) ScaleValue(f float64)`中它是值接收者。

- **指针接收者与值接收者的区别**

  | -              | 值接收者                                                     | 指针接收者                                                   |
  | -------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
  | 值类型调用者   | 方法会使用调用者的一个副本，类似于“传值”                     | 使用值的引用来调用方法，上例中，`qcrao.growUp()` 实际上是 `(&qcrao).growUp()` |
  | 指针类型调用者 | 指针被解引用为值，上例中，`stefno.howOld()` 实际上是 `(*stefno).howOld()` | 实际上也是“传值”，方法里的操作会影响到调用者，类似于指针传参，拷贝了一份指针 |

  结论如下：

  实现了接收者是值类型的方法，相当于自动实现了接收者是指针类型的方法；

  实现了接收者是指针类型的方法，不会自动生成对应接收者是值类型的方法。

## 接口

在Java中，明确的使用了`implements`来实现接口，但是在Go中并非如此，而是通过以下语法：

``` go
type Sayer interface {
	say()
}

type dog struct{}

type cat struct{}

// 实现Sayer的say()
func (d dog) say() {
	fmt.Println("www...")
}

func (c cat) say() {
	fmt.Println("mmm...")
}
```

比如在dog实现Sayer的接口时，作出`func (d dog) say()`的声明，并没有明确指出Sayer与dog的关系。

当我们在以上代码中增加一个接口时：

``` go
type SecondSayer interface {
    say()
}
```

意味着dog与cat既实现了Sayer接口，也实现了SecondSayer接口，代码依然正常运行。

这令人感到混乱。

### 接口嵌套

``` go
// Sayer 接口
type Sayer interface {
    say()
}

// Mover 接口
type Mover interface {
    move()
}

// 接口嵌套
type animal interface {
    Sayer
    Mover
}
```

类似Java中模板模式的`public abstract AnimalTemplate implements Sayer, Animal`

## 指针

与指针相关的语法通常有``*`、`&`

``` go
// 声明指针
var ptr *int
i := 42

// ptr指向i的内存的地址
ptr = &i

// 打印ptr该内存地址的内容
fmt.Println(*p)
```

