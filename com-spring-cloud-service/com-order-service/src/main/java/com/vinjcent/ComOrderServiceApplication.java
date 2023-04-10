package com.vinjcent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author vinjcent
 */
@RefreshScope
@EnableDiscoveryClient
@SpringBootApplication
public class ComOrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComOrderServiceApplication.class, args);
    }

}
