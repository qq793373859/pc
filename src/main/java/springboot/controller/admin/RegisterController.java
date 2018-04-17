package springboot.controller.admin;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import springboot.bean.admin.user.User;
import springboot.common.MD5Utils;
import springboot.common.Message;
import springboot.service.admin.user.UserService;

@Controller
@RequestMapping("/register")
public class RegisterController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/register/toRegister")
	@ResponseBody
	public Message register(User user){
		user.setPassword(MD5Utils.getPassWordMD5(user.getPassword()));
		user.setCreateTime(new Date());
		user.setUpdateTime(new Date());
		userService.insert(user);
		Message message = new Message();
		message.setType("success");
		message.setContent("注册成功");
		return message;
	}
}
