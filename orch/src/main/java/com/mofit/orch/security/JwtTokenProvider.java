package com.mofit.orch.security;

import com.mofit.mainmofitapiservice.models.Permission;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import static java.util.stream.Collectors.toList;

@Slf4j
@Component
public class JwtTokenProvider {

    // TODO: Extract this in a config-server somewhere
    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length}")
    private long validityInMilliseconds;

    private final MyUserDetails myUserDetails;

    @Autowired
    public JwtTokenProvider(@Lazy MyUserDetails myUserDetails) {
        this.myUserDetails = myUserDetails;
    }

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String username, List<Permission> permissions, String userType, Integer userId) {

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("auth", permissions.stream().map(s -> new SimpleGrantedAuthority(
            s.getAuthority())).collect(toList()));
        claims.put("userType", userType);
        claims.put("userId", userId);
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }

    Authentication getAuthentication(String username) {
        UserDetails userDetails = myUserDetails.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, "",
                                                       userDetails.getAuthorities());
    }

    String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    String getUsername(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody()
                .getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED,
                                               "Expired or invalid JWT token",
                                               e.getMessage().getBytes(), Charset.defaultCharset());
        }
    }

}
