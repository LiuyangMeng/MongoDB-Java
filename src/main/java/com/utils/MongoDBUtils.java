package com.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bson.Document;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.util.JSON;

/**
 * 将mongodb的返回数据解析为json格式
 * 
 * @author DLHT
 *
 */
public class MongoDBUtils {
	/*
	 * 将查询结果转为json FindIterable<Document> to json
	 */
	public static List<Map<String, Object>> findItDTOJson(FindIterable<Document> fid) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// 空数据直接返回
		if (null == fid) {
			return null;
		}

		// 定义返回变量
		MongoCursor<Document> mcd = fid.iterator();
		while (mcd.hasNext()) {
			Document doc = mcd.next();
			Set<Entry<String, Object>> sets = doc.entrySet();
			Iterator<Entry<String, Object>> its = sets.iterator();
			Map<String, Object> hmap = new HashMap<String, Object>();
			while (its.hasNext()) {
				Entry<String, Object> eso = its.next();
				hmap.put(eso.getKey(), eso.getValue());
			}
			list.add(hmap);

		}
		return list;
	}
}
