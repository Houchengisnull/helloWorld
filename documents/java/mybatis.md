[toc]

# Bind

- **参考**
- [MyBatis动态sql之bind标签](http://c.biancheng.net/view/4382.html)

``` xml
<!--使用bind元素进行模糊查询-->
<select id="selectUserByBind" resultType="com.po.MyUser" parameterType= "com.po.MyUser">
    <!-- bind 中的 uname 是 com.po.MyUser 的属性名-->
    <bind name="paran_uname" value="'%' + uname + '%'"/>
        select * from user where uname like #{paran_uname}
</select>
```

