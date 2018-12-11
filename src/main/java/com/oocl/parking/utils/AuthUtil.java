package com.oocl.parking.utils;

import com.oocl.parking.domain.Employee;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AuthUtil {
    static final long EXPIRATION_TIME = 432_000_000;
    static final String SECRET = "HEYHEY3SECRETisDoingSomethingIDunnoWhatItIsSecretly";
    static final String TOKEN_PREFIX = "Bearer";

    public static String generateJsonWebTokenForEmployee(Employee employee) {
        return Jwts.builder()
                .setClaims(createClaimsFromEmployee(employee))
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    private static Date generateExpirationDate(){
        return new Date(System.currentTimeMillis() + EXPIRATION_TIME);
    }

    private static Map<String, Object> createClaimsFromEmployee(Employee employee){
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("id", employee.getId());
        claims.put("username", employee.getAccountName());
        claims.put("role", employee.getRole());
        return claims;
    }
}
