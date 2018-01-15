package util;

import com.minibox.exception.ServerException;
import com.minibox.exception.TakenExpException;
import com.minibox.exception.TakenVirifyException;
import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static util.RedirectPrinter.*;
import static util.Print.*;

/**
 * @author MEI
 */
public class JavaWebTaken {
    private static Key getKeyInstance() {
        //We will sign our JavaWebToken with our ApiKey secret
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("miniBox");
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        return signingKey;
    }

    public static String createJavaWebToken(Map<String, Object> claims) {
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, getKeyInstance()).compact();
    }

    public static Map<String, Object> verifyJavaWebToken(String jwt) throws ServerException, TakenVirifyException {
        Map<String, Object> jwtClaims = null;
        try {
            jwtClaims = Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(jwt).getBody();
            long time = (long) jwtClaims.get("exp");
            if (System.currentTimeMillis() > time) {
                throw new TakenExpException();
            }
        } catch (ExpiredJwtException e) {
            throw new TakenVirifyException();
        } catch (UnsupportedJwtException e) {
            throw new TakenVirifyException();
        } catch (MalformedJwtException e) {
            throw new TakenVirifyException();
        } catch (SignatureException e) {
            throw new TakenVirifyException();
        } catch (IllegalArgumentException e) {
            throw new TakenVirifyException();
        } catch (TakenExpException e) {
            throw new TakenVirifyException();
        }
        return jwtClaims;
    }

    public static int getTakenUserId(String taken) throws ServerException, TakenVirifyException {
        Map<String,Object> jwtClaims = verifyJavaWebToken(taken);
        return (int) jwtClaims.get("userId");
    }

    private static String createJWT(String id) {

//The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

//We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("miniBox");
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claimsq
        long expMillis = nowMillis + 1000 * 60 * 60 * 24;
        JwtBuilder builder = Jwts.builder().setId(id).setExpiration(new Date(expMillis))
                .signWith(signatureAlgorithm, signingKey);

//if it has been specified, let's add the expiration

//Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }


    public static void main(String[] args) throws ServerException, TakenVirifyException {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", 111);
        long now = System.currentTimeMillis();
        map.put("exp", now);
        System.out.println(now);
        String jwt = createJavaWebToken(map);
        printnb(jwt);
    }
}
