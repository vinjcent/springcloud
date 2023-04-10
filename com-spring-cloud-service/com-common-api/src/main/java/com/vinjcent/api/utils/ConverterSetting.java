package com.vinjcent.api.utils;

import org.springframework.cglib.core.Converter;

/**
 * @author vinjcent
 * @description bean拷贝转换器(不推荐使用, 尽量设计时保持类型一致)
 * @since 2023/3/28 15:06
 */
public class ConverterSetting implements Converter {

    /**
     * @param source  源对象值
     * @param target  目标类型
     * @param setFunc 实体类的set方法
     * @return
     */
    @Override
    public Object convert(Object source, Class target, Object setFunc) {
        if (source instanceof Enum) {
            return source;
        } else {
            return source.toString();
        }
    }
}
