package com.project.demo.controller.student;

import com.project.demo.controller.BaseController;
import com.project.demo.error.BusinessException;
import com.project.demo.error.EmBusinessErr;
import com.project.demo.response.CommonReturnType;
import com.project.demo.response.RTStr;
import com.project.demo.service.StudentService;
import com.project.demo.utils.FileUtil;
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

@RestController
@RequestMapping("/student")
public class StudentController extends BaseController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/studentRegistered")
    public CommonReturnType studentRegistered(@RequestParam String nickName,
                                              @RequestParam String account,
                                              @RequestParam String password) throws BusinessException {
        studentService.studentRegistered(nickName, account, password);
        return CommonReturnType.create(RTStr.SUCCESS);
    }


    @PostMapping("/studentLogin")
    public CommonReturnType studentLogin(@RequestParam String account,
                                         @RequestParam String password) throws BusinessException {
        studentService.studentLogin(account, password);
        return CommonReturnType.create(RTStr.SUCCESS);
    }

    @PostMapping("/submitWork")
    public CommonReturnType submitWork(@RequestParam("file") MultipartFile file) throws BusinessException {
        if (file.isEmpty()) {
            throw new BusinessException(EmBusinessErr.FILE_UPLOAD_ERROR);
        } else {
            String fileName = FileUtil.saveFile(file);
            return CommonReturnType.create(fileName + " submit success");
        }
    }

    @GetMapping("/downloadFile")
    public ResponseEntity<Resource> downloadFile(@RequestParam String fileName) throws BusinessException {
        return FileUtil.getFile(fileName);

    }

//    @GetMapping("/delete")
}
