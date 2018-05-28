package springboot.service.admin.user.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springboot.bean.admin.user.Role;
import springboot.mapper.admin.user.RoleMapper;
import springboot.service.admin.user.RoleService;
@Service
@Transactional
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleMapper mapper;
	
	public Role getRoleById(String id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public void insert(Role role) {
		mapper.insert(role);
	}

	@Override
	public List<Role> getRoleByRole(Role role) {
		return mapper.select(role);
	}

}
