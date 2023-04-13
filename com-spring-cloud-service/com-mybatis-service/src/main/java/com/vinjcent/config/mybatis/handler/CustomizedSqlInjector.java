package com.vinjcent.config.mybatis.handler;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.AbstractSqlInjector;
import com.baomidou.mybatisplus.core.injector.methods.*;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author vinjcent
 * @description 自定义SQL注入器
 * @since 2023/3/22 13:57
 */
@Component
public class CustomizedSqlInjector extends AbstractSqlInjector {
    /**
     * 如果只需增加方法,保留mybatis plus自带方法
     * 可以先获取super.getMethodList()再添加add
     *
     * @param mapperClass 映射类型
     * @param tableInfo   表信息
     * @return 自带映射方法
     */
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
        return Stream.of(
                new Insert(),
                new Delete(),
                new DeleteByMap(),
                new Update(),
                new SelectByMap(),
                new SelectCount(),
                new SelectMaps(),
                new SelectMapsPage(),
                new SelectObjs(),
                new SelectList(),
                new SelectPage(),
                new InsertBatch()
        ).collect(Collectors.toList());
    }
}
