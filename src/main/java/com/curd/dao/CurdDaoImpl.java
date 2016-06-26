package com.curd.dao;

import java.util.List;
import java.util.Map;

import org.bson.Document;
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

	/*
	 * 根据id删除用户
	 * 
	 * @see com.curd.dao.CurdDao#delUserById(java.lang.String)
	 */
	public long delUserById(String id) {
		return MongoDBPool.getDB().getCollection("user").deleteOne(new BasicDBObject("_id", new ObjectId(id)))
				.getDeletedCount();
	}
	/*
	 * 新增一个用户 objectid,name,age,like
	 * 
	 * @see com.curd.dao.CurdDao#saveUser(java.lang.Object[])
	 */

	public long saveUser(Object[] params) {
		// 封装新增数据
		Document dsave = new Document();
		dsave.append("name", params[1]).append("age", params[2])
				.append("likes", params[3]);
		MongoDBPool.getDB().getCollection("user").insertOne(dsave);
		return 1;
	}

	/*
	 * 根据objectid更新一个用户 objectid,name,age,like
	 * 
	 * @see com.curd.dao.CurdDao#updateUser(java.lang.Object[])
	 */
	public long updateUser(Object[] params) {
		// 封装更新数据
		BasicDBObject upuser = new BasicDBObject();
		upuser.append("$set",
				new BasicDBObject().append("name", params[1]).append("age", params[2]).append("likes", params[3]));
		return MongoDBPool.getDB().getCollection("user")
				.updateOne(new BasicDBObject("_id", new ObjectId(params[0].toString())), upuser).getModifiedCount();
	}

}
