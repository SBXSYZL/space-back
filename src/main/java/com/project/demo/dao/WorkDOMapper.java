package com.project.demo.dao;

import com.project.demo.DO.WorkDO;
import com.project.demo.DO.WorkSubmitDO;
import com.project.demo.VO.WorkSubmitVO;
import com.project.demo.VO.WorkVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface WorkDOMapper {
    int deleteByPrimaryKey(Integer workId);

    int insert(WorkDO record);

    int insertSelective(WorkDO record);

    WorkDO selectByPrimaryKey(Integer workId);

    int updateByPrimaryKeySelective(WorkDO record);

    int updateByPrimaryKeyWithBLOBs(WorkDO record);

    int updateByPrimaryKey(WorkDO record);

    void createWork(@Param("userId") Integer userId, @Param("courseId") Integer courseId, @Param("workName") String workName, @Param("workDesc") String workDesc, @Param("deadline") Date deadline);

    List<WorkVO> getWorkList(@Param("userId") Integer userId);

    List<WorkVO> searchWork(@Param("userId") Integer userId, @Param("searchKey") String searchKey);
}