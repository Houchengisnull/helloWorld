package main

import "fmt"

func main() {
	i, j := 1996, 1028
	var p *int
	fmt.Println(&i)
	fmt.Println(&j)

	// & 指向i的内存地址
	p = &i
	fmt.Println(p)
	fmt.Println(*p)
	// 修改该内存地址的值
	*p = j
	fmt.Print(i)
}
