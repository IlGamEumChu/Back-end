package com.ilgamumchu.demar.security.jwt;

import com.ilgamumchu.demar.common.ErrorMessage;
import com.ilgamumchu.demar.utils.exception.TokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class JwtTokenProvider {

    @Value("${spring.jwt.secretKey.access}")
    private String accessSecretKey;

    private final ZoneId KST = ZoneId.of("Asia/Seoul");

    public String generateAccessToken(Authentication authentication) {
        val encodedKey = encodeKey(accessSecretKey);
        val secretKeyBytes = DatatypeConverter.parseBase64Binary(encodedKey);
        val accessKey = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS256.getJcaName());

        val now = getCurrentTime();
        val expireTime = Date.from(now.plusHours(20).atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .setSubject(String.valueOf(authentication.getPrincipal()))
                .setHeader(createHeader())
                .setExpiration(expireTime)
                .signWith(accessKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateTokenExpiration(String token) {
        try {
            val claims = getClaimsFromToken(token);

            val now = getCurrentTime();
            val expireTime = claims.getExpiration().toInstant().atZone(KST).toLocalDateTime();
            if (expireTime.isBefore(now)) {
                throw new TokenException(ErrorMessage.EXPIRED_TOKEN.getName());
            }

            return true;
        } catch (ExpiredJwtException e) {
            throw new TokenException(ErrorMessage.EXPIRED_TOKEN.getName());
        } catch (SignatureException e) {
            throw new TokenException(ErrorMessage.INVALID_SIGNATURE.getName());
        }
    }

    public AdminAuthentication getAuthentication(String token) {
        return new AdminAuthentication(getId(token), null, null);
    }

    public Long getId(String token) {
        try {
            val claims = getClaimsFromToken(token);

            val now = getCurrentTime();
            val expireTime = claims.getExpiration().toInstant().atZone(KST).toLocalDateTime();
            if (expireTime.isBefore(now)) {
                throw new TokenException(ErrorMessage.EXPIRED_TOKEN.getName());
            }

            return Long.parseLong(claims.getSubject());
        } catch (ExpiredJwtException e) {
            throw new TokenException(ErrorMessage.EXPIRED_TOKEN.getName());
        } catch (SecurityException e) {
            throw new TokenException(ErrorMessage.INVALID_SIGNATURE.getName());
        }
    }

    private Claims getClaimsFromToken(String token) {
        val encodedKey = encodeKey(accessSecretKey);

        return Jwts.parserBuilder()
                .setSigningKey(encodedKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String resolveToken(HttpServletRequest request) {
        val headerAuth = request.getHeader("Authorization");
        return (StringUtils.hasText(headerAuth)) ? headerAuth : null;
    }

    private String encodeKey(String secretKey) {
        return Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    private LocalDateTime getCurrentTime() {
        return LocalDateTime.now(KST);
    }

    private Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();

        header.put("typ", "JWT");
        header.put("alg", "HS256");
        header.put("regDate", System.currentTimeMillis());

        return header;
    }
}