package org.hc.learning.template;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 标准单元测试模板
 */

/**
 * 需要注册的Bean的路径
 */
@ComponentScan(basePackages = {}, basePackageClasses = {})
/**
 * 启动profile
 */
@ActiveProfiles({"dev"})
/**
 * 填写配置类
 */
@ContextConfiguration(classes = {})
@RunWith(SpringRunner.class) // <==> @RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SpringBootStandardTemplateTest {



}
