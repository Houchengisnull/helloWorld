package hc.web.test;


import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import hc.web.Application;
import hc.web.bean.User;
import hc.web.mapper.UserMapper;

/**
 * 单元测试
 * @author Administrator
 *
 */
@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class UnitTesting {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private ConfigurableApplicationContext context;

	@Test
	public void test() throws Exception {
		
		List<User> users = userMapper.getUsers();
		users.forEach( (user)->{
			System.out.println(user.getUsername());
		});
		
		context.close();
		/*String[] names  = context.getBeanDefinitionNames();
		for( String name : names ){
			System.out.println( name );
		}*/
	}
}
