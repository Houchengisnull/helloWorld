[toc]

# Usage

## Install

- **参考**

  [windows环境搭建Vue开发环境](https://www.cnblogs.com/zhaomeizi/p/8483597.html)

``` shell
npm install vue -g
npm install vue-cli -g
```

## Init

``` shell
npm creaete vue@latest
vue init webpack-simple $project
```

## Export

### Component

在导出组件时，必须使用`export default`，否则会报错：

``` text
Cannot read property '__vccOpts' of undefined
TypeError: Cannot read property '__vccOpts' of undefined
    at exports.default (webpack-internal:///./node_modules/vue-loader/dist/exportHelper.js:9:22)
    at eval (webpack-internal:///./src/components/rentalApp.vue:13:123)
    at Module../src/components/rentalApp.vue (http://localhost:8080/js/app.js:129:1)
    at __webpack_require__ (http://localhost:8080/js/app.js:301:32)
    at fn (http://localhost:8080/js/app.js:530:21)
    at eval (webpack-internal:///./node_modules/babel-loader/lib/index.js??clonedRuleSet-40.use[0]!./node_modules/vue-loader/dist/index.js??ruleSet[0].use[0]!./src/App.vue?vue&type=script&lang=js:2:83)
    at Module../node_modules/babel-loader/lib/index.js??clonedRuleSet-40.use[0]!./node_modules/vue-loader/dist/index.js??ruleSet[0].use[0]!./src/App.vue?vue&type=script&lang=js (http://localhost:8080/js/app.js:19:1)
    at __webpack_require__ (http://localhost:8080/js/app.js:301:32)
    at fn (http://localhost:8080/js/app.js:530:21)
    at eval (webpack-internal:///./src/App.vue?vue&type=script&lang=js:5:206)
```

查询了很多资料，并没有提到组件的导出必须使用`export default`，但所有的vue开发者似乎都默认了这件事。

## Router

### Install

``` shell
npm install vue-router --save
```

### Usage

- **RouterLink**

  与<a></a>不同的是：<RouterLink>能够在不触发加载页面的情况下更新URL。

- **RouterView**

- **$route**

  可以在组件模板中使用 `$route` 来访问当前的路由对象。

### Method

- **createRouter()**



# Command

- **npm run dev**	本地运行
- **npm run build**	构建

# FAQ

## component has been registered but not used

- 打开package.json

- 增加规则

  ``` json
  "eslintConfig": {
    "rules": {
      "vue/no-unused-components": "off"
    }
  }
  ```

  