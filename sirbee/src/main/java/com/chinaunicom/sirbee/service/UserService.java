package com.chinaunicom.sirbee.service;


import com.chinaunicom.sirbee.dao.model.result.ResultDTO;


public interface UserService {

    /**
     * 登录来得到 token
     * @param userId
     * @param password
     * @return
     */
    ResultDTO loginGetToken(String userid, String password,String type);


    /**
     * 修改
     * @param newPassword
     * @param uid
     * @return
     */
    ResultDTO modifyPassword(String token, String newPassword, String type);


    /**
     * 验证是否是后台
     * @param token
     * @return
     */
    ResultDTO verifyBackground(String token);

    /**
     * 验证是否是客户
     * @param token
     * @return
     */
    ResultDTO verifyCustomer(String token);

    /**
     * 验证是否是施工方
     * @param token
     * @return
     */
    ResultDTO verifyDealManager(String token);



}
