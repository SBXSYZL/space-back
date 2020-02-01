package com.project.demo.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.project.demo.DO.CourseDO;
import com.project.demo.DO.LessonDO;
import com.project.demo.DO.UserDO;
import com.project.demo.VO.CourseVO;
import com.project.demo.VO.ElectiveVO;
import com.project.demo.dao.CourseDOMapper;
import com.project.demo.dao.ElectiveDOMapper;
import com.project.demo.dao.LessonDOMapper;
import com.project.demo.dao.UserDOMapper;
import com.project.demo.error.BusinessException;
import com.project.demo.error.EmBusinessErr;
import com.project.demo.service.TeacherService;
import com.project.demo.utils.MD5Util;
import com.project.demo.utils.MySessionUtil;
import com.project.demo.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    UserDOMapper userDOMapper;

    @Autowired
    CourseDOMapper courseDOMapper;

    @Autowired
    LessonDOMapper lessonDOMapper;

    @Autowired
    ElectiveDOMapper electiveDOMapper;

    @Override
    public void teacherLogin(String account, String password) throws BusinessException {
        login(account, password, userDOMapper, (byte) 1);
    }

    protected static void login(String account, String password, UserDOMapper userDOMapper, Byte authority) throws BusinessException {
        try {
            UserDO userDO = userDOMapper.selectByAccount(account);
            if (userDO != null) {
                if (!userDO.getAuthority().equals(authority) ||
                        !userDO.getAccount().equals(account) ||
                        !userDO.getPassword().equals(MD5Util.getMD5(password))) {
                    throw new BusinessException(EmBusinessErr.USER_LOGIN_ERROR);
                }
            }
            throw new BusinessException(EmBusinessErr.USER_NOT_EXISTS);
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                throw (BusinessException) e;
            } else {
                throw new BusinessException(EmBusinessErr.USER_NOT_EXISTS);
            }
        }
    }

    @Override
    public void teacherRegistered(String nickName, String account, String password) throws BusinessException {
        registered(nickName, account, password, userDOMapper, (byte) 1);
    }

    protected static void registered(String nickName, String account, String password, UserDOMapper userDOMapper, Byte authority) throws BusinessException {
        try {
            UserDO userDO = new UserDO();
            userDO.setNickName(nickName);
            userDO.setAccount(account);
            userDO.setPassword(MD5Util.getMD5(password));
            userDO.setAuthority(authority);
            userDOMapper.studentRegistered(userDO);
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                throw new BusinessException(EmBusinessErr.USER_ACCOUNT_EXISTS_ALREADY);
            }
            throw new BusinessException(EmBusinessErr.USER_REGISTERED_ERROR);
        }
    }

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
    public Map getLessonList(Integer courseId, Integer pageNo, Integer pageSize) throws BusinessException {
        try {
            Page page = PageHelper.startPage(pageNo, pageSize);
            List<LessonDO> lessonList = lessonDOMapper.getLessonList(courseId);
            return PageUtil.getListWithPageInfo(lessonList, page);
        } catch (Exception e) {
            throw new BusinessException(EmBusinessErr.LESSON_LIST_GET_ERROR);
        }
    }

    @Override
    public void createCourse(String courseName, Date deadline, String courseDescription) throws BusinessException {
        try {
            CourseDO courseDO = new CourseDO();
            Integer userId = (Integer) MySessionUtil.getSession().getAttribute(MySessionUtil.USER_ID);
            courseDO.setAuthorId(userId);
            courseDO.setCourseName(courseName);
            courseDO.setCourseDeadline(deadline);
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
            courseDOMapper.deleteCourse(courseId);
        } catch (Exception e) {
            throw new BusinessException(EmBusinessErr.DELETE_COURSE_ERROR);
        }
    }

    @Override
    public Map getElectiveList(Integer courseId, Integer pageNo, Integer pageSize) throws BusinessException {
        try {
            Page page = PageHelper.startPage(pageNo, pageSize);
            List<ElectiveVO> electiveList = electiveDOMapper.getElectiveList(courseId);
            return PageUtil.getListWithPageInfo(electiveList, page);
        } catch (Exception e) {
            throw new BusinessException(EmBusinessErr.GET_ELECTIVE_LIST_ERROR);
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
}
