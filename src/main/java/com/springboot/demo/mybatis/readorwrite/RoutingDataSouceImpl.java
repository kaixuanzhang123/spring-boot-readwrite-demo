package com.springboot.demo.mybatis.readorwrite;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * 读写分离路由规则
 * @author wenl
 *
 */
@Component
public class RoutingDataSouceImpl extends AbstractRoutingDataSource {
	
	@Override
	public void afterPropertiesSet() {
		//初始化bean的时候执行，可以针对某个具体的bean进行配置
		//afterPropertiesSet 早于init-method
		//将datasource注入到targetDataSources中，可以为后续路由用到的key
		this.setDefaultTargetDataSource(writeDataSource);
		Map<Object,Object>targetDataSources=new HashMap<Object,Object>();
		targetDataSources.put( DataSourceType.write.name(), writeDataSource);
		targetDataSources.put( DataSourceType.read.name(),  readDataSource);
		this.setTargetDataSources(targetDataSources);
		//执行原有afterPropertiesSet逻辑，
		//即将targetDataSources中的DataSource加载到resolvedDataSources
		super.afterPropertiesSet();
	}
	@Override
	protected Object determineCurrentLookupKey() {
		//这里边就是读写分离逻辑，最后返回的是setTargetDataSources保存的Map对应的key
		String typeKey = DataSourceContextHolder.getReadOrWrite();  
		Assert.notNull(typeKey, "数据库路由发现typeKey is null，无法抉择使用哪个库");
		log.info("使用"+typeKey+"数据库.............");  
		return typeKey;
	}
	
	private static Logger log = LoggerFactory.getLogger(RoutingDataSouceImpl.class); 
	@Autowired  
	@Qualifier("writeDataSource")  
	private DataSource writeDataSource;  
	@Autowired  
	@Qualifier("readDataSource")  
	private DataSource readDataSource;  
}
