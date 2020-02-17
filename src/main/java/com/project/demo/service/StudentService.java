package com.project.demo.service;

import com.project.demo.VO.UserVO;
import com.project.demo.error.BusinessException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface StudentService {
    String studentLogin(String account, String password) throws BusinessException;

    void studentRegistered(String nickName, String account, String password, String tel) throws BusinessException;

    UserVO getInfo(Integer userId) throws BusinessException;

    void writeMsg(Integer parentId, String content, Integer toId) throws BusinessException;

    Map getMassageListForSelf(Byte status, Integer pageNo, Integer pageSize) throws BusinessException;

    Map getMyWriteToList(Integer pageNo, Integer pageSize) throws BusinessException;

    Map searchUsers(String searchKey, Integer pageNo, Integer pageSize) throws BusinessException;

    void readMsg(Integer parentId) throws BusinessException;
}
