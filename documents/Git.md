

[TOC]

- **参考**
- [Git的诞生](https://www.liaoxuefeng.com/wiki/896043488029600/896202815778784)

- [Rebase](https://www.liaoxuefeng.com/wiki/896043488029600/1216289527823648)

- [团队开发中的 Git 实践]([团队开发中的 Git 实践 (qq.com)](https://mp.weixin.qq.com/s/1uAxTzqWvP0ccePf7c3Z2w))
- [Git 合并多次 commit 、 删除某次 commit](https://blog.csdn.net/al_assad/article/details/81145856)

# 安装Git Server

## Gitblit

- [在 Windows 上部署 gitblit](https://blog.csdn.net/longintchar/article/details/80787907)

# 分支模型 Git Flow

有个很成熟的叫 Git Flow 的分支模型，它能够应对 99% 的场景，剩下的那 1% 留给几乎不存在的极度变态的场景。

需要注意的是，**它只是一个模型，而不是一个工具；你可以用工具去应用这个模型，也可以用最朴实的命令行。所以，重要的是理解概念，不要执着于实行的手段。**

简单说来，Git Flow 就是给原本普普通通的分支赋予了不同的「职责」：

- **master**——最为稳定功能最为完整的随时可发布的代码；

- hotfix——修复线上代码的 bug；
- **develop**——永远是功能最新最全的分支；
- feature——某个功能点正在开发阶段；
- release——发布定期要上线的功能。

# git status

``` shell
$ git status
```

# git diff

查看本次修改的具体内容

``` shell
$ git diff
```

# git log

``` shell
$ git log
```

# stage

暂存区。

## 移除

- 已存在repository中

  ``` shell
  $ git reset HEAD #{filename}
  ```

- 不在repository中

  ``` shell
  $ git rm --cached #{filename}
  ```

## 添加

``` shell
$ git add #{filename}
```

当发现添加失败时，可以使用以下命令

``` shell
$ git add .
$ git add --all
```

# commit

- 提交时的粒度是一个小功能点或者一个 bug fix，这样进行恢复等的操作时能够将「误伤」减到最低；
- 用一句简练的话写在第一行，然后空一行稍微详细阐述该提交所增加或修改的地方；
- 不要每提交一次就推送一次，多积攒几个提交后一次性推送，这样可以避免在进行一次提交后发现代码中还有小错误。

假如已经把代码提交了，对这次提交的内容进行检查时发现里面有个变量单词拼错了或者其他失误，只要还没有推送到远程，就有一个不被他人发觉你的疏忽的补救方法——

首先，把失误修正之后提交，可以用与上次提交同样的信息。

然后，终端中执行命令 `git rebase -i [SHA]`，其中 SHA 是上一次提交之前的那次提交的，在这里是 `3b22372`。

最后，这样就将两次提交的节点合并成一个，甚至能够修改提交信息！

谁说历史不可篡改了？前提是，**想要合并的那几次提交还没有推送到远程！**

## 提交

``` shell
$ git commit -m "hello world"
```

# merge

在将其他分支的代码合并到当前分支时，如果那个分支是当前分支的父分支，为了保持图表的可读性和可追踪性，可以考虑用 `git rebase` 来代替 `git merge`；反过来或者不是父子关系的两个分支以及互相已经 `git merge` 过的分支，就不要采用 `git rebase` 了，避免出现重复的冲突和提交节点。

| `commands` | `descriptions` |
| ---------- | ------------ |
| pick           | 提交             |
| reword           | 提交,并编辑提交信息             |
| edit           | 提交,并停止修正             |
| squash           | 提交,并合并到过去提交             |
| fixup           | 提交,并合并到过去提交，且撤回本次提交信息             |
| drop           | 移除本次提交             |

# rebase

## 通过Rebase合并

- 对最近4个`commit`合并

  `git rebase -i HEAD~4`

## 撤消Rebase

`git rebase --abort`

# stash

- **stash**	备份当前工作区到Git栈。

## 用法一 备份本地修改

``` bash
git stash 
git pull
git stash pop # 从Git栈中取出上次修改内容
```

## 用法二 放弃本地修改

``` bash
git reset
git pull
```

# 仓库

## 添加远程仓库

```  java
# 添加远程仓库 origin 仓库名
git remote add origin ssh://software@172.16.0.30/~/houc/.git
# push 远程分支 master 分支名
git push origin master
```

# 回滚

## 回滚单个文件

- 查看日志

``` bash
git log #{filename}
git log -number #{filename}
```

- 根据日志信息选择回退版本

``` bash
git reset #{verison} #{filename}
```

# 保护分支

## Github

进入`仓库 - Settings - Branches - Add rule`。然后参考：

- [About protected branches](https://docs.github.com/en/repositories/configuring-branches-and-merges-in-your-repository/defining-the-mergeability-of-pull-requests/about-protected-branches)

### 测试一下

- 设置保护分支

设置完成后做一些小改动以推送。

目前保护分支的名称是`feature_branch_protection_rule`。

推送成功后怀疑是由于使用的是管理员账号，勾选`Include administrators`再测试。

奇怪了，居然又推送成功了。

将`Branch name pattern`修改成`*protection*`测试下。

- 删除分支时发现

  ``` txt
  C:\Users\admin\Desktop\helloWorld> git.exe -c "credential.helper=C:/Program\ Files/SmartGit/lib/credentials.cmd" push --porcelain --progress origin :refs/heads/feature_branch_protection_rule
  remote: error: GH006: Protected branch update failed for refs/heads/feature_branch_protection_rule.        
  remote: error: Cannot delete this protected branch        
  failed to push some refs to 'https://github.com/Houchengisnull/helloWorld.git'
  To https://github.com/Houchengisnull/helloWorld.git
  ! :refs/heads/feature_branch_protection_rule [remote rejected] (protected branch hook declined)
  Done
  ```

​	但是怎么禁止推送还是很迷惑...

- 设置`Require approvals`

  勾选`Require a pull request before merging`，再勾选`Require approvals`。

  ``` text
  remote: error: GH006: Protected branch update failed for refs/heads/feature_branch_protection_rule.        
  remote: error: At least 1 approving review is required by reviewers with write access.        
  ```

- 设置为`Dismiss stale pull request approvals when new commits are pushed`

  勾选`Require a pull request before merging`，再勾选`Dismiss stale pull request approvals when new commits are pushed`。

  ``` text 
  remote: error: GH006: Protected branch update failed for refs/heads/feature_branch_protection_rule.        
  remote: error: Changes must be made through a pull request. 
  ```



# FAQ

## Couldn't find remote ref refs/heads/xxx [core]

- [Fix fatal: Couldn't find remote ref refs/heads/xxx [core] fatal: The remote end hung up unexpectedly](https://blog.csdn.net/Tyro_java/article/details/76064584)