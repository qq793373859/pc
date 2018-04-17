package springboot.service.admin.user;

import java.util.List;

import springboot.bean.admin.user.User;

public interface UserService{
	public User getUser(int i);
	
	public List<User> getUserAll();
	
	public void insert(User user);
	
	public List<User> getUserByUser(User user);

}
