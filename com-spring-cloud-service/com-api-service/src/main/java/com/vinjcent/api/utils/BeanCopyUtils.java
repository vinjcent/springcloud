package com.vinjcent.api.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author vinjcent
 * @description bean 拷贝工具类(底层使用反射)
 */
public class BeanCopyUtils {

    public BeanCopyUtils() {
    }

    /**
     * 单个对象拷贝
     *
     * @param source 源对象
     * @param target 目标类
     * @return 单个目标类
     */
    public static void copyBean(Object source, Object target) {
        // 如果源对象为空,结束
        if (source == null) {
            return;
        }
        // 用来判断目标类型空指针异常
        Objects.requireNonNull(target);
        // 实现属性copy
        BeanUtils.copyProperties(source, target);
    }

    /**
     * 单个对象拷贝
     *
     * @param source 源对象
     * @param target 目标类
     * @param <T>    目标类型
     * @return 单个目标类
     */
    public static <T> T copyBean(Object source, Class<T> target) {
        // 如果源对象为空,结束
        if (source == null) {
            return null;
        }
        // 用来判断目标类型空指针异常
        Objects.requireNonNull(target);
        // 创建目标对象
        T result = null;
        try {
            result = target.newInstance();
            // 实现属性copy
            copyBean(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 返回结果
        return result;
    }

    /**
     * 集合拷贝
     *
     * @param sources 源集合
     * @param target  目标类
     * @param <S>     源类型
     * @param <T>     目标类型
     * @return 目标类集合
     */
    public static <S, T> List<T> copyBeanList(List<S> sources, Class<T> target) {
        // 使用流去映射、收集
        return sources.stream()
                .map(source -> copyBean(source, target))
                .collect(Collectors.toList());
    }
}
