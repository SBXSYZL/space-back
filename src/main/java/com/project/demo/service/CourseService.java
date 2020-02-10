package com.project.demo.service;

import com.project.demo.VO.ElectiveVO;
import com.project.demo.VO.ScoreVO;
import com.project.demo.error.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.Map;

@Service
public interface CourseService {
    Map getCourseList(Integer userId, Integer pageNo, Integer pageSize) throws BusinessException;

    Map getWorkList(Integer userId, Integer pageNo, Integer pageSize) throws BusinessException;

    void createCourse(String courseName, Date deadline, Integer schedule, String courseDescription) throws BusinessException;

    void deleteCourse(Integer courseId) throws BusinessException;

    Map searchCourseList(String searchKey, Integer pageNo, Integer pageSize, Integer userId) throws BusinessException;


    void createWork(Integer courseId, String workName, Date deadline, String workDesc) throws BusinessException;


    Map getListOfStudentSubmissionsForTheClass(Integer workId, Integer pageNo, Integer pageSize) throws BusinessException;

    void gradeAssignment(Integer submitId, Byte status, Integer score) throws BusinessException;

    Map searchWork(Integer userId, String searchKey, Integer pageNo, Integer pageSize) throws BusinessException;

    void courseGrading(Integer courseId, Integer stuId, Integer teacherId, Float performanceScore, Float examScore) throws BusinessException;

    Map getSelectedCourseList(Integer userId, Integer pageNo, Integer pageSize) throws BusinessException;

    ScoreVO getCourseScore(Integer userId, Integer courseId) throws BusinessException;

    void joinCourse(Integer userId, Integer courseId) throws BusinessException;
}
