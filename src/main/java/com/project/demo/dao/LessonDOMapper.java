package com.project.demo.dao;

import com.project.demo.DO.LessonDO;
import com.project.demo.DO.LessonDOKey;

import java.util.List;

public interface LessonDOMapper {
    int deleteByPrimaryKey(LessonDOKey key);

    int insert(LessonDO record);

    int insertSelective(LessonDO record);

    LessonDO selectByPrimaryKey(LessonDOKey key);

    int updateByPrimaryKeySelective(LessonDO record);

    int updateByPrimaryKey(LessonDO record);


    List<LessonDO> getLessonList(Integer courseId);
}