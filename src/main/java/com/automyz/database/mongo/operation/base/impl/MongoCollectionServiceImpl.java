package com.automyz.database.mongo.operation.base.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.automyz.database.mongo.MongoConfigurationService;
import com.automyz.database.mongo.context.MongoCollectionContext;
import com.automyz.database.mongo.operation.base.MongoCollectionService;
import com.automyz.utils.Preconditions;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.WriteModel;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

public class MongoCollectionServiceImpl implements MongoCollectionService<MongoCollectionContext> {

	private static Logger logger = LoggerFactory.getLogger(MongoCollectionServiceImpl.class);

	/**
	 * Creates a collection with a given name and options. If the collection
	 * already exists, this throws a CommandFailureException.
	 */
	@Override
	public void createCollection(MongoCollectionContext argMongoCollectionContext) {
		MongoDatabase db = MongoConfigurationService.getInstance().getDb(argMongoCollectionContext.getDatabaseName());
		db.createCollection(argMongoCollectionContext.getCollectionName());
	}

	/**
	 * This tells whether the specified collection exists in a given database or
	 * not.
	 */
	@Override
	public boolean isCollectionExists(String argDatabaseName, String argCollectionName) {
		MongoCollection<Document> collection = getCollection(argDatabaseName, argCollectionName);
		return (collection != null);
	}

	/**
	 * Gets a collection with a given name. If the collection does not exist, a
	 * new collection is created.
	 */
	@Override
	public MongoCollection<Document> getCollection(String argDatabaseName, String argCollectionName) {
		logger.info("Database Name: {} and the Collection name: {} ", argDatabaseName, argCollectionName);
		String databaseName = Preconditions.assertNotEmpty(argDatabaseName, "Database Name");
		String collectionName = Preconditions.assertNotEmpty(argCollectionName, "Collection Name");
		MongoDatabase db = MongoConfigurationService.getInstance().getDb(databaseName);
		if (Preconditions.checkNotNull(db)) {
			return db.getCollection(collectionName);
		}
		return null;
	}

	/**
	 * Difference between getCollection(...) Vs getCollectionFromString(...) is,
	 * A collection can be identified by a namespace, "db.collection".
	 * getCollectionFromString gets the collection for that sort of namespace.
	 * getCollection gets it for just the raw collection.
	 * 
	 * See the links
	 * "https://groups.google.com/forum/?fromgroups=#!topic/mongodb-user/ll0mf1uqMFo"
	 * "http://stackoverflow.com/questions/29085102/mongodb-difference-between-getcollectionstring-name-and-getcollectionfromstr"
	 */
	@Override
	public DBCollection getCollectionFromString(String argDatabaseName, String argCollectionName) {
		throw new RuntimeException("Method not implemented:");
	}

	/**
	 * Returns a set of collection names from a given database.
	 */
	@Override
	public List<String> getCollectionNames(String argDatabaseName) {
		return MongoConfigurationService.getInstance().getCollectionNames(argDatabaseName);
	}

	/**
	 * Returns statistics that reflect the use state of a single database. Here
	 * is the example,
	 * <ul>
	 * <li>"db" : "admin",<br/>
	 * "collections" : 4,<br/>
	 * "objects" : 14,<br/>
	 * "avgObjSize" : 189.71428571428572,<br/>
	 * "dataSize" : 2656,<br/>
	 * "storageSize" : 28672,<br/>
	 * "numExtents" : 4,<br/>
	 * "indexes" : 3,<br/>
	 * "indexSize" : 24528,<br/>
	 * "fileSize" : 67108864,<br/>
	 * "nsSizeMB" : 16,<br/>
	 * "extentFreeList" : {<br/>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	 * "num" : 0,<br/>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	 * "totalSize" : 0<br/>
	 * },<br/>
	 * "dataFileVersion" : {<br/>
	 * "major" : 4,<br/>
	 * "minor" : 22<br/>
	 * },<br/>
	 * "ok" : 1</li>
	 * </ul>
	 */
	@Override
	public String getDatabaseStats(String argDatabaseName) {
		MongoDatabase db = MongoConfigurationService.getInstance().getDb(argDatabaseName);
		Document document = db.runCommand(new Document("dbstats", 1));
		return document.toJson();
	}

	/**
	 * Return a reference to another database using this same connection. This
	 * allows for cross database queries. Another synonym is "getSisterDB" .
	 * Usage example: db.getSiblingDB('production').getCollectionNames()
	 */
	@Override
	public DB getSecondDatabase(String argSecondaryDatabaseName) {
		throw new RuntimeException("Method not implemented:");
	}

	/**
	 * It will tell you how many documents are there in a collection.
	 */
	@Override
	public long getCount(String argDatabaseName, String argCollectionName) {
		MongoCollection<Document> collection = getCollection(argDatabaseName, argCollectionName);
		if (Preconditions.checkNotNull(collection)) {
			return collection.count();
		}
		return 0;
	}

	/**
	 * It will tell you how many documents are there in a collection based on a
	 * query (Where Condition).
	 */
	@Override
	public long getCountByQuery(MongoCollectionContext argMongoCollectionContext) {
		String databaseName = argMongoCollectionContext.getDatabaseName();
		String collectionName = argMongoCollectionContext.getCollectionName();
		Bson dbObject = (Bson) Preconditions.assertNotNull(argMongoCollectionContext.getQuery(), "DBObject");
		MongoCollection<Document> collection = getCollection(databaseName, collectionName);
		if (Preconditions.checkNotNull(collection)) {
			FindIterable<Document> iterable = collection.find(dbObject);
			if (Preconditions.checkNotNull(iterable)) {
				return iterable.spliterator().getExactSizeIfKnown();
			}
		}
		return 0;
	}

	/**
	 * It will remove/drop the specified collection.
	 */
	@Override
	public void dropCollection(String argDatabaseName, String argCollectionName) {
		MongoCollection<Document> collection = getCollection(argDatabaseName, argCollectionName);
		if (Preconditions.checkNotNull(collection)) {
			collection.drop();
		}
	}

	/**
	 * It will insert the single/multiple documents.
	 */
	@Override
	public void insert(String argDatabaseName, String argCollectionName, Document... argDbObjects) {
		MongoCollection<Document> collection = getCollection(argDatabaseName, argCollectionName);
		if (Preconditions.checkNotNull(collection)) {
			List<Document> documentList = Arrays.stream(argDbObjects).collect(Collectors.toList());
			if (documentList == null || documentList.size() < 0) {
				throw new RuntimeException("Exception while converting the Document Array to List while inserting:");
			}
			collection.insertMany(documentList);
		}
	}

	/**
	 * It will insert the list of documents.
	 */
	@Override
	public void insert(String argDatabaseName, String argCollectionName, List<Document> argDbObjectList) {
		MongoCollection<Document> collection = getCollection(argDatabaseName, argCollectionName);
		if (Preconditions.checkNotNull(collection)) {
			collection.insertMany(argDbObjectList);
		}
	}

	/**
	 * It removes the document from a collection. We need to specified the
	 * DatabaseName, CollectionName and the Query.
	 */
	@Override
	public long remove(MongoCollectionContext argMongoCollectionContext) {
		MongoCollection<Document> collection = getCollection(argMongoCollectionContext.getDatabaseName(),
				argMongoCollectionContext.getCollectionName());
		if (Preconditions.checkNotNull(collection)) {
			DeleteResult deleteResult = collection.deleteOne(argMongoCollectionContext.getQuery());
			if (deleteResult != null) {
				return deleteResult.getDeletedCount();
			}
		}
		return 0;
	}

	/**
	 * It updates a record in a specified collection.
	 */
	@Override
	public UpdateResult update(MongoCollectionContext argMongoCollectionContext) {
		MongoCollection<Document> dbCollection = getCollection(argMongoCollectionContext.getDatabaseName(),
				argMongoCollectionContext.getCollectionName());
		if (Preconditions.checkNotNull(dbCollection)) {
			return dbCollection.updateOne(argMongoCollectionContext.getQuery(), argMongoCollectionContext.getUpdate());
		}
		return null;
	}

	/**
	 * It updates multiple records in a specified collection.
	 */
	@Override
	public UpdateResult updateMulti(MongoCollectionContext argMongoCollectionContext) {
		MongoCollection<Document> dbCollection = getCollection(argMongoCollectionContext.getDatabaseName(),
				argMongoCollectionContext.getCollectionName());
		if (Preconditions.checkNotNull(dbCollection)) {
			UpdateOptions updateOptions = new UpdateOptions();
			updateOptions.upsert(false);
			return dbCollection.updateMany(argMongoCollectionContext.getQuery(), argMongoCollectionContext.getUpdate(),
					updateOptions);
		}
		return null;
	}

	/**
	 * It upserts one or more records in a specified collection.
	 */
	@Override
	public UpdateResult upsert(MongoCollectionContext argMongoCollectionContext) {
		MongoCollection<Document> dbCollection = getCollection(argMongoCollectionContext.getDatabaseName(),
				argMongoCollectionContext.getCollectionName());
		if (Preconditions.checkNotNull(dbCollection)) {
			UpdateOptions updateOptions = new UpdateOptions();
			updateOptions.upsert(true);
			return dbCollection.updateMany(argMongoCollectionContext.getQuery(), argMongoCollectionContext.getUpdate(),
					updateOptions);
		}
		return null;
	}

	/**
	 * It will give a list of documents in the specified collection.
	 */
	@Override
	public FindIterable<Document> getDocuments(String argDatabaseName, String argCollectionName) {
		MongoCollection<Document> dbCollection = getCollection(argDatabaseName, argCollectionName);
		if (Preconditions.checkNotNull(dbCollection)) {
			return dbCollection.find();
		}
		return null;
	}

	/**
	 * It will give a list of documents in the specified collection with a
	 * specified limit.
	 */
	@Override
	public FindIterable<Document> getDocuments(String argDatabaseName, String argCollectionName, int argLimit) {
		MongoCollection<Document> dbCollection = getCollection(argDatabaseName, argCollectionName);
		if (Preconditions.checkNotNull(dbCollection)) {
			return dbCollection.find().limit(argLimit);
		}
		return null;
	}

	/**
	 * It will give a list of documents in the specified collection with a
	 * specified fields. The output documents has only these fields and their
	 * values. It excludes other fields.
	 * 
	 * Reference:
	 * "http://www.javacodegeeks.com/2013/07/mongodb-pro-tip-field-projections.html"
	 */
	@Override
	public FindIterable<Document> getDocuments(String argDatabaseName, String argCollectionName,
			List<String> argFieldNames) {
		MongoCollection<Document> dbCollection = getCollection(argDatabaseName, argCollectionName);
		BasicDBObjectBuilder basicDBObjectBuilder = new BasicDBObjectBuilder();
		if (Preconditions.checkNotEmpty(argFieldNames)) {
			for (String fieldName : argFieldNames) {
				basicDBObjectBuilder.add(fieldName, 1);
			}
			basicDBObjectBuilder.add("_id", 0);
		}
		if (Preconditions.checkNotNull(dbCollection)) {
			return dbCollection.find().projection(Projections.include(argFieldNames));
		}
		return null;
	}

	/**
	 * It will give a list of documents in the specified collection with a
	 * specified fields and limit. The output documents has only these fields
	 * and their values. It excludes other fields.
	 * 
	 * Reference:
	 * "http://www.javacodegeeks.com/2013/07/mongodb-pro-tip-field-projections.html"
	 */
	@Override
	public FindIterable<Document> getDocuments(String argDatabaseName, String argCollectionName,
			List<String> argFieldNames, int argSize) {
		return getDocuments(argDatabaseName, argCollectionName, argFieldNames).limit(argSize);
	}

	/**
	 * It will give the first document in the specified collection.
	 */
	@Override
	public Document getFirstDocuments(String argDatabaseName, String argCollectionName) {
		MongoCollection<Document> dbCollection = getCollection(argDatabaseName, argCollectionName);
		if (Preconditions.checkNotNull(dbCollection)) {
			return dbCollection.findOneAndDelete(null);
		}
		return null;
	}

	/**
	 * https://stackoverflow.com/questions/31470702/bulk-upsert-with-mongodb-
	 * java-3-0-driver MongoCollection<Document> collection =
	 * db.getCollection("sample");
	 * 
	 * List<WriteModel<Document>> updates = Arrays.<WriteModel<Document>>asList(
	 * new UpdateOneModel<Document>( new Document(), // find part new
	 * Document("$set",1), // update part new UpdateOptions().upsert(true) //
	 * options like upsert ) );
	 * 
	 * BulkWriteResult bulkWriteResult = collection.bulkWrite(updates);
	 */

	@Override
	public BulkWriteResult bulkInsert(String argDatabaseName, String argCollectionName,
			List<WriteModel<Document>> argDocumentList, String argBulkOperationName) {
		MongoDatabase db = MongoConfigurationService.getInstance().getDb(argDatabaseName);
		if (db != null) {
			MongoCollection<Document> dbCollection = db.getCollection(argCollectionName);
			if (dbCollection != null) {
				return dbCollection.bulkWrite(argDocumentList);
			}
		}
		return null;
	}

	@Override
	public Collection<DB> getUsedDatabases() {
		MongoClient mongo = MongoConfigurationService.getInstance().getMongo();
		return mongo.getUsedDatabases();
	}

	@Override
	public long deleteDocument(String argDatabaseName, String argCollectionName, Document argDbObjectQuery) {
		if (argDatabaseName != null && !argDatabaseName.isEmpty() && argCollectionName != null
				&& !argCollectionName.isEmpty() && argDbObjectQuery != null) {
			MongoCollection<Document> dbCollection = MongoConfigurationService.getInstance()
					.getCollection(argDatabaseName, argCollectionName);
			if (dbCollection != null) {
				DeleteResult writeResult = dbCollection.deleteOne(argDbObjectQuery);
				if (writeResult != null) {
					return writeResult.getDeletedCount();
				}
			}
		}
		return -1;
	}
}
