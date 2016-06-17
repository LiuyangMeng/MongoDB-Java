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
		// ȷ���������ݿ�����
		if (null == mdb) {
			System.out.println("����mongodb�쳣");
			return;
		}

		/*
		 * //����һ������ mdb.createCollection("javacol");
		 * 
		 * //��ȡһ������ MongoCollection<Document> collection=
		 * mdb.getCollection("javacol"); //��ѯ�����ݣ���������ֵ if(collection.count()>0){
		 * //��ȡ������ FindIterable<Document> fid=collection.find(); //��ȡ�α�
		 * MongoCursor<Document> mcd=fid.iterator(); while(mcd.hasNext()){
		 * System.out.println(mcd.next()); } }
		 */

		// �����ĵ�
		/*
		 * 1. �����ĵ� org.bson.Document ����Ϊkey-value�ĸ�ʽ 2. �����ĵ�����List<Document> 3.
		 * ���ĵ����ϲ������ݿ⼯���� mongoCollection.insertMany(List<Document>) ���뵥���ĵ�������
		 * mongoCollection.insertOne(Document)
		 * 
		 * Document document=new Document("title", "java MongoDB new"
		 * ).append("content", "ʹ��java����mongodb����new").append("showcount", 9);
		 * document.append("inserttime", new Date());
		 * 
		 * //ѡ��һ����Ҫ����ļ��� MongoCollection<Document>
		 * collections=mdb.getCollection("javacol");
		 * 
		 * //���ĵ�����ѡ��ļ����� collections.insertOne(document);
		 */

		// �����ĵ�
		// ��ȡһ������
		MongoCollection<Document> collection = mdb.getCollection("javacol");
		// ��ѯ�����ݣ���������ֵ
		if (collection.count() > 0) {
			// ��ȡ������
			FindIterable<Document> fid = collection.find();
			// ��ȡ�α�
			MongoCursor<Document> mcd = fid.iterator();
			while (mcd.hasNext()) {
				System.out.println(mcd.next());
			}
		}
		
		// ���ĵ���showcountС��20�ĸ�����25
		MongoCollection<Document> collections = mdb.getCollection("javacol");
		//��һ�ָ���
		//collections.updateMany(Filters.lt("showcount", 20), new Document("$set", new Document("showcount", 25)));
		
		//�ڶ��ָ��� dbpar whereһ���������� and
		BasicDBObject dbpar=new BasicDBObject();
		dbpar.append("showcount", new BasicDBObject("$lt",25));
		dbpar.append("title", "java MongoDB");
		
		
		//or���� paror ĳ��������or
		BasicDBList dblist=new BasicDBList();
		dblist.add(new BasicDBObject("showcount", new BasicDBObject("$lt",25)));
		dblist.add(new BasicDBObject("title", "java MongoDB"));
		BasicDBObject paror=new BasicDBObject("$or",dblist);
		
		//in���� parin 
		BasicDBList dbinlist=new BasicDBList();
		dbinlist.add(21);
		dbinlist.add(27);
		BasicDBObject parin=new BasicDBObject("showcount",new BasicDBObject("$in",dbinlist));
		
		//�����������
		BasicDBObject bdo2=new BasicDBObject();
		
		BasicDBList dbor=new BasicDBList();
		
		dbor.add(new BasicDBObject("likes",new BasicDBObject("$exists",false)));
		//dbor.add(new BasicDBObject("likes",new BasicDBObject("$ne",null)));
		//dbor.add(new BasicDBObject("likes",null));
		dbor.add(new BasicDBObject("showcount", 25));

		
		bdo2.put("$or",dbor);
		bdo2.put("content","ʹ��java����mongodb����new1");
		
		
		//setֵ
		BasicDBObject dbinc=new BasicDBObject("showcount", 0);
		BasicDBObject dbset=new BasicDBObject("title", "update Mongodb java0");
		
		BasicDBObject dbsetall=new BasicDBObject();
		dbsetall.put("$inc", dbinc);
		dbsetall.put("$set", dbset);
		
		//���º������
		//collections.updateMany(bdo2, dbsetall);
		
		//ɾ���ĵ�1
		//collections.deleteOne(Filters.eq("showcount",25 ));
		//ɾ���ĵ�2
		BasicDBObject bpar=new BasicDBObject();
		bpar.append("showcount", new BasicDBObject("$lt",25));
		//collections.deleteMany(bpar);
		
		
		System.out.println("���º�����");
		collection = mdb.getCollection("javacol");
		// ��ѯ�����ݣ���������ֵ
		if (collection.count() > 0) {
			// ��ȡ������
			FindIterable<Document> fid = collection.find();
			// ��ȡ�α�
			MongoCursor<Document> mcd = fid.iterator();
			while (mcd.hasNext()) {
				System.out.println(mcd.next());
			}
		}
		
		
		
		
		//�Զ�������id
		//����counters���ϣ������ֶο���������
		
		//mdb.createCollection("counters");
		
		//����һ���ĵ���ʹ��productidΪkey
		
		//mdb.getCollection("counters").insertOne(new Document().append("_id", "productid").append("sequence_value", 0));
		
		//����һ��������ָ�������л��Զ����� 1 ��������������ֵ
		//int id=getNextSequenceValue("productid", mdb);
		
		//����һ����¼
		Document incdoc=new Document();
		incdoc.append("_id", getNextSequenceValue("productid", mdb));
		incdoc.append("productname", "samsung");
		incdoc.append("category", "mobiles");
		mdb.getCollection("products").insertOne(incdoc);

	}
	
	
	//����һ��������ָ�������л��Զ����� 1 ��������������ֵ
	public static int getNextSequenceValue(String key,MongoDatabase mdb){
		Document doc= mdb.getCollection("counters").findOneAndUpdate(new BasicDBObject("_id",key), new BasicDBObject("$inc",new BasicDBObject("sequence_value",1)));
		return Integer.parseInt(doc.get("sequence_value").toString());
	}

}
