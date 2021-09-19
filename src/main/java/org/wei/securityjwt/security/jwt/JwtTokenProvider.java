package org.wei.securityjwt.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.wei.securityjwt.service.UserService;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private String secretKey;

    @Autowired
    JwtProperties jwtProperties;
    @Autowired
    UserService userService;

    /**
     * 初始化 secretKey
     */
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 生成 token
     */
    public String createToken(String username, Collection<? extends GrantedAuthority> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", roles);

        Date date = new Date();
        Date expTime = new Date(date.getTime() + jwtProperties.getExpTime());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(date)
                .setExpiration(expTime)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * 获取 username
     */
    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * 获取请求头里的 token
     */
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * 校验 token
     */
    public Boolean validateToken(String token) {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        return claimsJws.getBody().getExpiration().before(new Date());
    }
}
