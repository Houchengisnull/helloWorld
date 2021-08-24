[toc]



# CRON表达式

- **参考**
- [spring定时任务详解（@Scheduled注解）](https://www.bbsmax.com/A/6pdDylDzw3/)
- [spring-schedule框架实现定时任务](https://blog.csdn.net/qq_37585236/article/details/81841235)

例如：`0 0 0 * * ?`，表示每天零点执行一次。

在表达式中，顺序依次为秒、分、时、日期、月、周、年(非必须):

| 字段名     | 允许的值      | 允许的特殊字符   |
| ---------- | ------------- | ---------------- |
| 秒         | 0-59          | ， - * /         |
| 分         | 0-59          | ， - * /         |
| 小时       | 0-23          | ， - * /         |
| Date       | 1-31          | ， - * ？/ L W C |
| Month      | 1-12或Jan-Dec | ， - * /         |
| 周内日期   | 1-7或SUN-SAT  | ， - * ？/ L C # |
| 年(非必须) | 空或1970-2099 | ， - * /         |

特殊字符含义：

| 特殊字符 | 意义                                                         |
| -------- | ------------------------------------------------------------ |
| *        | 所有                                                         |
| ?        | 非明确的值，仅在日期和星期域内使用                           |
| -        | 范围，例如：10-12                                            |
| ,        | 可选值，例如：周一，周三，周五                               |
| /        | 指定增量，例如：0/15，表示从0开始，每过15个单位              |
| L        | 最后的，允许在日期域或周域出现，如果是周域6L，表示当月最后的周五 |
| W        | 最近的工作日，例如：15W，表示15号最近个工作日                |
| LW       | 一个月最后的工作日                                           |
| #        | 只允许在周域中出现，例如：6#3，表示当月第三周的周五          |
| C        | 允许在日期域或周域出现，依靠指定的日历，在周域中1C表示周日后的第一天 |



# SpringBoot集成

## 代码

``` java
@Configuration
@ComponentScan("org.hc.learning.spring.scheduled")
@EnableScheduling
public class TaskSchedulerConfig {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TaskSchedulerConfig.class);
		context.close();
	}
}
```

# FAQ

## [springboot使用定时注解 启动报错 ：Bean named 'defaultSockJsTaskScheduler' is expected to be of type](https://my.oschina.net/sangma/blog/4332297)

- **原因**

  不能同时使用`@EnableScheduling`与`@EnableWebSocket`。

- **解决方法**

  创建一个`TaskScheduler`的实例以避免冲突。

  ``` java
  @Configuration
  public class ScheduledConfig {
  
  	@Bean
  	public TaskScheduler taskScheduler() {
  		ThreadPoolTaskScheduler scheduling = new ThreadPoolTaskScheduler();
  		scheduling.setPoolSize(10);
  		scheduling.initialize();
  		return scheduling;
  	}
  }
  ```

  

