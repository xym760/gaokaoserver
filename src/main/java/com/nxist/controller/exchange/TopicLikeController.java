package com.nxist.controller.exchange;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nxist.dao.TopicDao;
import com.nxist.model.Like;

@Controller
@RequestMapping(value="/exchange")
public class TopicLikeController {
	
	@Resource
	private TopicDao topicDao;
	
	/**
	 * 点击点赞
	 * @param like
	 */
	@RequestMapping(value="/like",method=RequestMethod.POST)
	@ResponseBody
    public void likeTopic(@RequestBody Like like) {
		System.out.println("用户Id:"+like.getUserId());
		if(topicDao.isLike(like.getUserId(), like.getTopicId())==0) {//如果该用户没有点赞该话题
			like.setLikeDate(new Date());
			topicDao.likeTopic(like);//点赞
		}else {//取消点赞
			topicDao.cancelLikeTopic(like);
		}
		updateLikeNumber(like.getTopicId());
    }
	
	public void updateLikeNumber(Integer topicId) {
		int likeNumber=topicDao.countToicLike(topicId);
		topicDao.updateLikeNumber(likeNumber, topicId);
	}

}
