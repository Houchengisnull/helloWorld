package main

import (
	"log"

	"github.com/hajimehoshi/ebiten/v2"
	"github.com/hajimehoshi/ebiten/v2/ebitenutil"
)

const (
	window_width, window_height = 640, 480
	// layout_width, layout_height = 1200, 900
)

type Game struct{}

func (g *Game) Update() error {
	log.Println("Begin Update()")
	log.Println("End Update()")
	return nil
}

func (g *Game) Draw(screen *ebiten.Image) {
	log.Println("Begin Draw()")

	img, _, err := ebitenutil.NewImageFromFile("../resource/img/bg.png")
	if err != nil {
		log.Fatal(err)
	}
	bgWidth := img.Bounds().Dx()
	bgHeight := img.Bounds().Dy()

	// log.Println(fmt.Sprintf("bg size: %d, %d", bgWidth, bgHeight))
	log.Printf("bg size: %d, %d", bgWidth, bgHeight)

	// bgWidthScale :=

	op := &ebiten.DrawImageOptions{}

	// op.GeoM.Scale(0.5, 0.5)
	screen.DrawImage(img, op)

	log.Println("End Draw()")
}

func (g *Game) Layout(outsideWidth, outsideHeight int) (screenWidth, screenHeight int) {

	return outsideWidth, outsideHeight
}

func main() {
	ebiten.SetWindowSize(window_width, window_height)
	ebiten.SetWindowResizingMode(ebiten.WindowResizingModeEnabled)
	ebiten.SetWindowTitle("The Secret Of Sea")
	if err := ebiten.RunGame(&Game{}); err != nil {
		log.Fatal(err)
	}
}
