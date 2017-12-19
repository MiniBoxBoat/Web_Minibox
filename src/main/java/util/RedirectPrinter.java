package util;

import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * @author MEI
 */
public class RedirectPrinter {

    public static void rPrint(String log){
        try {
            PrintStream ps = new PrintStream("F:\\log\\log_info.txt");
            System.setOut(ps);
            ps.append(log);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void rPrintError(String log){
        try {
            PrintStream ps = new PrintStream("F:\\log\\log_error.txt");
            System.setErr(ps);
            ps.append(log);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
