package com.springboot.demo.mybatis.readorwrite;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

@Aspect  
//exposeProxy=true AopContext 可以访问，proxyTargetClass=true CGLIB生成代理
@EnableAspectJAutoProxy(exposeProxy=true,proxyTargetClass=true)  
@Component  
public class DataSourceAopAspect implements PriorityOrdered{

	 @Before("execution(* com.springboot.demo.mybatis.service.readorwrite..*.*(..)) "  
            + " and @annotation(com.springboot.demo.mybatis.readorwrite.annatation.ReadDataSource) ")  
    public void setReadDataSourceType() {  
        //如果已经开启写事务了，那之后的所有读都从写库读  
            DataSourceContextHolder.setRead();    
    }  
    @Before("execution(* com.springboot.demo.mybatis.service.readorwrite..*.*(..)) "  
            + " and @annotation(com.springboot.demo.mybatis.readorwrite.annatation.WriteDataSource) ")  
    public void setWriteDataSourceType() {  
        DataSourceContextHolder.setWrite();  
    }  
	@Override
	public int getOrder() {
		/** 
         * 值越小，越优先执行 要优于事务的执行 
         * 在启动类中加上了@EnableTransactionManagement(order = 10)  
         */  
		return 1;
	}
}
