package com.project.demo.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.project.demo.DO.CourseDO;
import com.project.demo.DO.WorkSubmitDO;
import com.project.demo.VO.*;
import com.project.demo.dao.CourseDOMapper;
import com.project.demo.dao.ElectiveDOMapper;
import com.project.demo.dao.WorkDOMapper;
import com.project.demo.dao.WorkSubmitDOMapper;
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

    @Autowired
    WorkDOMapper workDOMapper;

    @Autowired
    WorkSubmitDOMapper workSubmitDOMapper;

    @Autowired
    ElectiveDOMapper electiveDOMapper;

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
    public Map getWorkList(Integer userId, Integer pageNo, Integer pageSize) throws BusinessException {
        try {
            Page page = PageHelper.startPage(pageNo, pageSize);
//            List<WorkDO> lessonList = lessonDOMapper.getLessonList(courseId);
            List<WorkVO> lessonList = workDOMapper.getWorkList(userId);
            return PageUtil.getListWithPageInfo(lessonList, page);
        } catch (Exception e) {
            e.printStackTrace();
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
    public Map searchCourseList(String searchKey, Integer pageNo, Integer pageSize, Integer userId) throws BusinessException {
        try {
            Page page = PageHelper.startPage(pageNo, pageSize);
            List<CourseVO> courseVOS = courseDOMapper.searchCourseList(searchKey, userId);
            return PageUtil.getListWithPageInfo(courseVOS, page);
        } catch (Exception e) {
            throw new BusinessException(EmBusinessErr.SEARCH_COURSE_ERROR);
        }
    }

    @Override
    public void createWork(Integer courseId, String workName, Date deadline, String workDesc) throws BusinessException {
        try {
            Integer userId = (Integer) MySessionUtil.getSession().getAttribute(MySessionUtil.USER_ID);
            workDOMapper.createWork(userId, courseId, workName, workDesc, deadline);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(EmBusinessErr.CREATE_WORK_ERROR);
        }
    }

    @Override
    public Map getListOfStudentSubmissionsForTheClass(Integer workId, Integer pageNo, Integer pageSize) throws BusinessException {
        try {
            Page page = PageHelper.startPage(pageNo, pageSize);
            List<WorkSubmitVO> workSubmitList = workSubmitDOMapper.getWorkSubmitList(workId);
            return PageUtil.getListWithPageInfo(workSubmitList, page);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(EmBusinessErr.GET_SUBMIT_WORK_ERROR);
        }
    }

    @Override
    public void gradeAssignment(Integer submitId, Byte status, Integer score) throws BusinessException {
        try {
            workSubmitDOMapper.gradeAssignment(submitId, status, score);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(EmBusinessErr.SUBMIT_EVAL_ERROR);
        }
    }

    @Override
    public Map searchWork(Integer userId, String searchKey, Integer pageNo, Integer pageSize) throws BusinessException {
        try {
            Page page = PageHelper.startPage(pageNo, pageSize);
            List<WorkVO> workVOS = workDOMapper.searchWork(userId, searchKey);
            return PageUtil.getListWithPageInfo(workVOS, page);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(EmBusinessErr.SEARCH_WORK_ERROR);
        }
    }

    @Override
    public void courseGrading(Integer courseId, Integer stuId, Integer teacherId, Float performanceScore, Float examScore) throws BusinessException {
        try {
            electiveDOMapper.courseGrading(courseId, stuId, teacherId, performanceScore, examScore);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(EmBusinessErr.COURSE_GRADING_ERROR);
        }
    }

    @Override
    public Map getSelectedCourseList(Integer userId, Integer pageNo, Integer pageSize) throws BusinessException {
        try {
            Page page = PageHelper.startPage(pageNo, pageSize);
            List<StuCourseVO> selectedCourseList = courseDOMapper.getSelectedCourseList(userId);
            return PageUtil.getListWithPageInfo(selectedCourseList, page);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(EmBusinessErr.GET_SELECTED_COURSE_LIST_ERROR);
        }
    }

    @Override
    public ScoreVO getCourseScore(Integer userId, Integer courseId) throws BusinessException {
        try {
            return electiveDOMapper.getCourseScore(userId, courseId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(EmBusinessErr.GET_COURSE_SCORE_ERROR);
        }
    }

    @Override
    public void joinCourse(Integer userId, Integer courseId) throws BusinessException {
        try {
            electiveDOMapper.joinCourse(userId, courseId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(EmBusinessErr.JOIN_COURSE_ERROR);
        }
    }

    @Override
    public Map getOptionalCourseList(Integer pageNo, Integer pageSize, Integer userId) throws BusinessException {
        try {
            Page page = PageHelper.startPage(pageNo, pageSize);
            List<CourseVO> optionalCourseList = courseDOMapper.getOptionalCourseList(userId);
            return PageUtil.getListWithPageInfo(optionalCourseList, page);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(EmBusinessErr.GET_OPTIONAL_COURSE_LIST);
        }
    }

    @Override
    public Map getWorkOfCourse(Integer userId, Integer pageNo, Integer pageSize) throws BusinessException {
        try {
            Page page = PageHelper.startPage(pageNo, pageSize);
            List<CourseVO> workOfCourse = courseDOMapper.getWorkOfCourse(userId);
            return PageUtil.getListWithPageInfo(workOfCourse, page);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(EmBusinessErr.GET_SUBMIT_WORK_ERROR);
        }
    }

    @Override
    public Integer getWorkScore(Integer userId, Integer workId) throws BusinessException {
        try {
            return workSubmitDOMapper.getWorkScore(userId, workId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(EmBusinessErr.GET_WORK_SCORE_ERROR);
        }
    }

    @Override
    public void submitWork(Integer userId, Integer courseId, Integer workId, String fileName) throws BusinessException {
        try {
            List<WorkSubmitDO> check = workSubmitDOMapper.check(userId, workId);
            if (check == null || check.size() <= 0) {
                workDOMapper.updateSubmit(workId);
            }
            workSubmitDOMapper.submitWork(userId, courseId, workId, fileName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(EmBusinessErr.SUBMIT_WORK_ERROR);
        }
    }

    @Override
    public void deleteWork(Integer userId, Integer workId) throws BusinessException {
        try {
            workDOMapper.deleteWork(userId, workId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(EmBusinessErr.DELETE_WORK_ERROR);
        }
    }
}
