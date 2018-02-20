package com.automyz.utils;

public class IdGenerator {
	
	private static long lastId = System.currentTimeMillis(); 
	
	public static synchronized long getId() {
		long currentId = System.currentTimeMillis();
		if(lastId >= currentId){
			  currentId = currentId + (lastId - currentId) + 1;
		}
		lastId = currentId;
		return lastId;
	}
}
