package com.minibox.util;

import java.util.ArrayList;
import java.util.List;

public class MCollections {

    /**
     * 把数组转换为ArrayList
     */
    public static <T> List<T> list(T[] array){
        ArrayList<T> list = new ArrayList();
        for (T t : array) {
            list.add(t);
        }
        return list;
    }
}
