package springboot.controller.admin;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import springboot.bean.admin.user.Permission;
import springboot.bean.admin.user.Role;
import springboot.bean.admin.user.User;
import springboot.bean.admin.user.UserRole;
import springboot.common.MD5Utils;
import springboot.common.Message;
import springboot.service.admin.user.PermissionService;
import springboot.service.admin.user.RoleService;
import springboot.service.admin.user.UserRoleService;
import springboot.service.admin.user.UserService;
@Controller
@RequestMapping("/")
public class LoginController {
	
	@Autowired
    private UserService userService;
	@Autowired
    private UserRoleService userRoleService;
	@Autowired
    private PermissionService permissionService;
	
	@RequestMapping("/login")
	public String index(){
		return "/login";
	}

	@RequestMapping("/toLogin")
	@ResponseBody
	public Message toLogin(String username, String password){
		Message message = new Message();
		try{
			UsernamePasswordToken token = new UsernamePasswordToken(username, MD5Utils.getPassWordMD5(password));
			SecurityUtils.getSubject().login(token);
			User user = new User();
			user.setUsername(username);
			user = userService.getUserByUser(user).get(0);
			user.setPassword(null);
			SecurityUtils.getSubject().getSession().setAttribute("User", user);
			getMenu(user);
			message.setType("success");
			message.setContent("/index");
		} catch (Exception e) {
			message.setType("error");
			message.setContent("账号密码错误");
		}
		return message;
	}
	/**
	 * 获取菜单
	 * @param user
	 */
	public void getMenu(User user){
		UserRole userRole = new UserRole();
		userRole.setUserId(user.getId());
		List<UserRole> list = userRoleService.getUserRoles(userRole);
		List<String> str = new ArrayList<String>();
		if(null != list && !list.isEmpty()){
			for (int i = 0; i < list.size(); i++) {
				str.add(list.get(i).getRoleId());
			}
		}
		List<Permission> ps = permissionService.getMenus(str);
		SecurityUtils.getSubject().getSession().setAttribute("ps", ps);
	}

}
