package controller

import (
	"net/http"

	"github.com/gin-gonic/gin"
)

func SomeJSON(c *gin.Context) {
	names := []string{"lena", "austin", "foo"}

	// 将输出：while(1);["lena","austin","foo"]
	c.SecureJSON(http.StatusOK, names)
}
