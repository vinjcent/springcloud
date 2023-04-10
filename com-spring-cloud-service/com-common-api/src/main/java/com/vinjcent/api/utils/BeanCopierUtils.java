package com.vinjcent.api.utils;

import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author vinjcent
 * @description cglib包中的bean拷贝(性能优于Spring当中的BeanUtils,底层使用字节码)
 * @since 2023/3/28 14:22
 */
public class BeanCopierUtils {


    /**
     * 创建一个map来存储BeanCopier缓存
     */
    private static final Map<String, BeanCopier> BEAN_COPIER_MAP = new ConcurrentHashMap<>();

    /**
     * 浅拷贝,我们可以直接传实例化的拷贝对象和被实例化的拷贝对象进行浅拷贝
     *
     * @param source 源对象
     * @param target 目标类
     */
    public static void copy(Object source, Object target) {
        // 获取当前两者转换map对应的key
        String key = getKey(source, target);
        BeanCopier beanCopier;
        // 判断键是否存在,不存在就将BeanCopier插入到map里,存在就直接获取
        if (!BEAN_COPIER_MAP.containsKey(key)) {
            // 参数1: 源对象类   参数2: 目标对象类   参数3: 是否使用自定义转换器
            beanCopier = BeanCopier.create(source.getClass(), target.getClass(), false);
            BEAN_COPIER_MAP.put(key, beanCopier);
        } else {
            beanCopier = BEAN_COPIER_MAP.get(key);
        }
        // 自定义转换器可在copy函数当中的第三个参数设置
        beanCopier.copy(source, target, null);
    }

    /**
     * 浅拷贝
     *
     * @param source 源对象
     * @param target 目标类
     * @param <T>    目标类型
     * @return 单个目标类
     */
    public static <T> T copy(Object source, Class<T> target) {
        // 如果源对象为空,结束
        if (source == null) {
            return null;
        }
        // 用来判断目标类型空指针异常
        Objects.requireNonNull(target);
        T result = null;
        try {
            result = target.newInstance();
            copy(source, result);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * List浅拷贝
     *
     * @param sources 源集合
     * @param target  目标类
     * @param <S>     源类型
     * @param <T>     目标类型
     * @return 目标类集合
     */
    public static <S, T> List<T> copyList(List<S> sources, Class<T> target) {
        // 用来判断目标类型空指针异常
        Objects.requireNonNull(target);
        return sources.stream().map(src -> copy(src, target)).collect(Collectors.toList());

    }

    /**
     * 自定义类型转换器
     *
     * @param source    源对象
     * @param target    目标类
     * @param converter 转换器
     */
    private static void copy(Object source, Object target, Converter converter) {
        if (!Objects.isNull(converter)) {
            BeanCopier beanCopier = BeanCopier.create(source.getClass(), target.getClass(), true);
            beanCopier.copy(source, target, converter);
        } else {
            copy(source, target);
        }
    }

    /**
     * 自定义类型转换器
     *
     * @param source    源对象
     * @param target    目标类
     * @param converter 转换器
     * @param <T>       目标类型
     * @return 单个目标类
     */
    public static <T> T copy(Object source, Class<T> target, Converter converter) {
        // 如果源对象为空,结束
        if (source == null) {
            return null;
        }
        // 用来判断目标类型空指针异常
        Objects.requireNonNull(target);
        T result = null;
        try {
            result = target.newInstance();
            copy(source, result, converter);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取Map中的Key
     *
     * @param source 源对象
     * @param target 目标类
     * @return 源对象与目标类名字的拼接
     */
    private static String getKey(Object source, Object target) {
        return source.getClass().getName() + "_" + target.getClass().getName();
    }

}
