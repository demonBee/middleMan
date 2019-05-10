package com.chinaunicom.sirbee.dao.mapper;


import com.chinaunicom.sirbee.dao.model.TApply;
import com.chinaunicom.sirbee.dao.model.TPayInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TApplyMapper {
    int deleteByPrimaryKey(Integer applyId);

    int insert(TApply record);

    int insertSelective(TApply record);

    TApply selectByPrimaryKey(Integer applyId);

    int updateByPrimaryKeySelective(TApply record);

    int updateByPrimaryKey(TApply record);



    //查询申请list
    List<TApply> listApply(@Param("applyType")String applyType, @Param("searchName")String searchName, @Param("sortName")String sortName, @Param("sortType")Integer sortType);

}