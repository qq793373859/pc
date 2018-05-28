package springboot.controller.admin;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import springboot.bean.admin.user.User;
import springboot.common.MD5Utils;
import springboot.common.Message;
import springboot.service.admin.user.UserService;
@Controller
@RequestMapping("/")
public class LoginController {
	
	@Autowired
    private UserService userService;
	
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
			message.setType("success");
			message.setContent("/index");
		} catch (Exception e) {
			message.setType("error");
			message.setContent("账号密码错误");
		}
		return message;
	}

}
