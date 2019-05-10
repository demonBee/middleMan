package com.chinaunicom.sirbee.dao.mapper;


import com.chinaunicom.sirbee.dao.model.TMeal;
import com.chinaunicom.sirbee.dao.model.TMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface TMenuMapper {
    int deleteByPrimaryKey(Integer menuId);

    int insert(TMenu record);

    int insertSelective(TMenu record);

    TMenu selectByPrimaryKey(Integer menuId);

    int updateByPrimaryKeySelective(TMenu record);

    int updateByPrimaryKey(TMenu record);

    List<TMenu>menuList();

    List<TMenu> listMenuSort(@Param("menuId") Integer menuId, @Param("status")String status, @Param("sortName")String sortName, @Param("sortType")Integer sortType);

}