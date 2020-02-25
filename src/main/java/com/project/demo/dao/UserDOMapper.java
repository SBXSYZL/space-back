package com.project.demo.dao;

import com.project.demo.DO.UserDO;
import com.project.demo.VO.UserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDOMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(UserDO record);

    int insertSelective(UserDO record);

    UserDO selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(UserDO record);

    int updateByPrimaryKey(UserDO record);

    UserDO selectByAccount(@Param("account") String account);

    void studentRegistered(UserDO userDO);

    List<UserVO> searchUser(@Param("searchKey") String searchKey);

    UserVO getInfo(@Param("userId") Integer userId);

    void modifyPass(@Param("userId") Integer userId, @Param("oldPass") String oldPass, @Param("newPass") String newPass);

}