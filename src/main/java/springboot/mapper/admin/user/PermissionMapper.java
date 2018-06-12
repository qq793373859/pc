package springboot.mapper.admin.user;

import java.util.List;

import springboot.bean.admin.user.Permission;
import springboot.common.BaseMapper;

public interface PermissionMapper extends BaseMapper<Permission>{

	public List<Permission> getMenus(List<String> list);
}
