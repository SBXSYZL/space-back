package com.project.demo.dao;

import com.project.demo.DO.MsgDO;
import com.project.demo.VO.MsgVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MsgDOMapper {
    int deleteByPrimaryKey(Integer msgId);

    int insert(MsgDO record);

    int insertSelective(MsgDO record);

    MsgDO selectByPrimaryKey(Integer msgId);

    int updateByPrimaryKeySelective(MsgDO record);

    int updateByPrimaryKeyWithBLOBs(MsgDO record);

    int updateByPrimaryKey(MsgDO record);

    void writeMsg(MsgDO msgDO);

    List<MsgVO> getMassageListForSelf(@Param("userId") Integer userId, @Param("status") Byte status);

    List<MsgVO> getMyWriteToList(@Param("myId") Integer myId);

    void readMsg(@Param("parentId") Integer parentId);

}