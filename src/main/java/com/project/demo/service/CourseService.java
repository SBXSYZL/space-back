package com.project.demo.service;

import com.project.demo.error.BusinessException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public interface CourseService {
    Map getCourseList(Integer userId, Integer pageNo, Integer pageSize) throws BusinessException;

    Map getWorkList(Integer courseId, Integer pageNo, Integer pageSize) throws BusinessException;

    void createCourse(String courseName, Date deadline, Integer schedule, String courseDescription) throws BusinessException;

    void deleteCourse(Integer courseId) throws BusinessException;

    Map searchCourseList(String searchKey, Integer pageNo, Integer pageSize) throws BusinessException;


    void createWork(Integer courseId, String workName, Date deadline, String workDesc) throws BusinessException;






}
