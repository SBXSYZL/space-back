package com.project.demo.dao;

import com.project.demo.DO.ElectiveDO;
import com.project.demo.DO.ElectiveDOKey;
import com.project.demo.VO.CourseVO;
import com.project.demo.VO.ElectiveVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ElectiveDOMapper {
    int deleteByPrimaryKey(ElectiveDOKey key);

    int insert(ElectiveDO record);

    int insertSelective(ElectiveDO record);

    ElectiveDO selectByPrimaryKey(ElectiveDOKey key);

    int updateByPrimaryKeySelective(ElectiveDO record);

    int updateByPrimaryKey(ElectiveDO record);

    List<ElectiveVO> getElectiveList(Integer courseId);

    void courseGrading(@Param("courseId") Integer courseId,
                       @Param("stuId") Integer stuId,
                       @Param("teacherId") Integer teacherId,
                       @Param("performanceScore") Float performanceScore,
                       @Param("examScore") Float examScore);
}