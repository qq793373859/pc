package springboot.controller.admin;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import springboot.bean.admin.user.Permission;
@Controller
@RequestMapping("/")
public class IndexController {
	@RequestMapping("/index")
	public String index(Model model){
		return "/index";
	}
}
