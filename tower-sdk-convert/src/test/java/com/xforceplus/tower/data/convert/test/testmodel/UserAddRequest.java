package com.xforceplus.tower.data.convert.test.testmodel;

import javax.validation.Valid;
import java.util.List;

/**
 * null
 */
public class UserAddRequest {
    private String accountNum = null;

    private String userCode = null;

    private String userNumber = null;

    private String userName = null;

    private String userPhone = null;

    private Integer userSex = null;

    private String userEmailAddr = null;

    private String userIdCard = null;

    private String userWorkTel = null;

    private String contactAddr = null;

//    @ApiModelProperty(value = "开票终端 ")
//    private List<String> ticketOpeningTerminal = null;
//
//    @ApiModelProperty(value = "打印设备")
//    private List<String> printingEquipment = null;
//
//    @ApiModelProperty(value = "发票类型 c：普票；s：专票；cs：专票和普票 ce：普电票")
//    private List<String> invoiceType = null;
//
//    @ApiModelProperty(value = "业务扩展属性")
//    private String businessExtensionAttribute = null;


    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Integer getUserSex() {
        return userSex;
    }

    public void setUserSex(Integer userSex) {
        this.userSex = userSex;
    }

    public String getUserEmailAddr() {
        return userEmailAddr;
    }

    public void setUserEmailAddr(String userEmailAddr) {
        this.userEmailAddr = userEmailAddr;
    }

    public String getUserIdCard() {
        return userIdCard;
    }

    public void setUserIdCard(String userIdCard) {
        this.userIdCard = userIdCard;
    }

    public String getUserWorkTel() {
        return userWorkTel;
    }

    public void setUserWorkTel(String userWorkTel) {
        this.userWorkTel = userWorkTel;
    }

    public String getContactAddr() {
        return contactAddr;
    }

    public void setContactAddr(String contactAddr) {
        this.contactAddr = contactAddr;
    }

    @Override
    public String toString() {
        return "UserAddRequest{" +
                "accountNum='" + accountNum + '\'' +
                ", userCode='" + userCode + '\'' +
                ", userNumber='" + userNumber + '\'' +
                ", userName='" + userName + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", userSex=" + userSex +
                ", userEmailAddr='" + userEmailAddr + '\'' +
                ", userIdCard='" + userIdCard + '\'' +
                ", userWorkTel='" + userWorkTel + '\'' +
                ", contactAddr='" + contactAddr + '\'' +
                '}';
    }
}
