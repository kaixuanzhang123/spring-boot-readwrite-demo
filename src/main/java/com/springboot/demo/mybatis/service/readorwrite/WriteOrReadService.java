package com.springboot.demo.mybatis.service.readorwrite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.demo.mybatis.mapper.UserTMapper;
import com.springboot.demo.mybatis.model.UserT;
import com.springboot.demo.mybatis.readorwrite.annatation.ReadDataSource;
import com.springboot.demo.mybatis.readorwrite.annatation.WriteDataSource;

/**
 * 多数据库，独立事务（每个库对应一个事务）
 * @author wenl
 */
@Service
public class WriteOrReadService {
	@Autowired
	UserTMapper userTMapper;
	@WriteDataSource
	@Transactional(value="writeOrReadTransactionManager")
	public void update(UserT record){
		userTMapper.updateByPrimaryKey(record);
//		throw new RuntimeException();
	}
	@ReadDataSource
	public UserT select(int userId){
		return userTMapper.selectByPrimaryKey(userId);
	}
}
