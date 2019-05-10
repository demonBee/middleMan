package com.chinaunicom.sirbee.service.impl;

import com.chinaunicom.sirbee.dao.mapper.*;
import com.chinaunicom.sirbee.dao.model.*;
import com.chinaunicom.sirbee.dao.model.result.ResultDTO;
import com.chinaunicom.sirbee.service.WebService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
 * @date 2019/5/5 13:12
 * @Description
 */
@Service
public class WebServiceImpl implements WebService {
    @Autowired
    private TMenuMapper tMenuMapper;
    @Autowired
    private TApplyMapper tApplyMapper;
    @Autowired
    private TContentMapper tContentMapper;



    @Override
    public ResultDTO menuList() {
        try {
            List<TMenu> tMenus = tMenuMapper.menuList();
            //循环修改格式
            ArrayList<Map<String, ArrayList<TMenu>>> arrayList = new ArrayList<>();

            for (int i = 0; i <tMenus.size(); i++) {
                TMenu tMenu = tMenus.get(i);
                String type = tMenu.getType();
                Boolean succeed =false;
                for (Map<String,ArrayList<TMenu>> map:
                        arrayList) {
                    if (null!=map&&map.keySet().iterator().next().equals(type)){
                        succeed=true;
                        ArrayList arrayList1 = map.get(type);
                        arrayList1.add(tMenu);
                    }
                }

                if (succeed==false){
                    HashMap<String, ArrayList<TMenu>> stringArrayListHashMap = new HashMap<String, ArrayList<TMenu>>();
                    ArrayList<TMenu> tMenus1 = new ArrayList<>();
                    tMenus1.add(tMenu);
                    stringArrayListHashMap.put(type,tMenus1);
                    arrayList.add(stringArrayListHashMap);
                }
            }

            return ResultDTO.businessSuccess("成功",arrayList);

        }catch (Exception e){
            return ResultDTO.bussinessFail("异常","异常"+e);
        }

    }


    @Override
    public ResultDTO insertToApply(TApply tApply) {
        try {
            int i = tApplyMapper.insertSelective(tApply);
            if (i<1){
                return ResultDTO.bussinessFail("申请提交失败",null);
            }
            return ResultDTO.businessSuccess("成功",null);

        }catch (Exception e){
            return ResultDTO.bussinessFail("异常","异常"+e);
        }
    }


    @Override
    public ResultDTO menuContentList(String type,Integer pagen,Integer pages) {
        try {
            if (null==pagen||0==pagen){
                pagen=10;
            }
            if (null==pages||0==pages){
                pages=1;
            }

            Page<Object> objects = PageHelper.startPage(pages, pagen);
            List<TContent> listInfo=tContentMapper.listForInfo(type);
            PageInfo<TContent> aa = new PageInfo<>(listInfo);
            long total = aa.getTotal();

            HashMap<String, Object> resultMap = new HashMap<>();
            resultMap.put("total",total);
            resultMap.put("data",listInfo);
            return ResultDTO.businessSuccess("成功",resultMap);
        }catch (Exception e){
            return ResultDTO.bussinessFail("异常","异常"+e);
        }finally {
            PageHelper.clearPage();
        }
    }



}
