package com.curd.service;
/**
 * 简单的增删改查 控制层接口
 * @author DLHT
 *
 */

import java.util.List;
import java.util.Map;


public interface CurdService {

	/*
	 * 获取全部用户信息
	 */
	public List<Map<String, Object>> getAllUser();
}
