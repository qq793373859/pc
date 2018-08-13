package springboot.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import springboot.bean.admin.user.Permission;  
  
public class MyInterceptor implements HandlerInterceptor {  
	@Override  
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)  
            throws Exception {  
		@SuppressWarnings("unchecked")
		List<Permission> ps = (List<Permission>)request.getSession().getAttribute("ps");
		if(!request.getServletPath().equals("/toLogin") && !request.getServletPath().equals("/index")
				&& !request.getServletPath().equals("/error")){
			if(null == ps || ps.isEmpty()){
				return false;
			}else{
				for (int i = 0; i < ps.size(); i++) {
					if(ps.get(i).getUrl().equals(request.getServletPath())){
						return true;
					}
				}
				return false;
			}
		}
		
		
        return true;
    }  
  
    @Override  
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,  
                           ModelAndView modelAndView) throws Exception {  
    	@SuppressWarnings("unchecked")
		List<Permission> ps = (List<Permission>)request.getSession().getAttribute("ps");
    	request.setAttribute("ps",ps);
    	request.setAttribute("ps1",ps);
    }  
  
    @Override  
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)  
            throws Exception {  
        System.out.println(">>>MyInterceptor1>>>>>>>在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）");  
    }  
} 