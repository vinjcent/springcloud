package com.ribbon;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author vinjcent
 * @description 订单负载均衡策略
 * @since 2023/4/12 13:45
 */
@Configuration
public class RibbonStrategy {

    @Bean("roundRobinRule")
    public IRule roundRobinRule() {
        return new RoundRobinRule();
    }
}
