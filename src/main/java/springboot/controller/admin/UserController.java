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

import springboot.bean.admin.user.User;
import springboot.common.MD5Utils;
import springboot.common.Message;
import springboot.common.SessionUtils;
import springboot.service.admin.user.UserService;
@Controller
public class UserController {
	@Autowired
    private UserService userService;
	
	/**
	 * 用户管理页
	 * @return
	 */
	@RequestMapping("/user/index")
	public String index(){
		return "/admin/user/index";
	}
	/**
	 * 添加用户页
	 * @return
	 */
	@RequestMapping("/user/addIndex")
	public String addIndex(){
		return "/admin/user/add";
	}
	/**
	 * 查询用户管理信息
	 * @param pageNum
	 * @param pageSize
	 * @param username
	 * @return
	 */
	@RequestMapping("/user/getUsers")
	@ResponseBody
	public Message getUsers(Integer pageNum ,Integer pageSize,String username){
		PageHelper.startPage(pageNum, pageSize);
		User user = new User();
		user.setUsername(username == "" ? null : username);
		List<User> list = userService.getUserByUser(user);
		PageInfo<User> pageInfo = new PageInfo<>(list);
		Message message = new Message();
		message.setType("success");
		message.setList(list);
		message.setCount(pageInfo.getTotal());
		return message;
	}
	
	@RequestMapping("/user/add")
	@ResponseBody
	public Message add(User user){
		Message message = new Message();
		try {
			User user2 = new User();
			user2.setUsername(user.getUsername());
			List<User> list = userService.getUserByUser(user2);
			if(null == list || list.size() == 0){
				user.setId(UUID.randomUUID().toString());
				user.setUpdateTime(new Date());
				user.setCreateTime(new Date());
				user.setPassword(MD5Utils.getPassWordMD5(MD5Utils.getMD5(user.getPassword())));
				userService.insert(user);
				message.setType("success");
				message.setContent("添加成功");
			}else{
				message.setType("error");
				message.setContent("用户名已存在");
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
