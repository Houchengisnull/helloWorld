[toc]

# 基础

- SqlSessionFactoryBuilder

- SqlSessionFactory

  创建SqlSession，工厂单例模式，存在于程序的整个生命周期。

- SqlSession

  代表一次数据库连接，一般通过调用Mapper访问数据库，线程不安全，要保证线程独享。

- SQL Mapper

## ResultType

要求select检索出的数据集合的字段名，必须与Java的实体类一一对应。如果需要将某几列的数据作为Java类的某个字段（List、Set......），那么可以使用ResultMap。

## Insert

### SelectKey

在插入元素时，可以使用SelectKey元素将数据插入数据库后，将Id回写到实体类。

``` xml
<!-- MySQL -->
<selectKey keyProperty="id" order="after" resultType="int">
    select LAST_INSERT_ID()
</selectKey>

<!-- ORACLE -->
<selectKey keyProperty="id" order="before" resultType="int">
    select SEQ_ID.nextval from dual
</selectKey>
```

# 代码生成器

MyBatis Generator是MyBatis团队提供的代码生成器。

运行方式：

- mvn mybatis-generator:generate

- Java代码

  ``` java
  @Test
  public void generateTest()
  {
      List<String> warings = new ArrayList<String>(); // 警告信息
      boolean overwrite = true;
      String genCfg = "generateConfig.xml";
      File configFile = new File(getClass().getClassLoader().getResource(genCfg).getFile());
      ConfigurationParser cp = new ConfigurationParser(warnings);
      Configuration config = null;
      try {
          config = cp.parseConfiguration(configFile); // 读取配置信息
      } catch(IOException e) {
          e.printStackTrace();
      } catch(XMLParserException e) {
          e.printStackTrace();
      }
      DefaultShellCallback callback = new DefaultShellCallback(overwrite);
      MyBatisGenerator myBatisGenerator = null;
      
      try {
          myBatisGenerator = new MyBatisGenerator(config, callback, warnings);//实例化生成器 
      } catch(InvalidConfigurationException e){
          e.printStackTrace();
      }
      try {
          myBatisGenerator.generate(null);
      } catch (SQLException e) {
          e.printStackTrace(); 
      } catch (IOException e) {
          e.printStackTrace(); 
      } catch (InterruptedException e) {
          e.printStackTrace(); 
      }
  }
  ```

- cmd

  ``` shell
  java -Dfile.encoding=UTF-8 -jar mybatis-generator-core-1.3.5.jar -configfile generatorConfig.xml -overwrite
  ```

# 缓存

## 一级缓存

一级缓存默认开启，想要关闭一级缓存可以在select标签上配置flushCache='true'。

一级缓存存在于SqlSession的生命周期中，在同一个SqlSession中查询时，MyBatis会把执行的方法和参数通过算法生成键值，将键值和查询结果存入一个Map对象中。

- 不会出现脏读
- 任何insert\update\delete操作都会清空一级缓存

## 二级缓存

二级缓存又称为应用缓存。存在于SqlSessionFactory的生命周期中，可以理解为跨SqlSession。

二级缓存以namespace为单位。不同的namespace操作互不影响。在MyBatis的核心配置文件中cacheEnabled参数是二级缓存的全局开关，默认为true。

- 开启二级缓存易出现脏读
- 映射语句文件中所有select会缓存
- 映射语句文件中所有的insert\update\delete会刷新缓存
- 按照LRU算法回收（最近最少使用）
- 缓存会存储列表集合或对象的512引用
- 缓存会被视为read/write的（可读/可写）

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

