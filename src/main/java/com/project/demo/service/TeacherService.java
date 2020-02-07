package com.project.demo.service;

import com.project.demo.error.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.Map;

@Service
public interface TeacherService {
    String teacherLogin(String account, String password) throws BusinessException;

    void teacherLogout();

    void teacherRegistered(String nickName, String account, String password, String tel) throws BusinessException;









    Map getElectiveList(Integer courseId, Integer pageNo, Integer pageSize) throws BusinessException;



    void writeMsg(Integer parentId, String content, Integer toId) throws BusinessException;

    Map getMassageListForSelf(Byte status, Integer pageNo, Integer pageSize) throws BusinessException;

    Map getMyWriteToList(Integer pageNo, Integer pageSize) throws BusinessException;

    Map searchUsers(String searchKey, Integer pageNo, Integer pageSize) throws BusinessException;


}
