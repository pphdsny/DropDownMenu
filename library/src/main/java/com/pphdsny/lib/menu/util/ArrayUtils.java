package com.pphdsny.lib.menu.util;

import java.util.List;

/**
 * Created by wangpeng on 2018/8/6.
 */
public class ArrayUtils {

    public static boolean isEmptyList(List list) {
        return list == null || list.size() == 0;
    }

    public static int[] toIntArr(List<Integer> list) {
        if (isEmptyList(list)) {
            return null;
        } else {
            int[] retArr = new int[list.size()];

            for (int i = 0; i < list.size(); ++i) {
                retArr[i] = (Integer) list.get(i);
            }

            return retArr;
        }
    }

    public static String[] toStringArr(List<String> list) {
        if (isEmptyList(list)) {
            return null;
        } else {
            String[] retArr = new String[list.size()];

            for (int i = 0; i < list.size(); ++i) {
                retArr[i] = (String) list.get(i);
            }

            return retArr;
        }
    }

}
