package com.chinaunicom.sirbee.service.impl;

import com.chinaunicom.sirbee.dao.mapper.*;
import com.chinaunicom.sirbee.dao.model.*;
import com.chinaunicom.sirbee.dao.model.result.ResultDTO;
import com.chinaunicom.sirbee.service.BackgroundService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.crypto.hash.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hjf
 * @date 2019/5/8 15:54
 * @Description
 */
@Service
public class BackgroundServiceImpl implements BackgroundService {
    @Autowired
    private TApplyMapper tApplyMapper;
    @Autowired
    private TOrderMapper tOrderMapper;
    @Autowired
    private TUserMapper tUserMapper;
    @Autowired
    private TPayInfoMapper tPayInfoMapper;
    @Autowired
    private TMealMapper tMealMapper;
    @Autowired
    private TMenuMapper tMenuMapper;
    @Autowired
    private TContentMapper tContentMapper;
    @Autowired
    private TProgressMapper tProgressMapper;
    @Autowired
    private TDictMapper tDictMapper;


    @Override
    public ResultDTO applyUpdate(TApply tApply) {
        try {
            int i = tApplyMapper.updateByPrimaryKeySelective(tApply);
            if (i<1){
                return ResultDTO.bussinessFail("修改申请失败",null);
            }
            return ResultDTO.businessSuccess("修改申请成功",null);

        }catch (Exception e){
            return ResultDTO.bussinessFail("异常","异常"+e);
        }
    }


    @Override
    public ResultDTO listApply(TApply tApply, String applyType, String searchName, String sortName, Integer sortType) {
        try {
            PageHelper.startPage(tApply.getPages(), tApply.getPagen());
            List<TApply> resultList=tApplyMapper.listApply(applyType,searchName,sortName,sortType);
            PageInfo pageInfo = new PageInfo(resultList);
            long total = pageInfo.getTotal();
            HashMap<String, Object> resultMap = new HashMap<>();
            resultMap.put("total",total);
            resultMap.put("data",resultList);
            return ResultDTO.businessSuccess("成功",resultMap);
        }catch (Exception e){
            return ResultDTO.bussinessFail("异常","异常"+e);
        }finally {
            PageHelper.clearPage();
        }
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultDTO applyToOrderAndUser(Integer applyId, Integer totailPrice, String priceDetail, String orderDescription,String userRemark) {
        try {
            //先修改为已转成订单
            TApply tApply = new TApply();
            tApply.setApplyId(applyId);
            tApply.setType("3");
            int i = tApplyMapper.updateByPrimaryKeySelective(tApply);
            if (i<1){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultDTO.bussinessFail("申请表类型修改失败",null);
            }

            //查出申请表
            TApply tApply1 = tApplyMapper.selectByPrimaryKey(applyId);
            if (tApply1==null){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultDTO.bussinessFail("未查出申请表数据",null);
            }

            //申请表中的数据赋值给工单表   外加价格 价格详情 订单详情
            TOrder tOrder = new TOrder();
            tOrder.setApplyId(tApply1.getApplyId());
            tOrder.setUserId(tApply1.getMobile());
            tOrder.setAddress(tApply1.getAddress());
            tOrder.setSetMeal(tApply1.getSetMeal());
            tOrder.setUserName(tApply1.getUsername());
            tOrder.setSex(tApply1.getSex());
            tOrder.seteMail(tApply1.geteMail());
            tOrder.setPortrait(tApply1.getPortrait());
            tOrder.setWechat(tApply1.getWechat());

            tOrder.setDescription(orderDescription);
            tOrder.setTotailPrice(totailPrice);
            tOrder.setPriceDetail(priceDetail);

            int i1 = tOrderMapper.insertSelective(tOrder);
            if (i1<1){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultDTO.bussinessFail("新增工单失败",null);
            }


            //插入生成用户
            TUser tUser = new TUser();
            tUser.setUserid(tApply1.getMobile());
            tUser.setUsername(tApply1.getUsername());
            tUser.setAddress(tApply1.getAddress());
            tUser.setSex(tApply1.getSex());
            tUser.seteMail(tApply1.geteMail());
            tUser.setPortrait(tApply1.getPortrait());
            tUser.setWechat(tApply1.getWechat());
            tUser.setComment(userRemark);

            int i2 = tUserMapper.insertSelective(tUser);
            if (i2<1){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                ResultDTO.bussinessFail("新增客户失败",null);
            }

            return ResultDTO.businessSuccess("新增工单用户成功,请及时修改密码",null);


        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultDTO.bussinessFail("异常","异常"+e);
        }
    }


//    pagen  pages   type 0为待施工  1为施工中 2为已完成未付款 3为已付款完工   9为取消订单   搜索orderId模糊查询  排序
    @Override
    public ResultDTO listOrder(TOrder tOrder,String type,String orderId,String sortName,Integer sortType) {
        try {
            PageHelper.startPage(tOrder.getPages(), tOrder.getPagen());
            //查询出工单 和 套餐
            List<TOrder> orderList=tOrderMapper.listOrder(type,orderId,sortName,sortType);
            PageInfo pageInfo = new PageInfo(orderList);
            long total = pageInfo.getTotal();
            PageHelper.clearPage();

            ArrayList<Integer> integers = new ArrayList<>();
            for (TOrder torder1:
            orderList) {
                integers.add(torder1.getOrderId());
            }


            //查询出 这些工单的  支付情况 明细list  和3个字段  总支付多少钱 多少笔  总价与当前付款占比
            List<TPayInfo> tPayInfos = tPayInfoMapper.listPay(null, null, null, null, integers);


            //工单的记录列表
            List<TProgress> tProgresses = tProgressMapper.listDiary(null, null, null, null, integers);




            ArrayList<TOrder> resultArrayList = new ArrayList<>();
            for (TOrder newTOrder:
            orderList) {
                ArrayList<Object> objects = new ArrayList<>();
                ArrayList<Object> objects2 = new ArrayList<>();
                newTOrder.setProcessList(objects);
                newTOrder.setPayInfoList(objects2);
                Integer orderId1 = newTOrder.getOrderId();

                for (TPayInfo tPayInfo:
                tPayInfos) {
                    if (tPayInfo.getOrderId().equals(orderId1)){
                        newTOrder.getPayInfoList().add(tPayInfo);
                    }
                }


                for (TProgress tProgress:
                tProgresses) {
                    if (tProgress.getOrderId().equals(orderId1)){
                        newTOrder.getProcessList().add(tProgress);
                    }
                }

                resultArrayList.add(newTOrder);
            }


            HashMap<String, Object> resultMap = new HashMap<>();
            resultMap.put("data",resultArrayList);
            resultMap.put("total",total);
            return ResultDTO.businessSuccess("成功",resultMap);
        }catch (Exception e){
            return ResultDTO.bussinessFail("异常","异常"+e);
        }finally {
            PageHelper.clearPage();
        }

    }







    @Override
    public ResultDTO modifiyOrder(TOrder tOrder) {
        try {
            int i = tOrderMapper.updateByPrimaryKeySelective(tOrder);
            if (i<1){
                return ResultDTO.bussinessFail("修改工单失败",null);
            }
            return ResultDTO.businessSuccess("修改工单成功",null);

        }catch (Exception e){
            return ResultDTO.bussinessFail("异常","异常"+e);
        }
    }











    @Override
    public ResultDTO payAdd(TPayInfo tPayInfo) {
        try {
            int i = tPayInfoMapper.insertSelective(tPayInfo);
            if (i<1){
                return ResultDTO.bussinessFail("新增支付失败",null);
            }

            return ResultDTO.businessSuccess("新增成功",null);


        }catch (Exception e){
            return ResultDTO.bussinessFail("异常","异常"+e);
        }
    }


    @Override
    public ResultDTO payDel(TPayInfo tPayInfo) {
        try {
            int i = tPayInfoMapper.deleteByPrimaryKey(tPayInfo.getPayInfoId());
            if (i<1){
                return ResultDTO.bussinessFail("删除支付信息失败",null);
            }

            return ResultDTO.businessSuccess("删除成功",null);


        }catch (Exception e){
            return ResultDTO.bussinessFail("异常","异常"+e);
        }
    }


    @Override
    public ResultDTO payModify(TPayInfo tPayInfo) {
        try {
            int i = tPayInfoMapper.updateByPrimaryKeySelective(tPayInfo);
            if (i<1){
                return ResultDTO.bussinessFail("修改支付信息失败",null);
            }

            return ResultDTO.businessSuccess("修改成功",null);

        }catch (Exception e){
            return ResultDTO.bussinessFail("异常","异常"+e);
        }
    }


    @Override
    public ResultDTO listPay(String payInfoId, String orderId, String sortName, Integer sortType, TPayInfo tPayInfo) {
        try {
            PageHelper.startPage(tPayInfo.getPages(), tPayInfo.getPagen());
            List<TPayInfo> resultList=tPayInfoMapper.listPay(payInfoId,orderId,sortName,sortType,null);

            PageInfo pageInfo = new PageInfo(resultList);
            long total = pageInfo.getTotal();
            HashMap<String, Object> resultMap = new HashMap<>();
            resultMap.put("total",total);
            resultMap.put("data",resultList);
            return ResultDTO.businessSuccess("成功",resultMap);
        }catch (Exception e){
            return ResultDTO.bussinessFail("异常","异常"+e);
        }finally {
            PageHelper.clearPage();
        }
    }


    @Override
    public ResultDTO mealAdd(TMeal tMeal) {
        try {
            int i = tMealMapper.insertSelective(tMeal);
            if (i<1){
                return ResultDTO.bussinessFail("新增套餐失败",null);
            }

            return ResultDTO.businessSuccess("新增成功",null);
        }catch (Exception e){
            return ResultDTO.bussinessFail("异常","异常"+e);
        }
    }


    @Override
    public ResultDTO mealDel(Integer mid) {
        try {
            int i = tMealMapper.deleteByPrimaryKey(String.valueOf(mid));
            if (i<1){
                return ResultDTO.bussinessFail("删除套餐",null);
            }

            return ResultDTO.businessSuccess("删除成功",null);
        }catch (Exception e){
            return ResultDTO.bussinessFail("异常","异常"+e);
        }
    }



    @Override
    public ResultDTO mealModify(TMeal tMeal) {
        try {
            int i = tMealMapper.updateByPrimaryKeySelective(tMeal);
            if (i<1){
                return ResultDTO.bussinessFail("修改套餐失败",null);
            }

            return ResultDTO.businessSuccess("修改成功",null);
        }catch (Exception e){
            return ResultDTO.bussinessFail("异常","异常"+e);
        }
    }


    @Override
    public ResultDTO listMeal(String mid, String status, String sortName, Integer sortType, TPayInfo tPayInfo) {
        try {
            PageHelper.startPage(tPayInfo.getPages(), tPayInfo.getPagen());
            List<TMeal> resultList=tMealMapper.listMeal(mid,status,sortName,sortType);
            PageInfo pageInfo = new PageInfo(resultList);
            long total = pageInfo.getTotal();
            HashMap<String, Object> resultMap = new HashMap<>();
            resultMap.put("total",total);
            resultMap.put("data",resultList);
            return ResultDTO.businessSuccess("成功",resultMap);
        }catch (Exception e){
            return ResultDTO.bussinessFail("异常","异常"+e);
        }finally {
            PageHelper.clearPage();
        }
    }


    @Override
    public ResultDTO menuAdd(TMenu tMenu) {
        try {
            int i = tMenuMapper.insertSelective(tMenu);
            if (i<1){
                return ResultDTO.bussinessFail("新增菜单失败",null);
            }

            return ResultDTO.businessSuccess("新增成功",null);
        }catch (Exception e){
            return ResultDTO.bussinessFail("异常","异常"+e);
        }
    }


    @Override
    public ResultDTO menuDel(Integer menuId) {
        try {
            int i = tMenuMapper.deleteByPrimaryKey(menuId);
            if (i<1){
                return ResultDTO.bussinessFail("删除菜单",null);
            }

            return ResultDTO.businessSuccess("删除成功",null);
        }catch (Exception e){
            return ResultDTO.bussinessFail("异常","异常"+e);
        }
    }


    @Override
    public ResultDTO menuModify(TMenu tMenu) {
        try {
            int i = tMenuMapper.updateByPrimaryKeySelective(tMenu);
            if (i<1){
                return ResultDTO.bussinessFail("修改菜单失败",null);
            }

            return ResultDTO.businessSuccess("修改成功",null);
        }catch (Exception e){
            return ResultDTO.bussinessFail("异常","异常"+e);
        }
    }


    @Override
    public ResultDTO listMenu(Integer menuId, String status, String sortName, Integer sortType, TPayInfo tPayInfo) {
        try {
            PageHelper.startPage(tPayInfo.getPages(), tPayInfo.getPagen());
            List<TMenu> resultList=tMenuMapper.listMenuSort(menuId,status,sortName,sortType);
            PageInfo pageInfo = new PageInfo(resultList);
            long total = pageInfo.getTotal();
            HashMap<String, Object> resultMap = new HashMap<>();
            resultMap.put("total",total);
            resultMap.put("data",resultList);
            return ResultDTO.businessSuccess("成功",resultMap);
        }catch (Exception e){
            return ResultDTO.bussinessFail("异常","异常"+e);
        }finally {
            PageHelper.clearPage();
        }
    }


    @Override
    public ResultDTO contentAdd(TContent tContent) {
        try {
            int i = tContentMapper.insertSelective(tContent);
            if (i<1){
                return ResultDTO.bussinessFail("新增内容失败",null);
            }

            return ResultDTO.businessSuccess("新增成功",null);
        }catch (Exception e){
            return ResultDTO.bussinessFail("异常","异常"+e);
        }
    }


    @Override
    public ResultDTO contentDel(Integer contentId) {
        try {
            int i = tContentMapper.deleteByPrimaryKey(contentId);
            if (i<1){
                return ResultDTO.bussinessFail("删除内容失败",null);
            }

            return ResultDTO.businessSuccess("删除成功",null);
        }catch (Exception e){
            return ResultDTO.bussinessFail("异常","异常"+e);
        }
    }


    @Override
    public ResultDTO contentModify(TContent tContent) {
        try {
            int i = tContentMapper.updateByPrimaryKeySelective(tContent);
            if (i<1){
                return ResultDTO.bussinessFail("修改内容失败",null);
            }

            return ResultDTO.businessSuccess("修改成功",null);
        }catch (Exception e){
            return ResultDTO.bussinessFail("异常","异常"+e);
        }
    }


    @Override
    public ResultDTO listContent(Integer contentId, String type, String status, String sortName, Integer sortType, TPayInfo tPayInfo) {
        try {
            PageHelper.startPage(tPayInfo.getPages(), tPayInfo.getPagen());
            List<TContent> resultList=tContentMapper.listContent(contentId,type,status,sortName,sortType);
            PageInfo pageInfo = new PageInfo(resultList);
            long total = pageInfo.getTotal();
            HashMap<String, Object> resultMap = new HashMap<>();
            resultMap.put("total",total);
            resultMap.put("data",resultList);
            return ResultDTO.businessSuccess("成功",resultMap);
        }catch (Exception e){
            return ResultDTO.bussinessFail("异常","异常"+e);
        }finally {
            PageHelper.clearPage();
        }
    }


    @Override
    public ResultDTO userAdd(TUser tUser) {
        try {
            int i = tUserMapper.insertSelective(tUser);
            if (i<1){
                return ResultDTO.bussinessFail("新增用户失败",null);
            }

            return ResultDTO.businessSuccess("新增成功",null);
        }catch (Exception e){
            return ResultDTO.bussinessFail("异常","异常"+e);
        }
    }


    @Override
    public ResultDTO userDel(Integer uid) {
        try {
            int i = tUserMapper.deleteByPrimaryKey(uid);
            if (i<1){
                return ResultDTO.bussinessFail("删除用户失败",null);
            }

            return ResultDTO.businessSuccess("删除成功",null);
        }catch (Exception e){
            return ResultDTO.bussinessFail("异常","异常"+e);
        }
    }


    @Override
    public ResultDTO userModify(TUser tUser) {
        try {
            int i = tUserMapper.updateByPrimaryKeySelective(tUser);
            if (i<1){
                return ResultDTO.bussinessFail("修改用户失败",null);
            }
            return ResultDTO.businessSuccess("修改成功",null);
        }catch (Exception e){
            return ResultDTO.bussinessFail("异常","异常"+e);
        }
    }


    @Override
    public ResultDTO listUser(Integer uid, String userid, String type, String status, String sortName, Integer sortType, TPayInfo tPayInfo) {
        try {
            PageHelper.startPage(tPayInfo.getPages(), tPayInfo.getPagen());
            List<TUser> resultList=tUserMapper.listUser(uid,userid,type,status,sortName,sortType);
            PageInfo pageInfo = new PageInfo(resultList);
            long total = pageInfo.getTotal();
            HashMap<String, Object> resultMap = new HashMap<>();
            resultMap.put("total",total);
            resultMap.put("data",resultList);
            return ResultDTO.businessSuccess("成功",resultMap);
        }catch (Exception e){
            return ResultDTO.bussinessFail("异常","异常"+e);
        }finally {
            PageHelper.clearPage();
        }
    }


    @Override
    public ResultDTO diaryAdd(TProgress tProgress) {
        try {
            int i = tProgressMapper.insertSelective(tProgress);
            if (i<1){
                return ResultDTO.bussinessFail("新增日记失败",null);
            }

            return ResultDTO.businessSuccess("新增成功",null);
        }catch (Exception e){
            return ResultDTO.bussinessFail("异常","异常"+e);
        }
    }

    @Override
    public ResultDTO diaryDel(Integer progressId) {
        try {
            int i = tProgressMapper.deleteByPrimaryKey(progressId);
            if (i<1){
                return ResultDTO.bussinessFail("删除日记失败",null);
            }

            return ResultDTO.businessSuccess("删除成功",null);
        }catch (Exception e){
            return ResultDTO.bussinessFail("异常","异常"+e);
        }
    }


    @Override
    public ResultDTO diaryModify(TProgress tProgress) {
        try {
            int i = tProgressMapper.updateByPrimaryKeySelective(tProgress);
            if (i<1){
                return ResultDTO.bussinessFail("修改日记失败",null);
            }
            return ResultDTO.businessSuccess("修改成功",null);
        }catch (Exception e){
            return ResultDTO.bussinessFail("异常","异常"+e);
        }
    }


    @Override
    public ResultDTO listDiary(Integer progressId, Integer orderId, String sortName, Integer sortType, TPayInfo tPayInfo) {
        try {
            PageHelper.startPage(tPayInfo.getPages(), tPayInfo.getPagen());
            List<TProgress> resultList=tProgressMapper.listDiary(progressId,orderId,sortName,sortType,null);
            PageInfo pageInfo = new PageInfo(resultList);
            long total = pageInfo.getTotal();
            HashMap<String, Object> resultMap = new HashMap<>();
            resultMap.put("total",total);
            resultMap.put("data",resultList);
            return ResultDTO.businessSuccess("成功",resultMap);
        }catch (Exception e){
            return ResultDTO.bussinessFail("异常","异常"+e);
        }finally {
            PageHelper.clearPage();
        }
    }


    @Override
    public ResultDTO dictAdd(TDict tDict) {
        try {
            int i = tDictMapper.insertSelective(tDict);
            if (i<1){
                return ResultDTO.bussinessFail("新增数据字典失败",null);
            }

            return ResultDTO.businessSuccess("新增成功",null);
        }catch (Exception e){
            return ResultDTO.bussinessFail("异常","异常"+e);
        }
    }


    @Override
    public ResultDTO dictDel(Integer dictId) {
        try {
            int i = tDictMapper.deleteByPrimaryKey(Long.valueOf(dictId));
            if (i<1){
                return ResultDTO.bussinessFail("删除数据字典失败",null);
            }

            return ResultDTO.businessSuccess("删除成功",null);
        }catch (Exception e){
            return ResultDTO.bussinessFail("异常","异常"+e);
        }
    }


    @Override
    public ResultDTO dictModify(TDict tDict) {
        try {
            int i = tDictMapper.updateByPrimaryKeySelective(tDict);
            if (i<1){
                return ResultDTO.bussinessFail("修改数据字典失败",null);
            }
            return ResultDTO.businessSuccess("修改成功",null);
        }catch (Exception e){
            return ResultDTO.bussinessFail("异常","异常"+e);
        }
    }


    @Override
    public ResultDTO listDict(String fieldName, String tableName, String sortName, Integer sortType, Integer pagen, Integer pages) {
        try {
            if (pagen==null){
                pagen=100000;
            }
            if (pages==null){
                pages=1;
            }

            PageHelper.startPage(pages,pagen);
            List<TDict> resultList=tDictMapper.listDict(fieldName,tableName,sortName,sortType);
            PageInfo pageInfo = new PageInfo(resultList);
            long total = pageInfo.getTotal();
            HashMap<String, Object> resultMap = new HashMap<>();
            resultMap.put("total",total);
            resultMap.put("data",resultList);
            return ResultDTO.businessSuccess("成功",resultMap);
        }catch (Exception e){
            return ResultDTO.bussinessFail("异常","异常"+e);
        }finally {
            PageHelper.clearPage();
        }
    }
}
