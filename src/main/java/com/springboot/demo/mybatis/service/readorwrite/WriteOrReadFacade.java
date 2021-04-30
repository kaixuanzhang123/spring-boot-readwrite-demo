package com.springboot.demo.mybatis.service.readorwrite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.demo.mybatis.model.UserT;
import com.springboot.demo.mybatis.readorwrite.annatation.ReadDataSource;
import com.springboot.demo.mybatis.readorwrite.annatation.WriteDataSource;

/**
 * 多数据库，独立事务（每个库对应一个事务）
 * @author wenl
 */
@Service
public class WriteOrReadFacade {
	@Autowired
	WriteOrReadService service;
	

	@ReadDataSource
	@Transactional(value="writeOrReadTransactionManager")
	public void selectAndUpdate(int userId){
		UserT record=service.select(userId);
		System.out.println(record.getAge());
		record.setAge(30);
		service.update(record);
		 record=service.select(userId);
		System.out.println(record.getAge());
	}
}
