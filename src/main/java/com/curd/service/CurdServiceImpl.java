package com.curd.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curd.dao.CurdDao;

/**
 * 简单的增删改查 控制层接口实现
 * 
 * @author DLHT
 *
 */
@Service("CurdService")
public class CurdServiceImpl implements CurdService {
	@Autowired
	private CurdDao curdDao;

	/*
	 * 获取所有用户信息
	 * 
	 * @see com.curd.service.CurdService#getAllUser()
	 */
	public List<Map<String, Object>> getAllUser() {
		return curdDao.getAllUser();
	}
	/*
	 * 根据id删除
	 * 
	 * @see com.curd.service.CurdService#delUserById(java.lang.String)
	 */

	public long delUserById(String id) {
		return curdDao.delUserById(id);
	}

	/*
	 * 根据objectid判断是插入还是更新数据
	 * 
	 * @see com.curd.service.CurdService#saveOrUpdateUser(java.lang.Object[])
	 */
	public long saveOrUpdateUser(Object[] params) {
		//新增数据
		if(params[0].toString().equals("0")){
			return curdDao.saveUser(params);
		//更新数据
		}else{
			return curdDao.updateUser(params);
		}
	}

}
