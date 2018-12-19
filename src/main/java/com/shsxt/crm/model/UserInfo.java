package com.shsxt.crm.model;

/**
 * Created by xlf on 2018/9/17.
 */
public class UserInfo {
    private String userIdStr;//加密的ID字符串
    private String userName;
    private String realName;

    public String getUserIdStr() {
        return userIdStr;
    }

    public void setUserIdStr(String userIdStr) {
        this.userIdStr = userIdStr;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
