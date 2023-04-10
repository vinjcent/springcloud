package com.vinjcent.api.exception;


import com.vinjcent.api.status.enums.AppHttpCodeEnum;

/**
 * @author vinjcent
 * 自定义系统异常处理
 */
public class SystemException extends RuntimeException {

    private int code;
    private String msg;

    public SystemException(AppHttpCodeEnum codeEnum) {
        super(codeEnum.getMsg());
        this.code = codeEnum.getCode();
        this.msg = codeEnum.getMsg();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
