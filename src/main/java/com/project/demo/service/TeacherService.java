package com.project.demo.service;

import com.project.demo.error.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.Map;

@Service
public interface TeacherService {
    void teacherLogin(String account, String password) throws BusinessException;

    void teacherRegistered(String nickName, String account, String password) throws BusinessException;

    Map getCourseList(Integer userId, Integer pageNo, Integer pageSize) throws BusinessException;

    Map getLessonList(Integer courseId, Integer pageNo, Integer pageSize) throws BusinessException;

    void createCourse(String courseName, Date deadline, String courseDescription) throws BusinessException;

    void deleteCourse(Integer courseId) throws BusinessException;

    Map getElectiveList(Integer courseId, Integer pageNo, Integer pageSize) throws BusinessException;

    Map searchCourseList(String searchKey, Integer pageNo, Integer pageSize) throws BusinessException;
}
