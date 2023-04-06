package com.vinjcent.api.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author vinjcent
 * 用来获取springboot创建好的上下文
 */
@Component
public class ApplicationContextUtils implements ApplicationContextAware {

    /**
     * 当前的应用上下文
     */
    private static ApplicationContext applicationContext;

    /**
     * 将创建好的上下文以参数的形式传递给这个类
     *
     * @param applicationContext the ApplicationContext object to be used by this object
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtils.applicationContext = applicationContext;
    }

    /**
     * 提供在上下文中调用对象的方法,获取bean
     *
     * @param name bean名称
     * @return 返回bean
     */
    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    /**
     * 提供在上下文中调用对象的方法,获取bean
     *
     * @param clazz bean类型
     * @return 返回bean
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    /**
     * 提供在上下文中调用对象的方法,获取bean
     *
     * @param beanName bean名称
     * @param clazz    bean类型
     * @return 返回bean
     */
    public static <T> T getBean(String beanName, Class<T> clazz) {
        return applicationContext.getBean(beanName, clazz);
    }

}
