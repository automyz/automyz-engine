package com.automyz.utils;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;


public class IdGeneratorTest {

	@Test
	public void test() throws InterruptedException {
		Set<Long> ids = new LinkedHashSet<>();
		for (int i = 0; i < 100; i++) {
			long id = IdGenerator.getId();
			if(i == 2){
				Thread.sleep(2_000);
			}
			ids.add(id);
		}
		System.out.println(ids);
		Assert.assertEquals(100, ids.size());
	}
}
