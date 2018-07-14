package com.nxist.controller.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInterceptor implements HandlerInterceptor {
	
	/** 
     * Handler执行之前调用这个方法 
     */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//获取请求的URL  
        String url = request.getRequestURI();
        
        //"/gaokao/user/login"是公开的,其它的URL都进行拦截控制  
        if(url.indexOf("/gaokao/user/login")>=0){  
            return true;  
        }
        
        //"管理员登录是公开的,其它的URL都进行拦截控制  
        if(url.indexOf("/gaokao/admin/adminLogin")>=0){  
            return true;  
        }
        
        //"/gaokao/user/register"是公开的,其它的URL都进行拦截控制  
        if(url.indexOf("/gaokao/user/register")>=0){  
            return true;  
        }
        
        //"/gaokao/user/update"是公开的,其它的URL都进行拦截控制  
        if(url.indexOf("/gaokao/user/update")>=0){  
            return true;  
        }
        
        //获取Session  
        HttpSession session = request.getSession();  
        Integer userId = (Integer) session.getAttribute("userId");  
          
        if(userId != null){  
            return true;
        }  
        //不符合条件的，跳转到登录界面  
        System.out.println("拦截地址："+url);
        request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
        return false; 
	}
	
	/** 
     * Handler执行完成之后调用这个方法 
     */  
    public void afterCompletion(HttpServletRequest request,  
            HttpServletResponse response, Object handler, Exception exc)  
            throws Exception {  
          
    }  
  
    /** 
     * Handler执行之后，ModelAndView返回之前调用这个方法 
     */  
    public void postHandle(HttpServletRequest request, HttpServletResponse response,  
            Object handler, ModelAndView modelAndView) throws Exception {  
    }  

}
