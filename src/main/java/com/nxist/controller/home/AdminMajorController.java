package com.nxist.controller.home;

import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nxist.controller.base.BaseData;
import com.nxist.controller.base.PageHandler;
import com.nxist.dao.MajorDao;
import com.nxist.model.Major;

@Controller
@RequestMapping(value="/admin")
public class AdminMajorController {
	
	@Resource
	private MajorDao majorDao;
	
	/**
	 * 院校管理
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "majorManage")
    public ModelAndView getDataList(Integer pageNo,String majorName,String majorCode) throws ParseException {
		ModelAndView mav = new ModelAndView();
		int count=majorDao.getMajorSum(majorName,majorCode);
		int start=PageHandler.handle(pageNo, count, mav);
		List<Major> dataList=majorDao.findAllMajorLimit(start,BaseData.PAGE_SIZE,majorName,majorCode);
        mav.addObject("dataList",dataList);
        mav.addObject("majorName",majorName);
        mav.addObject("majorCode",majorCode);
        mav.setViewName("jsp/home/major");
        //updateCategory();
        return mav;
	}
	
	//更新门类
	private void updateCategory() {
		int count=majorDao.getMajorSum(null,null);
		List<Major> dataList=majorDao.findAllMajorLimit(1,count,null,null);
		int size=dataList.size();
		for(int i=0;i<size;i++) {
			String tempMajorCode=dataList.get(i).getMajorCode();
			if(tempMajorCode.length()==4) {
				for(int j=0;j<size;j++) {
					String category=tempMajorCode.substring(0,2);
					if(dataList.get(j).getMajorCode().equals(category)) {
						majorDao.updateMajorCategory(dataList.get(i).getMajorId(),dataList.get(j).getMajorName());
						break;
					}		
				}
			}
			if(tempMajorCode.length()>=6) {
				for(int j=0;j<size;j++) {
					String category=tempMajorCode.substring(0,4);
					if(dataList.get(j).getMajorCode().equals(category)) {
						majorDao.updateMajorCategory(dataList.get(i).getMajorId(),dataList.get(j).getMajorName());
						break;
					}		
				}
			}
		}
	}

}
