package com.vinjcent.api.valid;

/**
 * @author vinjcent
 * <p>属性数据的表达式接口,定义了相关的正则表达式和提示文本</p>
 *
 * <p>使用接口主要是为了便于定义常量,并利于相关类实现,以简化在类中使用常量的语法</p>
 */
public interface UserRegisterRegExpression extends RegExpression {

    String MESSAGE_NULL_USERNAME = "用户名不能为空";
    String MESSAGE_NULL_PASSWORD = "密码不能为空";
    String MESSAGE_NULL_NICKNAME = "昵称不能为空";
    String MESSAGE_NULL_EMAIL = "邮箱不能为空";

}
