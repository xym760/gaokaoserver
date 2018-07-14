package com.nxist.controller.home;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.nxist.dao.MajorDao;
import com.nxist.model.Collect;
import com.nxist.model.College;
import com.nxist.model.Major;
import com.nxist.model.MajorItem;
import com.nxist.model.Topic;
import com.nxist.model.TopicData;

@Controller
@RequestMapping(value="/home")
public class MajorQueryController {
	@Resource
	private MajorDao majorDao;
	
	/**
	 * 返回专业JSON数据
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/majorQuery",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getMajor(HttpServletResponse response) throws IOException {
        List<Major> tempList=majorDao.findAllMajor();
        List<MajorItem> returnList=new ArrayList<MajorItem>();
        for(Major c:tempList) {
        	returnList.add(new MajorItem(c.getMajorName(),c.getMajorCode()));
        }
        String jsonString = JSON.toJSONString(returnList);
        return jsonString;
	}
	
	@RequestMapping(value="/getMajorId",method=RequestMethod.POST)
	@ResponseBody
    public int getMajorId(@RequestBody Major major) {
		Major temp = null;
		if(major.getMajorCode()!=null&&!major.getMajorCode().equals(""))
			temp=majorDao.findMajorByMajorCode(major.getMajorCode());
		if(major.getMajorName()!=null&&!major.getMajorName().equals(""))
			temp=majorDao.findMajorByMajorName(major.getMajorName());
		if(temp!=null)
			return temp.getMajorId();
		else
			return -1;
    }
	
	/**
	 * 跳转至查看页面
	 * @param majorCode
	 * @return
	 */
	@RequestMapping(value = "majorDetailtoAndroid")
    public ModelAndView detailtoAndroid(String majorCode) {
		List<Major> majorList=majorDao.findMajorListByMajorCode(majorCode);
		ModelAndView mav = new ModelAndView();
		if(majorList.size()>0)
			mav.addObject("major",majorList.get(0));
        mav.setViewName("jsp/home/majorDetailtoAndroid");
        return mav;
	}
	
	/**
	 * 跳转至查看页面
	 * @param majorName
	 * @return
	 */
	@RequestMapping(value = "majorDetailtoAndroidByMajorName")
    public ModelAndView detailtoAndroidMajorName(String majorName) {
		Major major=majorDao.findMajorByMajorName(majorName);
		ModelAndView mav = new ModelAndView();
		mav.addObject("major",major);
        mav.setViewName("jsp/home/majorDetailtoAndroid");
        return mav;
	}
	
	/**
	 * 关注专业
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping(value="/majorCollect",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public int collegeCollect(HttpServletRequest request) throws IOException {
		System.out.println("用户关注Id："+request.getParameter("userId"));
		int userId=Integer.parseInt(request.getParameter("userId"));
		int majorId=Integer.parseInt(request.getParameter("majorId"));
		if(majorDao.isCollect(userId,majorId)==0) {//如果用户没有关注该院校
			Collect collect=new Collect();
			collect.setUserId(userId);
			collect.setMajorId(majorId);
			collect.setCollectDate(new Date());
			majorDao.collectMajor(collect);//关注
			return collect.getCollectId();
		}
		return -1;//用户已关注该院校，返回-1
	}
	
	/**
	 * 取消关注该专业
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/majorCancelCollect",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public int majorCancelCollect(HttpServletRequest request) throws IOException {
		System.out.println("用户Id："+request.getParameter("userId"));
		int userId=Integer.parseInt(request.getParameter("userId"));
		int majorId=Integer.parseInt(request.getParameter("majorId"));
		if(majorDao.isCollect(userId,majorId)==0) {//如果用户没有关注该院校
			return -1;//用户没有关注该院校，返回0
		}
		else {//用户已关注该院校，取消关注
			majorDao.cancelCollect(userId,majorId);
			return 0;
		}
	}
	
	/**
	 * 判断用户否关注该专业
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/majorIsCollect",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public int majorIsCollect(HttpServletRequest request) throws IOException {
		System.out.println("用户Id："+request.getParameter("userId"));
		int userId=Integer.parseInt(request.getParameter("userId"));
		int majorId=Integer.parseInt(request.getParameter("majorId"));
		if(majorDao.isCollect(userId,majorId)==0) {//如果用户没有关注该院校
			return 0;//用户没有关注该院校，返回0
		}
		return 1;//用户已关注该院校，返回1
	}

}
