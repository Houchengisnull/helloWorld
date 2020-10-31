[toc]

# Java基础

## 实现深克隆

- 继承`Cloneable`接口
- 重写`clone()`
- 在`clone()`中调用`super.clone()`

> 在实现深克隆重写`clone()`方法时，对引用数据类型重复以上步骤，并调用该成员变量的`clone()`

``` java
    @Override
    public Object clone() throws CloneNotSupportedException {
        // TODO Auto-generated method stub
        //注意以下代码
        Teacher teacher = (Teacher)super.clone();
        teacher.setStudent((Student)teacher.getStudent().clone());
        return teacher;
    }
```

- 参考

https://www.cnblogs.com/liqiangchn/p/9465186.html

https://blog.csdn.net/lovezhaohaimig/java/article/details/80372233s

## List、Set、Map相同、区别及应用场景

### 区别
- List与Set继承自Collection接口，而Map未继承Collection接口
- List允许元素不唯一，常用于顺序、元素唯一场景
- Set不允许存储重复元素，常用于元素唯一场景
- Map使用键值对存储，`Key`不可重复，`Value`可重复

# 并发

## sleep与wait方法不同

- `wait()`属于`class Object`方法 ，`sleep()`属于`class Thread`方法；
- `wait()`将释放锁，`sleep()`将持有锁；
- `wait()`通常用于线程间交互，`sleep()`通常用于暂停执行；

## Synchronized与Lock区别

- `synchronized`是`java`关键字，`Lock`是`java`接口；
- `synchronized`不需要用户手动释放锁，`Lock`需要用户手动释放锁，否则可能导致死锁现象；
- `synchronized`是不可中断锁，`Lock`是可中断锁；
- `synchronized`未非公平锁，`Lock`默认情况下为非公平锁，但可设置公平锁；
- `Lock`可返回是否获得锁；
- `Lock`可设置等待锁时间；

# 并发容器

## ConcurrentHashMap

- **private transient volatile int sizeCtl**

  | value    | mean                         |
  | -------- | ---------------------------- |
  | -1       | 正在初始化                   |
  | 0        | table还未被初始化            |
  | -N       | N-1个线程正在进行扩容        |
  | 其他正数 | 初始化或下一次进行扩容的大小 |

- 数据结构

  `Node`是存储结构的基本单元。继承`HashMap`中的`Entry`，用于存储数据。

  `TreeNode`继承`Node`，但是数据结构换成了二叉树结构，是红黑树的存储结构，用于红黑树中存储数据。

  `TreeBin`是封装`TreeNode`的容器，提供转换红黑树的一些条件和锁的控制。

- 存储对象

  1. 如果没有初始化，就调用`initTable()`初始化。
  2. 如果没有`hash`冲突就直接`CAS`无锁插入。
  3. 如果需要扩容就先进行扩容。
  4. 如果存在`hash`冲突，就加锁来保证线程安全，两种情况：一种是链表形式就直接便利到尾端插入，一种是红黑树就按照红黑树结构插入。
  5. 如果该链表的数量大于阈值8，就要先转换成红黑树的结构，break再一次进入循环。
  6. 如果添加成功就调用`addCount()`方法统计`size`，并且检查是否需要扩容。

- 扩容方法**transfer()**

  默认容量16，扩容时，容量变为原来的两倍。

  > **helpTransfer()**
  >
  > 调用多个工作线程一起帮助进行扩容，这样的效率会更高。

- 获取对象时

  1. 计算`hash`值
  2. 如果遇到扩容时，会调用标记正在扩容结点`ForwardingNode.find()`，查找该节点，匹配就返回
  3. 以上都不符合则往下便利，匹配则返回，否则最后返回空。



## `HashMap`和`Hashtable`区别

- `HashMap`是线程不安全的，`HashTable`是线程安全的。

- 由于线程安全，`HashTable`效率更低。

- `HashMap`最多只允许一条记录的键为空，允许多条记录的值为空；而`HashTable`不允许。

- `HashMap`默认初始化数组的大小为16，`HashTable`为11。前者扩容时，扩大两倍，后者扩大两倍+1。

- `HashMap`需要重新计算`hash`值。而`HashTable`直接使用对象的`hashCode`。

  

## `HashMap`和`ConcurrentHashMap`区别

- `ConcurrentHashMap`不允许空键空值，`HashMap`允许空键空值。
- 红黑树的结点类不同，`HashMap`的红黑树结点是`LinkedHashMap.Entry`的子类，而`ConcurrentHashMap`的`TreeNode`是`Node`的子类。



## `ConcurrentHashMap`和`Hashtable`区别

- `HashTable`使用`synchronized`关键字加锁，效率较低。
- `ConcurrentHashMap`在`JDK1.7`中采用分段锁的方式（继承`ReentrantLock`的`Segment`），在`JDK1.8`中直接采用了`CAS + synchronized`，也采用了分段锁的方式并大大缩小了锁的粒度。



## 为什么`ConcurrentHashMap`比`Hashtable`效率要高

- `HashMap`使用一把锁锁住整个链表结构处理并发问题，多个线程竞争一把锁易阻塞。

- `JDK1.7`中使用分段锁，相当于把一个`HashMap`非常多段，每段分配一把锁，这样支持多线程访问。锁粒度：基于`Segment`

- `JDK1.8`中使用了`CAS + synchronized + Node + RedBlackTree`。锁粒度：`Node`。

  > `Node`继承`HashMap.Entry<K, V>`



## `ConcurrentHashMap`锁机制分析(1.7&1.8)

- `JDK1.7`中采用分段锁，实现并发的更新操作，底层采用`数组 + 链表`的存储结构。包括两个核心静态内部类`Segment`和`HashEntry`

- `JDK1.8`中采用了`Node + CAS + synchronized`保证并发安全。取消`Segment`，直接采用`table`数组存储键值对。

  当结点的`size`超过`TREEIFY_THRESHOLD`时，链表转换成红黑树。



## `ConcurrentHashMap`在1.8中为什么要使用内置锁`synchronized`来代替`ReentrantLock`

- `JVM`开发团队在`JDK1.8`中对`synchronized`做了大量性能上的优化，而且基于`JVM`的`synchonized`优化空间更大与自然。

- 在大量的数据操作下，对于`JVM`的内存压力，基于`API`的`ReentrantLock`会开销更多内存。

  

## `ConcurrentHashMap`的并发度是什么

在`JDK1.7`中程序运行时能够同时更新`ConcurrentHashMap`且不产生锁竞争的最大线程数。默认16，且可以在构造函数中设置。当用户设置并发度时，`ConcurrentHashMap`会使用大于等于该值的最小2幂指数作为实际并发度。

在`1.8`中仅仅当设置的初始容量小于并发度，将初始容量提升至并发度大小。



# Spring

## Bean生命流程

## `BeanFactory`与`ApplicationContext`区别

## `xml`配置中`ref bean`与`ref local`区别

- `ref bean`寻找所有XML配置文件中的`bean`
- `local bean`则利用`xml解析器`的能力在***同一个xml配置文件***中寻找`bean`，若没有匹配元素则会配出error

## 常用注解

- 声明`Bean`

  | name       | explain    |
  | ---------- | ---------- |
  | @Component | 无明确角色 |
  | @Service | 业务层 |
  | @Repository | 数据访问层 |
  | @Controller | 控制层 |
  


- 注入`Bean`

  | name          | explain |
  | ------------- | ------- |
  | `@Autowired ` |         |
  | `@Inject`     |         |
  | `@Resource`   |         |

- 配置注解

  | name             | explain                                        |
  | ---------------- | ---------------------------------------------- |
  | `@Configuration` | 等价于XML配置中`beans`标签                     |
  | `@Bean`          | 等价于XML配置Bean，将方法返回值注入给`IOC容器` |

  

## `SpringMVC`

### 什么是`SpringMVC`

`SpringMVC`是`spring`一个模块，基于`MVC`的一个框架。

### `SpringMVC`工作原理

- 用户发送请求至前端控制器 `DispatcherServlet`。 

- `DispatcherServlet`收到请求调用`HandlerMapping`处理器映射器。

- `HandlerMapping`找到具体的处理器(可以根据xml 配置、注解进行查找)，生成处理器对象

及处理器拦截器(如果有则生成)一并返回给 DispatcherServlet。 

- `DispatcherServlet`调用 `HandlerAdapter `处理器适配器。

- `HandlerAdapter` 经过适配调用具体的处理器(Controller，也叫后端控制器)。 

- `Controller` 执行完成返回 `ModelAndView`。
- `HandlerAdapter` 将 controller 执行结果`ModelAndView` 返回给`DispatcherServlet`。 

- `DispatcherServlet`将`ModelAndView`传给`ViewReslover` 视图解析器。

- `ViewReslover`解析后返回具体`View`。

- `DispatcherServlet`根据`View`进行渲染视图（即将模型数据填充至视图中）。

- `DispatcherServlet`响应用户。

### SpringMVC作用

- 解耦

### SpringMVC背景

早期`Java Web` 开发中，将显示层、控制层、数据层的操作全部交给`JSP`或者`Java Bean`。

结果代码出现严重耦合——JSP与Java Bean、Java代码和前端代码耦合在了一起。

导致一下问题：

  - 前后端相互依赖
  - 代码难以复用

于是这种方式被`servlet`+`jsp`+`java bean`替代了。这也是最早的`MVC模型`。

### 核心组件

#### 视图解析器 `ViewResolver`

- xml配置

  ``` xml
  <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
  
  <property name="viewClass"
  
  value="org.springframework.web.servlet.view.JstlView" />
  
  <property name="prefix" value="/WEB-INF/jsp/" />
  
  <property name="suffix" value=".jsp" />
  
  </bean>
  ```

个人理解，将（`Controller`/`Handler`/`HandlerAdapter`返回的`ModelAndView`中的）`View`配上`prefiex`属性和`suffix`属性以找到相应视图。

#### 处理映射器 HandlerMapping

- 配置

  ``` xml
  <bean id="handlerMapping" class="org.springframework.web.servlet.handler.SimpleUrlHandlerMapping">
      <property name="mappings">
          <props>
              <prop key="/toHello.do">helloController</prop>
          </props>
      </property>
  </bean>
  
  <!-- 配置控制器 -->
  <bean id="helloController" class="com.xcz.controller.ToHelloController"></bean>
  ```

#### DispatcherServlet

``` java
//前端控制器分派方法  
protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {  
        HttpServletRequest processedRequest = request;  
        HandlerExecutionChain mappedHandler = null;  
        int interceptorIndex = -1;
        try {  
            ModelAndView mv;  
            boolean errorView = false;    
            try {  
        //检查是否是请求是否是multipart（如文件上传），如果是将通过MultipartResolver解析  
                processedRequest = checkMultipart(request);  
        //步骤2、请求到处理器（页面控制器）的映射，通过HandlerMapping进行映射  
                mappedHandler = getHandler(processedRequest, false);  
                if (mappedHandler == null || mappedHandler.getHandler() == null) {  
                    noHandlerFound(processedRequest, response);  
                    return;  
                }  
 		//步骤3、处理器适配，即将我们的处理器包装成相应的适配器（从而支持多种类型的处理器）  
                HandlerAdapter ha = getHandlerAdapter(mappedHandler.getHandler());    
                // 304 Not Modified缓存支持  
                // 此处省略具体代码    
                // 执行处理器相关的拦截器的预处理（HandlerInterceptor.preHandle）  
                // 此处省略具体代码    
                // 步骤4、由适配器执行处理器（调用处理器相应功能处理方法）  
                mv = ha.handle(processedRequest, response, mappedHandler.getHandler());    
                // Do we need view name translation?  
                if (mv != null && !mv.hasView()) {  
                    mv.setViewName(getDefaultViewName(request));  
                }    
                // 执行处理器相关的拦截器的后处理（HandlerInterceptor.postHandle）  
                //此处省略具体代码  
            }  
            catch (ModelAndViewDefiningException ex) {  
                logger.debug("ModelAndViewDefiningException encountered", ex);  
                mv = ex.getModelAndView();  
            }  
            catch (Exception ex) {  
                Object handler = (mappedHandler != null ? mappedHandler.getHandler() : null);  
                mv = processHandlerException(processedRequest, response, handler, ex);  
                errorView = (mv != null);  
            }    
				//步骤5 步骤6、解析视图并进行视图的渲染  
				//步骤5 由ViewResolver解析View（viewResolver.resolveViewName(viewName, locale)）  
            	//步骤6 视图在渲染时会把Model传入
			（view.render(mv.getModelInternal(), request, response);）  
            if (mv != null && !mv.wasCleared()) {  
                render(mv, processedRequest, response);  
                if (errorView) {  
                    WebUtils.clearErrorRequestAttributes(request);  
                }  
            }  
            else {  
                if (logger.isDebugEnabled()) {  
                    logger.debug("Null ModelAndView returned to DispatcherServlet with name '" + getServletName() +  
                            "': assuming HandlerAdapter completed request handling");  
                }  
            }    
            // 执行处理器相关的拦截器的完成后处理（HandlerInterceptor.afterCompletion）  
            // 此处省略具体代码    
        catch (Exception ex) {  
            // Trigger after-completion for thrown exception.  
            triggerAfterCompletion(mappedHandler, interceptorIndex, processedRequest, response, ex)            throw ex;  
        }  
        catch (Error err) {  
            ServletException ex = new NestedServletException("Handler processing failed", err);  
            // Trigger after-completion for thrown exception.  
            triggerAfterCompletion(mappedHandler, interceptorIndex, processedRequest, response, ex);  
            throw ex;  
        }    
        finally {  
            // Clean up any resources used by a multipart request.  
            if (processedRequest != request) {
                cleanupMultipart(processedRequest);  
            }  
        }  
    }  
```

## 事务

### Spring事务实现方式

#### 编程式事务管理

通过`TransactionTemplate`或者`TransactionManager`手动管理事务。

##### `TransactionTemplate`

``` java
@Autowired
private TransactionTemplate transactionTemplate;

public void transactionTemplate() {
    transactionTemplate.execute(new TransactionCallbackWithoutResult(){
       @Override
        protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
            try {
                // ...业务代码
            } catch (Exeception e) {
                // rollback
                transactionStatus.setRollbackOnly();
            }
        }
    });
}
```

##### `TransactionManager`

``` java
@Autowired
private PlatformTransactionManager transactionManager;

public void testTransaction() {

  TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
          try {
               // ....  业务代码
              transactionManager.commit(status);
          } catch (Exception e) {
              transactionManager.rollback(status);
          }
}
```

#### 声明式事务管理

- 对比编程式事务管理优点

  代码侵入量小

##### 实现原理

`@Transactional`工作机制基于`AOP`实现，`AOP`基于动态代理实现。

- 如果目标对象实现了接口，默认情况下采用`JDK`动态代理

- 如果目标对象没有实现接口，会使用`CGLIB`动态代理

##### 作用范围

- 方法

  推荐将注解应用在方法上，**但仅对public方法生效**

- 类

  表明该注解对该类中的所有public方法生效

- 接口

  不推荐在接口上使用

###### Spring AOP自调用问题

若同一类中的未被@Transactional注解声明方法调用被@Transactional注解声明方法，则被@Transactional注解声明方法事务将失效。

这是因为`Spring AOP`代理原因造成的。只有当`@Transactional`注解被类以外方法调用时，Spring事务管理才生效。

> 解决方法——避免同类自调用或使用`AspectJ`取代`Spring AOP`代理。

### 事务属性

#### 隔离级别 Isolation

Spring将隔离级别定义了一个枚举类`public enum Isolation`,一共5个隔离级别。

| 隔离级别         | 解释                                                         |
| ---------------- | ------------------------------------------------------------ |
| DEFAULT          | 使用数据库默认隔离级别，`MySQL`默认采用`REPEATABLE_READ`，`Oracle`默认采用`READ_COMMITED` |
| READ_UNCOMMITTED | 允许读取尚未提交的数据变更，可能导致**脏读、幻读或不可重复读** |
| READ_COMMITTED   | 允许读取并发事务已经提交的数据，**可以阻止脏读，但是幻读或不可重复读仍有可能发生** |
| REPEATABLE_READ  | 对同一字段的多次读取结果都是一致的，除非数据是被本身事务自己所修改，**可以阻止脏读和不可重复读，但幻读仍有可能发生。** |
| `SERIALIZABLE`   | 最高的隔离级别，完全服从 ACID 的隔离级别。所有的事务依次逐个执行，这样事务之间就完全不可能产生干扰，也就是说，**该级别可以防止脏读、不可重复读以及幻读**。但是这将严重影响程序的性能。通常情况下也不会用到该级别。 |

> 事务隔离主要预防并发情况下的数据安全问题。

#### 传播行为 Propagation

Spring将传播行为定义了一个枚举类`public enum Propagation`，一共定义了7种事务传播类型。

| name          | explain                                                      |
| ------------- | ------------------------------------------------------------ |
| REQUIRED      | @Transactional默认事务传播行为，如果当前存在事务，则加入该事务；如果没有，则创建一个新的事务。 |
| REQUIRES_NEW  | 创建一个新的事务，如果当前存在事务，则把当前事务挂起。       |
| NESTED        | 如果当前存在事务，则创建一个事务作为当前事务的嵌套事务来运行；如果当前没有事务，则该取值等价于`TransactionDefinition.PROPAGATION_REQUIRED`。 |
| MANDATORY     | 如果当前存在事务，则加入该事务；如果当前没有事务，则抛出异常。（mandatory：强制性） |
| SUPPORTS      | 支持当前事务，如果当前没有事务，则以非事务方式执行。         |
| NEVER         | 以非事务方式执行，如果当前存在事务，则抛出异常。             |
| NOT_SUPPORTED | 以非事务方式执行，如果当前存在事务，则将当前事务挂起。       |

- NESTED
  1. 在外部方法未开启事务的情况下`Propagation.NESTED`和`Propagation.REQUIRED`作用相同，修饰的内部方法都会新开启自己的事务，且开启的事务相互独立，互不干扰。
  2. **如果外部方法开启事务的话，`Propagation.NESTED`修饰的内部方法属于外部事务的子事务，外部主事务回滚的话，子事务也会回滚，而内部子事务可以单独回滚而不影响外部主事务和其他子事务。**

#### 回滚规则 `rollbackFor`

默认情况下，事务只有遇到运行期异常（`RuntimeException` 及其子类）时才会回滚，Error 也会导致事务回滚，但是，在遇到检查型（Checked）异常时不会回滚。

如果你想要回滚你定义的特定的异常类型的话，可以这样：

```java
@Transactional(rollbackFor= MyException.class)
```

#### 是否只读 `isReadOnly`

对于只有读取数据查询的事务，可以指定事务类型为`readonly`，即只读事务。只读事务不涉及数据的修改，数据库会提供一些优化手段，适合用在有多条数据库查询操作的方法中。

> `MySQL` 默认对每一个新建立的连接都启用了`autocommit`模式。在该模式下，每一个发送到 `MySQL` 服务器的`sql`语句都会在一个单独的事务中进行处理，执行结束后会自动提交事务，并开启一个新的事务。

但是，如果你给方法加上了`Transactional`注解的话，这个方法执行的所有`sql`会被放在一个事务中。如果声明了只读事务的话，数据库就会去优化它的执行，并不会带来其他的什么收益。

如果不加`Transactional`，每条`sql`会开启一个单独的事务，中间被其它事务改了数据，都会实时读取到最新值。

- 应用
  - 如果你一次执行单条查询语句，则没有必要启用事务支持，数据库默认支持 `SQL` 执行期间的读一致性；
  - 如果你一次执行多条查询语句，例如统计查询，报表查询，在这种场景下，多条查询 `SQL` 必须保证整体的读一致性，否则，在前条 `SQL` 查询之后，后条 `SQL` 查询之前，数据被其他用户改变，则该次整体的统计查询将会出现读数据不一致的状态，此时，应该启用事务支持

#### 事务超时

所谓事务超时，就是指一个事务所允许执行的最长时间，如果超过该时间限制但事务还没有完成，则自动回滚事务。在 `TransactionDefinition` 中以 int 的值来表示超时时间，其单位是秒，默认值为-1。