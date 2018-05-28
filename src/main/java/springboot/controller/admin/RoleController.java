package springboot.controller.admin;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import springboot.bean.admin.user.Role;
import springboot.bean.admin.user.User;
import springboot.common.MD5Utils;
import springboot.common.Message;
import springboot.common.SessionUtils;
import springboot.service.admin.user.RoleService;

@Controller
public class RoleController {
	@Autowired
    private RoleService roleService;
	
	/**
	 * 角色管理页
	 * @return
	 */
	@RequestMapping("/role/index")
	public String index(){
		return "/admin/role/index";
	}
	
	/**
	 * 添加角色页
	 * @return
	 */
	@RequestMapping("/role/addIndex")
	public String addIndex(){
		return "/admin/role/add";
	}
	
	/**
	 * 查询角色管理信息
	 * @param pageNum
	 * @param pageSize
	 * @param username
	 * @return
	 */
	@RequestMapping("/role/getRoles")
	@ResponseBody
	public Message getUsers(Integer pageNum ,Integer pageSize,String name){
		PageHelper.startPage(pageNum, pageSize);
		PageHelper.orderBy("create_time desc");
		Role role = new Role();
		role.setName(name == "" ? null : name);
		List<Role> list = roleService.getRoleByRole(role);
		PageInfo<Role> pageInfo = new PageInfo<>(list);
		Message message = new Message();
		message.setType("success");
		message.setList(list);
		message.setCount(pageInfo.getTotal());
		return message;
	}
	
	@RequestMapping("/role/add")
	@ResponseBody
	public Message add(Role role){
		Message message = new Message();
		try {
			Role role2 = new Role();
			role2.setName(role.getName());
			List<Role> list = roleService.getRoleByRole(role2);
			if(null == list || list.size() == 0){
				role.setId(UUID.randomUUID().toString());
				role.setUpdateTime(new Date());
				role.setCreateTime(new Date());
				roleService.insert(role);
				message.setType("success");
				message.setContent("添加成功");
			}else{
				message.setType("error");
				message.setContent("角色名已存在");
			}
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			message.setType("error");
			message.setContent("添加失败");
		}
		return message;
	}
}
