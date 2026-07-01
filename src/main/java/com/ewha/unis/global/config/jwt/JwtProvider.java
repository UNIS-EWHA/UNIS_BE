package com.ewha.unis.global.config.jwt;

import com.ewha.unis.member.domain.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtProvider {
    private final SecretKey key;
    private final long accessTokenValidity;

    public JwtProvider(JwtProperties props) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(props.secret()));
        this.accessTokenValidity = props.accessTokenValidity();
    }

    public String createAccessToken(Long memberId, Role role) {
        Date now = new Date();

        return Jwts.builder()
                .subject(String.valueOf(memberId))
                .claim("role", role.name())
                .issuedAt(now)
                .expiration(new Date(now.getTime() + accessTokenValidity))
                .signWith(key)
                .compact();
    }

    public String createRefreshToken(Long memberId, long validityMs) {
        Date now = new Date();
        return Jwts.builder()
                .subject(String.valueOf(memberId))
                .issuedAt(now)
                .expiration(new Date(now.getTime() + validityMs))
                .signWith(key)
                .compact();
    }

    public Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Long getMemberId(String token) {
        return Long.valueOf(parseClaims(token).getSubject());
    }

    public Role getRole(String token) {
        return Role.valueOf(parseClaims(token).get("role", String.class));
    }

    public long getRemainingSeconds(String token) {
        long remain = parseClaims(token).getExpiration().getTime() - System.currentTimeMillis();
        return Math.max(remain / 1000, 0);
    }
}
