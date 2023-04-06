package com.vinjcent.api.status.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vinjcent.api.status.enums.AppHttpCodeEnum;

import java.io.Serializable;

/**
 * @param <T> 数据参数类型
 * @author vinjcent
 * 自定义相应格式
 * <br>@JsonInclude只有不是为null的字段才会被序列化</br>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseResult<T> implements Serializable {

    /**
     * 响应状态码
     */
    private Integer code;
    /**
     * 响应信息
     */
    private String msg;
    /**
     * 响应携带数据
     */
    private T data;

    /**
     * 默认构造成功返回
     */
    public ResponseResult() {
        this.code = AppHttpCodeEnum.SUCCESS.getCode();
        this.msg = AppHttpCodeEnum.SUCCESS.getMsg();
    }

    /**
     * 响应码、响应携带数据构造
     *
     * @param code 响应码
     * @param data 响应携带数据
     */
    public ResponseResult(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    /**
     * 响应码、响应信息构造
     *
     * @param code 响应码
     * @param msg  响应信息
     */
    public ResponseResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 响应码、响应信息、响应携带数据构造
     *
     * @param code 响应码
     * @param msg  响应信息
     * @param data 响应携带数据
     */
    public ResponseResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 普通响应(自定义响应码、响应消息)
     *
     * @param code 响应码
     * @param msg  相应信息
     */
    public static <T> ResponseResult<T> commonResult(Integer code, String msg) {
        return new ResponseResult<>(code, msg, null);
    }

    /**
     * 成功响应
     */
    public static <T> ResponseResult<T> success() {
        return new ResponseResult<>();
    }

    /**
     * 成功响应
     *
     * @param msg  响应消息
     * @param data 响应数据
     */
    public static <T> ResponseResult<T> success(String msg, T data) {
        ResponseResult<T> result = setAppHttpCodeEnum(AppHttpCodeEnum.SUCCESS, msg);
        if (data != null) {
            result.setData(data);
        }
        return result;
    }

    /**
     * 成功响应
     *
     * @param data 响应数据
     */
    public static <T> ResponseResult<T> success(T data) {
        ResponseResult<T> result = setAppHttpCodeEnum(AppHttpCodeEnum.SUCCESS);
        if (data != null) {
            result.setData(data);
        }
        return result;
    }

    /**
     * 成功响应
     *
     * @param msg 响应消息
     */
    public static <T> ResponseResult<T> success(String msg) {
        return setAppHttpCodeEnum(AppHttpCodeEnum.SUCCESS, msg);
    }

    /**
     * 枚举封装
     *
     * @param codeEnum 响应枚举
     */
    public static <T> ResponseResult<T> setAppHttpCodeEnum(AppHttpCodeEnum codeEnum) {
        return commonResult(codeEnum.getCode(), codeEnum.getMsg());
    }

    /**
     * 枚举封装(自定义响应消息)
     *
     * @param codeEnum 响应枚举
     */
    public static <T> ResponseResult<T> setAppHttpCodeEnum(AppHttpCodeEnum codeEnum, String msg) {
        return commonResult(codeEnum.getCode(), msg);
    }


    /**
     * 失败响应
     */
    public static <T> ResponseResult<T> error() {
        return setAppHttpCodeEnum(AppHttpCodeEnum.FAILURE);
    }

    /**
     * 失败响应(自定义响应消息)
     *
     * @param msg 响应消息
     */
    public static <T> ResponseResult<T> error(String msg) {
        ResponseResult<T> result = error();
        result.setMsg(msg);
        return result;
    }

    /**
     * 失败响应(自定义响应码、响应消息)
     *
     * @param code 响应码
     * @param msg  响应消息
     */
    public static <T> ResponseResult<T> error(Integer code, String msg) {
        return new ResponseResult<>(code, msg);
    }

    /**
     * 失败响应
     *
     * @param codeEnum 枚举
     */
    public static <T> ResponseResult<T> error(AppHttpCodeEnum codeEnum) {
        return setAppHttpCodeEnum(codeEnum);
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
