package com.vinjcent;

import com.ribbon.RibbonStrategy;
import com.vinjcent.constants.OrderApiConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author vinjcent
 */
@EnableFeignClients(basePackages = {
        "com.vinjcent.endpoint"
})
@RibbonClients(
        value = {
                @RibbonClient(name = OrderApiConfig.PLACE_HOLD_SERVICE_NAME, configuration = RibbonStrategy.class)
        }
)
@RefreshScope
@EnableDiscoveryClient
@SpringBootApplication
public class ComFeignServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComFeignServiceApplication.class, args);
    }

}
