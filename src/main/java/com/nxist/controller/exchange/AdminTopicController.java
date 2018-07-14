package com.nxist.controller.exchange;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nxist.controller.base.BaseData;
import com.nxist.controller.base.BaseUtil;
import com.nxist.controller.base.PageHandler;
import com.nxist.dao.TopicDao;
import com.nxist.dao.UserDao;
import com.nxist.model.Reply;
import com.nxist.model.TopicData;
import com.nxist.model.User;
/**
 * 管理员话题管理
 * @author xym760
 *
 */
@Controller
@RequestMapping(value="/admin")
public class AdminTopicController {
	@Resource
	private UserDao userDao;
	@Resource
	private TopicDao topicDao;
	
	
	/**
	 * 话题管理
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "topicManage")
    public ModelAndView getDataList(Integer pageNo,String userName,String startDate) throws ParseException {
		System.out.println("页码:"+pageNo);
		System.out.println("userName:"+userName);
		System.out.println("startDate:"+startDate);
		ModelAndView mav = new ModelAndView();
		Integer userId=null;
		if(userName!=null&&!userName.equals("")) {
			User temp=userDao.findUserByUserName(userName);
			if(temp!=null)
				userId=temp.getUserId();
		}
		System.out.println("用户的Id:"+userId);
		int count=topicDao.getTopicSum(userId,startDate);
		int start=PageHandler.handle(pageNo, count, mav);
		List<TopicData> dataList=topicDao.findAllTopicLimit(start,BaseData.PAGE_SIZE,userId,startDate);
		mav.addObject("userName",userName);
		mav.addObject("startDate",startDate);
        mav.addObject("dataList",dataList);
        mav.setViewName("jsp/exchange/topic");
        return mav;
	}
	
	/**
	 * 跳转至查看页面
	 * @param topicId
	 * @return
	 */
	@RequestMapping(value = "topicDetail")
    public ModelAndView detail(Integer topicId) {
		TopicData TopicData=topicDao.findTopicByTopicId(topicId);
		ModelAndView mav = new ModelAndView();
		mav.addObject("TopicData",TopicData);
		List<String> pictureList=new ArrayList<String>();
			if(TopicData.getStringPictureList()!=null)
				pictureList=Arrays.asList(TopicData.getStringPictureList().split(","));
		mav.addObject("pictureList",pictureList);
		List<Reply> rList=topicDao.getReplyByTopicId(TopicData.getTopicId());
		mav.addObject("rList",rList);//查询该话题的所有回复
        mav.setViewName("jsp/exchange/topicDetail");
        return mav;
	}
	
	/**
	 * 删除话题
	 * @param topicId
	 * @return
	 */
	@RequestMapping(value = "topicDelete")
    public String delete(int topicId) {
		System.out.println("you come in delete");
		TopicData topicData=topicDao.findTopicByTopicId(topicId);
		//删除该话题的评论
		topicDao.deleteTopicReplyByTopicId(topicId);
		//删除该话题的点赞
		topicDao.deleteTopicLikeByTopicId(topicId);
		//删除话题图片
		if(topicData!=null) {
			if(topicData.getStringPictureList()!=null) {
				List<String> pictureList=new ArrayList<String>();
				pictureList=Arrays.asList(topicData.getStringPictureList().split(","));
				for(int i=0;i<pictureList.size();i++) {
					BaseUtil.delete("C:/userpic/"+topicId+"_"+pictureList.get(i));
				}
			}
		}
		//删除话题
		topicDao.deleteTopic(topicId);
        return "redirect:topicManage";
	}
}



