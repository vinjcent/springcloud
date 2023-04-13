package com.vinjcent.config.mybatis.handler;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

/**
 * @author vinjcent
 * @description 自定义映射器
 * @since 2023/3/22 11:48
 */
public interface CustomizedMapper<T> extends BaseMapper<T> {

    /**
     * 批量插入
     *
     * @param list 数据集合
     * @return 记录行数
     */
    int insertBatch(@Param("list") Collection<T> list);

}
