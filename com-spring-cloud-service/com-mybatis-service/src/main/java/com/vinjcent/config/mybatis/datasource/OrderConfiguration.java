package com.vinjcent.config.mybatis.datasource;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.vinjcent.config.mybatis.handler.CustomizedSqlInjector;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

/**
 * @author vinjcent
 * @description 订单数据层配置类
 * @since 2023/3/22 11:21
 */
@Configuration
@PropertySource(value = {"classpath:mybatis/datasource/mybatis-${spring.profiles.active}.properties"})
@MapperScan(basePackages = {"com.vinjcent.mapper.order"}, sqlSessionFactoryRef = "orderSqlSessionFactory", sqlSessionTemplateRef = "orderTransactionTemplate")
public class OrderConfiguration {

    /**
     * mybatis分页拦截器
     */
    private final MybatisPlusInterceptor paginationInterceptor;

    /**
     * SQL注入器
     */
    private final CustomizedSqlInjector sqlInjector;

    @Autowired
    public OrderConfiguration(MybatisPlusInterceptor paginationInterceptor, CustomizedSqlInjector sqlInjector) {
        this.paginationInterceptor = paginationInterceptor;
        this.sqlInjector = sqlInjector;
    }


    /**
     * 配置相应模块的数据源
     * 注意前缀格式
     */
    @Bean(name = "orderDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.order")
    public DataSource orderDatasource() {
        // 底层会自动拿到spring.datasource中的配置,创建一个DruidDataSource
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * SqlSessionFactory(ibatis会话工厂)
     *
     * @param dataSource 数据源
     */
    @Bean("orderSqlSessionFactory")
    public MybatisSqlSessionFactoryBean orderSqlSessionFactory(@Qualifier("orderDataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        /* 配置数据源 */
        sqlSessionFactory.setDataSource(dataSource);

        /* 配置mapper映射文件 */
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath*:mybatis/mapper/order/*.xml"));

        /* 全局配置(自定义注入器) */
        GlobalConfig globalConfig = GlobalConfigUtils.defaults();
        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
        globalConfig.setDbConfig(dbConfig);
        globalConfig.setSqlInjector(sqlInjector);
        sqlSessionFactory.setGlobalConfig(globalConfig);

        /* 主库设置sql控制台打印 */
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setLogImpl(StdOutImpl.class);
        sqlSessionFactory.setConfiguration(configuration);

        // 设置 MyBatis-Plus 分页插件
        Interceptor[] plugins = {paginationInterceptor};
        sqlSessionFactory.setPlugins(plugins);

        /* 实体类映射前缀 */
        sqlSessionFactory.setTypeAliasesPackage("com.vinjcent.domain.entity");
        return sqlSessionFactory;
    }

    /**
     * TransactionManager(事务管理者)
     */
    @Bean("orderTransactionManager")
    public DataSourceTransactionManager orderTransactionManager(@Qualifier("orderDataSource") DataSource orderDataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(orderDataSource);
        return transactionManager;
    }

    /**
     * TransactionTemplate(事务模板)
     */
    @Bean("orderTransactionTemplate")
    public TransactionTemplate orderTransactionTemplate(@Qualifier("orderTransactionManager") DataSourceTransactionManager orderTransactionManager) {
        return new TransactionTemplate(orderTransactionManager);
    }
}
