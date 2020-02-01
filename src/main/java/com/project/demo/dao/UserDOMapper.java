package com.project.demo.dao;

import com.project.demo.DO.UserDO;
import org.apache.ibatis.annotations.Param;

public interface UserDOMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(UserDO record);

    int insertSelective(UserDO record);

    UserDO selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(UserDO record);

    int updateByPrimaryKey(UserDO record);

    UserDO selectByAccount(@Param("account") String account);

    void studentRegistered(UserDO userDO);
}