package com.chinaunicom.sirbee.dao.mapper;


import com.chinaunicom.sirbee.dao.model.TOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TOrderMapper {
    int deleteByPrimaryKey(Integer orderId);

    int insert(TOrder record);

    int insertSelective(TOrder record);

    TOrder selectByPrimaryKey(Integer orderId);

    int updateByPrimaryKeySelective(TOrder record);

    int updateByPrimaryKey(TOrder record);

    List<TOrder> listOrder(@Param("type")String type, @Param("orderId")String orderId, @Param("sortName")String sortName, @Param("sortType")Integer sortType);

}