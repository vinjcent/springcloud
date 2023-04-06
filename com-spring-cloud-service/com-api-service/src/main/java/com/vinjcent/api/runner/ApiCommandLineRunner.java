package com.vinjcent.api.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author vinjcent
 * @description 程序启动时, 运行该类中的run方法
 * @since 2023/3/27 16:22
 */
@Slf4j
@Component
public class ApiCommandLineRunner implements ApplicationRunner {

    private final Environment env;

    public ApiCommandLineRunner(Environment env) {
        this.env = env;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String host = "";
        try {
            host = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.error(e.getMessage(), e);
        }
        String port = env.getProperty("server.port");
        String servletPath = env.getProperty("spring.mvc.servlet.path");
        log.info("\n----------------------------启动信息---------------------------------------\n\t"
                        + "Application '{}' is running! Access URLs:\n\t"
                        + "Local: \t\thttp://localhost:{}\n\t"
                        + "External: \thttp://{}:{}\n\t"
                        + "Swagger: \thttp://{}:{}/swagger-ui.html\n\t"
                        + "Doc: \t\thttp://{}:{}/doc.html\n"
                        + "-----------------------------release list---------------------------------------\n\t"
                ,
                env.getProperty("spring.application.name"),
                env.getProperty("server.port") + servletPath,
                host,
                port + servletPath,
                host,
                port + servletPath,
                host,
                port + servletPath);
    }

}
