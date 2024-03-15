package main

import "fmt"

type dog struct{}

func (d dog) say() {
	fmt.Println("www...")
}

func (d *dog) move() {
	fmt.Println("狗会动")
}
