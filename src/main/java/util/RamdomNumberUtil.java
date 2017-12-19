package util;

public class RamdomNumberUtil {
    public static String makeCode(){
        StringBuffer code = new StringBuffer();
        for (int i =0; i<=5; i++){
            int a =  (int)Math.floor(Math.random()*10);
            code.append(String.valueOf(a));
        }
        return code.toString();
    }

}
