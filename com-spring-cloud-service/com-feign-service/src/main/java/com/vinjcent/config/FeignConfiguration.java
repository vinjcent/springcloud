package com.vinjcent.config;

import com.vinjcent.interceptor.FeignInterceptor;
import feign.Logger;
import feign.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author vinjcent
 * @description Feign配置类
 * <br>
 * 全局配置直接使用@Configuration
 * 局部配置配合@FeignClient使用：configuration = FeignConfig.class
 * </br>
 * @since 2023/4/13 16:08
 */
@Configuration
public class FeignConfiguration {

    /**
     * <br>NONE【性能最佳，适用于生产】：不记录任何日志（默认值）</br>
     * <br>BASIC【适用于生产环境追踪问题】：仅记录请求方法、URL、响应状态代码以及执行时间。</br>
     * <br>HEADERS：记录BASIC级别的基础上，记录请求和响应的header。</br>
     * <br>FULL【比较适用于开发及测试环境定位问题】：记录请求和响应的header、body和元数据。</br>
     */
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    /**
     * 超时时间配置
     */
    @Bean
    public Request.Options options() {
        return new Request.Options(5000L, TimeUnit.MILLISECONDS, 10000L, TimeUnit.MILLISECONDS, true);
    }

    /**
     * 自定义拦截器
     */
    @Bean
    public FeignInterceptor feignInterceptor() {
        return new FeignInterceptor();
    }

}
