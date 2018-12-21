package com.shsxt.crm.dto;

import com.shsxt.crm.po.User;

/**
 * Created by xlf on 2018/9/20.
 */
public class UserDto extends User {

    private String roleName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
