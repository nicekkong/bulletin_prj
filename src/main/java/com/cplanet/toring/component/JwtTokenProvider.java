package com.cplanet.toring.component;

import com.cplanet.toring.domain.security.MemberPrincipal;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${spring.profiles}")
    String profile;

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    public String generateToken(Authentication authentication) {

        MemberPrincipal userPrincipal = (MemberPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder().setSubject(Long.toString(userPrincipal.getId()))
                            .setIssuedAt(new Date())
                            .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    public Long getMemberIdFromJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }

    public String getJwtFromRequest(HttpServletRequest request) {

        logger.info("profile ::: {} ", profile);
        String bearerToken = null;
        if(profile.equals("local")) {
            // local 환경인 경우는 Header에서 token값을 가져오고,
            bearerToken = request.getHeader("Authorization");
            if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
                bearerToken = bearerToken.substring(7, bearerToken.length());
            }

        } else {
            // 서버 환경인 경우 request에서 Cookie를 통해 token을 가져온다.
            Cookie[] cookies = request.getCookies();
            if (cookies == null) {
                return null;
            }

            for (Cookie c : cookies) {
                if ("access_token".equals(c.getName())) {
                    bearerToken = c.getValue();
                }
            }
        }
        logger.info("bearerToken ::: {}", bearerToken);

        return bearerToken;
    }
}
