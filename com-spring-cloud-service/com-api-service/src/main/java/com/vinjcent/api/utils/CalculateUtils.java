package com.vinjcent.api.utils;

import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author vinjcent
 * @description 计算工具类
 * @since 2023/4/3 17:33
 */
public class CalculateUtils {

    /**
     * 计算集合总和
     *
     * @param collection 需要计算的集合
     * @param getFunc    Function(T,R)有参有返回型函数: T为入参类型,R为返回结果
     * @param <T>        接收的参数类型
     * @return 返回计算结果
     */
    public static <T> BigDecimal sumBigDecimal(Collection<T> collection, Function<T, BigDecimal> getFunc) {
        return sumBigDecimal(collection, getFunc, t -> true, false, null);
    }

    /**
     * 计算集合总和
     *
     * @param collection 数据集合
     * @param getFunc    Function(T,R)有参有返回型函数: T为入参类型,R为返回结果
     * @param predicate  断言T类型对象中的属性,返回的是boolean类型
     * @param isScale    是否使用精度
     * @param scale      精度值
     * @param <T>        接收的参数类型
     * @return 返回计算结果
     */
    public static <T> BigDecimal sumBigDecimal(Collection<T> collection, Function<T, BigDecimal> getFunc, Predicate<T> predicate, boolean isScale, Integer scale) {
        if (CollectionUtils.isEmpty(collection) || Objects.isNull(getFunc)) {
            return BigDecimal.ZERO;
        }
        BigDecimal decimal = collection.stream().filter(predicate).map(getFunc).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
        if (isScale) {
            return decimal.setScale(scale, RoundingMode.HALF_UP);
        } else {
            return decimal;
        }
    }

}
