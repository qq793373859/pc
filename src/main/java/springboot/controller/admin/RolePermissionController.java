package springboot.controller.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;

import springboot.bean.admin.common.Tree;
import springboot.bean.admin.user.Permission;
import springboot.bean.admin.user.RolePermission;
import springboot.common.Message;
import springboot.service.admin.user.PermissionService;
import springboot.service.admin.user.RolePermissionService;

@Controller
public class RolePermissionController {
	@Autowired
	private RolePermissionService rolePermissionService;
	@Autowired
	private PermissionService permissionService;

	/**
	 * 用户关联角色页
	 * @return
	 */
	@RequestMapping("/rolePermission/index")
	public String index(String roleId,Model model){
		model.addAttribute("roleId", roleId);
		return "/admin/rolePermission/index";
	}

	@RequestMapping("/rolePermission/getPermissions")
	@ResponseBody
	public Message getRoles(String roleId){
		Message message = new Message();
		try {
			message.setType("success");
			PageHelper.orderBy("level asc");
			message.setList(permissionService.getPermissionByPermission(null));
			RolePermission rolePermission = new RolePermission();
			rolePermission.setRoleId(roleId);
			message.setList1(rolePermissionService.getRolePermissions(rolePermission));
		} catch (Exception e) {
			e.printStackTrace();
			message.setType("error");
			message.setContent("获取权限信息失败");
		}
		return message;
	}

	@RequestMapping("/rolePermission/add")
	@ResponseBody
	public Message add(String str ,String roleId){
		Message message = new Message(); 
		try {
			String arr[] = str.split(",");
			// 1.删除角色和权限关联
			RolePermission role = new RolePermission();
			role.setRoleId(roleId);
			rolePermissionService.delete(role);
			// 2.插入新关联的角色权限
			if(arr.length > 0 && !arr[0].equals("")){
				for (int i = 0; i < arr.length; i++) {
					role.setCreateTime(new Date());
					role.setUpdateTime(new Date());
					Permission permission = new Permission();
					permission.setId(arr[i]);
					List<Permission> list = permissionService.getPermissionByPermission(permission);
					// 如果是二级菜单,给该角色附上二级菜单所属一级菜单的权限
					if(null != list && !list.isEmpty() && list.get(0).getLevel().equals("2")){
						RolePermission rps = new RolePermission();
						rps.setPermissionId(permission.getParentId());
						rps.setRoleId(roleId);
						List<RolePermission> ps = rolePermissionService.getRolePermissions(rps);
						if(null == ps || ps.isEmpty()){
							role.setId(UUID.randomUUID().toString());
							role.setPermissionId(list.get(0).getParentId());
							rolePermissionService.insert(role);
						}
					}
					role.setId(UUID.randomUUID().toString());
					role.setPermissionId(arr[i]);
					rolePermissionService.insert(role);
				}
			}
			message.setContent("角色权限关联成功");
			message.setType("success");
		} catch (Exception e) {
			e.printStackTrace();
			message.setType("error");
			message.setContent("关联失败");
		}
		return message;
	}


	@RequestMapping("/rolePermission/tree")
	@ResponseBody
	public Message tree(String roleId){
		Message message = new Message();
		try {
			message.setType("success");
			PageHelper.orderBy("level asc");
			List<Permission> list = permissionService.getPermissionByPermission(null);
			RolePermission rolePermission = new RolePermission();
			rolePermission.setRoleId(roleId);
			//List<RolePermission> rolePermissions = rolePermissionService.getRolePermissions(rolePermission);

			List<Tree> trees = new ArrayList<Tree>();
			// 主菜单
			for (int i = 0; i < list.size(); i++) {
				Permission permission = list.get(i);
				if(permission.getLevel().equals("1")){
					Tree tree = new Tree();
					tree.setDisabled(false);
					tree.setExpand(true);
					tree.setId(permission.getId());
					rolePermission.setPermissionId(permission.getId());
					List<RolePermission> info = rolePermissionService.getRolePermissions(rolePermission);
					if(null != info && info.size() > 0){
						tree.setSelected(false);
						tree.setChecked(false);
					}else{
						tree.setSelected(false);
						tree.setChecked(false);
					}
					tree.setTitle(permission.getName());
					tree.setPid(permission.getParentId());
					trees.add(tree);
				}

			}

			// 子菜单
			for (int i = 0; i < list.size(); i++) {
				Permission permission = list.get(i);
				if(permission.getLevel().equals("2")){
					Tree tree = new Tree();
					tree.setDisabled(false);
					tree.setExpand(true);
					tree.setId(permission.getId());
					rolePermission.setPermissionId(permission.getId());
					List<RolePermission> info = rolePermissionService.getRolePermissions(rolePermission);
					if(null != info && info.size() > 0){
						tree.setSelected(false);
						tree.setChecked(true);
					}else{
						tree.setSelected(false);
						tree.setChecked(false);
					}
					tree.setTitle(permission.getName());
					tree.setPid(permission.getParentId());
					for (int j = 0; j < trees.size(); j++) {
						if(trees.get(j).getId().equals(tree.getPid())){
							List<Tree> t2 = new ArrayList<Tree>();
							if(null != trees.get(j).getChildren() && trees.get(j).getChildren().size() > 0){
								trees.get(j).getChildren().add(tree);
							}else{
								t2.add(tree);
								trees.get(j).setChildren(t2);
							}

						}
					}
				}

			}

			// 功能
			for (int i = 0; i < list.size(); i++) {
				Permission permission = list.get(i);
				if(permission.getLevel().equals("3")){
					Tree tree = new Tree();
					tree.setDisabled(false);
					tree.setExpand(true);
					tree.setId(permission.getId());
					rolePermission.setPermissionId(permission.getId());
					List<RolePermission> info = rolePermissionService.getRolePermissions(rolePermission);
					if(null != info && info.size() > 0){
						tree.setSelected(false);
						tree.setChecked(true);
					}else{
						tree.setSelected(false);
						tree.setChecked(false);
					}
					tree.setTitle(permission.getName());
					tree.setPid(permission.getParentId());

					for (int j = 0; j < trees.size(); j++) {
						if(null != trees.get(j).getChildren() && trees.get(j).getChildren().size() > 0 ){

							for (int j2 = 0; j2 < trees.get(j).getChildren().size(); j2++) {
								List<Tree> t3 = new ArrayList<Tree>();
								if(trees.get(j).getChildren().get(j2).getId().equals(tree.getPid())){
									t3.add(tree);

									if(null != trees.get(j).getChildren().get(j2).getChildren() && !trees.get(j).getChildren().get(j2).getChildren().isEmpty()){
										//trees.get(j).getChildren().get(j2).setChildren(t3);
										List<Tree> tt = trees.get(j).getChildren().get(j2).getChildren();
										tt.add(tree);
										trees.get(j).getChildren().get(j2).setChildren(tt);
									}else{
										trees.get(j).getChildren().get(j2).setChildren(t3);
									}
								}

							}

						}
					}
				}

			}

			message.setType("success");

			message.setList(trees);



		} catch (Exception e) {
			e.printStackTrace();
			message.setType("error");
			message.setContent("获取权限信息失败");
		}
		return message;
	}
}
