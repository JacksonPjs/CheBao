package com.chebao.bean;

/**
 * 创建日期：2018/5/12 on 14:45
 * 描述:借款人信息
 * 作者:jackson Administrator
 */

import java.math.BigDecimal;
import java.util.Date;

/** 借款人信息 **/
public class TPersonBorrower {
    /****/
    private Long id;

    /** 借款人姓名 **/
    private String borrowerName;

    /** 借款人身份证 **/
    private String borrowerIdCard;

    /** 借款人手机号 **/
    private String borrowerCellPhone;

    /** 借款人出生日期 **/
    private Date birthDate;

    /** 借款人性别(1,男，2女) **/
    private Integer sex;

    /** 年龄 **/
    private Integer age;

    /** 是否结婚（0，否，1，是，2，未知） **/
    private Integer maritalStatus;

    /** 是否有孩子（0，否，1，是，2，未知） **/
    private Integer childrenStatus;

    /** 户籍省份 **/
    private String houseRegisteProvince;

    /** 户籍城市 **/
    private String houseRegisteCity;

    /** 月收入水平 **/
    private String monthlyIncomeLevel;

    /** 是否有房（0，否，1，是，2，未知） **/
    private Integer houseStatus;

    /** 是否有车（0，否，1，是，2，未知） **/
    private Integer carStatus;

    private BigDecimal borrowmoney;

    private int borrowtime;

    /** 创建时间 **/
    private long createTime;
    /**
     * 借款人介绍
     */
    private String borrowpersonImg;


    public BigDecimal getBorrowmoney() {
        return borrowmoney;
    }

    public void setBorrowmoney(BigDecimal borrowmoney) {
        this.borrowmoney = borrowmoney;
    }

    public int getBorrowtime() {
        return borrowtime;
    }

    public void setBorrowtime(int borrowtime) {
        this.borrowtime = borrowtime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    public String getBorrowerName() {
        return this.borrowerName;
    }

    public void setBorrowerIdCard(String borrowerIdCard) {
        this.borrowerIdCard = borrowerIdCard;
    }

    public String getBorrowerIdCard() {
        return this.borrowerIdCard;
    }

    public void setBorrowerCellPhone(String borrowerCellPhone) {
        this.borrowerCellPhone = borrowerCellPhone;
    }

    public String getBorrowerCellPhone() {
        return this.borrowerCellPhone;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setMaritalStatus(Integer maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Integer getMaritalStatus() {
        return this.maritalStatus;
    }

    public void setChildrenStatus(Integer childrenStatus) {
        this.childrenStatus = childrenStatus;
    }

    public Integer getChildrenStatus() {
        return this.childrenStatus;
    }

    public void setHouseRegisteProvince(String houseRegisteProvince) {
        this.houseRegisteProvince = houseRegisteProvince;
    }

    public String getHouseRegisteProvince() {
        return this.houseRegisteProvince;
    }

    public void setHouseRegisteCity(String houseRegisteCity) {
        this.houseRegisteCity = houseRegisteCity;
    }

    public String getHouseRegisteCity() {
        return this.houseRegisteCity;
    }

    public void setMonthlyIncomeLevel(String monthlyIncomeLevel) {
        this.monthlyIncomeLevel = monthlyIncomeLevel;
    }

    public String getMonthlyIncomeLevel() {
        return this.monthlyIncomeLevel;
    }

    public void setHouseStatus(Integer houseStatus) {
        this.houseStatus = houseStatus;
    }

    public Integer getHouseStatus() {
        return this.houseStatus;
    }

    public void setCarStatus(Integer carStatus) {
        this.carStatus = carStatus;
    }

    public Integer getCarStatus() {
        return this.carStatus;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getBorrowpersonImg() {
        return borrowpersonImg;
    }

    public void setBorrowpersonImg(String borrowpersonImg) {
        this.borrowpersonImg = borrowpersonImg;
    }

}
