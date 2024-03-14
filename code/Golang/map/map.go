package main

import (
	"fmt"
	"strings"
)

func main() {
	str := "Hello world world"
	s := strings.Fields(str)
	var m = make(map[string]int)
	for _, i := range s {
		var num = m[i]
		m[i] = num + 1
	}
	fmt.Println(m)
}
