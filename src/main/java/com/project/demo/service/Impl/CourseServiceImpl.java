package com.project.demo.service.Impl;

import com.project.demo.dao.CourseDOMapper;
import com.project.demo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseDOMapper courseDOMapper;
}
