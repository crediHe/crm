package com.shsxt.crm.exceptions;

public class LoginException extends  RuntimeException {
    private Integer code= 300;
    private String msg="用户未登陆";

    public LoginException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public LoginException(Integer code) {
        super("用户未登陆");
        this.code = code;
    }

    public LoginException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
