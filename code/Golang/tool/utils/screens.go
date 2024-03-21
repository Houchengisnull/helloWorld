package utils

import (
	"syscall"
)

/**
* read screen size
* args 0 : get width
* args 1 : get height
 */
func GetSystemMetrics(index int) float32 {
	x, _, _ := syscall.NewLazyDLL(`User32.dll`).NewProc(`GetSystemMetrics`).Call(uintptr(index))
	return float32(x)
}

func GetWidth() float32 {
	return GetSystemMetrics(0)
}

func GetHeight() float32 {
	return GetSystemMetrics(0)
}
