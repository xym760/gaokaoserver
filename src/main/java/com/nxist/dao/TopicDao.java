package com.nxist.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nxist.model.College;
import com.nxist.model.Like;
import com.nxist.model.Reply;
import com.nxist.model.Topic;
import com.nxist.model.TopicData;

public interface TopicDao {
	//插入一个没有图片的话题
	public abstract void insertTopicWithoutPicture(Topic topic);
	
	//获取总记录数
	public abstract int getTopicSum(@Param("userId") Integer userName,@Param("startDate") String startDate);
	
	//获取所有话题
	public abstract List<TopicData> findAllTopic();
	
	//获取所有话题（有限制）
	public abstract List<TopicData> findAllTopicLimit(@Param("start") Integer start,@Param("limit") Integer limit,@Param("userId") Integer userName,@Param("startDate") String startDate);
	
	//查看某条话题是否点赞,返回0表示没有点赞
	public abstract int isLike(@Param("userId") Integer userId,@Param("topicId") Integer topicId);
	
	//对话题点赞
	public abstract void likeTopic(Like like);
	
	//对话题取消点赞
	public abstract void cancelLikeTopic(Like like);
	
	//计算某条话题的点赞数
	public abstract int countToicLike(@Param("topicId") Integer topicId);
	
	//修改某条话题的点赞数
	public abstract void updateLikeNumber(@Param("likeNumber") Integer likeNumber,@Param("topicId") Integer topicId);
	
	//对话题评论
	public abstract void replyTopic(Reply reply);
	
	//计算某条话题的点赞数
	public abstract int countToicReply(@Param("topicId") Integer topicId);
	
	//修改某条话题的回复数
	public abstract void updateRePlyNumber(@Param("replyNumber") Integer replyNumber,@Param("topicId") Integer topicId);
	
	//查看某条TopicData通过topicId
	public abstract TopicData findTopicByTopicId(Integer topicId);
	
	//查看话题Id查看该话题的所有回复
	public abstract List<Reply> getReplyByTopicId(int topicId);
	
	//删除话题通过topicId
	public abstract void deleteTopic(int topicId);
	
	//删除话题评论通过topicId
	public abstract void deleteTopicReplyByTopicId(int topicId);
	
	//删除话题点赞通过topicId
	public abstract void deleteTopicLikeByTopicId(int topicId);
}
