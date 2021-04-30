package com.springboot.demo.mybatis.readorwrite;

import java.io.IOException;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 读写分离数据库配置
 * 
 * @author wenl
 */
@Configuration
@MapperScan(basePackages = "com.springboot.demo.mybatis.mapper", sqlSessionFactoryRef = "writeOrReadsqlSessionFactory")
public class WriteOrReadDatabaseConfig {

	private static Logger log = LoggerFactory.getLogger(DataSourceContextHolder.class);

	// 定义读库，写库的 DataSource
	@Primary
	@Bean(name = "writeDataSource", destroyMethod = "close")
	@ConfigurationProperties(prefix = "test_write")
	public DataSource writeDataSource() {
		return new DruidDataSource();
	}

	@Bean(name = "readDataSource", destroyMethod = "close")
	@ConfigurationProperties(prefix = "test_read")
	public DataSource readDataSource() {
		return new DruidDataSource();
	}
	// ----sqlSessionFactory------------------------------------------
	@Bean(name = "writeOrReadsqlSessionFactory")
	public SqlSessionFactory sqlSessionFactorys(RoutingDataSouceImpl roundRobinDataSouceProxy) throws Exception {
		try {
			SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
			bean.setDataSource(roundRobinDataSouceProxy);
			ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			// 实体类对应的位置
			bean.setTypeAliasesPackage("com.springboot.demo.mybatis.model");
			// mybatis的XML的配置
			bean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
			return bean.getObject();
		} catch (IOException e) {
			log.error("" + e);
			return null;
		} catch (Exception e) {
			log.error("" + e);
			return null;
		}
	}

//	@Bean(name = "writeOrReadsqlSessionTemplate")
//	public SqlSessionTemplate sqlSessionTemplate(
//			@Qualifier("writeOrReadsqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
//		// SqlSessionTemplate 是 MyBatis-Spring 的核心。 这个类负责管理 MyBatis 的
//		// SqlSession,
//		// 调用 MyBatis 的 SQL 方法, 翻译异常。 SqlSessionTemplate 是线程安全的。
//		// spring获取到SqlSessionTemplate然后注入给工具类，让工具类操作数据库
//		return new SqlSessionTemplate(sqlSessionFactory);
//	}

	// 事务管理
	@Bean(name = "writeOrReadTransactionManager")
	public DataSourceTransactionManager transactionManager(RoutingDataSouceImpl roundRobinDataSouceProxy) {
		//Spring 的jdbc事务管理器
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(roundRobinDataSouceProxy);
		return transactionManager;
	}
}
