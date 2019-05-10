package com.chinaunicom.sirbee.dao.mapper;


import com.chinaunicom.sirbee.dao.model.TContent;
import com.chinaunicom.sirbee.dao.model.TMeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TContentMapper {
    int deleteByPrimaryKey(Integer menuId);

    int insert(TContent record);

    int insertSelective(TContent record);

    TContent selectByPrimaryKey(Integer menuId);

    int updateByPrimaryKeySelective(TContent record);

    int updateByPrimaryKey(TContent record);

    List<TContent> listForInfo(String type);

    List<TContent> listContent(@Param("contentId") Integer contentId, @Param("type")String type, @Param("status")String status, @Param("sortName")String sortName, @Param("sortType")Integer sortType);

}