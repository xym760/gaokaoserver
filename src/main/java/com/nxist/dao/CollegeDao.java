package com.nxist.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nxist.model.Collect;
import com.nxist.model.College;

public interface CollegeDao {
	
	//根据院校名查询院校
	public abstract College findCollegeByCollegeName(String collegeName);
	
	//根据院校名查询院校
	public abstract College findCollegeByCollegeId(int collegeId);
		
	//插入一个院校
	public abstract int insertCollege(@Param("collegeId") Integer collegeId,@Param("name") String name,@Param("formerName") String formerName,@Param("province") String province,@Param("grade") String grade,
			@Param("property") String property,@Param("directlyUnder") Boolean directlyUnder,@Param("runNature") String runNature,@Param("intro") String intro,@Param("code") Integer code,
			@Param("ranking") Integer ranking,@Param("website") String website,@Param("telephone") String telephone,@Param("address") String address,@Param("mailbox") String mailbox);
	//获取所有院校
	public abstract List<College> findAllCollege();
	
	//查找所有院校（有限制）
	public abstract List<College> findAllCollegeLimit(@Param("start") Integer start,@Param("limit") Integer limit,@Param("province") String province,@Param("name") String name);
	
	//获取用户关注的所有院校
	public abstract List<College> findAllCollectCollege(@Param("userId") Integer userId);
	
	//查看特定用户是否关注指定的院校,返回0表示没有关注
	public abstract int isCollect(@Param("userId") Integer userId,@Param("collegeId") Integer collegeId);
	
	//关注院校
	public abstract void collectCollege(Collect collect);
	
	//取消关注院校
	public abstract void cancelCollect(@Param("userId") Integer userId,@Param("collegeId") Integer collegeId);
	
	//查询指定大学的关注日期
	public abstract String findCollectDateByCollegeId(@Param("collegeId") Integer collegeId,@Param("userId") Integer userId);
	
	//获取总记录数
	public abstract int getCollegeSum(@Param("province") String province,@Param("name") String name);
	
	//修改院校
	public abstract void updateCollege(College college);
	
	//删除院校
	public abstract void deleteCollege(College college);
	
	//删除关注通过collegeId
	public abstract void deleteCollectByCollegeId(int collegeId);
	
	//查询在排名范围之内的学校
	public abstract List<College> searchCollegeByRankRange(@Param("small") Integer small,@Param("big") Integer big);
}
