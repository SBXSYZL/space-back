package com.project.demo.controller.student;

import com.project.demo.VO.ScoreVO;
import com.project.demo.VO.UserVO;
import com.project.demo.controller.BaseController;
import com.project.demo.error.BusinessException;
import com.project.demo.error.EmBusinessErr;
import com.project.demo.response.CommonReturnType;
import com.project.demo.response.RTStr;
import com.project.demo.service.CourseService;
import com.project.demo.service.FileService;
import com.project.demo.service.StudentService;
import com.project.demo.utils.DecimalUtil;
import com.project.demo.utils.FileUtil;
import com.project.demo.utils.MySessionUtil;
import com.project.demo.validator.MyValidator;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
    private final FileService fileService;

    public StudentController(StudentService studentService,
                             CourseService courseService,
                             FileService fileService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.fileService = fileService;
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


    @ApiOperation("获取学生可选课程列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "页码"),
            @ApiImplicitParam(name = "pageSize", value = "数据量")
    })
    @GetMapping("/getOptionalCourseList")
    public CommonReturnType getOptionalCourseList(@RequestParam Integer pageNo,
                                                  @RequestParam Integer pageSize) throws BusinessException {
        Integer userId = (Integer) MySessionUtil.getSession().getAttribute(MySessionUtil.USER_ID);
        Map optionalCourseList = courseService.getOptionalCourseList(pageNo, pageSize, userId);
        return CommonReturnType.create(optionalCourseList);
    }


    @ApiOperation("获取学生选课成绩")
    @ApiImplicitParams({@ApiImplicitParam(name = "courseId", value = "课程ID")})
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

    @ApiOperation("学生加入课程")
    @ApiImplicitParams({@ApiImplicitParam(name = "courseId", value = "课程ID")})
    @GetMapping("/joinCourse")
    public CommonReturnType joinCourse(@RequestParam Integer courseId) throws BusinessException {
        Integer userId = (Integer) MySessionUtil.getSession().getAttribute(MySessionUtil.USER_ID);
        courseService.joinCourse(userId, courseId);
        return CommonReturnType.create(RTStr.SUCCESS);
    }


    @ApiOperation("获取学生信息")
    @GetMapping("/getInfo")
    public CommonReturnType getInfo() throws BusinessException {
        Integer userId = (Integer) MySessionUtil.getSession().getAttribute(MySessionUtil.USER_ID);
        UserVO info = studentService.getInfo(userId);
        return CommonReturnType.create(info);
    }

    @ApiOperation("获取课程的所有课时")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "页码"),
            @ApiImplicitParam(name = "pageSize", value = "数据量")
    })
    @GetMapping("/getWorkOfCourse")
    public CommonReturnType getWorkOfCourse(@RequestParam Integer pageNo,
                                            @RequestParam Integer pageSize) throws BusinessException {
        Integer userId = (Integer) MySessionUtil.getSession().getAttribute(MySessionUtil.USER_ID);
        Map workOfCourse = courseService.getWorkOfCourse(userId, pageNo, pageSize);
        return CommonReturnType.create(workOfCourse);
    }


    @ApiOperation("获取作业成绩")
    @GetMapping("/getWorkScore")
    public CommonReturnType getWorkScore(@RequestParam Integer workId) throws BusinessException {
        Integer userId = (Integer) MySessionUtil.getSession().getAttribute(MySessionUtil.USER_ID);
        Integer workScore = courseService.getWorkScore(userId, workId);
        return CommonReturnType.create(workScore);
    }


    @ApiOperation("回复消息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentId", value = "被回复消息ID"),
            @ApiImplicitParam(name = "content", value = "回复内容"),
            @ApiImplicitParam(name = "toId", value = "被回复人ID")
    })
    @PostMapping("/replyMessage")
    public CommonReturnType replyMessage(@RequestParam Integer parentId,
                                         @RequestParam String content,
                                         @RequestParam Integer toId) throws BusinessException {
        studentService.readMsg(parentId);
        studentService.writeMsg(parentId, content, toId);
        return CommonReturnType.create(RTStr.SUCCESS);
    }

    @ApiOperation("发送消息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "content", value = "消息内容"),
            @ApiImplicitParam(name = "toId", value = "对方ID")
    })
    @PostMapping("/writeMessageTo")
    public CommonReturnType writeMessageTo(@RequestParam String content,
                                           @RequestParam Integer toId) throws BusinessException {
        studentService.writeMsg(null, content, toId);
        return CommonReturnType.create(RTStr.SUCCESS);
    }

    @ApiOperation("获取未读消息列表-分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "页码"),
            @ApiImplicitParam(name = "pageSize", value = "数据量")
    })
    @GetMapping("/getUnreadMessageList")
    public CommonReturnType getUnreadMessageList(@RequestParam Integer pageNo,
                                                 @RequestParam Integer pageSize) throws BusinessException {
        MyValidator.checkIntNull(pageNo, pageSize);
        Map massageListForSelf = studentService.getMassageListForSelf((byte) 0, pageNo, pageSize);
        return CommonReturnType.create(massageListForSelf);
    }

    @ApiOperation("获取已读消息列表-分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "页码"),
            @ApiImplicitParam(name = "pageSize", value = "数据量")
    })
    @GetMapping("/getHaveReadMessageList")
    public CommonReturnType getHaveReadMessageList(@RequestParam Integer pageNo,
                                                   @RequestParam Integer pageSize) throws BusinessException {
        MyValidator.checkIntNull(pageNo, pageSize);
        Map massageListForSelf = studentService.getMassageListForSelf((byte) 1, pageNo, pageSize);
        return CommonReturnType.create(massageListForSelf);
    }

    @ApiOperation("获取已发送信息")
    @ApiImplicitParams({})
    @GetMapping("/getMySendMsg")
    public CommonReturnType getMySendMsg(@RequestParam Integer pageNo,
                                         @RequestParam Integer pageSize) throws BusinessException {
        MyValidator.checkIntNull(pageNo, pageSize);
        Map myWriteToList = studentService.getMyWriteToList(pageNo, pageSize);
        return CommonReturnType.create(myWriteToList);
    }


    @ApiOperation("搜索联系人-分页")
    @ApiImplicitParam(name = "searchKey", value = "搜索关键字")
    @GetMapping("/searchUser")
    public CommonReturnType searchUser(@RequestParam String searchKey,
                                       @RequestParam Integer pageNo,
                                       @RequestParam Integer pageSize) throws BusinessException {
        MyValidator.checkIntNull(pageNo, pageSize);
        Map map = studentService.searchUsers(searchKey, pageNo, pageSize);
        return CommonReturnType.create(map);
    }

    @ApiOperation("提交作业")
    @ApiImplicitParams({})
    @PostMapping("/submitWork")
    @Transactional
    public CommonReturnType submitWork(@RequestParam("file") MultipartFile file,
                                       @RequestParam Integer courseId,
                                       @RequestParam Integer workId) throws BusinessException {
        //存储方式：work1的提交文件a.txt存储为 work/#{workId}/a.txt
        if (file.isEmpty()) {
            throw new BusinessException(EmBusinessErr.FILE_UPLOAD_ERROR);
        }
        String fileName = FileUtil.saveFile(file, "work/workId_" + workId);
        Integer userId = (Integer) MySessionUtil.getSession().getAttribute(MySessionUtil.USER_ID);
        courseService.submitWork(userId, courseId, workId, fileName);
        return CommonReturnType.create(RTStr.SUCCESS);
    }
}
