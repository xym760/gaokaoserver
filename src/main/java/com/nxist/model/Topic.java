package com.nxist.model;

import java.util.Date;

public class Topic {
	private Integer topicId;
	private Integer userId;
	private Date date;
	private String content;
	private String pictureList;
	private String location;
	private Integer commentNumber;
	private Integer browseNumber;
	private Integer likeNumber;
	public Topic() {
        super();
    }
	public Integer getTopicId() {
		return topicId;
	}
	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPictureList() {
		return pictureList;
	}
	public void setPictureList(String pictureList) {
		this.pictureList = pictureList;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Integer getCommentNumber() {
		return commentNumber;
	}
	public void setCommentNumber(Integer commentNumber) {
		this.commentNumber = commentNumber;
	}
	public Integer getBrowseNumber() {
		return browseNumber;
	}
	public void setBrowseNumber(Integer browseNumber) {
		this.browseNumber = browseNumber;
	}
	public Integer getLikeNumber() {
		return likeNumber;
	}
	public void setLikeNumber(Integer likeNumber) {
		this.likeNumber = likeNumber;
	}
}
