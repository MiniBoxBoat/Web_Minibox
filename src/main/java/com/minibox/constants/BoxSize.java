package com.minibox.constants;

public enum BoxSize {

    SMALL("小"),LARGE("大");

    String size;

    BoxSize(String size){
        this.size = size;
    }

    public String size(){
        return size;
    }
}
