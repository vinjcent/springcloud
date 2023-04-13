package com.vinjcent.config.mybatis.handler;


import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author vinjcent
 */
@Slf4j
@Component
public class CustomizedMateObjectHandler implements MetaObjectHandler {

    /**
     * 插入时的填充策略
     *
     * @param metaObject 原对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");

        /**
         default MetaObjectHandler
         setFieldValByName(String fieldName,	//属性字段名
         Object fieldVal,	//给属性字段赋值
         MetaObject metaObject)	//使用的元对象metaObject
         */
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);


    }

    /**
     * 更新时的填充策略
     *
     * @param metaObject 源对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        // 输出消息
        log.info("start insert fill ....");
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }


}



