package com.automyz.database.mongo.context;

import org.bson.conversions.Bson;

import com.mongodb.WriteConcern;

public class MongoCollectionContext {

	private String collectionName;
	private String databaseName;
	private Bson query;
	private Bson update;
	private boolean upsert;
	private boolean updateMulti;
	private WriteConcern writeConcern;

	public String getCollectionName() {
		return this.collectionName;
	}

	public MongoCollectionContext setCollectionName(String argCollectionName) {
		this.collectionName = argCollectionName;
		return this;
	}

	public String getDatabaseName() {
		return this.databaseName;
	}

	public MongoCollectionContext setDatabaseName(String argDatabaseName) {
		this.databaseName = argDatabaseName;
		return this;
	}

	public Bson getQuery() {
		return this.query;
	}

	public MongoCollectionContext setQuery(Bson argQuery) {
		this.query = argQuery;
		return this;
	}

	public Bson getUpdate() {
		return this.update;
	}

	public MongoCollectionContext setUpdate(Bson argUpdate) {
		this.update = argUpdate;
		return this;
	}

	public boolean isUpsert() {
		return this.upsert;
	}

	public MongoCollectionContext setUpsert(boolean argUpsert) {
		this.upsert = argUpsert;
		return this;
	}

	public boolean isUpdateMulti() {
		return this.updateMulti;
	}

	public MongoCollectionContext setUpdateMulti(boolean argUpdateMulti) {
		this.updateMulti = argUpdateMulti;
		return this;
	}

	public WriteConcern getWriteConcern() {
		return this.writeConcern;
	}

	public MongoCollectionContext setWriteConcern(WriteConcern argWriteConcern) {
		this.writeConcern = argWriteConcern;
		return this;
	}

}
