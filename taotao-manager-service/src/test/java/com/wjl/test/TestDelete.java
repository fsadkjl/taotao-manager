package com.wjl.test;


import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class TestDelete {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		String[] strings = {"2","3","4"};
		Long[] longs = new Long[strings.length];
		for (int i = 0; i < strings.length; i++) {
			longs[i] = Long.valueOf(strings[i]);
		}
		List<Long> list = Arrays.asList(longs);
		list.forEach(System.out::println);
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).getClass());
		}
//		for (int i = 0; i < longs.length; i++) {
//			System.out.println(longs[i]);
//			System.out.println(longs[i].getClass());
//		}
	}

}
