package com.vinjcent.api.status.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author vinjcent
 */
public enum AppHttpCodeEnum {

    // 全局响应码
    SUCCESS(200, "操作成功"),
    FAILURE(0, "操作失败"),
    UNAUTHENTICATED(401, "需要登陆后操作"),
    UNAUTHORIZED(403, "无权限操作"),
    INVALID_PARAM(7777, "非法参数"),
    SERVER_ERROR(500, "抱歉,系统繁忙,请稍后重试"),
    AUTHENTICATION_EXPIRED(501, "身份已过期"),
    INVALID_TOKEN(502, "无效令牌"),
    FILE_ERROR(503, "文件类型错误,请上传.jpg/.png文件"),
    LOGIN_ERROR(504, "用户名或密码错误"),
    USERNAME_ERROR(505, "用户名不符合规范"),
    PASSWORD_ERROR(506, "密码不符合规范"),
    NICKNAME_ERROR(507, "昵称不符合规范"),
    EMAIL_ERROR(508, "邮箱不符合规范"),
    USERNAME_EXIST(509, "用户名已存在"),
    LOGIN_SUCCESS(510, "登陆成功");

    /**
     * 状态响应码
     */
    private final Integer code;
    /**
     * 响应码信息
     */
    private final String msg;

    private static final Map<Integer, AppHttpCodeEnum> APP_HTTP_ENUM_MAP = new HashMap<>();

    static {
        for (AppHttpCodeEnum item : AppHttpCodeEnum.values()) {
            APP_HTTP_ENUM_MAP.put(item.code, item);
        }
    }

    /**
     * 根据响应码获取枚举
     *
     * @param value 响应码
     * @return 请求枚举
     */
    public static AppHttpCodeEnum getByValue(Integer value) {
        return APP_HTTP_ENUM_MAP.get(value);
    }


    /**
     * 构造方法
     *
     * @param code 响应码
     * @param msg  响应信息
     */
    AppHttpCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    /**
     * 用于获取code和对应的message
     */
    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

