package com.nxist.controller.home;

import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.nxist.controller.base.BaseData;
import com.nxist.controller.base.PageHandler;
import com.nxist.dao.CollegeDao;
import com.nxist.dao.ProvinceDao;
import com.nxist.dao.UserDao;
import com.nxist.model.College;
import com.nxist.model.Province;
/**
 * 管理员院校管理
 * @author xym760
 *
 */
@Controller
@RequestMapping(value="/admin")
public class AdminCollegeController {
	@Resource
	private UserDao userDao;
	@Resource
	private CollegeDao collegeDao;
	@Resource
	private ProvinceDao provinceDao;
	
	
	/**
	 * 院校管理
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "collegeManage")
    public ModelAndView getDataList(Integer pageNo,String province,String collegeName) throws ParseException {
		ModelAndView mav = new ModelAndView();
		int count=collegeDao.getCollegeSum(province,collegeName);
		int start=PageHandler.handle(pageNo, count, mav);
		List<College> dataList=collegeDao.findAllCollegeLimit(start,BaseData.PAGE_SIZE,province,collegeName);
		System.out.println("记录数:"+dataList.size());
        mav.addObject("dataList",dataList);
        List<Province> pList=provinceDao.getProvinceList();
        mav.addObject("pList",pList);
        mav.addObject("province",province);
        mav.addObject("collegeName",collegeName);
        mav.setViewName("jsp/home/college");
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
        mav.setViewName("jsp/home/collegeDetail");
        return mav;
	}
	
	/**
	 * 修改学校
	 * @param messageId
	 * @param title
	 * @param picture
	 * @param content
	 * @return
	 */
	@RequestMapping(value="/collegeManageUpdateDispose",method=RequestMethod.POST)
    public String updateDispose(College college) {
		if(college.getCollegeId()!=null) {
			collegeDao.updateCollege(college);
		}
		return "redirect:collegeManage";
    }
	
	/**
	 * 删除学校
	 * @param messageId
	 * @return
	 */
	@RequestMapping(value = "deleteCollege")
    public String delete(int collegeId) {
		College college=new College();
		college.setCollegeId(collegeId);
		//删除关注表中已关注该学校的记录
		collegeDao.deleteCollectByCollegeId(collegeId);
		//删除学校表中该学校的记录
		collegeDao.deleteCollege(college);
        return "redirect:collegeManage";
	}
	
	
	@RequestMapping(value = "updateCollege")
    public ModelAndView update(int collegeId) {
		College college=collegeDao.findCollegeByCollegeId(collegeId);
		ModelAndView mav = new ModelAndView();
		mav.addObject("college",college);
		List<Province> pList=provinceDao.getProvinceList();
        mav.addObject("pList",pList);
        mav.addObject("province",college.getProvince());
        mav.setViewName("jsp/home/collegeUpdate");
        return mav;
	}
}


