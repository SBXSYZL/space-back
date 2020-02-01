package com.project.demo.dao;

import com.project.demo.DO.WorkSubmitDO;

public interface WorkSubmitDOMapper {
    int deleteByPrimaryKey(Integer submitId);

    int insert(WorkSubmitDO record);

    int insertSelective(WorkSubmitDO record);

    WorkSubmitDO selectByPrimaryKey(Integer submitId);

    int updateByPrimaryKeySelective(WorkSubmitDO record);

    int updateByPrimaryKey(WorkSubmitDO record);
}