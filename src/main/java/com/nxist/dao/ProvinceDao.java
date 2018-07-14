package com.nxist.dao;

import java.util.List;

import com.nxist.model.Province;

public interface ProvinceDao {
	//查询所有省
	public abstract List<Province> getProvinceList();
}
