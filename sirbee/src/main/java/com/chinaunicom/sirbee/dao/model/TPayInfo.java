package com.chinaunicom.sirbee.dao.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class TPayInfo {
    private Integer payInfoId;   //主键

    private Integer orderId;   //工单id

    private Integer payInfoNumber;   //银行支付凭证

    private String payNumber;   //支付金额

    private String payAccount;   //付款账户

    private String payUserId;   //付款的用户

    private String receiveAccount;   //收款账户

    private String comment;    //支付的留言

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    private Integer readed;  //0未读  1已读



    private Integer pagen=10;
    private Integer pages=1;



    public Integer getPayInfoId() {
        return payInfoId;
    }

    public void setPayInfoId(Integer payInfoId) {
        this.payInfoId = payInfoId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getPayInfoNumber() {
        return payInfoNumber;
    }

    public void setPayInfoNumber(Integer payInfoNumber) {
        this.payInfoNumber = payInfoNumber;
    }

    public String getPayNumber() {
        return payNumber;
    }

    public void setPayNumber(String payNumber) {
        this.payNumber = payNumber == null ? null : payNumber.trim();
    }

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount == null ? null : payAccount.trim();
    }

    public String getPayUserId() {
        return payUserId;
    }

    public void setPayUserId(String payUserId) {
        this.payUserId = payUserId == null ? null : payUserId.trim();
    }

    public String getReceiveAccount() {
        return receiveAccount;
    }

    public void setReceiveAccount(String receiveAccount) {
        this.receiveAccount = receiveAccount == null ? null : receiveAccount.trim();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getReaded() {
        return readed;
    }

    public void setReaded(Integer readed) {
        this.readed = readed;
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
}