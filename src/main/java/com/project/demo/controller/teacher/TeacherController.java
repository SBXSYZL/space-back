package com.project.demo.controller.teacher;

import com.project.demo.controller.BaseController;
import com.project.demo.error.BusinessException;
import com.project.demo.response.CommonReturnType;
import com.project.demo.response.RTStr;
import com.project.demo.service.TeacherService;
import com.project.demo.utils.MySessionUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/teacher")
public class TeacherController extends BaseController {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @ApiOperation("教师账号注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nickName", value = "昵称"),
            @ApiImplicitParam(name = "account", value = "账号"),
            @ApiImplicitParam(name = "password", value = "密码")
    })
    @PostMapping("/teacherRegistered")
    public CommonReturnType teacherRegistered(@RequestParam String nickName,
                                              @RequestParam String account,
                                              @RequestParam String password) throws BusinessException {
        teacherService.teacherRegistered(nickName, account, password);
        return CommonReturnType.create(RTStr.SUCCESS);
    }


    @PostMapping("/teacherLogin")
    @ApiOperation("教师登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "账号"),
            @ApiImplicitParam(name = "password", value = "密码")
    })
    public CommonReturnType teacherLogin(@RequestParam String account,
                                         @RequestParam String password) throws BusinessException {
        teacherService.teacherLogin(account, password);
        return CommonReturnType.create(RTStr.SUCCESS);
    }

    @GetMapping("/getCourseList")
    @ApiOperation("获取课程列表-分页 ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID"),
            @ApiImplicitParam(name = "pageNo", value = "页码"),
            @ApiImplicitParam(name = "pageSize", value = "数据量")
    })
    public CommonReturnType getCourseList(@RequestParam(required = false) Integer userId,
                                          @RequestParam Integer pageNo,
                                          @RequestParam Integer pageSize) throws BusinessException {
        if (userId == null) {
            userId = (Integer) MySessionUtil.getSession().getAttribute(MySessionUtil.USER_ID);
        }
        Map courseList = teacherService.getCourseList(userId, pageNo, pageSize);
        return CommonReturnType.create(courseList);
    }

    @ApiOperation("获取课时列表-分页 ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId", value = "课程ID"),
            @ApiImplicitParam(name = "pageNo", value = "页码"),
            @ApiImplicitParam(name = "pageSize", value = "数据量")
    })
    @GetMapping("/getLessonList")
    public CommonReturnType getLessonList(@RequestParam Integer courseId,
                                          @RequestParam Integer pageNo,
                                          @RequestParam Integer pageSize) throws BusinessException {
        Map lessonList = teacherService.getLessonList(courseId, pageNo, pageSize);
        return CommonReturnType.create(lessonList);
    }

    @GetMapping("/createCourse")
    @ApiOperation("创建课程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseName", value = "课程名"),
            @ApiImplicitParam(name = "deadline", value = "截止日期"),
            @ApiImplicitParam(name = "courseDescription", value = "课程描述")
    })
    public CommonReturnType createCourse(@RequestParam String courseName,
                                         @RequestParam Date deadline,
                                         @RequestParam String courseDescription) throws BusinessException {
        teacherService.createCourse(courseName, deadline, courseDescription);
        return CommonReturnType.create(RTStr.SUCCESS);
    }


    @GetMapping("/getElectiveList")
    @ApiOperation("获取选中该门课的学生列表-分页")
    public CommonReturnType getElectiveList(@RequestParam Integer courseId,
                                            @RequestParam Integer pageNo,
                                            @RequestParam Integer pageSize) throws BusinessException {
        Map electiveList = teacherService.getElectiveList(courseId, pageNo, pageSize);
        return CommonReturnType.create(electiveList);
    }

    @GetMapping("/deleteCourse")
    @ApiOperation("删除课程")
    @ApiImplicitParam(name = "courseId", value = "课程ID")
    public CommonReturnType deleteCourse(@RequestParam Integer courseId) throws BusinessException {
        teacherService.deleteCourse(courseId);
        return CommonReturnType.create(RTStr.SUCCESS);
    }

    @GetMapping("/searchCourse")
    @ApiOperation("搜索课程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "searchKey", value = "搜索关键字"),
            @ApiImplicitParam(name = "pageNo", value = "页码"),
            @ApiImplicitParam(name = "pageSize", value = "数据量")
    })
    public CommonReturnType searchCourse(@RequestParam String searchKey,
                                         @RequestParam Integer pageNo,
                                         @RequestParam Integer pageSize) throws BusinessException {
        Map map = teacherService.searchCourseList(searchKey, pageNo, pageSize);
        return CommonReturnType.create(map);
    }
}
