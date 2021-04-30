package com.springboot.demo.mybatis.service.readorwrite;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.springboot.demo.mybatis.model.UserT;
@RunWith(SpringRunner.class)
@SpringBootTest
public class WriteOrReadServiceTest {
	@Autowired
	 WriteOrReadService service;
	@Test
	public void test() {
		UserT record=service.select(1);
		Assert.assertTrue(record.getPassword().equals("sfasgfaf"));
		record.setAge(29);
		service.update(record);
		UserT record2=service.select(1);
		Assert.assertTrue(record2.getAge().equals(29));
	}

}
