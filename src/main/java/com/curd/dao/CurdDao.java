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
	/*
	 * 通过用户id删除用户
	 */
	public long delUserById(String id);
	/*
	 * 新增一个用户
	 */
	public long saveUser(Object[] params);
	/*
	 * 更新一个用户
	 */
	public long updateUser(Object[] params);
}
