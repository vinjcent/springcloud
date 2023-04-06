package com.vinjcent.api.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author vinjcent
 * 请求注解
 */
@Retention(RetentionPolicy.RUNTIME)     // 保持这个注解持续的阶段
@Target({ElementType.METHOD})   // 注解的作用域
public @interface SystemLog {
    // 操作方法的名称(可以在controller中使用,作为注解的参数)
    String businessName();
}
