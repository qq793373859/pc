package springboot.service.admin.user.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springboot.bean.admin.user.Permission;
import springboot.bean.admin.user.UserRole;
import springboot.mapper.admin.user.PermissionMapper;
import springboot.mapper.admin.user.UserRoleMapper;
import springboot.service.admin.user.PermissionService;
import springboot.service.admin.user.UserRoleService;
@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService{

	@Autowired
	private UserRoleMapper mapper;

	@Override
	public void insert(UserRole role) {
		mapper.insert(role);
		
	}
	@Override
	public List<UserRole> getUserRoles(UserRole role) {
		return mapper.select(role);
	}
	@Override
	public void delete(UserRole role) {
		mapper.delete(role);
	}
	

	

}
