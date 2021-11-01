[toc]

# 安装

- **参考**

  [windows环境搭建Vue开发环境](https://www.cnblogs.com/zhaomeizi/p/8483597.html)

# 常用命令

- **npm run dev**	本地运行
- **npm run build**	构建

# 名词

- **vetur** `visual code`下的`vue`插件
- **npm**	`nodejs`的包管理工具，类似`pip`(`python`的包管理工具)

# 点击事件

``` vue
<div id='element-girl' @click='say('hello')'>
    我是一个嫡女div
</div>
/* 或者 */
<div id='element-boy' v-on:click='say('hello')'>
    我是一个嫡子
</div>
```

