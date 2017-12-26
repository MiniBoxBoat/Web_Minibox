package com.minibox.constants;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public enum BoxSize {

    SMALL("小"),LARGE("大");

    String size;
    //这里只能是私有的构造函数，因为枚举类型不允许我们在外面再用枚举类型的构造器构造枚举类型
    BoxSize(String size){
        this.size = size;
    }

    public String size(){
        return size;
    }
}
