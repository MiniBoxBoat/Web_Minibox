package com.minibox.util;

import java.io.PrintWriter;

/**
 * @author MEI
 */
public class Print {

    private static PrintWriter printWriter = new PrintWriter(System.out, true);

    public static void printnb(String str){
        printWriter.println(str);
    }

}
