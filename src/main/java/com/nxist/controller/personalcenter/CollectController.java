package com.nxist.controller.personalcenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.nxist.dao.CollegeDao;
import com.nxist.dao.MajorDao;
import com.nxist.model.College;
import com.nxist.model.CollegeItem;
import com.nxist.model.Major;
import com.nxist.model.MajorCollectItem;

@Controller
@RequestMapping(value="/personalcenter")
public class CollectController {
	
	@Resource
	private CollegeDao collegeDao;
	@Resource
	private MajorDao majorDao;
	
	/**
	 * 查询指定用户收集的学校
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/collectCollege",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getCollectCollege(HttpServletRequest request) throws IOException {
		System.out.println("你进入了关注院校。。。。。");
		int userId=Integer.parseInt(request.getParameter("userId"));
        List<College> tempList=collegeDao.findAllCollectCollege(userId);
        List<CollegeItem> returnList=new ArrayList<CollegeItem>();
        for(College c:tempList) {
        	returnList.add(new CollegeItem(c.getName(),c.getProvince(),collegeDao.findCollectDateByCollegeId(c.getCollegeId(),userId),c.getCollegeId()));
        }
        String jsonString = JSON.toJSONString(returnList);
        return jsonString;
	}
	
	@RequestMapping(value="/collectMajor",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getCollectMajor(HttpServletRequest request) throws IOException {
		System.out.println("你进入了关注专业。。。。。");
		int userId=Integer.parseInt(request.getParameter("userId"));
        List<Major> tempList=majorDao.findAllCollectMajor(userId);
        List<MajorCollectItem> returnList=new ArrayList<MajorCollectItem>();
        for(Major m:tempList) {
        	returnList.add(new MajorCollectItem(m.getMajorName(),m.getMajorCode(),majorDao.findCollectDateByMajorId(m.getMajorId(),userId),m.getMajorId()));
        }
        String jsonString = JSON.toJSONString(returnList);
        return jsonString;
	}

}
