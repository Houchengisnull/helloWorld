package main

import (
	"gin.com/controller"
	"github.com/gin-gonic/gin"
)

func main() {
	router := Router()
	router.Run(":8080")
}

func Router() *gin.Engine {
	router := gin.Default()

	api := router.Group("/api")
	v1 := api.Group("v1")

	post := v1.Group("/post")
	post.POST("/query", controller.Query)

	get := v1.Group("/get")
	get.GET("/someJSON", controller.SomeJSON)

	return router
}
