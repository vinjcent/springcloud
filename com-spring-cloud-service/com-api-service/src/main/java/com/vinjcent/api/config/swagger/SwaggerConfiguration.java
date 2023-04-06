package com.vinjcent.api.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author vinjcent
 */
@EnableSwagger2
@Configuration
public class SwaggerConfiguration {

    /**
     * 配置Swagger信息==apiInfo
     *
     * @return ApiInfo
     */
    private ApiInfo apiInfo() {

        // 作者信息
        Contact contact = new Contact("vinjcent", "https://blog.csdn.net/Wei_Naijia", "2573552788@qq.com");

        return new ApiInfoBuilder()
                .title("Vinjcent's Api")
                .description("电商后台管理系统")
                .version("v1.1.0")
                .contact(contact)
                .build();
    }

    /**
     * 配置了Swagger的Docket的bean实例
     *
     * @return Docket
     */
    @Bean
    public Docket docket() {
        /*
         // RequestHandlerSelectors : 配置要扫描接口的方式
         // basePackage() : 指定要扫描的包
         // any() : 扫描全部
         // none() : 不扫描
         // withClassAnnotation : 扫描类上的注解,参数是一个注解的反射对象
         // withMethodAnnotation : 扫描方法上的注解
         // paths() : 过滤什么路径
         // groupName() : 分组名
         // enable() : 是否启动Swagger,如果为false,则Swagger不再浏览器中访问
         */
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                //.paths(PathSelectors.ant("/vinjcent/**"))
                .build();
    }


}
