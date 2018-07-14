package com.nxist.controller.exchange;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.nxist.dao.TopicDao;
import com.nxist.model.Reply;
import com.nxist.model.TopicData;
import com.nxist.model.User;

@Controller
@RequestMapping(value="/exchange")
public class TopicCommentController {
	
	@Resource
	private TopicDao topicDao;
	
	/**
	 * 话题评论
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping(value="/topicComment",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public void TopicComment(HttpServletRequest request) throws IOException {
		System.out.println("用户评论Id："+request.getParameter("userId"));
		int userId=Integer.parseInt(request.getParameter("userId"));
		int topicId=Integer.parseInt(request.getParameter("topicId"));
		String replyContent=request.getParameter("replyContent");
		System.out.println("用户评论topicId："+request.getParameter("topicId"));
		Reply reply=new Reply();
		reply.setUserId(userId);
		reply.setTopicId(topicId);
		reply.setReplyContent(replyContent);
		reply.setReplyDate(new Date());
		topicDao.replyTopic(reply);//回复
		updateReplyNumber(reply.getTopicId());
	}
	
	public void updateReplyNumber(Integer topicId) {
		int replyNumber=topicDao.countToicReply(topicId);
		topicDao.updateRePlyNumber(replyNumber, topicId);
	}

}
