package com.nxist.dao;

import java.util.List;

import com.nxist.model.User;

public interface UserDao {
	//通过用户账户查找用户
	public abstract User findByUserAccount(String userAccount);
	
	//通过用户Id查找用户
	public abstract User findUserByUserId(int userId);
	
	//插入用户到数据库
	public abstract void insertUser(User user);
	
	//插入用户到数据库
	public abstract void updateUser(User user);
	
	//通过用户Id查找用户名
	public abstract String findUserNameByUserId(int userId);
	
	//通过userName查找用户名
	public abstract User findUserByUserName(String userName);
	
	//查找所有用户
	public abstract List<User> findAllUser();
	
	//修改数据库中用户头像文件名
	public abstract void updateAvatar(User user);
}
