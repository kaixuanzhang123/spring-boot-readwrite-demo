package com.springboot.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.springboot.demo.mybatis.readorwrite.DataSourceContextHolder;

public class Test {
public static void main(String[] args) {
	ExecutorService ex=Executors.newFixedThreadPool(1);
	Runnable able=()->{
		System.out.println("begin:"+DataSourceContextHolder.getReadOrWrite());
		DataSourceContextHolder.setRead();
		System.out.println("change read:"+DataSourceContextHolder.getReadOrWrite());
		DataSourceContextHolder.setWrite();
		System.out.println("change write:"+DataSourceContextHolder.getReadOrWrite());
	};
	for(int i=0;i<5;i++) {
		ex.submit(able);
		
	}
	ex.shutdown();
}
}
