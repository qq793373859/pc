package springboot.controller.admin;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import springboot.bean.admin.user.Permission;
import springboot.bean.admin.user.UserRole;
import springboot.common.Message;
import springboot.service.admin.user.PermissionService;
import springboot.service.admin.user.RoleService;
import springboot.service.admin.user.UserRoleService;

@Controller
public class UserRoleController {
	@Autowired
    private UserRoleService userRoleService;
	@Autowired
	private RoleService roleService;
	
	/**
	 * 用户关联角色页
	 * @return
	 */
	@RequestMapping("/userRole/index")
	public String index(String userId,Model model){
		model.addAttribute("userId", userId);
		return "/admin/userRole/index";
	}
	
	@RequestMapping("/userRole/getRoles")
	@ResponseBody
	public Message getRoles(String userId){
		Message message = new Message();
		try {
			message.setType("success");
			message.setList(roleService.getRoleByRole(null));
			
			UserRole role = new UserRole();
			role.setUserId(userId);
			message.setList1(userRoleService.getUserRoles(role));
		} catch (Exception e) {
			e.printStackTrace();
			message.setType("error");
			message.setContent("获取角色信息失败");
		}
		return message;
	}
	
	@RequestMapping("/userRole/add")
	@ResponseBody
	public Message add(String arr[] ,String userId){
		Message message = new Message();
		try {
			// 1.删除角色关联
			UserRole role = new UserRole();
			role.setUserId(userId);
			userRoleService.delete(role);
			// 2.插入新关联的角色
			if(arr.length > 0){
				for (int i = 0; i < arr.length; i++) {
					role.setCreateTime(new Date());
					role.setUpdateTime(new Date());
					role.setRoleId(arr[i]);
					role.setId(UUID.randomUUID().toString());
					userRoleService.insert(role);
				}
			}
			message.setContent("角色关联成功");
			message.setType("success");
		} catch (Exception e) {
			e.printStackTrace();
			message.setType("error");
			message.setContent("关联失败");
		}
		return message;
	}
	
}
