package springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;

import springboot.bean.user.User;
import springboot.service.user.UserService;
@RestController
public class Test {
	@Autowired
    private UserService userService;

    @RequestMapping("/test")
    public User test() {
        return userService.getUser(1);
    }
    
    @RequestMapping("/testAll")
    public List<User> testAll() {
    	PageHelper.startPage(1, 10);
        return userService.getUserAll();
    }
    
    @RequestMapping("/getUser")
    public List<User> getUser() {
    	User user = new User();
    	user.setUsername("admin");
    	user.setEmail("999");
        return userService.getUserByUser(user);
    }
    
}
