package BaseDao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientOptions.Builder;
import com.mongodb.MongoCredential;
import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoDatabase;

import utils.PropertyUtils;

/**
 * ʹ����������mongodb���ݿ�
 * 
 * @author DLHT
 *
 */

public class MongoDBPool {
	// ����ȫ�ֱ���mongoclient
	private static MongoClient mongoClient = null;

	// ����getdb�����������ⲿ���÷���
	public static MongoDatabase getDB() {
		MongoDatabase conn = null;
		// ���mongoclientδ��ʼ�������ȳ�ʼ��
		if (mongoClient == null) {
			// �����Լ���Ҫ���ӣ������ʼ������֤��
			initMongoDBAuth();

			// ���辭����֤
			// initMongoDBNoAuth();
		}
		conn = mongoClient.getDatabase(PropertyUtils.getValue("database"));
		return conn;
	}

	// mongodb ���ӳز���
	private static MongoClientOptions mongoClientOptions() {
		Builder builder = new MongoClientOptions.Builder();
		builder.connectionsPerHost(100);// ÿ�����������������
		builder.threadsAllowedToBlockForConnectionMultiplier(50);// �������ȴ���
		builder.minConnectionsPerHost(10);// ÿ����������С������
		builder.connectTimeout(5000);// ���ӳ�ʱʱ��
		builder.heartbeatConnectTimeout(5000);// ��̨���ÿ��mongoclient���ӵĳ�ʱʱ��
		builder.heartbeatFrequency(100000);// ��̨���ÿ��mongoclient���ӵ�Ƶ��
		builder.minHeartbeatFrequency(10000);// ��̨���ÿ��mongoclient���ӵ���СƵ��
		builder.heartbeatSocketTimeout(2000);// ��̨���ÿ��mongoclient���׽��ֳ�ʱʱ��
		builder.maxConnectionIdleTime(10000);// ����������ʱ��
		builder.maxConnectionLifeTime(100000);// �������ʱ��
		builder.maxWaitTime(10000);// ���ȴ�ʱ��
		builder.serverSelectionTimeout(50000);// ������ѡ��ʱʱ��
		builder.socketKeepAlive(false);// �����׽������ô��
		builder.socketTimeout(1000);// �׽��ֳ�ʱʱ��
		builder.sslEnabled(false);// �Ƿ�����ssl
		builder.sslInvalidHostNameAllowed(false);// �Ƿ�������Ч��ssl����
		builder.readConcern(ReadConcern.DEFAULT);// д ��ע ʲô��
		builder.writeConcern(WriteConcern.ACKNOWLEDGED);// ͬ��
		builder.readPreference(ReadPreference.nearest());// ������Ȳ���
		return builder.build();
	}

	/**
	 * ������֤�˺���������,�������ݿ�����
	 */
	private static void initMongoDBNoAuth() {
		// ���ӵ�MongoDB���� �����Զ�����ӿ����滻��localhost��Ϊ����������IP��ַ
		// ServerAddress()���������ֱ�Ϊ ��������ַ �� �˿�
		ServerAddress serverAddress = new ServerAddress(PropertyUtils.getValue("server"),
				Integer.parseInt(PropertyUtils.getValue("port")));

		List<ServerAddress> addrs = new ArrayList<ServerAddress>();
		addrs.add(serverAddress);

		// ����������֤���ܻ�ȡ�����ݿ�����
		mongoClient = new MongoClient(addrs, mongoClientOptions());

	}

	/**
	 * ��֤�˺��������ӣ��������ݿ�����
	 */
	private static void initMongoDBAuth() {
		// ���ӵ�MongoDB���� �����Զ�����ӿ����滻��localhost��Ϊ����������IP��ַ
		// ServerAddress()���������ֱ�Ϊ ��������ַ �� �˿�
		ServerAddress serverAddress = new ServerAddress(PropertyUtils.getValue("server"),
				Integer.parseInt(PropertyUtils.getValue("port")));

		List<ServerAddress> addrs = new ArrayList<ServerAddress>();
		addrs.add(serverAddress);

		// MongoCredential.createScramSha1Credential()���������ֱ�Ϊ �û��� ���ݿ����� ����
		MongoCredential credential = MongoCredential.createScramSha1Credential(PropertyUtils.getValue("user"),
				PropertyUtils.getValue("database"), PropertyUtils.getValue("password").toCharArray());

		List<MongoCredential> credentials = new ArrayList<MongoCredential>();
		credentials.add(credential);

		// ����ͨ��������֤���ܻ�ȡ�����ݿ�����
		mongoClient = new MongoClient(addrs, credentials, mongoClientOptions());
	}

	/**
	 * �ر�����
	 */
	public static void closeMongoClient() {
		if (null != mongoClient) {
			mongoClient.close();
			mongoClient = null;
		}
	}
	

	public static void main(String[] args) {
		
		//������־��ʽ
		Logger mongologger=Logger.getLogger("");
		mongologger.setLevel(Level.WARNING);
		

		try {
			System.out.println(MongoDBPool.getDB().getCollection("javacol").find().first().toJson());
			Thread.sleep(3000);
			System.out.println(MongoDBPool.getDB().getCollection("javacol").find().first().toJson());
			Thread.sleep(3000);
			System.out.println(MongoDBPool.getDB().getCollection("javacol").find().first().toJson());
			MongoDBPool.closeMongoClient();
			Thread.sleep(3000);
			System.out.println(MongoDBPool.getDB().getCollection("javacol").find().first().toJson());

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
