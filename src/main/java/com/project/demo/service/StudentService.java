package com.project.demo.service;

import com.project.demo.error.BusinessException;
import org.springframework.stereotype.Service;

@Service
public interface StudentService {
    void studentLogin(String account, String password) throws BusinessException;

    void studentRegistered(String nickName, String account, String password,String tel) throws BusinessException;
}
