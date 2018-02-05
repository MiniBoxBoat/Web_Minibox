package com.minibox.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

/**
 * @author MEI
 */
public class RedirectPrinter {

    public static void rPrint(String log){
        try {
            PrintStream ps = new PrintStream("/home/log.log");
            System.setOut(ps);
            ps.append(log);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void rPrintError(String log){
        try {
            File file = new File("/home/log.log");
            if (!file.exists()){
                file.createNewFile();
            }
            PrintStream ps = new PrintStream(file);
            System.setErr(ps);
            ps.println(log);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
