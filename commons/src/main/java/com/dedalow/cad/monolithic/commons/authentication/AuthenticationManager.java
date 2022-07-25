package com.dedalow.cad.monolithic.commons.authentication;

import com.dedalow.cad.monolithic.commons.exception.CadException;
import com.dedalow.cad.monolithic.commons.util.JsonUtils;
import com.dedalow.cad.monolithic.commons.util.ObjectMapperUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.xml.bind.DatatypeConverter;
import org.json.JSONObject;

public class AuthenticationManager {

  private static String jwtSecret = "oIXlABJHdaZ94Za9sQXO";

  private static int jwtExpirationInMs = 604800000;

  public static Object executeDecodeToken(String token) throws CadException {
    return ObjectMapperUtil.readValue(decodeToken(token).toString(), Object.class);
  }

  private static JSONObject decodeToken(String token) throws CadException {
    try {
      return new JSONObject(
          Jwts.parser()
              .setSigningKey(DatatypeConverter.parseBase64Binary(jwtSecret))
              .parseClaimsJws(removeBearer(token))
              .getBody()
              .get("object")
              .toString());
    } catch (SignatureException
        | MalformedJwtException
        | ExpiredJwtException
        | UnsupportedJwtException
        | IllegalArgumentException e) {
      throw new CadException("Incorrect token");
    }
  }

  public static boolean executeValidateToken(String token) {
    try {
      decodeToken(token);
      return true;
    } catch (CadException e) {
      return false;
    }
  }

  public static String executeGenerateToken(Object object) throws CadException {
    return addBearer(generateToken(object));
  }

  public static String generateToken(Object object) throws CadException {

    Date expirationDate = new Date(new Date().getTime() + jwtExpirationInMs);

    Map<String, Object> tokenData = new HashMap<>();
    if (Objects.isNull(object)) {
      throw new CadException("One of the elements is null");
    }
    try {
      tokenData.put("object", JsonUtils.modelToJson(object).toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
    tokenData.put("create_date", new Date());

    JwtBuilder jwtBuilder = Jwts.builder();
    jwtBuilder.setClaims(tokenData);
    jwtBuilder.setExpiration(expirationDate);

    String token = jwtBuilder.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();

    return token;
  }

  public static String addBearer(String token) throws CadException {
    return (new StringBuilder()).append("Bearer ").append(token).toString();
  }

  public static String removeBearer(String token) throws CadException {
    return token.replaceFirst("Bearer ", "");
  }
}
