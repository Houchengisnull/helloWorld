package main

import "fmt"

type cat struct{}

func (c cat) say() {
	fmt.Println("mmm...")
}
