package com.project.demo.dao;

import com.project.demo.DO.FolderDO;
import com.project.demo.VO.FileVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FolderDOMapper {
    int deleteByPrimaryKey(Integer folderId);

    int insert(FolderDO record);

    int insertSelective(FolderDO record);

    FolderDO selectByPrimaryKey(Integer folderId);

    int updateByPrimaryKeySelective(FolderDO record);

    int updateByPrimaryKey(FolderDO record);

    void createFolder(@Param("path") String path, @Param("userId") Integer userId, @Param("parentFolderId") Integer parentFolderId);

    List<FileVO> getFilesUnderFolderId(@Param("userId") Integer userId, @Param("folderId") Integer folderId);

    void deleteDir(@Param("folderId") Integer folderId);
}