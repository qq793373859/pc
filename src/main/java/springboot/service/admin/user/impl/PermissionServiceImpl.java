package springboot.service.admin.user.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springboot.bean.admin.user.Permission;
import springboot.mapper.admin.user.PermissionMapper;
import springboot.service.admin.user.PermissionService;
@Service
@Transactional
public class PermissionServiceImpl implements PermissionService{

	@Autowired
	private PermissionMapper mapper;

	@Override
	public Permission getPermissionById(String id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Permission> getPermissionByPermission(Permission permission) {
		return mapper.select(permission);
	}

	@Override
	public void insert(Permission permission) {
		mapper.insert(permission);
	}
	

}
