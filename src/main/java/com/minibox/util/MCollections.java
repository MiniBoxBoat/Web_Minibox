package com.minibox.util;

import java.util.ArrayList;
import java.util.List;

public class MCollections {
    public static <T> List<T> list(T[] array){
        ArrayList<T> list = new ArrayList();
        for (T t : array) {
            list.add(t);
        }
        return list;
    }
}
