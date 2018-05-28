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
import springboot.common.Message;
import springboot.service.admin.user.PermissionService;

@Controller
public class PermissionController {
	@Autowired
    private PermissionService permissionService;
	
	/**
	 * 权限管理页
	 * @return
	 */
	@RequestMapping("/permission/index")
	public String index(){
		return "/admin/permission/index";
	}
	
	/**
	 * 添加权限页
	 * @return
	 */
	@RequestMapping("/permission/addIndex")
	public String addIndex(){
		return "/admin/permission/add";
	}
	
	@RequestMapping("/permission/getPermissionLevel")
	@ResponseBody
	public Message getPermissionLevel(){
		Permission permission = new Permission();
		List<Permission> list = permissionService.getPermissionByPermission(permission);
		Message message = new Message();
		message.setType("success");
		message.setList(list);
		return message;
	}
	
	/**
	 * 查询权限管理信息
	 * @param pageNum
	 * @param pageSize
	 * @param username
	 * @return
	 */
	@RequestMapping("/permission/getPermissions")
	@ResponseBody
	public Message getPermissions(Integer pageNum ,Integer pageSize,String name){
		PageHelper.startPage(pageNum, pageSize);
		PageHelper.orderBy("create_time desc");
		Permission permission = new Permission();
		permission.setName(name == "" ? null : name);
		List<Permission> list = permissionService.getPermissionByPermission(permission);
		PageInfo<Permission> pageInfo = new PageInfo<>(list);
		Message message = new Message();
		message.setType("success");
		message.setList(list);
		message.setCount(pageInfo.getTotal());
		return message;
	}
	
	@RequestMapping("/permission/add")
	@ResponseBody
	public Message add(Permission permission){
		Message message = new Message();
		try {
			Permission permission2 = new Permission();
			permission2.setName(permission.getName());
			List<Permission> list = permissionService.getPermissionByPermission(permission2);
			if(null == list || list.size() == 0){
				permission.setId(UUID.randomUUID().toString());
				permission.setUpdateTime(new Date());
				permission.setCreateTime(new Date());
				permissionService.insert(permission);
				message.setType("success");
				message.setContent("添加成功");
			}else{
				message.setType("error");
				message.setContent("权限名已存在");
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
