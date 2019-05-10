package com.chinaunicom.sirbee.dao.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class TOrder {
    private Integer orderId;  //工单id  必穿

    private Integer applyId;  //申请表id

    private String userId;  //用户id

    private Integer type;  //0为待施工  1为施工中 2为已完成未付款 3为已付款完工   9为取消订单

    private String address;  //地址   暂时全部文字

    private String description;  //备注

    private String setMeal;  //套餐  对应套餐表

    private String userName;  //用户名

    private String sex;  //0为男性  1为女性

    private String eMail;  //电子邮箱

    private String portrait;  //头像

    private String wechat;  //wechat

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date modifyTime;

    private Integer totailPrice;  //工单总价

    private String priceDetail;  //价格详情

    private String dealManager;  //施工方负责人

    private String dealMen;  //逗号分开 该单子由哪些个体施工


    private Integer pagen=10;

    private Integer pages=1;

    private TMeal tMeal;


    private List processList;

    private List payInfoList;


    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getSetMeal() {
        return setMeal;
    }

    public void setSetMeal(String setMeal) {
        this.setMeal = setMeal == null ? null : setMeal.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail == null ? null : eMail.trim();
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait == null ? null : portrait.trim();
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat == null ? null : wechat.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getTotailPrice() {
        return totailPrice;
    }

    public void setTotailPrice(Integer totailPrice) {
        this.totailPrice = totailPrice;
    }

    public String getPriceDetail() {
        return priceDetail;
    }

    public void setPriceDetail(String priceDetail) {
        this.priceDetail = priceDetail == null ? null : priceDetail.trim();
    }

    public String getDealManager() {
        return dealManager;
    }

    public void setDealManager(String dealManager) {
        this.dealManager = dealManager;
    }

    public String getDealMen() {
        return dealMen;
    }

    public void setDealMen(String dealMen) {
        this.dealMen = dealMen;
    }

    public Integer getPagen() {
        return pagen;
    }

    public void setPagen(Integer pagen) {
        this.pagen = pagen;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public List getProcessList() {
        return processList;
    }

    public void setProcessList(List processList) {
        this.processList = processList;
    }

    public List getPayInfoList() {
        return payInfoList;
    }

    public void setPayInfoList(List payInfoList) {
        this.payInfoList = payInfoList;
    }

    public TMeal gettMeal() {
        return tMeal;
    }

    public void settMeal(TMeal tMeal) {
        this.tMeal = tMeal;
    }
}