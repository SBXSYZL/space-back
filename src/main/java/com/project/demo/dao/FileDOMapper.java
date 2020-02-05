package com.project.demo.dao;

import com.project.demo.DO.FileDO;
import com.project.demo.DO.FolderDO;
import com.project.demo.VO.FileVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FileDOMapper {
    int deleteByPrimaryKey(Integer fileId);

    int insert(FileDO record);

    int insertSelective(FileDO record);

    FileDO selectByPrimaryKey(Integer fileId);

    int updateByPrimaryKeySelective(FileDO record);

    int updateByPrimaryKey(FileDO record);

    List<FileVO> getFilesUnderFolderId(@Param("userId") Integer userId, @Param("folderId") Integer folderId);

    void uploadFile(@Param("userId") Integer userId, @Param("fileName") String fileName, @Param("folderId") Integer folderId);
}