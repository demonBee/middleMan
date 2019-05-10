package com.chinaunicom.sirbee.dao.mapper;


import com.chinaunicom.sirbee.dao.model.TUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TUserMapper {
    int deleteByPrimaryKey(Integer uid);

    int insert(TUser record);

    int insertSelective(TUser record);

    TUser selectByPrimaryKey(Integer uid);

    int updateByPrimaryKeySelective(TUser record);

    int updateByPrimaryKey(TUser record);




    TUser selectByUserid(String userid);

    TUser selectUserByToken(String token);

    List<TUser> listUser(@Param("uid") Integer uid, @Param("userid")String userid, @Param("type")String type, @Param("status")String status, @Param("sortName")String sortName, @Param("sortType")Integer sortType);

}