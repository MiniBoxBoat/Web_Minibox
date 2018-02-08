package com.minibox.service.util;

import com.minibox.exception.TokenVerifyException;
import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author MEI
 */
public class JavaWebToken {

    public static String TAKEN_USER_ID = "userId";

    private static Key getKeyInstance() {
        //We will sign our JavaWebToken with our ApiKey secret
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("miniBox");
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        return signingKey;
    }

    public static String createJavaWebToken(int userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(TAKEN_USER_ID, userId);
        Date expireDate = new Date(System.currentTimeMillis() + 24*60*60*1000);
        return Jwts.builder().setClaims(claims).setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, getKeyInstance()).compact();
    }

    public static Map<String, Object> verifyJavaWebToken(String jwt) throws TokenVerifyException {
        Map<String, Object> jwtClaims = null;
        try {
            jwtClaims = Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(jwt).getBody();
        } catch (ExpiredJwtException e) {
            throw new TokenVerifyException();
        } catch (UnsupportedJwtException e) {
            throw new TokenVerifyException();
        } catch (MalformedJwtException e) {
            throw new TokenVerifyException();
        } catch (SignatureException e) {
            throw new TokenVerifyException();
        } catch (IllegalArgumentException e) {
            throw new TokenVerifyException();
        }
        return jwtClaims;
    }

    public static int getUserIdAndVerifyTakenFromTaken(String taken){
        Map<String, Object> map = verifyJavaWebToken(taken);
        int userId = (int) map.get("userId");
        return userId;
    }

    public boolean isTokenTrue(String taken){
        try {
            verifyJavaWebToken(taken);
        }catch (TokenVerifyException e) {
            return false;
        }
        return true;
    }
}
