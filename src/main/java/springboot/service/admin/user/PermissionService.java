package springboot.service.admin.user;

import java.util.List;

import springboot.bean.admin.user.Permission;

public interface PermissionService{
	
	public Permission getPermissionById(String id);
	
	public List<Permission> getPermissionByPermission(Permission permission);

	public void insert(Permission permission);
	
	public List<Permission> getMenus(List<String> list);
}
