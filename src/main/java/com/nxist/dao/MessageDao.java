package com.nxist.dao;

import java.util.List;

import com.nxist.model.Message;

public interface MessageDao {
	//新增消息
	public abstract int add(Message message);
	
	//查找所有消息
	public abstract List<Message> findAllMessage();
	
	//根据messageId查询某条消息
	public abstract Message findMessageByMessageId(int messageId);
	
	//插入没有图片的消息
	public abstract void insertMessageWithoutPicture(Message message);
	
	//删除话题
	public abstract void deleteMessage(int messageId);
	
	//修改消息
	public abstract void updateMessageWithoutPicture(Message message);

}
