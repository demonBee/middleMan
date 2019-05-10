package com.chinaunicom.sirbee.dao.mapper;


import com.chinaunicom.sirbee.dao.model.TPayInfo;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

public interface TPayInfoMapper {
    int deleteByPrimaryKey(Integer payInfoId);

    int insert(TPayInfo record);

    int insertSelective(TPayInfo record);

    TPayInfo selectByPrimaryKey(Integer payInfoId);

    int updateByPrimaryKeySelective(TPayInfo record);

    int updateByPrimaryKey(TPayInfo record);




    List<TPayInfo> listPay(@Param("payInfoId")String payInfoId, @Param("orderId")String orderId, @Param("sortName")String sortName, @Param("sortType")Integer sortType, @Param("orderIds") List<Integer> orderIds);

}