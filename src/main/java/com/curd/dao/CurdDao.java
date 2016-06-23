package com.curd.dao;

import java.util.List;
import java.util.Map;



/**
 * 简单的增删改查 接口
 * 
 * @author DLHT
 */
public interface CurdDao {
	/*
	 * 获取全部用户信息
	 */
	public List<Map<String, Object>> getAllUser();
}
