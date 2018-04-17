package springboot.service.admin.user.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springboot.bean.admin.user.User;
import springboot.mapper.admin.user.UserMapper;
import springboot.service.admin.user.UserService;
@Service
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private UserMapper mapper;
	
	public User getUser(int id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public List<User> getUserAll() {
		return mapper.selectAll();
	}

	@Override
	public void insert(User user) {
		mapper.insert(user);
	}

	@Override
	public List<User> getUserByUser(User user) {
		return mapper.select(user);
	}
}
