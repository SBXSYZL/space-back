package com.project.demo.controller.teacher;

import com.project.demo.VO.FileVO;
import com.project.demo.controller.BaseController;
import com.project.demo.error.BusinessException;
import com.project.demo.error.EmBusinessErr;
import com.project.demo.response.CommonReturnType;
import com.project.demo.response.RTStr;
import com.project.demo.service.CourseService;
import com.project.demo.service.FileService;
import com.project.demo.service.TeacherService;
import com.project.demo.utils.FileUtil;
import com.project.demo.utils.MySessionUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javafx.util.Pair;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/teacher")
public class TeacherController extends BaseController {
    private final TeacherService teacherService;
    private final FileService fileService;
    private final CourseService courseService;

    public TeacherController(TeacherService teacherService,
                             FileService fileService,
                             CourseService courseService) {
        this.teacherService = teacherService;
        this.fileService = fileService;
        this.courseService = courseService;
    }

    @ApiOperation("教师账号注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nickName", value = "昵称"),
            @ApiImplicitParam(name = "account", value = "账号"),
            @ApiImplicitParam(name = "password", value = "密码"),
            @ApiImplicitParam(name = "tel", value = "联系方式")
    })
    @PostMapping("/teacherRegistered")
    public CommonReturnType teacherRegistered(@RequestParam String nickName,
                                              @RequestParam String account,
                                              @RequestParam String password,
                                              @RequestParam String tel) throws BusinessException {
        teacherService.teacherRegistered(nickName, account, password, tel);
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
        String s = teacherService.teacherLogin(account, password);
        return CommonReturnType.create(s);
    }


    @GetMapping("/teacherLogout")
    @ApiOperation("教师登出")
    public void teacherLogout() {
        teacherService.teacherLogout();
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
        Map courseList = courseService.getCourseList(userId, pageNo, pageSize);
        return CommonReturnType.create(courseList);
    }

    @ApiOperation("获取课时列表-分页 ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "页码"),
            @ApiImplicitParam(name = "pageSize", value = "数据量")
    })
    @GetMapping("/getLessonList")
    public CommonReturnType getLessonList(@RequestParam Integer pageNo,
                                          @RequestParam Integer pageSize) throws BusinessException {
        Integer userId = (Integer) MySessionUtil.getSession().getAttribute(MySessionUtil.USER_ID);
        Map lessonList = courseService.getWorkList(userId, pageNo, pageSize);
        return CommonReturnType.create(lessonList);
    }

    @GetMapping("/createCourse")
    @ApiOperation("创建课程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseName", value = "课程名"),
            @ApiImplicitParam(name = "deadline", value = "截止日期"),
            @ApiImplicitParam(name = "schedule", value = "课时数量"),
            @ApiImplicitParam(name = "courseDescription", value = "课程描述")
    })
    public CommonReturnType createCourse(@RequestParam String courseName,
                                         @RequestParam Date deadline,
                                         @RequestParam Integer schedule,
                                         @RequestParam String courseDescription) throws BusinessException {
        courseService.createCourse(courseName, deadline, schedule, courseDescription);
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
        courseService.deleteCourse(courseId);
        return CommonReturnType.create(RTStr.SUCCESS);
    }

    @GetMapping("/searchCourse")
    @ApiOperation("搜索课程-分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "searchKey", value = "搜索关键字"),
            @ApiImplicitParam(name = "pageNo", value = "页码"),
            @ApiImplicitParam(name = "pageSize", value = "数据量")
    })
    public CommonReturnType searchCourse(@RequestParam String searchKey,
                                         @RequestParam Integer pageNo,
                                         @RequestParam Integer pageSize) throws BusinessException {
        Integer userId = (Integer) MySessionUtil.getSession().getAttribute(MySessionUtil.USER_ID);
        Map map = courseService.searchCourseList(searchKey, pageNo, pageSize, userId);
        return CommonReturnType.create(map);
    }

    @ApiOperation("删除文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileName", value = "文件名,如 1.jpg"),
            @ApiImplicitParam(name = "fileId", value = "文件ID"),
            @ApiImplicitParam(name = "dir", value = "文件所属目录，如删除用户8目录下的a文件夹下的b文件夹的1.jpg: a/b ")
    })
    @GetMapping("/deleteFile")
    public CommonReturnType deleteFile(@RequestParam String fileName,
                                       @RequestParam Integer fileId,
                                       @RequestParam(required = false) String dir) throws BusinessException {
        Integer userId = (Integer) MySessionUtil.getSession().getAttribute(MySessionUtil.USER_ID);
        if (dir != null) {
            dir = userId + "/" + dir;
        } else {
            dir = userId + "/";
        }
        FileUtil.deleteFile(fileName, dir);
        fileService.deleteFile(fileId);
        return CommonReturnType.create(RTStr.SUCCESS);
    }

    @PostMapping("/uploadFile")
    @ApiOperation("上传文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dir", value = "要上传的文件目录，如 a/a"),
            @ApiImplicitParam(name = "folderId", value = "要上传的目录ID")
    })
    @Transactional
    public CommonReturnType submitWork(@RequestParam("file") MultipartFile file,
                                       @RequestParam(required = false) String dir,
                                       @RequestParam(required = false) Integer folderId) throws BusinessException {
        if (file.isEmpty()) {
            throw new BusinessException(EmBusinessErr.FILE_UPLOAD_ERROR);
        } else {
            String fileName = null;
            Integer userId = (Integer) MySessionUtil.getSession().getAttribute(MySessionUtil.USER_ID);
            if (dir != null) {
                dir = userId + "/" + dir;
            } else {
                dir = userId + "/";
            }
            fileName = FileUtil.saveFile(file, dir);
            if (folderId == null) folderId = 0;
            fileName = fileName;
            fileService.uploadFile(fileName, folderId, userId);
            return CommonReturnType.create(RTStr.SUCCESS);
        }
    }

    @ApiOperation("下载文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "path", value = "文件路径（若有目录需要一起组成字符串传入，如 a/1.jpg ）")
    })
    @GetMapping("/downloadFile")
    public ResponseEntity<Resource> downloadFile(@RequestParam String path) throws BusinessException {
        Integer userId = (Integer) MySessionUtil.getSession().getAttribute(MySessionUtil.USER_ID);
        path = userId + "/" + path;
//        System.out.println(path);
        return FileUtil.getFile(path);
    }


    @ApiOperation("创建文件夹")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "path", value = "完整路径，如用户8在根目录下创建a目录，传入a,在a下创建目录b，传入a/b"),
            @ApiImplicitParam(name = "parentFolderId", value = "父文件夹的ID"),
            @ApiImplicitParam(name = "folderName", value = "文件夹名称")
    })
    @GetMapping("createDirs")
    @Transactional
    public CommonReturnType createDirs(@RequestParam String path,
                                       @RequestParam Integer parentFolderId,
                                       @RequestParam String folderName) throws BusinessException, IOException {
        Integer userId = (Integer) MySessionUtil.getSession().getAttribute(MySessionUtil.USER_ID);
        path = userId + "/" + path;
        System.out.println(path);
        File file = FileUtil.createDirs(path);
//        String url = file.getAbsolutePath();
//        int index = url.indexOf("\\static\\upload\\");
//        fileService.createFolder(url.substring(index + 15).replace('\\', '/'), parentFolderId);
        fileService.createFolder(folderName, parentFolderId);
        return CommonReturnType.create(RTStr.SUCCESS);
    }


    @ApiOperation("删除文件夹")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "folderId", value = "文件夹ID"),
            @ApiImplicitParam(name = "path", value = "文件夹完整路径，如删除用户8目录下的a目录下的b目录： a/b")
    })
    @GetMapping("/deleteDir")
    public CommonReturnType deleteDir(@RequestParam Integer folderId,
                                      @RequestParam String path) throws BusinessException {
        fileService.deleteDir(folderId);
        Integer userId = (Integer) MySessionUtil.getSession().getAttribute(MySessionUtil.USER_ID);
        if (path != null) {
            path = userId + "/" + path;
        } else {
            path = userId + "/";
        }

//        System.out.println("---------------------------------------------------------------------");
//        System.out.println(path);
        FileUtil.deleteDir(path);
        return CommonReturnType.create(RTStr.SUCCESS);
    }

    @ApiOperation("获取目录下所有文件/目录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "folderId", value = "文件夹ID")
    })
    @GetMapping("/getFilesUnderFolderId")
    public CommonReturnType getFilesUnderFolderId(@RequestParam Integer folderId) throws BusinessException {
        Integer userId = (Integer) MySessionUtil.getSession().getAttribute(MySessionUtil.USER_ID);
        List<FileVO> files = fileService.getFilesUnderFolderId(folderId, userId);
        return CommonReturnType.create(files);
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
        teacherService.readMsg(parentId);
        teacherService.writeMsg(parentId, content, toId);
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
        teacherService.writeMsg(null, content, toId);
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
        Map massageListForSelf = teacherService.getMassageListForSelf((byte) 0, pageNo, pageSize);
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
        Map massageListForSelf = teacherService.getMassageListForSelf((byte) 1, pageNo, pageSize);
        return CommonReturnType.create(massageListForSelf);
    }

    @ApiOperation("获取已发送信息")
    @ApiImplicitParams({})
    @GetMapping("/getMySendMsg")
    public CommonReturnType getMySendMsg(@RequestParam Integer pageNo,
                                         @RequestParam Integer pageSize) throws BusinessException {
        Map myWriteToList = teacherService.getMyWriteToList(pageNo, pageSize);
        return CommonReturnType.create(myWriteToList);
    }


    @ApiOperation("搜索联系人-分页")
    @ApiImplicitParam(name = "searchKey", value = "搜索关键字")
    @GetMapping("/searchUser")
    public CommonReturnType searchUser(@RequestParam String searchKey,
                                       @RequestParam Integer pageNo,
                                       @RequestParam Integer pageSize) throws BusinessException {
        Map map = teacherService.searchUsers(searchKey, pageNo, pageSize);
        return CommonReturnType.create(map);
    }

    @ApiOperation("创建课时")
    @ApiImplicitParams({})
    @GetMapping("createWork")
    public CommonReturnType createWork(@RequestParam Integer courseId,
                                       @RequestParam String workName,
                                       @RequestParam Date deadline,
                                       @RequestParam String workDesc) throws BusinessException {
        courseService.createWork(courseId, workName, deadline, workDesc);
        return CommonReturnType.create(RTStr.SUCCESS);
    }

    @ApiOperation("获得该课时学生提交作业列表-分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "workId", value = "课时ID"),
            @ApiImplicitParam(name = "pageNo", value = "页码"),
            @ApiImplicitParam(name = "pageSize", value = "数据量 ")
    })
    @GetMapping("/getListOfStudentSubmissionsForTheClass")
    public CommonReturnType getListOfStudentSubmissionsForTheClass(@RequestParam Integer workId,
                                                                   @RequestParam Integer pageNo,
                                                                   @RequestParam Integer pageSize) throws BusinessException {
        Map map = courseService.getListOfStudentSubmissionsForTheClass(workId, pageNo, pageSize);
        return CommonReturnType.create(map);
    }

    @ApiOperation("教师提交学生作业评价")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "submitId", value = "提交作业的ID"),
            @ApiImplicitParam(name = "status", value = "作业是否提交"),
            @ApiImplicitParam(name = "score", value = "作业评分")
    })
    @GetMapping("/gradeAssignment")
    public CommonReturnType gradeAssignment(@RequestParam Integer submitId,
                                            @RequestParam Integer status,
                                            @RequestParam Integer score) throws BusinessException {
        Byte r_status = status == 1 ? (byte) 1 : (byte) 0;
        courseService.gradeAssignment(submitId, r_status, score);
        return CommonReturnType.create(RTStr.SUCCESS);
    }

    @ApiOperation("下载学生作业")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "submitId", value = "提交的作业ID"),
            @ApiImplicitParam(name = "content", value = "文件名")
    })
    @GetMapping("/downloadSubmitWork")
    public ResponseEntity<Resource> downloadSubmitWork(@RequestParam Integer submitId,
                                                       @RequestParam String content) throws BusinessException {
        String path = "submitId_" + submitId + "/" + content;
        return FileUtil.getFile(path);
    }

    @ApiOperation("搜索课时")
    @ApiImplicitParams({})
    @GetMapping("/searchWork")
    public CommonReturnType searchWork(@RequestParam String searchKey,
                                       @RequestParam Integer pageNo,
                                       @RequestParam Integer pageSize) throws BusinessException {
        Integer userId = (Integer) MySessionUtil.getSession().getAttribute(MySessionUtil.USER_ID);
        Map map = courseService.searchWork(userId, searchKey, pageNo, pageSize);
        return CommonReturnType.create(map);
    }

    @ApiOperation("教师对学生课程评分")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId", value = "课程ID"),
            @ApiImplicitParam(name = "userId", value = "学生ID"),
            @ApiImplicitParam(name = "performanceScore", value = "表现分"),
            @ApiImplicitParam(name = "examScore", value = "考试分")
    })
    @GetMapping("/courseGrading")
    public CommonReturnType courseGrading(@RequestParam Integer courseId,
                                          @RequestParam Integer userId,
                                          @RequestParam Float performanceScore,
                                          @RequestParam Float examScore) throws BusinessException {
        Integer teacherId = (Integer) MySessionUtil.getSession().getAttribute(MySessionUtil.USER_ID);
        courseService.courseGrading(courseId, userId, teacherId, performanceScore, examScore);
        return CommonReturnType.create(RTStr.SUCCESS);
    }


}
