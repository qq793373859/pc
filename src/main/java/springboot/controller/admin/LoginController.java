package springboot.controller.admin;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import springboot.common.MD5Utils;
import springboot.common.Message;
@Controller
@RequestMapping("/")
public class LoginController {
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
			message.setType("success");
			message.setContent("/index");
		} catch (Exception e) {
			message.setType("error");
			message.setContent("账号密码错误");
		}
		return message;
	}

}
