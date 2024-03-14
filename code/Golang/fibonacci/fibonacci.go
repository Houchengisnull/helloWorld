package main

import "fmt"

// 返回一个“返回int的函数”
func fibonacci() func() int {
	back1, back2 := 0, 1 // 预先设定好两个初始值

	return func() int {

		temp := back1                         //记录（back1）的值
		back1, back2 = back2, (back1 + back2) // 重新赋值(这个就是核心代码)
		return temp                           //返回temp
	}
}

func main() {
	f := fibonacci()
	for i := 0; i < 10; i++ {
		fmt.Println(f())
	}
}
