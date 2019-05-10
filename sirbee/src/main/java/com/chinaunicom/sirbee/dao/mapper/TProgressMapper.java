package com.chinaunicom.sirbee.dao.mapper;


import com.chinaunicom.sirbee.dao.model.TProgress;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TProgressMapper {
    int deleteByPrimaryKey(Integer progressId);

    int insert(TProgress record);

    int insertSelective(TProgress record);

    TProgress selectByPrimaryKey(Integer progressId);

    int updateByPrimaryKeySelective(TProgress record);

    int updateByPrimaryKey(TProgress record);

    List<TProgress> listDiary(@Param("progressId") Integer progressId, @Param("orderId")Integer orderId, @Param("sortName")String sortName, @Param("sortType")Integer sortType,@Param("integers")List<Integer> integers);

}