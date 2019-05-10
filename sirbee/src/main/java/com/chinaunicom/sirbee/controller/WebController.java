package com.chinaunicom.sirbee.controller;

import com.chinaunicom.sirbee.dao.model.TApply;
import com.chinaunicom.sirbee.dao.model.TUser;
import com.chinaunicom.sirbee.dao.model.result.ResultDTO;
import com.chinaunicom.sirbee.service.UserService;
import com.chinaunicom.sirbee.service.WebService;
import com.chinaunicom.sirbee.utils.SFTPTools;
import com.jcraft.jsch.ChannelSftp;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;





/**
 * @author hjf
 * @date 2019/5/5 12:19
 * @Description
 */
@RestController
@RequestMapping(value = "/web")
@CrossOrigin
public class WebController {
private final Logger logger = LogManager.getLogger(this.getClass());


    private static ChannelSftp sftp;
    private static SFTPTools sf;
    private static  String path;
    private static  String addr;
    private static  String  port;
    private static  String username;
    private static  String password;

    static {
        sf = new SFTPTools();
        path=sf.ymlPropertiesValue("img.path");
        addr=sf.ymlPropertiesValue("img.server.address");
        port=sf.ymlPropertiesValue("img.server.port");
        username=sf.ymlPropertiesValue("img.server.username");
        password=sf.ymlPropertiesValue("img.server.password");
//        #warning 先注释掉
//        sftp=sf.connect(addr,Integer.valueOf(port), username, password);
        System.out.print("连接"+sftp);
    }



    @Autowired
    private WebService webService;
    @Autowired
    private UserService userService;



    @InitBinder
    protected void init(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    /**
     * 测试
     */
    @RequestMapping("/ceshi")
    @ResponseBody
    public ResultDTO ceshi(){

        return ResultDTO.businessSuccess("测试成功","这只是一段文字描述这只是一段文字描述这只是一段文字描述");
    }


    /**
     * userid  password  type 0客户 1商家 3后台管理系统
     * 通用登录接口
     */
    @RequestMapping(value ="/modifyPassword",method = RequestMethod.POST)
    @ResponseBody
    public ResultDTO modifyPassword(@RequestParam(value = "token",required = true) String token,@RequestParam(value = "newPassword",required = true) String newPassword,@RequestParam(value = "type",required = true) String type){

        //修改密码
        ResultDTO dto=userService.modifyPassword(token,newPassword,type);
        return dto;
    }



    @RequestMapping(value ="/login",method = RequestMethod.POST)
    @ResponseBody
    public ResultDTO login(@RequestParam(value = "userid",required = true) String userid,@RequestParam(value = "password",required = true) String password,@RequestParam(value = "type",required = true) String type){
        return userService.loginGetToken(userid,password,type);
    }


    /**
     * type 0 客户  1商家管理员 3后台管理员
     * token
     *
     * 该接口 不使用  只做测试用  验证是否登录
     *
     * @param token
     * @param type
     * @return
     */
    @RequestMapping(value ="/verifyLogin",method = RequestMethod.POST)
    @ResponseBody
    public ResultDTO verifyLogin(@RequestParam(value = "token",required = true) String token,@RequestParam(value = "type",required = true) String type){
        if (type.equals("0")){
            return userService.verifyCustomer(token);
        }

        if (type.equals("1")){
            return userService.verifyDealManager(token);
        }

        if (type.equals("3")){
            return userService.verifyBackground(token);
        }

        return ResultDTO.bussinessFail("type类型错误",null);
    }




//--------------------------------------------------------------------------------
//--------------------------------------------------------------------------------
//--------------------------------------------------------------------------------
    /**
     * 上传图片
     */
    @RequestMapping(method = RequestMethod.POST, path = "/upload")
    @ResponseBody
    public ResultDTO upload(@RequestPart("files") MultipartFile[] files) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String timeStr=sdf.format(date);
//        logger.info("----------图片上传-------->"+files);
        List<String> list = new ArrayList<String>();

        if (files!=null&&files.length>0){
            for (int i = 0; i < files.length; i++) {
                MultipartFile file=files[i];
                if (!file.isEmpty()){
                    String pictureName = UUID.randomUUID().toString().replace("-", "");
                    try {
                        String originalFileName=file.getOriginalFilename();
                        pictureName=pictureName+"-"+originalFileName;

                        String pathStr=path+timeStr;
//                        String fileSavePath = System.getProperty("java.io.tmpdir");
//                        String filePath =fileSavePath + pictureName;
//                        file.transferTo(new File(filePath));
                        //不要保存到本地了  直接转成input上传
                        InputStream stream =file.getInputStream();

                        if (sftp==null||!sftp.isConnected()){
                            sftp=sf.connect(addr, Integer.valueOf(port), username, password);
                        }


                        sf.upload(pathStr, pictureName,stream, sftp);
                        list.add(pictureName);
                    } catch (Exception e) {
                        return ResultDTO.bussinessFail("异常","异常+e");
                    }
                }
            }
        }else {
            return ResultDTO.bussinessFail("没有正确上传图片",null);
        }

        List resultList=new ArrayList<String>();
        for (String a:
        list) {
            a=timeStr+"/"+a;
            resultList.add(a);

        }

        return ResultDTO.businessSuccess("成功",resultList);
    }




    /**
     * 菜单
     *
     */
    @RequestMapping("/menuList")
    @ResponseBody
    public ResultDTO menuList(){
        ResultDTO dto=webService.menuList();
        return dto;
    }

    /**
     * 通过菜单的内容类型  来获取内容
     * 返回list  可能一条 可能多条
     * 底部的style 传  button_list_designer
     */
    @RequestMapping("/menuContentList")
    @ResponseBody
    public ResultDTO menuContentList(@RequestParam(value ="type",required = true) String type,Integer pagen,Integer pages){
        ResultDTO dto=webService.menuContentList(type,pagen,pages);
        return dto;
    }


    /**
     * 立即申请
     */
    @RequestMapping("/applyInstall")
    @ResponseBody
    public ResultDTO insertToApply(TApply tApply){
        ResultDTO dto=webService.insertToApply(tApply);
        return dto;

    }







}
