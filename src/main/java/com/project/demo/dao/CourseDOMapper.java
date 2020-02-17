package com.project.demo.dao;

import com.project.demo.DO.CourseDO;
import com.project.demo.VO.CourseVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

public interface CourseDOMapper {
    int deleteByPrimaryKey(Integer courseId);

    int insert(CourseDO record);

    int insertSelective(CourseDO record);

    CourseDO selectByPrimaryKey(Integer courseId);

    int updateByPrimaryKeySelective(CourseDO record);

    int updateByPrimaryKeyWithBLOBs(CourseDO record);

    int updateByPrimaryKey(CourseDO record);

    List<CourseVO> getCourseList(@Param("userId") Integer userId);

    void createCourse(@Param("courseDO") CourseDO courseDO);

    void deleteCourse(@Param("courseId") Integer courseId, @Param("userId") Integer userId);

    List<CourseVO> searchCourseList(@Param("searchKey") String searchKey, @Param("userId") Integer userId);

    List<CourseVO> getSelectedCourseList(@Param("userId") Integer userId);

    List<CourseVO> getOptionalCourseList(@Param("userId") Integer userId);

    List<CourseVO> getWorkOfCourse(@Param("userId") Integer userId);

}