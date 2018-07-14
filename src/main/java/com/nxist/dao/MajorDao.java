package com.nxist.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nxist.model.Collect;
import com.nxist.model.Major;


public interface MajorDao {
	//获取所有专业
	public abstract List<Major> findAllMajor();
	
	//根据专业代码查看专业
	public abstract Major findMajorByMajorCode(String majorCode);
	
	//根据专业代码查看专业
	public abstract List<Major> findMajorListByMajorCode(String majorCode);
	
	//根据专业名查看专业
	public abstract Major findMajorByMajorName(String majorName);
	
	//查看特定用户是否关注指定的专业,返回0表示没有关注
	public abstract int isCollect(@Param("userId") Integer userId,@Param("majorId") Integer majorId);
	
	//关注专业
	public abstract void collectMajor(Collect collect);
	
	//取消关注专业
	public abstract void cancelCollect(@Param("userId") Integer userId,@Param("majorId") Integer majorId);
	
	//获取总记录数
	public abstract int getMajorSum(@Param("majorName") String majorName,@Param("majorCode") String majorCode);
	
	//查找所有专业（有限制）
	public abstract List<Major> findAllMajorLimit(@Param("start") Integer start,@Param("limit") Integer limit,@Param("majorName") String majorName,@Param("majorCode") String majorCode);
	
	//修改专业门类
	public abstract void updateMajorCategory(@Param("majorId") Integer majorId,@Param("category") String category);
	
	//获取用户关注的所有专业
	public abstract List<Major> findAllCollectMajor(@Param("userId") Integer userId);
	
	//查询指定专业的关注日期
	public abstract String findCollectDateByMajorId(@Param("majorId") Integer majorId,@Param("userId") Integer userId);
}
