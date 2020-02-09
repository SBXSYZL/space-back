package com.project.demo.controller.student;

import com.project.demo.VO.ScoreVO;
import com.project.demo.controller.BaseController;
import com.project.demo.error.BusinessException;
import com.project.demo.error.EmBusinessErr;
import com.project.demo.response.CommonReturnType;
import com.project.demo.response.RTStr;
import com.project.demo.service.CourseService;
import com.project.demo.service.StudentService;
import com.project.demo.utils.DecimalUtil;
import com.project.demo.utils.FileUtil;
import com.project.demo.utils.MySessionUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController extends BaseController {
    private final StudentService studentService;
    private final CourseService courseService;

    public StudentController(StudentService studentService,
                             CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @ApiOperation("学生注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nickName", value = "昵称"),
            @ApiImplicitParam(name = "account", value = "账号"),
            @ApiImplicitParam(name = "password", value = "密码"),
            @ApiImplicitParam(name = "tel", value = "手机号")
    })
    @PostMapping("/studentRegistered")
    public CommonReturnType studentRegistered(@RequestParam String nickName,
                                              @RequestParam String account,
                                              @RequestParam String password,
                                              @RequestParam String tel) throws BusinessException {
        studentService.studentRegistered(nickName, account, password, tel);
        return CommonReturnType.create(RTStr.SUCCESS);
    }

    @ApiOperation("学生登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "账号"),
            @ApiImplicitParam(name = "password", value = "密码")
    })
    @PostMapping("/studentLogin")
    public CommonReturnType studentLogin(@RequestParam String account,
                                         @RequestParam String password) throws BusinessException {
        studentService.studentLogin(account, password);
        return CommonReturnType.create(RTStr.SUCCESS);
    }

//    @PostMapping("/submitWork")
//    public CommonReturnType submitWork(@RequestParam("file") MultipartFile file) throws BusinessException {
//        if (file.isEmpty()) {
//            throw new BusinessException(EmBusinessErr.FILE_UPLOAD_ERROR);
//        } else {
//            String fileName = FileUtil.saveFile(file);
//            return CommonReturnType.create(fileName + " submit success");
//        }
//    }
//
//    @GetMapping("/downloadFile")
//    public ResponseEntity<Resource> downloadFile(@RequestParam String fileName) throws BusinessException {
//        return FileUtil.getFile(fileName);
//
//    }

//    @GetMapping("/delete")

    @ApiOperation("获取学生已选中课程列表-分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "页码"),
            @ApiImplicitParam(name = "pageSize", value = "数据量")
    })
    @GetMapping("/getSelectedCourseList")
    public CommonReturnType getSelectedCourseList(@RequestParam Integer pageNo,
                                                  @RequestParam Integer pageSize) throws BusinessException {
        Integer userId = (Integer) MySessionUtil.getSession().getAttribute(MySessionUtil.USER_ID);
        Map selectedCourseList = courseService.getSelectedCourseList(userId, pageNo, pageSize);
        return CommonReturnType.create(selectedCourseList);
    }

    @ApiOperation("获取学生选课成绩")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "")
    })
    @GetMapping("/getCourseScore")
    public CommonReturnType getCourseScore(@RequestParam Integer courseId) throws BusinessException {
        Integer userId = (Integer) MySessionUtil.getSession().getAttribute(MySessionUtil.USER_ID);
        ScoreVO courseScore = courseService.getCourseScore(userId, courseId);
        if (courseScore != null) {

            courseScore.setTotalScore(DecimalUtil.avg(courseScore.getExamScore(),
                    courseScore.getPerformanceScore(),
                    courseScore.getWorkScore()));
        }
        return CommonReturnType.create(courseScore);
    }

//    @ApiOperation("学生加入课程")
//    @ApiImplicitParam()
//    @GetMapping("/joinCourse")
//    public CommonReturnType joinCourse(@RequestParam Integer courseId) {
//
//    }
}
