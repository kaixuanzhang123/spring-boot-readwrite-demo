package com.springboot.demo.mybatis.readorwrite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataSourceContextHolder {
	private static Logger log = LoggerFactory.getLogger(DataSourceContextHolder.class);  
	//线程本地环境  
    private static final ThreadLocal<String> local = new ThreadLocal<String>();   
    public static void setRead() {  
        local.set(DataSourceType.read.name());  
        log.info("数据库切换到读库...");  
    }  
    public static void setWrite() {  
        local.set(DataSourceType.write.name());  
        log.info("数据库切换到写库...");  
    }  
    public static String getReadOrWrite() {  
        return local.get();  
    }  
}
