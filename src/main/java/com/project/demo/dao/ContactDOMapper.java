package com.project.demo.dao;

import com.project.demo.DO.ContactDO;

public interface ContactDOMapper {
    int deleteByPrimaryKey(Integer contactId);

    int insert(ContactDO record);

    int insertSelective(ContactDO record);

    ContactDO selectByPrimaryKey(Integer contactId);

    int updateByPrimaryKeySelective(ContactDO record);

    int updateByPrimaryKey(ContactDO record);
}