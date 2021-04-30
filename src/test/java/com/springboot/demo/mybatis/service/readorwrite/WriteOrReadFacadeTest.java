package com.springboot.demo.mybatis.service.readorwrite;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
@RunWith(SpringRunner.class)
@SpringBootTest
public class WriteOrReadFacadeTest {
	@Autowired
	WriteOrReadFacade service;
	@Test
	public void test() {
		service.selectAndUpdate(1);
	}

}
