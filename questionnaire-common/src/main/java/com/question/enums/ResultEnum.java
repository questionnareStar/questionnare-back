package com.question.enums;

/**
 * 规范接口返回值内容
 * @author 问卷星球团队
 * @version 1.0
 * @date 2021/7/24 18:54
 */
public enum ResultEnum {

    SUCCESS(20000, "成功"),

    LOGIN_ERROR(20001, "请登录"),

    ROLE_ERROR(20002, "权限不足"),

    NO_REGISTER(20004, "请先注册"),

    RUNTIME_ERROR(50002, "运行异常"),

    ERROR(50000, "异常");

    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
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
