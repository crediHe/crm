package com.shsxt.crm.model;

/**
 * Created by xlf on 2018/9/17.
 */
public class ResultInfo {
    private Integer code = 200;
    private String msg = "请求成功";
    private Object result;//传输其他信息

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

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
