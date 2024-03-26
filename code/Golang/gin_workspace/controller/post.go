package controller

import (
	"fmt"

	"github.com/gin-gonic/gin"
)

/* curl -d name=hugo -d message=hello -X POST -v "http://127.0.0.1:8080/api/v1/post/query?id=1&page=1" */
func Query(ctx *gin.Context) {
	id := ctx.Query("id")
	page := ctx.DefaultQuery("page", "0")
	name := ctx.PostForm("name")
	message := ctx.PostForm("message")

	fmt.Printf("id: %s; page: %s; name: %s; message: %s", id, page, name, message)
}
