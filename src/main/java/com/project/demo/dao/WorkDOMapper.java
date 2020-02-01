package com.project.demo.dao;

import com.project.demo.DO.WorkDO;

public interface WorkDOMapper {
    int deleteByPrimaryKey(Integer workId);

    int insert(WorkDO record);

    int insertSelective(WorkDO record);

    WorkDO selectByPrimaryKey(Integer workId);

    int updateByPrimaryKeySelective(WorkDO record);

    int updateByPrimaryKeyWithBLOBs(WorkDO record);

    int updateByPrimaryKey(WorkDO record);
}