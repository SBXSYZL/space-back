package com.project.demo.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.project.demo.DO.CourseDO;
import com.project.demo.DO.MsgDO;
import com.project.demo.DO.UserDO;
import com.project.demo.DO.WorkDO;
import com.project.demo.VO.CourseVO;
import com.project.demo.VO.ElectiveVO;
import com.project.demo.VO.MsgVO;
import com.project.demo.VO.UserVO;
import com.project.demo.dao.*;
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

    UserDOMapper userDOMapper;


    ElectiveDOMapper electiveDOMapper;


    MsgDOMapper msgDOMapper;


    public TeacherServiceImpl(UserDOMapper userDOMapper, ElectiveDOMapper electiveDOMapper, MsgDOMapper msgDOMapper) {
        this.userDOMapper = userDOMapper;
        this.electiveDOMapper = electiveDOMapper;
        this.msgDOMapper = msgDOMapper;
    }

    @Override
    public String teacherLogin(String account, String password) throws BusinessException {
        return login(account, password, userDOMapper, (byte) 1);
    }

    protected static String login(String account, String password, UserDOMapper userDOMapper, Byte authority) throws BusinessException {
        try {
            UserDO userDO = userDOMapper.selectByAccount(account);
            if (userDO != null) {
                if (!userDO.getAuthority().equals(authority) ||
                        !userDO.getAccount().equals(account) ||
                        !userDO.getPassword().equals(MD5Util.getMD5(password))) {
                    throw new BusinessException(EmBusinessErr.USER_LOGIN_ERROR);
                } else {
                    MySessionUtil.getSession().setAttribute(MySessionUtil.USER_ID, userDO.getUserId());
                    return userDO.getNickName();
                }
            } else {
                throw new BusinessException(EmBusinessErr.USER_NOT_EXISTS);
            }
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                throw (BusinessException) e;
            } else {
                throw new BusinessException(EmBusinessErr.USER_NOT_EXISTS);
            }
        }
    }

    @Override
    public void teacherLogout() {
        logout();
    }

    protected static void logout() {
        MySessionUtil.getSession().removeAttribute(MySessionUtil.USER_ID);
    }

    @Override
    public void teacherRegistered(String nickName, String account, String password, String tel) throws BusinessException {
        registered(nickName, account, password, userDOMapper, tel, (byte) 1);
    }

    protected static void registered(String nickName, String account, String password, UserDOMapper userDOMapper, String tel, Byte authority) throws BusinessException {
        try {
            UserDO userDO = new UserDO();
            userDO.setNickName(nickName);
            userDO.setAccount(account);
            userDO.setPassword(MD5Util.getMD5(password));
            userDO.setTel(tel);
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
    public void writeMsg(Integer parentId, String content, Integer toId) throws BusinessException {
        try {
            Integer authorId = (Integer) MySessionUtil.getSession().getAttribute(MySessionUtil.USER_ID);
            MsgDO msgDO = new MsgDO();
            if (parentId != null) {
                msgDO.setParentId(parentId);
            }
            msgDO.setAuthorId(authorId);
            msgDO.setContent(content);
            msgDO.setToId(toId);
            msgDOMapper.writeMsg(msgDO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(EmBusinessErr.POST_MSG_ERROR);
        }
    }

    @Override
    public Map getMassageListForSelf(Byte status, Integer pageNo, Integer pageSize) throws BusinessException {
        try {
            Integer selfId = (Integer) MySessionUtil.getSession().getAttribute(MySessionUtil.USER_ID);
            Page page = PageHelper.startPage(pageNo, pageSize);
            List<MsgVO> massageListForSelf = msgDOMapper.getMassageListForSelf(selfId, status);
            return PageUtil.getListWithPageInfo(massageListForSelf, page);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(EmBusinessErr.GET_MSG_LIST_ERROR);
        }
    }

    @Override
    public Map getMyWriteToList(Integer pageNo, Integer pageSize) throws BusinessException {
        try {
            Integer selfId = (Integer) MySessionUtil.getSession().getAttribute(MySessionUtil.USER_ID);
            Page page = PageHelper.startPage(pageNo, pageSize);
            List<MsgVO> myWriteToList = msgDOMapper.getMyWriteToList(selfId);
            return PageUtil.getListWithPageInfo(myWriteToList, page);
        } catch (Exception e) {
            throw new BusinessException(EmBusinessErr.GET_MSG_LIST_ERROR);
        }
    }

    @Override
    public Map searchUsers(String searchKey, Integer pageNo, Integer pageSize) throws BusinessException {
        try {
            Page page = PageHelper.startPage(pageNo, pageSize);
            List<UserVO> userVOS = userDOMapper.searchUser(searchKey);
            return PageUtil.getListWithPageInfo(userVOS, page);
        } catch (Exception e) {
            throw new BusinessException(EmBusinessErr.SEARCH_USER_ERROR);
        }
    }

    @Override
    public void readMsg(Integer parentId) throws BusinessException {
        try {
            msgDOMapper.readMsg(parentId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(EmBusinessErr.POST_MSG_ERROR);
        }
    }
}
