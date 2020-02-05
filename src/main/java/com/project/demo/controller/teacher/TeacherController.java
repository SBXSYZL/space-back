package com.project.demo.controller.teacher;

import com.project.demo.VO.FileVO;
import com.project.demo.controller.BaseController;
import com.project.demo.error.BusinessException;
import com.project.demo.error.EmBusinessErr;
import com.project.demo.response.CommonReturnType;
import com.project.demo.response.RTStr;
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

    public TeacherController(TeacherService teacherService, FileService fileService) {
        this.teacherService = teacherService;
        this.fileService = fileService;
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
        Map lessonList = teacherService.getWorkList(courseId, pageNo, pageSize);
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
        teacherService.createCourse(courseName, deadline, schedule, courseDescription);
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

    @ApiOperation("删除文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileName", value = "文件名"),
            @ApiImplicitParam(name = "dir", value = "文件所属目录")
    })
    @GetMapping("deleteFile")
    public CommonReturnType deleteFile(@RequestParam String fileName,
                                       @RequestParam(required = false) String dir) throws BusinessException {
        if (dir != null) {
            FileUtil.deleteFile(fileName, dir);
        } else {
            FileUtil.deleteFile(fileName);
        }

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

    @ApiOperation("获取未读消息列表")
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

    @ApiOperation("获取已读消息列表")
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


    @ApiOperation("搜索联系人")
    @ApiImplicitParam(name = "searchKey", value = "搜索关键字")
    @GetMapping("/searchUser")
    public CommonReturnType searchUser(@RequestParam String searchKey,
                                       @RequestParam Integer pageNo,
                                       @RequestParam Integer pageSize) throws BusinessException {
        Map map = teacherService.searchUsers(searchKey, pageNo, pageSize);
        return CommonReturnType.create(map);
    }
}
