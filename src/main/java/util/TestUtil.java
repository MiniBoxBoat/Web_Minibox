package util;

import org.springframework.cglib.core.Local;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TestUtil {

    public static String test(){
        StringBuffer code = new StringBuffer();
        for (int i =0; i<=5; i++){
            int a =  (int)Math.floor(Math.random()*10);
            code.append(a);
        }
        return code.toString();
    }

    public static void main(String[] args){
        LocalDateTime time = LocalDateTime.parse("2017-10-5 12:25", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        System.out.println(time.getYear());
        System.out.println(time.getMinute());
/*        LocalDateTime time = LocalDateTime.now();
        System.out.println(time.toString());*/


    }
}
