package com.chinaunicom.sirbee.service.impl;





import com.chinaunicom.sirbee.dao.mapper.TUserMapper;
import com.chinaunicom.sirbee.dao.model.TUser;
import com.chinaunicom.sirbee.dao.model.result.ResultDTO;
import com.chinaunicom.sirbee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private TUserMapper tUserMapper;


    @Override
    public ResultDTO loginGetToken(String userid, String password,String TheType) {

        try {
            TUser tUser = tUserMapper.selectByUserid(userid);
            if (tUser==null){
                return ResultDTO.bussinessFail("无此用户",null);
            }

            if (!tUser.getPassword().equals(password)){
                return ResultDTO.bussinessFail("密码错误",null);
            }

            //得到token
            //0客户  1商家   3管理员
            String type = tUser.getType();
            if (type==null||!TheType.equals(type)){
                return ResultDTO.bussinessFail("登录失败，账户类型不符",null);
            }


            String token = type+"-"+UUID.randomUUID().toString().replace("-", "");

            //修改时间
            TUser tUser1 = new TUser();
            tUser1.setUid(tUser.getUid());
            tUser1.setLastLoginTime(new Date());
            tUser1.setToken(token);
            int i = tUserMapper.updateByPrimaryKeySelective(tUser1);
            if (i<1){
                return ResultDTO.bussinessFail("修改最后登录时间失败",null);
            }

            HashMap<String, Object> resultMap = new HashMap<>();
            resultMap.put("token",token);
            resultMap.put("userid",tUser.getUserid());
            resultMap.put("username",tUser.getUsername());
            resultMap.put("type",tUser.getType());
            resultMap.put("lastLoginTime",tUser1.getLastLoginTime());
            return ResultDTO.businessSuccess("登录成功",resultMap);


        }catch (Exception e){
            return ResultDTO.bussinessFail("异常","异常"+e);
        }

    }


    @Override
    public ResultDTO modifyPassword(String token, String newPassword, String type) {
        if (!type.equals("0")&&!type.equals("1")&&!type.equals("3")){
            return ResultDTO.bussinessFail("type类型错误",null);

        }



        TUser tUser =null;
        if (type.equals("0")){
            ResultDTO verifyResult =verifyCustomer(token);
            if (!verifyResult.getCode().equals("0")){
                return verifyResult;
            }
            tUser = (TUser) verifyResult.getData();
        }else if (type.equals("1")){
            ResultDTO verifyResult =verifyDealManager(token);
            if (!verifyResult.getCode().equals("0")){
                return verifyResult;
            }
            tUser = (TUser) verifyResult.getData();
        }else if (type.equals("3")){
            ResultDTO verifyResult =verifyBackground(token);
            if (!verifyResult.getCode().equals("0")){
                return verifyResult;
            }
            tUser = (TUser) verifyResult.getData();
        }



        if (tUser==null){
            return ResultDTO.bussinessFail("失败",null);
        }


        TUser tUser1 = new TUser();
        tUser1.setUid(tUser.getUid());
        tUser1.setModifyTime(new Date());
        tUser1.setPassword(newPassword);
        int i = tUserMapper.updateByPrimaryKeySelective(tUser1);
        if (i<1){
            return ResultDTO.bussinessFail("修改密码失败",null);
        }

        return ResultDTO.businessSuccess("修改密码成功",null);




    }

    @Override
    public ResultDTO verifyBackground(String token) {
        String type="3";
        ResultDTO resultDTO = judgeUserLogined(token, type);
        return resultDTO;
    }

    @Override
    public ResultDTO verifyCustomer(String token) {
        String type="0";
        ResultDTO resultDTO = judgeUserLogined(token, type);
        return resultDTO;
    }

    @Override
    public ResultDTO verifyDealManager(String token) {
        String type="1";
        ResultDTO resultDTO = judgeUserLogined(token, type);
        return resultDTO;
    }


    /**
     * ------------------------------------------------------------------------------------------------
     * -------------------------------------------------------------------------------------------------
     * -------------------------------------------------------------------------------------------------
     * @param token
     * @param type
     * @return
     */

    public ResultDTO judgeUserLogined(String token,String type){
        String[] split = token.split("-");
        String sType = split[0];

        if (sType==null||!sType.equals(type)){
            return ResultDTO.bussinessFail("验证token失败,账户类型不匹配",null);
        }


        try {
            TUser tUser =tUserMapper.selectUserByToken(token);
            if (tUser==null){
                return ResultDTO.bussinessFail("验证token失败",null);
            }

            Date lastLoginTime = tUser.getLastLoginTime();
            long longTime = System.currentTimeMillis() - 12 * 60 * 60 * 1000;
            Date date = new Date(longTime);
            if (lastLoginTime.getTime()<date.getTime()){
                return ResultDTO.bussinessFail("登录超时，请重新登录",null);
            }

            return ResultDTO.businessSuccess("token验证成功",tUser);


        }catch (Exception e){
            return ResultDTO.bussinessFail("异常","异常"+e);

        }
    }

}
