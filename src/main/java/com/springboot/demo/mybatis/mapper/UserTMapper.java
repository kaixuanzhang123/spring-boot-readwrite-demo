package com.springboot.demo.mybatis.mapper;

import com.springboot.demo.mybatis.model.UserT;

public interface UserTMapper {

    UserT selectByPrimaryKey(Integer id);



    int updateByPrimaryKey(UserT record);
}