package com.vinjcent.api.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vinjcent
 */
public class ArrayUtils {

    /**
     * 返回list1有,list2没有的值
     *
     * @param list1 集合1
     * @param list2 集合2
     * @param <T>   集合数据类型
     * @return 返回list1有, list2没有的数据
     */
    public static <T> List<T> preCompare(List<T> list1, List<T> list2) {
        List<T> list = new ArrayList<>();
        list1.forEach(l -> {
            if (!list2.contains(l)) {
                list.add(l);
            }
        });
        return list;
    }

    /**
     * 返回list2有,list1没有的值
     *
     * @param list1 集合1
     * @param list2 集合2
     * @param <T>   集合数据类型
     * @return 返回list2有, list1没有的数据
     */
    public static <T> List<T> postCompare(List<T> list1, List<T> list2) {
        List<T> list = new ArrayList<>();
        list2.forEach(l -> {
            if (!list1.contains(l)) {
                list.add(l);
            }
        });
        return list;
    }
}
