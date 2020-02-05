package com.project.demo.service.Impl;

import com.project.demo.DO.FileDO;
import com.project.demo.DO.FolderDO;
import com.project.demo.VO.FileVO;
import com.project.demo.dao.FileDOMapper;
import com.project.demo.dao.FolderDOMapper;
import com.project.demo.error.BusinessException;
import com.project.demo.error.EmBusinessErr;
import com.project.demo.service.FileService;
import com.project.demo.utils.MySessionUtil;
import javafx.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: YZL
 * @time: 2020/2/4 16:04
 */
@Service
public class FileServiceImpl implements FileService {

    private final FolderDOMapper folderDOMapper;
    private final FileDOMapper fileDOMapper;

    public FileServiceImpl(FolderDOMapper folderDOMapper, FileDOMapper fileDOMapper) {
        this.folderDOMapper = folderDOMapper;
        this.fileDOMapper = fileDOMapper;
    }

    @Override
    public void createFolder(String path, Integer parentFolderId) throws BusinessException {
        try {
            Integer userId = (Integer) MySessionUtil.getSession().getAttribute(MySessionUtil.USER_ID);
            folderDOMapper.createFolder(path, userId, parentFolderId);
        } catch (Exception e) {
            throw new BusinessException(EmBusinessErr.CREATE_DIR_ERROR);
        }
    }

    @Override
    public void uploadFile(String fileName, Integer folderId, Integer userId) throws BusinessException {
        try {
            fileDOMapper.uploadFile(userId, fileName, folderId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(EmBusinessErr.FILE_UPLOAD_ERROR);
        }
    }

    @Override
    public List<FileVO> getFilesUnderFolderId(Integer folderId, Integer userId) throws BusinessException {
        try {
            List<FileVO> files = fileDOMapper.getFilesUnderFolderId(userId, folderId);
            List<FileVO> folders = folderDOMapper.getFilesUnderFolderId(userId, folderId);
            files.addAll(folders);
            return files;
        } catch (Exception e) {
            throw new BusinessException(EmBusinessErr.GET_FILES_UNDER_FOLDER_ID_ERROR);
        }
    }
}
