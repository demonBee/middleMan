package com.chinaunicom.sirbee.dao.model.result;

import java.io.Serializable;

/**
 *
 * @Author bee
 * @Create 2018-10-15 10:30
 **/
public class ResultDTO<T> implements Serializable {

    public static final String BUSINESS_SUCCESS = "0";   //业务成功
    public static final String BUSINESS_FAIL = "1";     //业务失败
    public static final String AUTHORITY_FAIL = "-1";  //没有权限
    public static final String LOGIN_FAIL="-2";   //未登录


    /**
     * 0成功 1失败  -1没有权限 -2没有登录
     */
    private String code;

    /**
     * 返回消息
     */
    private String desc;

    /**
     * 记录
     */
    private T data;



    public static<T> ResultDTO businessSuccess(String message,T record){
        return new ResultDTO(BUSINESS_SUCCESS,message,record);
    }

    public static<T> ResultDTO bussinessFail(String message,T record){
        return new ResultDTO(BUSINESS_FAIL,message,record);
    }

    public static <T> ResultDTO authorityFail(String message,T record){
        return new ResultDTO(AUTHORITY_FAIL,message,record);
    }

    public static <T> ResultDTO loginFail(String message,T record){
        return new ResultDTO(LOGIN_FAIL,message,record);
    }


    //---   历史需要
    public ResultDTO(String code, String msg) {
        this.code = code;
        this.desc = msg;
    }
    public ResultDTO(T data) {
        this.code =BUSINESS_SUCCESS;
        this.data = data;
        this.desc="成功！！";
    }

    //-----

    /**
     * new   业务成功 0
     * @param record
     */
    public ResultDTO(String resCode,String resMessage,T record){
        this.code=resCode;
        this.desc=resMessage;
        this.data=record;
    }



    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultDTO{" +
                "code='" + code + '\'' +
                ", desc='" + desc + '\'' +
                ", data=" + data +
                '}';
    }
}
