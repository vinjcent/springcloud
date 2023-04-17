package com.vinjcent.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author vinjcent
 * @description Feign拦截器
 * @since 2023/4/13 16:22
 */
public class FeignInterceptor implements RequestInterceptor {

    /**
     * 记录当前类的信息
     */
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void apply(RequestTemplate template) {
        // TODO
        // requestTemplate...
        logger.info("自定义Feign拦截器");
    }
}
