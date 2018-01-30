package util;

public class TestUtil {

    public static String test(){
        StringBuffer code = new StringBuffer();
        for (int i =0; i<=5; i++){
            int a =  (int)Math.floor(Math.random()*10);
            code.append(a);
        }
        return code.toString();
    }

    public static void testStr(String s){
        String[] strings = s.split("/");
        String str = strings[(strings.length-1)];
        for (String string : strings) {
            System.out.println(string);
        }
    }

    public static void main(String[] args){
        testStr("http://box.jay86.com:8080/minibox/image/4256-106.jpg");
    }
}
