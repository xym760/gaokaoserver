package com.nxist.controller.home;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.nxist.dao.CollegeDao;
import com.nxist.model.College;
import com.nxist.model.CollegeItem;

/**
 * 学校查询
 * @author 徐源茂
 *
 */
@Controller
@RequestMapping(value="/home")
public class CollegeQueryController {
	
	@Resource
	private CollegeDao collegeDao;
	/**
	 * 专业查询
	 * @param college Android端返回的json对象
	 */
	@RequestMapping(value="/insertCollege",method=RequestMethod.POST)
	@ResponseBody
    public College collegeQuery(@RequestBody College college) {
		System.out.println("学校名："+college.getName());
		College college1=collegeDao.findCollegeByCollegeName(college.getName());
		if(college1==null) {//本地库中无该院校,执行添加操作
			collegeDao.insertCollege(null,college.getName(), college.getFormerName(),
					college.getProvince(), college.getGrade(), college.getProperty(), 
					college.getDirectlyUnder(), college.getRunNature(), college.getIntro(),
					college.getCode(), college.getRanking(), college.getWebsite(),
					college.getTelephone(), college.getAddress(), college.getMailbox());
			int collegeId=collegeDao.findCollegeByCollegeName(college.getName()).getCollegeId();
			System.out.println("已添加collegeId:"+collegeId);
			return collegeDao.findCollegeByCollegeName(college.getName());
		}
		else {
			System.out.println("不做任何操作:"+college1.getCollegeId());
			return college1;
		}
    }
	
	/**
	 * 向Android端发送JSON数据
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/collegeQuery",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getCollege(HttpServletResponse response) throws IOException {
        List<College> tempList=collegeDao.findAllCollege();
        List<CollegeItem> returnList=new ArrayList<CollegeItem>();
        for(College c:tempList) {
        	returnList.add(new CollegeItem(c.getName(),c.getProvince()));
        }
        String jsonString = JSON.toJSONString(returnList);
        return jsonString;
	}
}
