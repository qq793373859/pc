package springboot.service.admin.user;

import java.util.List;

import springboot.bean.admin.user.Role;

public interface RoleService{
	
	public Role getRoleById(String id);
	
	public List<Role> getRoleByRole(Role role);

	public void insert(Role role);
}
