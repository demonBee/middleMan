package com.chinaunicom.sirbee.service;

import com.chinaunicom.sirbee.dao.model.*;
import com.chinaunicom.sirbee.dao.model.result.ResultDTO;

/**
 * @author hjf
 * @date 2019/5/8 15:54
 * @Description
 */
public interface BackgroundService {

    ResultDTO applyUpdate(TApply tApply);

    ResultDTO listApply(TApply tApply, String applyType, String searchName, String sortName, Integer sortType);

    ResultDTO applyToOrderAndUser(Integer applyId, Integer totailPrice, String priceDetail, String orderDescription, String userRemark);

    ResultDTO listOrder(TOrder tOrder,String type,String orderId,String sortName,Integer sortType);

    ResultDTO modifiyOrder(TOrder tOrder);





    ResultDTO payAdd(TPayInfo tPayInfo);

    ResultDTO payDel(TPayInfo tPayInfo);

    ResultDTO payModify(TPayInfo tPayInfo);

    ResultDTO listPay(String payInfoId, String orderId, String sortName, Integer sortType, TPayInfo tPayInfo);

    ResultDTO mealAdd(TMeal tMeal);

    ResultDTO mealDel(Integer mid);

    ResultDTO mealModify(TMeal tMeal);

    ResultDTO listMeal(String mid, String status, String sortName, Integer sortType, TPayInfo tPayInfo);


    ResultDTO menuAdd(TMenu tMenu);

    ResultDTO menuDel(Integer menuId);

    ResultDTO menuModify(TMenu tMenu);

    ResultDTO listMenu(Integer menuId, String status, String sortName, Integer sortType, TPayInfo tPayInfo);

    ResultDTO contentAdd(TContent tContent);


    ResultDTO contentDel(Integer contentId);

    ResultDTO contentModify(TContent tContent);

    ResultDTO listContent(Integer contentId, String type, String status, String sortName, Integer sortType, TPayInfo tPayInfo);


    ResultDTO userAdd(TUser tUser);

    ResultDTO userDel(Integer uid);

    ResultDTO userModify(TUser tUser);

    ResultDTO listUser(Integer uid, String userid, String type, String status, String sortName, Integer sortType, TPayInfo tPayInfo);


    ResultDTO diaryAdd(TProgress tProgress);

    ResultDTO diaryDel(Integer progressId);

    ResultDTO diaryModify(TProgress tProgress);

    ResultDTO listDiary(Integer progressId, Integer orderId, String sortName, Integer sortType, TPayInfo tPayInfo);

    ResultDTO dictAdd(TDict tDict);

    ResultDTO dictDel(Integer dictId);

    ResultDTO dictModify(TDict tDict);

    ResultDTO listDict(String fieldName, String tableName, String sortName, Integer sortType, Integer pagen, Integer pages);



}
