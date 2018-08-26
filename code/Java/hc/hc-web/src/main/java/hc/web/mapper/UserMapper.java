package hc.web.mapper;

import java.util.List;

//import org.apache.ibatis.annotations.Select;

import hc.web.bean.User;

public interface UserMapper {
	
//	@Select("SELECT * FROM user")
	List<User> getUsers();
	
}
