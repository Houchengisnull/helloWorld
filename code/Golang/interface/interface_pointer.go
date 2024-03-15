package main

import "fmt"

type song interface {
	words()
}

type 雨爱 struct {
	singer string
}

func (y *雨爱) words() {
	fmt.Println("看不清 我也不想看清")
}
