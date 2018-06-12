package springboot.service.admin.user;

import java.util.List;

import springboot.bean.admin.user.RolePermission;

public interface RolePermissionService {
    public void insert(RolePermission permission);
	
	public List<RolePermission> getRolePermissions(RolePermission permission);
	
	public void delete(RolePermission permission);
}
