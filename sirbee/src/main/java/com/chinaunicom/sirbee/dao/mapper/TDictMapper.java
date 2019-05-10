package com.chinaunicom.sirbee.dao.mapper;


import com.chinaunicom.sirbee.dao.model.TDict;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TDictMapper {
    int deleteByPrimaryKey(Long dictId);

    int insert(TDict record);

    int insertSelective(TDict record);

    TDict selectByPrimaryKey(Long dictId);

    int updateByPrimaryKeySelective(TDict record);

    int updateByPrimaryKey(TDict record);



    List<TDict> listDict(@Param("fieldName") String fieldName, @Param("tableName")String tableName, @Param("sortName")String sortName, @Param("sortType")Integer sortType);
}