package hc.web.bean;

public class User {
	
	private int id;
	private String username;
	private String usernum;
	private String userpassword;
	private int salary;
	private int realSalary;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsernum() {
		return usernum;
	}
	public void setUsernum(String usernum) {
		this.usernum = usernum;
	}
	public String getUserpassword() {
		return userpassword;
	}
	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	public int getRealSalary() {
		return realSalary;
	}
	public void setRealSalary(int realSalary) {
		this.realSalary = realSalary;
	}

}
