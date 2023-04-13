package com.vinjcent.constants;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;

/**
 * @author vinjcent
 * @description 订单feign接口名称
 * <br>@ConditionalOnExpression是否满足环境配置当中应用名称以特定字符结尾</br>
 * @since 2023/4/11 15:55
 */
@Configuration
@ConditionalOnExpression("#{!environment['spring.application.name'].endsWith('" + OrderApiConfig.SERVICE_NAME + "')}")
public class OrderApiConfig {

    /**
     * 服务名
     */
    public static final String SERVICE_NAME = "com-order-service";

    /**
     * 服务名占位符
     */
    public static final String PLACE_HOLD_SERVICE_NAME = "${e-commerce.service." + SERVICE_NAME + ":" + SERVICE_NAME + "}";

}
