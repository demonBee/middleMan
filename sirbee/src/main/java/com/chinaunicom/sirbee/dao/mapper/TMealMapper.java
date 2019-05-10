package com.chinaunicom.sirbee.dao.mapper;


import com.chinaunicom.sirbee.dao.model.TMeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TMealMapper {
    int deleteByPrimaryKey(String mid);

    int insert(TMeal record);

    int insertSelective(TMeal record);

    TMeal selectByPrimaryKey(String mid);

    int updateByPrimaryKeySelective(TMeal record);

    int updateByPrimaryKey(TMeal record);

    List<TMeal> listMeal(@Param("mid") String mid, @Param("status")String status, @Param("sortName")String sortName, @Param("sortType")Integer sortType);
}