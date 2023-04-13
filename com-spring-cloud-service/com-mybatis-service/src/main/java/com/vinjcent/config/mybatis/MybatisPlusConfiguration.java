package com.vinjcent.config.mybatis;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author vinjcent
 */
@EnableTransactionManagement
@Configuration
public class MybatisPlusConfiguration {

    /**
     * 注册乐观锁插件,在spring启动时回去扫描MybatisPlusAutoConfiguration,将该拦截器注册
     * @return  拦截器
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {

        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 配置分页拦截器
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));

        return interceptor;
    }
}



