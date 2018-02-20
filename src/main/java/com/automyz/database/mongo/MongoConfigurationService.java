package com.automyz.database.mongo;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.bson.Document;

import com.automyz.database.mongo.util.MongoUtil;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

public class MongoConfigurationService {

	private static MongoConfigurationService instance = createInstance();

	private MongoUtil mongoUtil;

	/*
	 * propertiesFilePath for Mongo initialization.
	 */
	private static final String propertiesFilePath = "mongodb.conf";

	private static MongoConfigurationService createInstance() {
		MongoConfigurationService mongoConfigHelper = new MongoConfigurationService();
		mongoConfigHelper.initialize();
		return mongoConfigHelper;
	}

	private synchronized void initialize() {
		this.setMongoUtil(MongoUtil.createInstance(propertiesFilePath));
	}

	public static MongoConfigurationService getInstance() {
		return instance;
	}

	public Properties getProperties() {
		return this.mongoUtil.getProperties();
	}

	public MongoUtil getMongoUtil() {
		return this.mongoUtil;
	}

	public void setMongoUtil(MongoUtil argMongoUtil) {
		this.mongoUtil = argMongoUtil;
	}

	public MongoClient getMongo() {
		return this.mongoUtil.getMongo();
	}

	public void close() {
		this.mongoUtil.close();
	}

	public MongoDatabase getDb(String argDatabaseName) {
		MongoClient mongoClient = getMongo();
		return mongoClient.getDatabase(argDatabaseName);
	}

	public List<String> getDatabaseNames() {
		MongoClient mongoClient = getMongo();
		MongoCursor<String> iterator = mongoClient.listDatabaseNames().iterator();
		List<String> databaseNames = new ArrayList<>();
		while (iterator.hasNext()) {
			databaseNames.add(iterator.next());
		}
		return databaseNames;
	}

	public MongoCollection<Document> getCollection(String argDatabaseName, String argCollectionName) {
		MongoDatabase db = getDb(argDatabaseName);
		return db.getCollection(argCollectionName);
	}

	public List<String> getCollectionNames(String argDatabaseName) {
		List<String> collectionNameList = new ArrayList<>();
		if (argDatabaseName != null && !argDatabaseName.isEmpty()) {
			MongoDatabase mongoDatabase = getDb(argDatabaseName);
			if (mongoDatabase != null) {
				MongoIterable<String> iterable = mongoDatabase.listCollectionNames();
				MongoCursor<String> iterator = iterable.iterator();
				while (iterator.hasNext()) {
					String collectionName = iterator.next();
					collectionNameList.add(collectionName);
				}
			}
		}
		return collectionNameList;
	}
}
