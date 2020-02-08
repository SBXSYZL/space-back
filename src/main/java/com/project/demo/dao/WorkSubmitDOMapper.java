package com.project.demo.dao;

import com.project.demo.DO.WorkSubmitDO;
import com.project.demo.VO.WorkSubmitVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WorkSubmitDOMapper {
    int deleteByPrimaryKey(Integer submitId);

    int insert(WorkSubmitDO record);

    int insertSelective(WorkSubmitDO record);

    WorkSubmitDO selectByPrimaryKey(Integer submitId);

    int updateByPrimaryKeySelective(WorkSubmitDO record);

    int updateByPrimaryKeyWithBLOBs(WorkSubmitDO record);

    int updateByPrimaryKey(WorkSubmitDO record);

    List<WorkSubmitVO> getWorkSubmitList(@Param("workId") Integer workId);

    void gradeAssignment(@Param("submitId") Integer submitId, @Param("status") Byte status, @Param("score") Integer score);
}