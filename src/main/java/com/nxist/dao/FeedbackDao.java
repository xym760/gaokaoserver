package com.nxist.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nxist.model.Feedback;
import com.nxist.model.FeedbackCustom;

public interface FeedbackDao {
	//新增反馈
	public abstract int add(Feedback feedback);
	
	//获取总记录数
	public abstract int getFeedbackSum();
	
	//查找所有反馈
	public abstract List<FeedbackCustom> findAllFeedback(@Param("start") Integer start,@Param("limit") Integer limit);
	
	//根据feedbackId查询某条反馈
	public abstract Feedback findFeedbackByFeedbackId(int feedbackId);
	
	//插入没有图片的反馈
	public abstract void insertFeedbackWithoutPicture(Feedback feedback);
	
	//删除反馈
	public abstract void deleteFeedback(int feedbackId);
	
	//修改数据库中反馈图片文件名
	public abstract void updatePicture(Feedback feedback);
	
	//审核反馈
	public abstract void auditFeedback(@Param("feedbackId")int feedbackId);
	
	//撤销审核反馈
	public abstract void cancelAuditFeedback(@Param("feedbackId")int feedbackId);

}
