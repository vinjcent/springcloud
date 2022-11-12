package com.vinjcent;

import java.util.BitSet;

public class Solution {
    public int[][] merge(int[][] intervals) {
        BitSet bitSet = new BitSet();
        int max = 0;
        for (int[] interval : intervals) {
            //  防止类似 [1,4] [5,5] 存在开始区间是结束区间的例子出错
            int temp = interval[1] * 2 + 1;
            // [start, end) 这些位设置为bool值
            bitSet.set(interval[0] * 2, temp, true);
            // 获取最大值
            max = temp >= max ? temp : max;
        }

        int index = 0, count = 0;
        while (index < max) {
            // 返回从index开始的第一个为true的位的索引
            int start = bitSet.nextSetBit(index);
            // 返回从start开始的第一个为false的位的索引
            int end = bitSet.nextClearBit(start);

            // 获取一个合并
            int[] item = {start / 2, (end - 1) / 2};
            intervals[count++] = item;

            // 从新更新枚举条件,为每次false的位置
            index = end;
        }
        // 根据count数量,从intervals被替换的前count个数组中获取二维数组
        int[][] ret = new int[count][2];
        for (int i = 0; i < count; i++) {
            ret[i] = intervals[i];
        }

        return ret;
    }
}
