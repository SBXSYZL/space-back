package com.project.demo.service.Impl;

import com.project.demo.DO.UserDO;
import com.project.demo.dao.UserDOMapper;
import com.project.demo.error.BusinessException;
import com.project.demo.error.EmBusinessErr;
import com.project.demo.service.StudentService;
import com.project.demo.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    UserDOMapper userDOMapper;

    @Override
    public void studentLogin(String account, String password) throws BusinessException {
        TeacherServiceImpl.login(account, password, userDOMapper, (byte) 0);
    }

    @Override
    public void studentRegistered(String nickName, String account, String password, String tel) throws BusinessException {
        TeacherServiceImpl.registered(nickName, account, password, userDOMapper, tel, (byte) 0);
    }
}
