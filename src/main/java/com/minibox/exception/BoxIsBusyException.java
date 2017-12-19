package com.minibox.exception;

public class BoxIsBusyException extends Exception {
    private static String msg = "箱子正在被使用";

    public BoxIsBusyException(){
        super(msg);
    }
}
