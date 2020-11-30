package com.DanceBytes.mybatis.bean;

/**
 * @author 孟享广
 * @date 2020-11-30 10:38 上午
 * @description
 *      希望数据库保存的是100,200这些状态码，而不是默认0,1或者枚举的名
 */
public enum  EmpStatus {
    LOGIN(100, "用户登陆"), LOGOUT(200, "用户登出"), REMOVE(300, ("用户不存在"));
    Integer code;
    String msg;

    EmpStatus(Integer code, String msg) {
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

    //按照状态码返回枚举对象
    public static EmpStatus getEmpStatusByCode(Integer code) {
        switch (code) {
            case 100:
                return LOGIN;
            case 200:
                return LOGOUT;
            case 300:
                return REMOVE;
            default:
                return LOGOUT;
        }
    }
}
