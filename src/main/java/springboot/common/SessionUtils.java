package springboot.common;

import org.apache.shiro.SecurityUtils;

import springboot.bean.admin.user.User;

public class SessionUtils {
	public static User getUser(){
		return (User)SecurityUtils.getSubject().getSession().getAttribute("User");
	}
}
