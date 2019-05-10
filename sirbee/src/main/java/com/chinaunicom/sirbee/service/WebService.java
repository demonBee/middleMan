package com.chinaunicom.sirbee.service;

import com.chinaunicom.sirbee.dao.model.TApply;
import com.chinaunicom.sirbee.dao.model.result.ResultDTO;

/**
 * @author hjf
 * @date 2019/5/5 13:11
 * @Description
 */
public interface WebService {
    ResultDTO menuList();

    ResultDTO insertToApply(TApply tApply);

    ResultDTO menuContentList(String type,Integer pagen,Integer pages);

}
