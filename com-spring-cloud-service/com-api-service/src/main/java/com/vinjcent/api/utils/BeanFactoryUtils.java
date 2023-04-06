package com.vinjcent.api.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author vinjcent
 * @description BeanFactoryPostProcessor和BeanPostProcessor这两个接口都是初始化bean时对外暴露的入口之一，和Aware类似
 * <br>
 * ConfigurableBeanFactory允许Bean工厂自定义配置，如是单例模式还是原型模式。这样拆分再合并就可以按照接口的约束和规范来进行扩展操作
 * </br>
 * @since 2023/3/30 14:35
 */
@Component
public final class BeanFactoryUtils implements BeanFactoryPostProcessor {

    /**
     * ConfigurableListableBeanFactory是Spring中非常重要的容器，继承于ListableBeanFactory、AutowireCapableBeanFactory和ConfigurableBeanFactory
     */
    private static ConfigurableListableBeanFactory beanFactory;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanFactoryUtils.beanFactory = beanFactory;
    }

    /**
     * 提供在上下文中调用对象的方法,获取bean
     *
     * @param name bean名称
     * @return 返回bean
     */
    public static Object getBean(String name) {
        return beanFactory.getBean(name);
    }

    /**
     * 提供在上下文中调用对象的方法,获取bean
     *
     * @param clazz bean类型
     * @return 返回bean
     */
    public static <T> T getBean(Class<T> clazz) {
        return beanFactory.getBean(clazz);
    }

    /**
     * 提供在上下文中调用对象的方法,获取bean
     *
     * @param name  bean名称
     * @param clazz bean类型
     * @return 返回bean
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return beanFactory.getBean(name, clazz);
    }


    /**
     * bean工厂当中是否存在某个bean
     *
     * @param name bean名称
     * @return 是否存在某个bean
     */
    public static boolean containsBean(String name) {
        return beanFactory.containsBean(name);
    }

    /**
     * 根据类型获取所有的bean
     *
     * @param clazz bean类型
     * @param <T>   bean泛型
     * @return bean类型下的所有bean
     */
    public static <T> List<T> getBeans(Class<T> clazz) {
        Map<String, T> map = new HashMap<>(16);
        String[] beanNames = beanFactory.getBeanNamesForType(clazz);
        if (!CollectionUtils.isEmpty(Arrays.asList(beanNames))) {
            for (String name : beanNames) {
                T bean = getBean(name, clazz);
                map.put(name, bean);
            }
        }
        return new ArrayList<>(map.values());
    }

    /**
     * 获取当前环境
     *
     * @return 环境
     */
    public static String getActiveProfile() {
        String active = null;
        String[] profiles = beanFactory.getBean(Environment.class).getActiveProfiles();
        if (!CollectionUtils.isEmpty(Arrays.asList(profiles)) && profiles.length > 0) {
            active = profiles[0];
        }
        return active;
    }

    /**
     * 根据键获取对应值
     *
     * @param key 键
     * @return key对应的值
     */
    public static String getPropertyByKey(String key) {
        return beanFactory.getBean(Environment.class).getProperty(key);
    }
}
