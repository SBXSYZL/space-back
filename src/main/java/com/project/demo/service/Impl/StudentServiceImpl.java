package com.project.demo.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.project.demo.DO.MsgDO;
import com.project.demo.DO.UserDO;
import com.project.demo.VO.MsgVO;
import com.project.demo.VO.UserVO;
import com.project.demo.dao.MsgDOMapper;
import com.project.demo.dao.UserDOMapper;
import com.project.demo.error.BusinessException;
import com.project.demo.error.EmBusinessErr;
import com.project.demo.service.StudentService;
import com.project.demo.utils.MD5Util;
import com.project.demo.utils.MySessionUtil;
import com.project.demo.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {
    private final UserDOMapper userDOMapper;

    private final MsgDOMapper msgDOMapper;

    public StudentServiceImpl(MsgDOMapper msgDOMapper, UserDOMapper userDOMapper) {
        this.msgDOMapper = msgDOMapper;
        this.userDOMapper = userDOMapper;
    }

    @Override
    public String studentLogin(String account, String password) throws BusinessException {
        return TeacherServiceImpl.login(account, password, userDOMapper, (byte) 0);
    }

    @Override
    public void studentRegistered(String nickName, String account, String password, String tel) throws BusinessException {
        TeacherServiceImpl.registered(nickName, account, password, userDOMapper, tel, (byte) 0);
    }

    @Override
    public UserVO getInfo(Integer userId) throws BusinessException {
        try {
            return userDOMapper.getInfo(userId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(EmBusinessErr.GET_INFO_ERROR);
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

    @Override
    public void modifyPass(Integer userId, String oldPass, String newPass) throws BusinessException {
        try {
            userDOMapper.modifyPass(userId, MD5Util.getMD5(oldPass), MD5Util.getMD5(newPass));
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(EmBusinessErr.USER_PSD_MODIFY_ERROR);
        }
    }
}
