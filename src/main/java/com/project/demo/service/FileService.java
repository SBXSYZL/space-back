package com.project.demo.service;

import com.project.demo.error.BusinessException;
import javafx.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: YZL
 * @time: 2020/2/4 16:03
 */
@Service
public interface FileService {

    void createFolder(String path, Integer parentFolderId) throws BusinessException;

    void uploadFile(String fileName, Integer folderId, Integer userId) throws BusinessException;

    List getFilesUnderFolderId(Integer folderId, Integer userId) throws BusinessException;

    void deleteDir(Integer folderId) throws BusinessException;

    void deleteFile(Integer fileId) throws BusinessException;
}
