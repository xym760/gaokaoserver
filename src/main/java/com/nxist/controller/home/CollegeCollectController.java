package com.nxist.controller.home;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nxist.dao.CollegeDao;
import com.nxist.model.Collect;
import com.nxist.model.Reply;

@Controller
@RequestMapping(value="/home")
public class CollegeCollectController {
	
	@Resource
	private CollegeDao collegeDao;
	
	/**
	 * 关注院校
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping(value="/collegeCollect",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public int collegeCollect(HttpServletRequest request) throws IOException {
		System.out.println("用户关注Id："+request.getParameter("userId"));
		int userId=Integer.parseInt(request.getParameter("userId"));
		int collegeId=Integer.parseInt(request.getParameter("collegeId"));
		if(collegeDao.isCollect(userId,collegeId)==0) {//如果用户没有关注该院校
			Collect collect=new Collect();
			collect.setUserId(userId);
			collect.setCollegeId(collegeId);
			collect.setCollectDate(new Date());
			collegeDao.collectCollege(collect);//关注
			return collect.getCollectId();
		}
		return -1;//用户已关注该院校，返回-1
	}
	
	/**
	 * 判断用户否关注该院校
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/collegeIsCollect",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public int collegeIsCollect(HttpServletRequest request) throws IOException {
		System.out.println("用户Id："+request.getParameter("userId"));
		int userId=Integer.parseInt(request.getParameter("userId"));
		int collegeId=Integer.parseInt(request.getParameter("collegeId"));
		if(collegeDao.isCollect(userId,collegeId)==0) {//如果用户没有关注该院校
			return 0;//用户没有关注该院校，返回0
		}
		return 1;//用户已关注该院校，返回1
	}
	
	/**
	 * 取消关注该院校
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/collegeCancelCollect",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public int collegeCancelCollect(HttpServletRequest request) throws IOException {
		System.out.println("用户Id："+request.getParameter("userId"));
		int userId=Integer.parseInt(request.getParameter("userId"));
		int collegeId=Integer.parseInt(request.getParameter("collegeId"));
		if(collegeDao.isCollect(userId,collegeId)==0) {//如果用户没有关注该院校
			return -1;//用户没有关注该院校，返回0
		}
		else {//用户已关注该院校，取消关注
			collegeDao.cancelCollect(userId,collegeId);
			return 0;
		}
	}

}
