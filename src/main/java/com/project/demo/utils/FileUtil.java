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
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class FileUtil {

    private static final String STATIC_UPLOAD = "static/upload";

    //有一个问题，一直new出很多File对象，会造成GC问题，
    // 还有就是stringBuilder线程不安全，可能造成错误，
    //使用同步锁尝试锁住，不知道是否有用空，有待观察
    /*
     * 保存文件
     * */
    public static synchronized String saveFile(MultipartFile file, String dir) throws BusinessException {
        try {
            StringBuilder fileName = new StringBuilder(Objects.requireNonNull(file.getOriginalFilename()));
            StringBuilder resultPath = new StringBuilder("static/upload/");
            //创建jar包的相对路径文件
//            File path = new File(ResourceUtils.getURL("classpath:").getPath());
//            if (!path.exists()) {
//                path = new File("");
//            }
            File path = createAbsoluteJarFile();
            //是否存在static/upload目录，不存在则创建
            File upload = null;
            if (dir != null) {
                upload = new File(path.getAbsolutePath(), STATIC_UPLOAD + "/" + dir);
            } else {
                upload = new File(path.getAbsolutePath(), STATIC_UPLOAD);
            }

            if (!upload.exists()) {
                upload.mkdirs();
            }
            //创建新的fileName文件，在static/upload目录下
            if (dir != null) {
                resultPath.append(dir).append("/").append(fileName);
            } else {
                resultPath.append("/").append(fileName);
            }

            upload = new File(path.getAbsolutePath(), "/" + resultPath.toString());

            //处理重名文件
            int index = 1;
            boolean first = true;
            while (true) {
                //如果fileName文件不存在重名，直接创建保存
                if (!upload.exists()) {
                    file.transferTo(upload);
                    break;
                }
                //否则用数字作为前缀，但不能一直加前缀，会造成名字太长，
                // 因此，从第二次进入该分支开始，都需要去除已经加上去的前缀，
                //然后重新加前缀，同时给数字下标前缀做自增
                else {
                    String indexStr = index + "-";
                    //如果第一次进入本分支，则不需要去除前缀,否则去除
                    if (!first) {
                        fileName.delete(0, indexStr.length());
                    } else {
                        //第一次进入后，把标识改成不是第一次进入
                        first = false;
                    }
                    //加入前缀
                    fileName.insert(0, indexStr);
                    //下标自增
                    ++index;
                    //创建文件
                    resultPath.delete(0, resultPath.toString().length());
                    resultPath.append("static/upload/").append(fileName);
                    upload = new File(path.getAbsolutePath(), resultPath.toString());
                }
            }
            return fileName.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(EmBusinessErr.FILE_SAVE_ERROR);
        }
    }

    /*
     * 保存文件
     * */
    public static String saveFile(MultipartFile file) throws BusinessException {
        return saveFile(file, null);
    }

    /*
     * 获取文件
     * */
    public static ResponseEntity<Resource> getFile(String fileName) throws BusinessException {
        try {
//            File path = new File(ResourceUtils.getURL("classpath:").getPath());
//            if (!path.exists()) {
//                path = new File("");
//            }
            File path = createAbsoluteJarFile();
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

    //创建jar包的相对路径文件
    private static File createAbsoluteJarFile() throws FileNotFoundException {
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if (!path.exists()) {
            path = new File("");
        }
        return path;
    }

    //创建多重目录
    public static File createDirs(String dir) throws BusinessException {
        try {
            File path = createAbsoluteJarFile();
            File file = new File(path.getAbsolutePath(), "static/upload/" + dir);
            if (!file.exists()) {
                file.mkdirs();
            }
            return file;
        } catch (Exception e) {
            throw new BusinessException(EmBusinessErr.CREATE_DIR_ERROR);
        }

    }

    //删除文件-带路径文件夹
    public static synchronized void deleteFile(String fileName, String dir) throws BusinessException {
        try {
            File path = createAbsoluteJarFile();
            File targetDir = null;
            if (dir != null) {
                targetDir = new File(path.getAbsolutePath(), STATIC_UPLOAD + "/" + dir);
            } else {
                targetDir = new File(path.getAbsolutePath(), STATIC_UPLOAD);
            }
            if (targetDir.exists()) {
                File targetFile = new File(targetDir.getAbsolutePath(), "/" + fileName);
                if (targetFile.exists()) {
                    targetFile.delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(EmBusinessErr.DELETE_FILE_ERROR);
        }
    }

    //删除文件
    public static void deleteFile(String fileName) throws BusinessException {
        deleteFile(fileName, null);
    }

}
