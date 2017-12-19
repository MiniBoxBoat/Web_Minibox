package util;

import com.minibox.exception.ServerException;
import com.minibox.exception.TakenVirifyException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static util.JavaWebTaken.verifyJavaWebToken;

/**
 * @author MEI
 */
public class AuthUtil {
    private static Map<String, Object> getClientLoginInfo(HttpServletRequest request) throws Exception {
        Map<String, Object> r = new HashMap<>();
        String sessionId = request.getHeader("sessionId");
        if (sessionId != null) {
            r = decodeSession(sessionId);
            return r;
        }
        throw new Exception("session解析错误");
    }

    public static int getUserId(HttpServletRequest request) throws Exception {
        return  Integer.valueOf((Integer)getClientLoginInfo(request).get("userId"));
    }

    /**
     * session解密
     */
    public static Map<String, Object> decodeSession(String sessionId) throws ServerException, TakenVirifyException {
            return verifyJavaWebToken(sessionId);
    }
}