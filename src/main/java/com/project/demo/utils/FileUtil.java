package com.project.demo.utils;


import com.project.demo.error.BusinessException;
import com.project.demo.error.EmBusinessErr;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;

import java.io.File;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class FileUtil {
    //    public static final String VIRTUAL_FILE_PATH = "D:\\MyDataArea\\JAVA_CODE\\fileTest\\";
//    public static final String VIRTUAL_FILE_PATH = "files/";

    public static void saveFile(MultipartFile file) throws BusinessException {
        try {
            String fileName = file.getOriginalFilename();
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            if (!path.exists()) {
                path = new File("");
            }
            File upload = new File(path.getAbsolutePath(), "static/upload/" + fileName);
            if (!upload.exists()) {
                upload.mkdirs();
            }
            file.transferTo(upload);
//            File dest = new File(VIRTUAL_FILE_PATH + fileName);
//            file.transferTo(dest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(EmBusinessErr.FILE_SAVE_ERROR);
        }
    }

    public static ResponseEntity<Resource> getFile(String fileName) throws BusinessException {
        try {
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            if (!path.exists()) {
                path = new File("");
            }
            File upload = new File(path.getAbsolutePath(), "static/upload/" + fileName);
            FileSystemResource resource = new FileSystemResource(upload.getAbsolutePath());
            String mediaTypeStr = URLConnection.getFileNameMap().getContentTypeFor(fileName);
            mediaTypeStr = (mediaTypeStr == null) ? MediaType.APPLICATION_OCTET_STREAM_VALUE : mediaTypeStr;
            MediaType mediaType = MediaType.parseMediaType(mediaTypeStr);
            HttpHeaders headers = new HttpHeaders();
            String fileNames = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            headers.setContentDispositionFormData("attachment", fileNames);
            headers.setContentType(mediaType);

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(resource.getInputStream().available())
                    .body(resource);
        } catch (Exception e) {
            throw new BusinessException(EmBusinessErr.FILE_NOT_FOUND);
        }

    }

}
