[toc]

> 有时候`IDE`的使用比语言、算法的学习还麻烦。

# .Run

用`vscode`写`C/C++`，直接运行[`Ctrl + Alt + N`]编译生成的`*.exe`总是生成在源文件目录下，即使配置了`tasks.json`来构建。

> 说实话这两处`直接运行`的结果与`build`[`Ctrl + Shift + B`]结果不同真的很奇怪耶。
>
> 只是作为编辑器能理解，但`Microsoft`的雄心就真的这么小？
>
> 其次，`tasks.json`与`settings.json`内置变量符号不同也造成了不少的困扰，比如：
>
> 在`tasks.json`中，`workspace`的根目录——`${workspaceFolder}`
>
> 在`settings.json`中，workspace的根目录——`${workspaceRoot}`

所以需要配置`settings.json`。

``` json
{
    "files.associations": {
        "iostream": "cpp"
    }
    , "code-runner.executorMap" : {
        "cpp": "cd $dir && g++ $fileName -o $workspaceRoot\\bin\\$fileNameWithoutExt.exe && $dir\\$fileNameWithoutExt.exe"
    }
}
```

`code-runner`应该是一个`vscode`插件，我在大部分的用`vscode`配置`C/C++`中都未见提及。那么变量格式的不同也能理解了。接下来即使不用`CMake`，也能根据我们的要求简单的构建`C/C++ project`了。

> 大概是有强迫症，所以对这个点看了很久。真的是很折腾。

- 参考

  <a href='https://blog.csdn.net/junqing_wu/article/details/103546663'>vscode code runner配置</a>

## CodeRunner

### 自定义参数

| Supported customized parameters | Description                    |
| ------------------------------- | ------------------------------ |
| $workspaceRoot                  | 当前工程目录路径               |
| $dir                            | 运行代码所在目录               |
| $dirWithoutTrailingSlash        | 运行代码所在目录，不带尾后斜杠 |
| $fullFileName                   | 运行代码文件全路径             |
| $fileName                       | 运行代码文件名称               |
| $fileNameWithoutExt             | 运行代码文件名称，不带后缀名   |
| $driveLetter                    | 运行代码所在盘符               |
| $pythonPath                     | Python解释器路径               |



# variables reference

## Predefined variables 内置变量

> 继续吐槽：在`tasks.json`中能使用的变量在`settings.json`中就使用不了。

> 作为`vscode`使用不多的受众，这些复杂的内置变量显得很不友好。在使用`vscode`学习`C/C++`，设置`vscode`的时间比学习语言本身的时间还要多。
>
> 这让我下载的那一堆插件显得很鸡肋。

- **如何得知真实的变量**

  ``` json
  {
    "version": "2.0.0",
    "tasks": [
      {
        "label": "echo",
        "type": "shell",
        "command": "echo ${workspaceFolder}"
      }
    ]
  }
  ```

  1. 将以上代码放在`tasks.json`中;
  2. 再执行`Terminal > Run Task`;
  3. 选中我们需要执行的`Task`(此处为`Echo`);

  > PS:还是很不直观，我只是想查看一个变量而已。

- **参考**
- <a href='https://code.visualstudio.com/docs/editor/variables-reference'>Variables Reference</a>

# launch.json

用于`debug`的配置文件。比如指定调试语言环境，调试类型。

## configuration



必填的三个属性`type`，`request`，`name`。

- **type**	编程环境

  比如`java`，`php`

# tasks.json

用于构建的配置文件。