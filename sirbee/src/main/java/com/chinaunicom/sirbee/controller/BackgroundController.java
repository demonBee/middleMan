package com.chinaunicom.sirbee.controller;

import com.chinaunicom.sirbee.dao.model.*;
import com.chinaunicom.sirbee.dao.model.result.ResultDTO;
import com.chinaunicom.sirbee.service.BackgroundService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hjf
 * @date 2019/5/8 15:53
 * @Description
 */
@RestController
@RequestMapping(value = "/background")
@CrossOrigin
public class BackgroundController {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private BackgroundService backgroundService;


    /**
     * 申请的内容修改
     * @param tApply
     * @return
     */
    @RequestMapping("/applyUpdate")
    @ResponseBody
    public ResultDTO applyUpdate(TApply tApply){
        ResultDTO dto=backgroundService.applyUpdate(tApply);
        return dto;
    }


    /**
     * pagen pages   applyType 0申请中  1跟进   2拒绝   3已转成订单   searchName模糊查询手机号 sortName是数据库字段名 非驼峰
     * sortType 0 desc 1asc
     * @param tApply
     * @param applyType
     * @param searchName
     * @param sortName
     * @param sortType
     * @return
     */
    @RequestMapping("/listApply")
    @ResponseBody
    public ResultDTO listApply(TApply tApply,String applyType,String searchName,String sortName,Integer sortType){
        ResultDTO dto=backgroundService.listApply(tApply,applyType,searchName,sortName,sortType);
        return dto;
    }


    /**
     * 申请转成订单
     * applyId
     *  还有订单需要的   totailPrice   price_detail   orderDescription
     * @param
     * @return
     */
    @RequestMapping("/applyToOrderAndUser")
    @ResponseBody
    public ResultDTO applyToOrderAndUser(Integer applyId,Integer totailPrice,String priceDetail,String orderDescription,String userRemark){
        ResultDTO dto=backgroundService.applyToOrderAndUser(applyId,totailPrice,priceDetail,orderDescription,userRemark);
        return dto;
    }


    /**
     * 订单list
     * pagen  pages   type 0为待施工  1为施工中 2为已完成未付款 3为已付款完工   9为取消订单   搜索orderId模糊查询   筛选排序
     *
     */
    @RequestMapping("/listOrder")
    @ResponseBody
    public ResultDTO listOrder(TOrder tOrder,String type,String orderId,String sortName,Integer sortType){
        ResultDTO dto=backgroundService.listOrder(tOrder,type,orderId,sortName,sortType);
        return dto;
    }


    /**
     * 修改订单
     * @param tOrder
     * @return
     */
    @RequestMapping("/modifiyOrder")
    @ResponseBody
    public ResultDTO modifiyOrder(TOrder tOrder){
        ResultDTO dto=backgroundService.modifiyOrder(tOrder);
        return dto;
    }


    /**
     * 简单的增删改查
     * ------------------------------------------------------------------------------------------------------
     * ------------------------------------------------------------------------------------------------------
     * ------------------------------------------------------------------------------------------------------
     */


    /**
     * 付款记录增
     * @param
     * @return
     */
    @RequestMapping("/payAdd")
    @ResponseBody
    public ResultDTO payAdd(TPayInfo tPayInfo){
        ResultDTO dto=backgroundService.payAdd(tPayInfo);
        return dto;
    }

    /**
     * 付款记录删
     * payInfoId  就这一个参数
     * @param tPayInfo
     * @return
     */
    @RequestMapping("/payDel")
    @ResponseBody
    public ResultDTO payDel(TPayInfo tPayInfo){
        ResultDTO dto=backgroundService.payDel(tPayInfo);
        return dto;
    }


    /**
     * 修改付款记录信息
     * @param tPayInfo
     * @return
     */
    @RequestMapping("/payModify")
    @ResponseBody
    public ResultDTO payModify(TPayInfo tPayInfo){
        ResultDTO dto=backgroundService.payModify(tPayInfo);
        return dto;
    }


    /**
     * 模糊查询 分页  排序  payInfoId  orderId   pagen pages  sortName sortType
     * @param tPayInfo
     * @return
     */
    @RequestMapping("/listPay")
    @ResponseBody
    public ResultDTO listPay(String payInfoId,String orderId,String sortName,Integer sortType,TPayInfo tPayInfo){
        ResultDTO dto=backgroundService.listPay(payInfoId,orderId,sortName,sortType,tPayInfo);
        return dto;
    }


    /**
     * 套餐增
     * @param
     * @return
     */
    @RequestMapping("/mealAdd")
    @ResponseBody
    public ResultDTO mealAdd(TMeal tMeal){
        ResultDTO dto=backgroundService.mealAdd(tMeal);
        return dto;
    }


    /**
     * 套餐删
     * mid  就这一个参数
     * @param
     * @return
     */
    @RequestMapping("/mealDel")
    @ResponseBody
    public ResultDTO mealDel(Integer mid){
        ResultDTO dto=backgroundService.mealDel(mid);
        return dto;
    }


    /**
     * 套餐修改
     * mid   套餐id
     * @param
     * @return
     */
    @RequestMapping("/mealModify")
    @ResponseBody
    public ResultDTO mealModify(TMeal tMeal){
        ResultDTO dto=backgroundService.mealModify(tMeal);
        return dto;
    }


    /**
     * 模糊查询 分页  排序  mid  status   pagen pages  sortName sortType
     * @param tPayInfo
     * @return
     */
    @RequestMapping("/listMeal")
    @ResponseBody
    public ResultDTO listMeal(String mid,String status,String sortName,Integer sortType,TPayInfo tPayInfo){
        ResultDTO dto=backgroundService.listMeal(mid,status,sortName,sortType,tPayInfo);
        return dto;
    }




    /**
     * 菜单增
     * @param
     * @return
     */
    @RequestMapping("/menuAdd")
    @ResponseBody
    public ResultDTO menuAdd(TMenu tMenu){
        ResultDTO dto=backgroundService.menuAdd(tMenu);
        return dto;
    }


    /**
     * 菜单删
     * menuId  就这一个参数
     * @param
     * @return
     */
    @RequestMapping("/menuDel")
    @ResponseBody
    public ResultDTO menuDel(Integer menuId){
        ResultDTO dto=backgroundService.menuDel(menuId);
        return dto;
    }



    /**
     * 套餐修改
     * menuId  必穿
     * @param
     * @return
     */
    @RequestMapping("/menuModify")
    @ResponseBody
    public ResultDTO menuModify(TMenu tMenu){
        ResultDTO dto=backgroundService.menuModify(tMenu);
        return dto;
    }


    /**
     * 模糊查询 分页  排序  menuId  status   pagen pages  sortName sortType
     * @param tPayInfo
     * @return
     */
    @RequestMapping("/listMenu")
    @ResponseBody
    public ResultDTO listMenu(Integer menuId,String status,String sortName,Integer sortType,TPayInfo tPayInfo){
        ResultDTO dto=backgroundService.listMenu(menuId,status,sortName,sortType,tPayInfo);
        return dto;
    }



    /**
     * 内容增
     * @param
     * @return
     */
    @RequestMapping("/contentAdd")
    @ResponseBody
    public ResultDTO contentAdd(TContent tContent){
        ResultDTO dto=backgroundService.contentAdd(tContent);
        return dto;
    }



    /**
     * 内容删
     * @param
     * @return
     */
    @RequestMapping("/contentDel")
    @ResponseBody
    public ResultDTO contentDel(Integer contentId){
        ResultDTO dto=backgroundService.contentDel(contentId);
        return dto;
    }


    /**
     * 内容修改
     * contentId  必穿
     * @param
     * @return
     */
    @RequestMapping("/contentModify")
    @ResponseBody
    public ResultDTO contentModify(TContent tContent){
        ResultDTO dto=backgroundService.contentModify(tContent);
        return dto;
    }



    /**
     * 内容list 查询
     * 模糊查询 分页  排序  contentId  type  status   pagen pages  sortName sortType
     * @param tPayInfo
     * @return
     */
    @RequestMapping("/listContent")
    @ResponseBody
    public ResultDTO listContent(Integer contentId,String type,String status,String sortName,Integer sortType,TPayInfo tPayInfo){
        ResultDTO dto=backgroundService.listContent(contentId,type,status,sortName,sortType,tPayInfo);
        return dto;
    }



    /**
     * 用户增
     * @param
     * @return
     */
    @RequestMapping("/userAdd")
    @ResponseBody
    public ResultDTO userAdd(TUser tUser){
        ResultDTO dto=backgroundService.userAdd(tUser);
        return dto;
    }



    /**
     * 用户删
     * @param
     * @return
     */
    @RequestMapping("/userDel")
    @ResponseBody
    public ResultDTO userDel(Integer uid){
        ResultDTO dto=backgroundService.userDel(uid);
        return dto;
    }


    /**
     * 内容修改
     * uid  必穿
     * @param
     * @return
     */
    @RequestMapping("/userModify")
    @ResponseBody
    public ResultDTO userModify(TUser tUser){
        ResultDTO dto=backgroundService.userModify(tUser);
        return dto;
    }


    /**
     * 内容list 查询
     * 模糊查询 分页  排序  uid  userid  type  status  pagen pages  sortName sortType
     * @param tPayInfo
     * @return
     */
    @RequestMapping("/listUser")
    @ResponseBody
    public ResultDTO listUser(Integer uid,String userid,String type,String status,String sortName,Integer sortType,TPayInfo tPayInfo){
        ResultDTO dto=backgroundService.listUser(uid,userid,type,status,sortName,sortType,tPayInfo);
        return dto;
    }


    /**
     * 日志插入新增
     * @param
     * @return
     */
    @RequestMapping("/diaryAdd")
    @ResponseBody
    public ResultDTO diaryAdd(TProgress tProgress){
        ResultDTO dto=backgroundService.diaryAdd(tProgress);
        return dto;
    }


    /**
     * 日记删
     * @param
     * @return
     */
    @RequestMapping("/diaryDel")
    @ResponseBody
    public ResultDTO diaryDel(Integer progressId){
        ResultDTO dto=backgroundService.diaryDel(progressId);
        return dto;
    }


    /**
     * 日记修改
     * progressId  必穿
     * @param
     * @return
     */
    @RequestMapping("/diaryModify")
    @ResponseBody
    public ResultDTO diaryModify(TProgress tProgress){
        ResultDTO dto=backgroundService.diaryModify(tProgress);
        return dto;
    }


    /**
     * 内容list 查询
     *  分页  排序  progressId  order_id   pagen pages  sortName sortType
     * @param tPayInfo
     * @return
     */
    @RequestMapping("/listDiary")
    @ResponseBody
    public ResultDTO listDiary(Integer progressId,Integer orderId,String sortName,Integer sortType,TPayInfo tPayInfo){
        ResultDTO dto=backgroundService.listDiary(progressId,orderId,sortName,sortType,tPayInfo);
        return dto;
    }



    /**
     * 数据字典新增
     * @param
     * @return
     */
    @RequestMapping("/dictAdd")
    @ResponseBody
    public ResultDTO dictAdd(TDict tDict){
        ResultDTO dto=backgroundService.dictAdd(tDict);
        return dto;
    }


    /**
     * 数据字典删
     * @param
     * @return
     */
    @RequestMapping("/dictDel")
    @ResponseBody
    public ResultDTO dictDel(Integer dictId){
        ResultDTO dto=backgroundService.dictDel(dictId);
        return dto;
    }


    /**
     * 数据字典修改
     * dictId  必穿
     * @param
     * @return
     */
    @RequestMapping("/dictModify")
    @ResponseBody
    public ResultDTO dictModify(TDict tDict){
        ResultDTO dto=backgroundService.dictModify(tDict);
        return dto;
    }


    /**
     * 内容list 查询
     *  分页  排序  fieldName  tableName   pagen pages  sortName sortType
     * @param
     * @return
     */
    @RequestMapping("/listDict")
    @ResponseBody
    public ResultDTO listDict(String fieldName,String tableName,String sortName,Integer sortType,Integer pagen,Integer pages){
        ResultDTO dto=backgroundService.listDict(fieldName,tableName,sortName,sortType,pagen,pages);
        return dto;
    }



}
