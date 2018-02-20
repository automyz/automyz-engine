package com.automyz.mongo.test;

import java.util.List;

import org.junit.Test;

import com.automyz.database.mongo.MongoConfigurationService;

public class MongoConnectionTest {

	@Test
	public void test() {
		MongoConfigurationService mongoConfigurationService = MongoConfigurationService.getInstance();
		List<String> databaseNames = mongoConfigurationService.getDatabaseNames();
		for (String databaseName : databaseNames) {
			System.out.println("Database Name: " + databaseName);
		}
	}

}
