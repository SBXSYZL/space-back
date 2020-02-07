package com.project.demo.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.project.demo.DO.CourseDO;
import com.project.demo.DO.WorkDO;
import com.project.demo.VO.CourseVO;
import com.project.demo.dao.CourseDOMapper;
import com.project.demo.error.BusinessException;
import com.project.demo.error.EmBusinessErr;
import com.project.demo.service.CourseService;
import com.project.demo.utils.MySessionUtil;
import com.project.demo.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseDOMapper courseDOMapper;

    @Override
    public Map getCourseList(Integer userId, Integer pageNo, Integer pageSize) throws BusinessException {
        try {
            Page page = PageHelper.startPage(pageNo, pageSize);
            List<CourseVO> list = courseDOMapper.getCourseList(userId);
            return PageUtil.getListWithPageInfo(list, page);
        } catch (Exception e) {
            throw new BusinessException(EmBusinessErr.COURSE_LIST_GET_ERROR);
        }
    }

    @Override
    public Map getWorkList(Integer courseId, Integer pageNo, Integer pageSize) throws BusinessException {
        try {
            Page page = PageHelper.startPage(pageNo, pageSize);
//            List<WorkDO> lessonList = lessonDOMapper.getLessonList(courseId);
            List<WorkDO> lessonList = null;
            return PageUtil.getListWithPageInfo(lessonList, page);
        } catch (Exception e) {
            throw new BusinessException(EmBusinessErr.LESSON_LIST_GET_ERROR);
        }
    }

    @Override
    public void createCourse(String courseName, Date deadline, Integer schedule, String courseDescription) throws BusinessException {
        try {
            CourseDO courseDO = new CourseDO();
            Integer userId = (Integer) MySessionUtil.getSession().getAttribute(MySessionUtil.USER_ID);
            courseDO.setAuthorId(userId);
            courseDO.setCourseName(courseName);
            courseDO.setCourseDeadline(deadline);
            courseDO.setSchedule(schedule);
            courseDO.setCourseDesc(courseDescription);
            courseDOMapper.createCourse(courseDO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(EmBusinessErr.CREATE_COURSE_ERROR);
        }
    }

    @Override
    public void deleteCourse(Integer courseId) throws BusinessException {
        try {
            Integer userId = (Integer) MySessionUtil.getSession().getAttribute(MySessionUtil.USER_ID);
            courseDOMapper.deleteCourse(courseId, userId);
        } catch (Exception e) {
            throw new BusinessException(EmBusinessErr.DELETE_COURSE_ERROR);
        }
    }

    @Override
    public Map searchCourseList(String searchKey, Integer pageNo, Integer pageSize) throws BusinessException {
        try {
            Page page = PageHelper.startPage(pageNo, pageSize);
            List<CourseVO> courseVOS = courseDOMapper.searchCourseList(searchKey);
            return PageUtil.getListWithPageInfo(courseVOS, page);
        } catch (Exception e) {
            throw new BusinessException(EmBusinessErr.SEARCH_COURSE_ERROR);
        }
    }

    @Override
    public void createWork(Integer courseId, String workName, Date deadline, String workDesc) throws BusinessException {
        try {
            Integer userId = (Integer) MySessionUtil.getSession().getAttribute(MySessionUtil.USER_ID);
            courseDOMapper.createWork(userId, courseId, workName, workDesc, deadline);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(EmBusinessErr.CREATE_WORK_ERROR);
        }
    }

}
