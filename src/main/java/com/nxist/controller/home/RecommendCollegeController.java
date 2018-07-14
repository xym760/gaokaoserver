package com.nxist.controller.home;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nxist.dao.CollegeDao;
import com.nxist.model.College;

/**
 * 专业推荐
 * @author xym760
 *
 */
@Controller
@RequestMapping(value="/home")
public class RecommendCollegeController {
	@Resource
	private CollegeDao collegeDao;
	
	@RequestMapping(value = "recommendCollegetoAndroid")
    public ModelAndView detailtoAndroid(Integer score,String province,String subject) {
		List<College> dataList=new ArrayList<College>();
		if(score>350) {//本科
			if(score>400) {//二本以上
				if(score>470) {//一本
					dataList=collegeDao.searchCollegeByRankRange(0, 500);
				}
				else {//二本
					dataList=collegeDao.searchCollegeByRankRange(500, 900);
				}
			}
			else {//三本
				dataList=collegeDao.searchCollegeByRankRange(900, 1200);
			}
		}
		else {//专科
			dataList=collegeDao.searchCollegeByRankRange(1200, 10000);
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject("dataList",dataList);
        mav.setViewName("jsp/home/recommendCollegetoAndroid");
        return mav;
	}
	
	/**
	 * 跳转至查看页面
	 * @param messageId
	 * @return
	 */
	@RequestMapping(value = "collegeDetail")
    public ModelAndView detail(String collegeName) {
		College college=collegeDao.findCollegeByCollegeName(collegeName);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("college",college);
        mav.setViewName("jsp/home/collegeDetailtoAndroid");
        return mav;
	}

}
