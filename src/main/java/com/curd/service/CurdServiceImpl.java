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

}
