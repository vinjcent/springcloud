package com.vinjcent.api.config.web;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vinjcent
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    /**
     * 全局配置 axios 跨域请求
     * addMapping：配置可以被跨域的路径,可以任意配置,可以具体到直接请求路径
     * allowedHeaders：允许所有的请求header访问，可以自定义设置任意请求头信息
     * allowedMethods：允许所有的请求方法访问该跨域资源服务器,如：POST、GET、PUT、DELETE等
     * allowCredentials：是否允许用户发送、处理 cookie
     * maxAge：预检请求的有效期,单位为秒.有效期内,不会重复发送预检请求
     * 当预检请求通过后,浏览器会发送真实请求到服务器.这就实现了跨域请求
     * allowedOrigins：允许所有的请求域名访问跨域资源,可以固定单条或者多条内容
     * 如：”<a href="http://www.xxx.com">...</a>“,只有该域名可以访问我们的跨域资源
     *
     * @param registry 注册
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String[] origins = {"*"};
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowCredentials(true)
                .maxAge(1800)
                .allowedOrigins(origins);
    }

    /**
     * 配置资源处理
     *
     * @param registry 注册参数
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/")
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/public/");
        /*
            解释:
            "/images/**": 相当于访问资源路径的别名
            "E:/images/": 存放本地文件的映射路径
        */
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:E:/images/");
        // WebMvcConfigurer.super.addResourceHandlers(registry);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(fastJsonHttpMessageConverter());
    }

    /**
     * 转换器
     *
     * @return Http消息转换器
     */
    @Bean
    public HttpMessageConverter<Object> fastJsonHttpMessageConverter() {
        // 1.定义一个Convert转换消息的对象
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        // 2.定义Fastjson对象并设定序列化规则
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");

        // 3.中文乱码解决方案
        List<MediaType> mediaTypes = new ArrayList<>();
        // 设定json格式且编码为UTF-8
        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastJsonHttpMessageConverter.setSupportedMediaTypes(mediaTypes);

        SerializeConfig.getGlobalInstance().put(Long.class, ToStringSerializer.instance);

        // 4.配置全局实例化
        fastJsonConfig.setSerializeConfig(SerializeConfig.globalInstance);

        // 5.为fastjson转化器配置序列化的配置
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);

        return fastJsonHttpMessageConverter;

    }

}
