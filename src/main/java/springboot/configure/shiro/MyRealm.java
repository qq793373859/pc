package springboot.configure.shiro;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import springboot.bean.admin.user.Permission;
import springboot.bean.admin.user.User;
import springboot.bean.admin.user.UserRole;
import springboot.service.admin.user.PermissionService;
import springboot.service.admin.user.UserRoleService;
import springboot.service.admin.user.UserService;

public class MyRealm extends AuthorizingRealm {
	@Autowired
    private UserService userService;
	@Autowired
    private UserRoleService userRoleService;
	@Autowired
    private PermissionService permissionService;
	/**
	* 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
	        PrincipalCollection principals) {
	    System.out.println("权限认证方法：MyShiroRealm.doGetAuthenticationInfo()");
	    User token = (User)SecurityUtils.getSubject().getPrincipal();
	    String userId = token.getId().toString();
	    SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();
	    //根据用户ID查询角色（role），放入到Authorization里。
	    UserRole userRole = new UserRole();
		userRole.setUserId(userId);
		List<UserRole> list = userRoleService.getUserRoles(userRole);
		List<String> strs = new ArrayList<String>();
		if(null != list && !list.isEmpty()){
			for (int i = 0; i < list.size(); i++) {
				strs.add(list.get(i).getRoleId());
			}
		}
		Set<String> str = new HashSet<String>(strs);
	    info.setRoles(str);
	    
	    Set<String> permissionSet = new HashSet<String>();
	    List<Permission> ps = permissionService.getMenus(strs);
	    if(null != ps && !ps.isEmpty()){
			for (int i = 0; i < ps.size(); i++) {
				permissionSet.add(ps.get(i).getIsView());
			}
		}
	    info.setStringPermissions(permissionSet);
	       return info;
	}


	/**
	* 认证信息.(身份验证) : Authentication 是用来验证用户身份
	 * 
	 * @param token
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
	        AuthenticationToken authcToken) throws AuthenticationException {
	    System.out.println("身份认证方法：MyShiroRealm.doGetAuthenticationInfo()");
	    UsernamePasswordToken token = (UsernamePasswordToken)authcToken;
	    User user = new User();
	    user.setUsername(token.getUsername());
	    user.setPassword(String.valueOf(token.getPassword()));
	    // 从数据库获取对应用户名密码的用户
	    List<User> userList = userService.getUserByUser(user);
	    if(null != userList && userList.size()>0){
	        user = userList.get(0);
	    }
	    if(null == user || userList.size() == 0) {
	        throw new AccountException("帐号或密码不正确！");
	    }
	    return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), getName());
	}

	 
	}
