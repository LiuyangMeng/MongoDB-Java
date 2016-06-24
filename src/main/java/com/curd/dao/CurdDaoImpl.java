package com.curd.dao;

import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.base.MongoDBPool;
import com.mongodb.BasicDBObject;
import com.mongodb.client.result.DeleteResult;
import com.utils.MongoDBUtils;

/**
 * 简单的增删改查 实现
 * 
 * @author DLHT
 *
 */
@Repository("CurdDao")
public class CurdDaoImpl extends MongoDBPool implements CurdDao {
	/*
	 * 获取所有用户信息
	 * 
	 * @see com.curd.dao.CurdDao#getAllUser()
	 */
	public List<Map<String, Object>> getAllUser() {
		return MongoDBUtils.findItDTOJson(MongoDBPool.getDB().getCollection("user").find());
	}

	public long delUserById(String id) {
		return MongoDBPool.getDB().getCollection("user").deleteOne(new BasicDBObject("_id", new ObjectId(id))).getDeletedCount();
	}

}
