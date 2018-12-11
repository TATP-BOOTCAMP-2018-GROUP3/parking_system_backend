package com.oocl.parking.utils;

import com.oocl.parking.domain.Employee;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

    private static Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    public static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (token != null) {
            Claims claims = getClaimsFromToken(token);

            String user = claims.get("username", String.class);

            List<GrantedAuthority> authorities =  AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_" + claims.get("role"));

            return user != null ?
                    new UsernamePasswordAuthenticationToken(user, null, authorities) :
                    null;
        }
        return null;
    }
}
