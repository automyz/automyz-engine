package com.automyz.database.mongo.operation.base;

import java.util.Collection;
import java.util.List;

import org.bson.Document;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.WriteModel;
import com.mongodb.client.result.UpdateResult;

public interface MongoCollectionService<T> {

	public void createCollection(T argCollectionContext);

	public boolean isCollectionExists(String argDatabaseName, String argCollectionName);

	public MongoCollection<Document> getCollection(String argDatabaseName, String argCollectionName);

	public DBCollection getCollectionFromString(String argDatabaseName, String argCollectionName);

	public List<String> getCollectionNames(String argDatabaseName);

	public String getDatabaseStats(String argDatabaseName);

	public DB getSecondDatabase(String argSecondaryDatabaseName);

	public long getCount(String argDatabaseName, String argCollectionName);

	public long getCountByQuery(T argCollectionContext);

	public void dropCollection(String argDatabaseName, String argCollectionName);

	public void insert(String argDatabaseName, String argCollectionName, Document... argDbObjects);

	public void insert(String argDatabaseName, String argCollectionName, List<Document> argDbObjectList);

	public long remove(T argCollectionContext);

	public UpdateResult update(T argCollectionContext);

	public UpdateResult updateMulti(T argCollectionContext);

	public UpdateResult upsert(T argCollectionContext);

	public FindIterable<Document> getDocuments(String argDatabaseName, String argCollectionName);

	public FindIterable<Document> getDocuments(String argDatabaseName, String argCollectionName, int argLimit);

	public FindIterable<Document> getDocuments(String argDatabaseName, String argCollectionName,
			List<String> argFieldNames);

	public FindIterable<Document> getDocuments(String argDatabaseName, String argCollectionName,
			List<String> argFieldNames, int argSize);

	public Document getFirstDocuments(String argDatabaseName, String argCollectionName);
	
	public long deleteDocument(String argDatabaseName, String argCollectionName, Document argDbObjectQuery);

	public BulkWriteResult bulkInsert(String argDatabaseName, String argCollectionName,
			List<WriteModel<Document>> argDocumentList, String argBulkOperationName);

	public Collection<DB> getUsedDatabases();

}
