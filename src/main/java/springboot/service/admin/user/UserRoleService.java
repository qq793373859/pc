package springboot.service.admin.user;

import java.util.List;

import springboot.bean.admin.user.UserRole;

public interface UserRoleService {

	public void insert(UserRole role);
	
	public List<UserRole> getUserRoles(UserRole role);
	
	public void delete(UserRole role);
}
