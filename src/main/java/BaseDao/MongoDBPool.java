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
 * 使用驱动链接mongodb数据库
 * 
 * @author DLHT
 *
 */

public class MongoDBPool {
	// 建立全局变量mongoclient
	private static MongoClient mongoClient = null;

	// 建立getdb方法，给出外部调用方法
	public static MongoDatabase getDB() {
		MongoDatabase conn = null;
		// 如果mongoclient未初始化，则先初始化
		if (mongoClient == null) {
			// 根据自己需要链接，这里初始化带认证的
			initMongoDBAuth();

			// 无需经过认证
			// initMongoDBNoAuth();
		}
		conn = mongoClient.getDatabase(PropertyUtils.getValue("database"));
		return conn;
	}

	// mongodb 连接池参数
	private static MongoClientOptions mongoClientOptions() {
		Builder builder = new MongoClientOptions.Builder();
		builder.connectionsPerHost(100);// 每个主机的最大链接数
		builder.threadsAllowedToBlockForConnectionMultiplier(50);// 最大请求等待数
		builder.minConnectionsPerHost(10);// 每个主机的最小链接数
		builder.connectTimeout(5000);// 链接超时时间
		builder.heartbeatConnectTimeout(5000);// 后台检测每个mongoclient链接的超时时间
		builder.heartbeatFrequency(100000);// 后台检测每个mongoclient链接的频率
		builder.minHeartbeatFrequency(10000);// 后台检测每个mongoclient链接的最小频率
		builder.heartbeatSocketTimeout(2000);// 后台检测每个mongoclient的套接字超时时间
		builder.maxConnectionIdleTime(10000);// 最大空闲链接时间
		builder.maxConnectionLifeTime(100000);// 最大生命时间
		builder.maxWaitTime(10000);// 最大等待时间
		builder.serverSelectionTimeout(50000);// 服务器选择超时时间
		builder.socketKeepAlive(false);// 设置套接字永久存活
		builder.socketTimeout(1000);// 套接字超时时间
		builder.sslEnabled(false);// 是否启用ssl
		builder.sslInvalidHostNameAllowed(false);// 是否允许无效的ssl链接
		builder.readConcern(ReadConcern.DEFAULT);// 写 关注 什么鬼
		builder.writeConcern(WriteConcern.ACKNOWLEDGED);// 同上
		builder.readPreference(ReadPreference.nearest());// 最近优先策略
		return builder.build();
	}

	/**
	 * 无需验证账号密码链接,返回数据库连接
	 */
	private static void initMongoDBNoAuth() {
		// 连接到MongoDB服务 如果是远程连接可以替换“localhost”为服务器所在IP地址
		// ServerAddress()两个参数分别为 服务器地址 和 端口
		ServerAddress serverAddress = new ServerAddress(PropertyUtils.getValue("server"),
				Integer.parseInt(PropertyUtils.getValue("port")));

		List<ServerAddress> addrs = new ArrayList<ServerAddress>();
		addrs.add(serverAddress);

		// 无需链接认证才能获取到数据库连接
		mongoClient = new MongoClient(addrs, mongoClientOptions());

	}

	/**
	 * 验证账号密码链接，返回数据库连接
	 */
	private static void initMongoDBAuth() {
		// 连接到MongoDB服务 如果是远程连接可以替换“localhost”为服务器所在IP地址
		// ServerAddress()两个参数分别为 服务器地址 和 端口
		ServerAddress serverAddress = new ServerAddress(PropertyUtils.getValue("server"),
				Integer.parseInt(PropertyUtils.getValue("port")));

		List<ServerAddress> addrs = new ArrayList<ServerAddress>();
		addrs.add(serverAddress);

		// MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
		MongoCredential credential = MongoCredential.createScramSha1Credential(PropertyUtils.getValue("user"),
				PropertyUtils.getValue("database"), PropertyUtils.getValue("password").toCharArray());

		List<MongoCredential> credentials = new ArrayList<MongoCredential>();
		credentials.add(credential);

		// 必须通过链接认证才能获取到数据库连接
		mongoClient = new MongoClient(addrs, credentials, mongoClientOptions());
	}

	/**
	 * 关闭连接
	 */
	public static void closeMongoClient() {
		if (null != mongoClient) {
			mongoClient.close();
			mongoClient = null;
		}
	}
	

	public static void main(String[] args) {
		
		//控制日志格式
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
