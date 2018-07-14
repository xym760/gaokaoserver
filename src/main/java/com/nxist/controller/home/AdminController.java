package com.nxist.controller.home;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nxist.controller.base.RedisUtil;
import com.nxist.dao.CollegeDao;
import com.nxist.dao.UserDao;
import com.nxist.model.User;

@Controller
@RequestMapping(value="/admin")
public class AdminController {
	
	@Resource
	private UserDao userDao;
	@Resource
	private CollegeDao collegeDao; 
	@Autowired
	RedisUtil redisUtil;
	
	/**
	 * 管理员登录
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/adminLogin")
	public ModelAndView adminLogin(User model,HttpSession session) {
		System.out.println("You come in......."+model.getUserAccount());
		User user=userDao.findByUserAccount(model.getUserAccount());
		if(user==null||!user.getFlag()||!user.getPassword().equals(model.getPassword())) {
			model=null;
			return new ModelAndView("redirect:/jsp/login.jsp");
		}else {
			//在Session里保存信息,用于验证用户是否登录  
	        session.setAttribute("userId", user.getUserId());
	        ModelAndView mav = new ModelAndView();
            mav.setViewName("jsp/index");
            
            int collegeNumber=0;
            if (redisUtil.exists("collegeNumber")) {
            	System.out.println("缓存中存在！！！！！！！！！！");
                collegeNumber = (int)redisUtil.get("collegeNumber");  //从缓存中查找collegeNumber
            }else{
            	System.out.println("缓存不存在。。。。。。。。。。。。");
            	collegeNumber = collegeDao.getCollegeSum(null,null) ;
                redisUtil.set("collegeNumber", collegeNumber);    //将token写入缓存 
            }
            mav.addObject("collegeNumber",collegeNumber);
            mav.addObject("user", user);
            return mav;
		}
	}
	
	@RequestMapping(value="/logout")  
    public String logout(HttpSession session) throws Exception{  
        //清除Session  
        session.invalidate();
        return "redirect:/jsp/login.jsp";  
    }

}
