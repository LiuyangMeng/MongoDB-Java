package controller;

import java.nio.file.DirectoryStream.Filter;
import java.sql.Timestamp;
import java.util.Date;

import org.bson.BSON;
import org.bson.Document;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import BaseDao.MongoDBPool;

public class MongoDB {

	public static void main(String[] args) {
		MongoDatabase mdb = MongoDBPool.getDB();
		// 确保链接数据库正常
		if (null == mdb) {
			System.out.println("链接mongodb异常");
			return;
		}

		/*
		 * //创建一个集合 mdb.createCollection("javacol");
		 * 
		 * //获取一个集合 MongoCollection<Document> collection=
		 * mdb.getCollection("javacol"); //查询到数据，迭代出数值 if(collection.count()>0){
		 * //获取迭代器 FindIterable<Document> fid=collection.find(); //获取游标
		 * MongoCursor<Document> mcd=fid.iterator(); while(mcd.hasNext()){
		 * System.out.println(mcd.next()); } }
		 */

		// 插入文档
		/*
		 * 1. 创建文档 org.bson.Document 参数为key-value的格式 2. 创建文档集合List<Document> 3.
		 * 将文档集合插入数据库集合中 mongoCollection.insertMany(List<Document>) 插入单个文档可以用
		 * mongoCollection.insertOne(Document)
		 * 
		 * Document document=new Document("title", "java MongoDB new"
		 * ).append("content", "使用java插入mongodb数据new").append("showcount", 9);
		 * document.append("inserttime", new Date());
		 * 
		 * //选择一个将要插入的集合 MongoCollection<Document>
		 * collections=mdb.getCollection("javacol");
		 * 
		 * //将文档插入选择的集合中 collections.insertOne(document);
		 */

		// 更新文档
		// 获取一个集合
		MongoCollection<Document> collection = mdb.getCollection("javacol");
		// 查询到数据，迭代出数值
		if (collection.count() > 0) {
			// 获取迭代器
			FindIterable<Document> fid = collection.find();
			// 获取游标
			MongoCursor<Document> mcd = fid.iterator();
			while (mcd.hasNext()) {
				System.out.println(mcd.next());
			}
		}
		
		// 将文档中showcount小于20的更新问25
		MongoCollection<Document> collections = mdb.getCollection("javacol");
		//第一种更新
		//collections.updateMany(Filters.lt("showcount", 20), new Document("$set", new Document("showcount", 25)));
		
		//第二种更新 dbpar where一个或多个条件 and
		BasicDBObject dbpar=new BasicDBObject();
		dbpar.append("showcount", new BasicDBObject("$lt",25));
		dbpar.append("title", "java MongoDB");
		
		
		//or操作 paror 某几个条件or
		BasicDBList dblist=new BasicDBList();
		dblist.add(new BasicDBObject("showcount", new BasicDBObject("$lt",25)));
		dblist.add(new BasicDBObject("title", "java MongoDB"));
		BasicDBObject paror=new BasicDBObject("$or",dblist);
		
		//in操作 parin 
		BasicDBList dbinlist=new BasicDBList();
		dbinlist.add(21);
		dbinlist.add(27);
		BasicDBObject parin=new BasicDBObject("showcount",new BasicDBObject("$in",dbinlist));
		
		//任意两个结合
		BasicDBObject bdo2=new BasicDBObject();
		
		BasicDBList dbor=new BasicDBList();
		
		dbor.add(new BasicDBObject("likes",new BasicDBObject("$exists",false)));
		//dbor.add(new BasicDBObject("likes",new BasicDBObject("$ne",null)));
		//dbor.add(new BasicDBObject("likes",null));
		dbor.add(new BasicDBObject("showcount", 25));

		
		bdo2.put("$or",dbor);
		bdo2.put("content","使用java插入mongodb数据new1");
		
		
		//set值
		BasicDBObject dbinc=new BasicDBObject("showcount", 0);
		BasicDBObject dbset=new BasicDBObject("title", "update Mongodb java0");
		
		BasicDBObject dbsetall=new BasicDBObject();
		dbsetall.put("$inc", dbinc);
		dbsetall.put("$set", dbset);
		
		//更新核心语句
		//collections.updateMany(bdo2, dbsetall);
		
		//删除文档1
		//collections.deleteOne(Filters.eq("showcount",25 ));
		//删除文档2
		BasicDBObject bpar=new BasicDBObject();
		bpar.append("showcount", new BasicDBObject("$lt",25));
		//collections.deleteMany(bpar);
		
		
		System.out.println("更新后数据");
		collection = mdb.getCollection("javacol");
		// 查询到数据，迭代出数值
		if (collection.count() > 0) {
			// 获取迭代器
			FindIterable<Document> fid = collection.find();
			// 获取游标
			MongoCursor<Document> mcd = fid.iterator();
			while (mcd.hasNext()) {
				System.out.println(mcd.next());
			}
		}
		
		
		
		
		//自动增长的id
		//创建counters集合，序列字段可以自增长
		
		//mdb.createCollection("counters");
		
		//插入一下文档，使用productid为key
		
		//mdb.getCollection("counters").insertOne(new Document().append("_id", "productid").append("sequence_value", 0));
		
		//创建一个函数，指定的序列会自动增长 1 并返回最新序列值
		//int id=getNextSequenceValue("productid", mdb);
		
		//插入一条记录
		Document incdoc=new Document();
		incdoc.append("_id", getNextSequenceValue("productid", mdb));
		incdoc.append("productname", "samsung");
		incdoc.append("category", "mobiles");
		mdb.getCollection("products").insertOne(incdoc);

	}
	
	
	//创建一个函数，指定的序列会自动增长 1 并返回最新序列值
	public static int getNextSequenceValue(String key,MongoDatabase mdb){
		Document doc= mdb.getCollection("counters").findOneAndUpdate(new BasicDBObject("_id",key), new BasicDBObject("$inc",new BasicDBObject("sequence_value",1)));
		return Integer.parseInt(doc.get("sequence_value").toString());
	}

}
