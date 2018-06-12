package springboot.service.admin.user.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springboot.bean.admin.user.RolePermission;
import springboot.mapper.admin.user.RolePermissionMapper;
import springboot.service.admin.user.RolePermissionService;
@Service
@Transactional
public class RolePermissionServiceImpl implements RolePermissionService{

	@Autowired
	private RolePermissionMapper mapper;

	@Override
	public void insert(RolePermission permission) {
		mapper.insert(permission);
	}

	@Override
	public List<RolePermission> getRolePermissions(RolePermission permission) {
		return mapper.select(permission);
	}

	@Override
	public void delete(RolePermission permission) {
		mapper.delete(permission);
	}


}
